package eu.coudwave.ude.graphiti.vectorgraphics.impl;

import java.io.InputStream;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IGaCreateService;
import org.eclipse.graphiti.services.IPeCreateService;

import eu.cloudwave.ude.graphiti.vectorgraphics.IAccessMonitor;
import eu.cloudwave.ude.graphiti.vectorgraphics.SVG2GraphitiConverter;
import eu.coudwave.ude.graphiti.vectorgraphics.converter.IConverter;

public class SVG2GraphitiConverterImpl extends SVG2GraphitiConverter {
	
	private Hashtable<String, IConverter[]> converterMapping=new Hashtable<String, IConverter[]>();
	private Hashtable<String, IAccessMonitor> accessMonitors=new Hashtable<String, IAccessMonitor>();
	
	public SVG2GraphitiConverterImpl() {
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void load(final String key, final InputStream in) {
		
		if(accessMonitors.get(key)!=null)return;
		final IAccessMonitor accessMonitor=new AccessMonitor();
		accessMonitor.allowAccess(false);
		accessMonitors.put(key, accessMonitor);
		Job job=new Job("Load vectorgraphic: "+key) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Parse file", 100);
				try {
					List<IConverter> converters=new ArrayList<IConverter>();
					XMLEventReader xmlEventReader=XMLInputFactory.newInstance().createXMLEventReader(in);
					while(xmlEventReader.hasNext()){
						XMLEvent xmlEvent=xmlEventReader.nextEvent();
						if(xmlEvent.isStartElement()){
							final StartElement startElement=xmlEvent.asStartElement();
							if(startElement.getName().getLocalPart().equals("path")){
								monitor.beginTask("Create converter for path", 100);
								final IConverter converter=new PathConverter();
								converter.load(startElement);
								converters.add(converter);
							}
						}
					}
					converterMapping.put(key, converters.toArray(new IConverter[converters.size()]));
					accessMonitor.allowAccess(true);
					System.out.println("Load vector graphic: "+key);
					return Status.OK_STATUS;
				} catch (XMLStreamException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				
			}
			
		};
		job.schedule();
		
	}

	@Override
	public GraphicsAlgorithm[] get(String key, Shape parent, IGaCreateService gaCreateService,
			IPeCreateService peCreateService, int width, int height) {
		accessMonitors.get(key).getAccess();
		IConverter[] converters=converterMapping.get(key);
		
		return new GraphicsAlgorithm[]{converters[0].transform(parent, gaCreateService, peCreateService, width, height)};
	}



	@Override
	public void resize(String key, Shape parent, int width, int height) {
		accessMonitors.get(key).getAccess();
		IConverter[] converters=converterMapping.get(key);
		converters[0].resize(parent, width, height);
	}

	
}
