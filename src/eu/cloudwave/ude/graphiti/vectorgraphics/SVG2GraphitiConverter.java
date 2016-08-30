package eu.cloudwave.ude.graphiti.vectorgraphics;

import java.io.InputStream;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IGaCreateService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;

import eu.coudwave.ude.graphiti.vectorgraphics.impl.SVG2GraphitiConverterImpl;

public abstract class SVG2GraphitiConverter {
	
	private static SVG2GraphitiConverter instance;
	public static SVG2GraphitiConverter getInstance(){
		if(instance==null)instance=new SVG2GraphitiConverterImpl();
		return instance;
	}
	
	public abstract void load(String key,InputStream in);
	public abstract GraphicsAlgorithm[] get(String key, Shape parent, IGaCreateService gaCreateService, IPeCreateService peCreateService, int width, int height);
	public abstract void resize(String key, Shape parent, int width, int height);
}
