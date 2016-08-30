package eu.coudwave.ude.graphiti.vectorgraphics.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IGaCreateService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.w3c.dom.Element;

import eu.coudwave.ude.graphiti.vectorgraphics.converter.IConverter;

public class PathConverter implements IConverter {
	private double x=0,y=0;
	private double preloadWidth=0;
	private double preloadHeight=0;
	private List<Double> pointlist=new LinkedList<Double>();
	
	
	private void doM(String... points){
		x=Double.valueOf(points[0]);
		y=Double.valueOf(points[1]);
	}
	
	private void doC(String... points){
		double x1=x;
		double y1=y;
		double x2=Double.valueOf(points[0]);
		double y2=Double.valueOf(points[1]);
		double x3=Double.valueOf(points[2]);
		double y3=Double.valueOf(points[3]);
		double x4=Double.valueOf(points[4]);
		double y4=Double.valueOf(points[5]);
		double x5=x1;
		double y5=y1;
		double x6=x2;
		double y6=y2;
		double x7=x5;
		double y7=y5;
		//directions
		int d=500;
		double dx1=(x2-x1)/d;
		double dy1=(y2-y1)/d;
		double dx2=(x3-x2)/d;
		double dy2=(y3-y2)/d;
		double dx3=(x4-x3)/d;
		double dy3=(y4-y3)/d;
		
		pointlist.add(x7);
		pointlist.add(y7);
		for(int i=1;i<d+1;i++){
			x1+=dx1;
			y1+=dy1;
			x2+=dx2;
			y2+=dy2;
			x3+=dx3;
			y3+=dy3;
			//directions
			x5=x1+(x2-x1)/d*i;
			y5=y1+(y2-y1)/d*i;
			x6=x2+(x3-x2)/d*i;
			y6=y2+(y3-y2)/d*i;
			x7=x5+(x6-x5)/d*i;
			y7=y5+(y6-y5)/d*i;
			pointlist.add(x7);
			pointlist.add(y7);
		
		}
		x=(int) x7;
		y=(int) y7;
	}
	
	private void calcPreloadDimension(){
		System.out.println(pointlist.size());
		for(int i=0;i<pointlist.size();i+=2){
			if(pointlist.get(i)>preloadWidth)preloadWidth=pointlist.get(i);
			if(pointlist.get(i+1)>preloadHeight)preloadHeight=pointlist.get(i+1);
		}
	}

	@Override
	public synchronized void load(StartElement startElement) {
		String[] strarray=startElement.getAttributeByName(new QName("d")).getValue().split(" ");
		//String[] strarray=element.getAttribute("d").split(" ");
		for(int i=0;i<strarray.length;i++){
			if(strarray[i].equals("M")){
				doM(strarray[i+1],strarray[i+2]);
				i=i+2;
			}else if(strarray[i].equals("C")){
				doC(strarray[i+1],strarray[i+2],strarray[i+3],strarray[i+4],
						strarray[i+5],strarray[i+6]);
				i=i+6;
			}else{
				break;
			}
		}
		calcPreloadDimension();
		System.out.println(this.preloadWidth);
		System.out.println(this.preloadHeight);
	}
	

	@Override
	public synchronized GraphicsAlgorithm transform(Shape shape, IGaCreateService gaCreateService,
			IPeCreateService peCreateService, int width, int height) {
		int[] points=new int[pointlist.size()];
		double xmultiplier=((double)width)/((double)preloadWidth);
        double ymultiplier=((double)height)/((double)preloadHeight);
		for(int i=0;i<pointlist.size();i+=2){
			points[i]=(int) (pointlist.get(i)*xmultiplier);
			points[i+1]=(int) (pointlist.get(i+1)*ymultiplier);
		}
		Polygon p=gaCreateService.createPolygon(shape, points);
		return p;
	}

	@Override
	public void resize(Shape shape, int width, int height) {
		Polygon p=(Polygon) shape.getGraphicsAlgorithm();
		double xmultiplier=((double)width)/((double)preloadWidth);
        double ymultiplier=((double)height)/((double)preloadHeight);
        for(int i=0;i<p.getPoints().size();i++){
        	int newx=(int) (pointlist.get(i*2)*xmultiplier);
        	int newy=(int) (pointlist.get(i*2+1)*ymultiplier);
        	p.getPoints().get(i).setX(newx);
        	p.getPoints().get(i).setY(newy);
        }
	}

}
