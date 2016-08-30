package eu.coudwave.ude.graphiti.vectorgraphics.converter;

import javax.xml.stream.events.StartElement;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IGaCreateService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IConverter {
	void load(StartElement startElement);
	GraphicsAlgorithm transform(Shape shape, IGaCreateService gaCreateService, IPeCreateService peCreateService, int width, int height);
	void resize(Shape shape, int width, int height);
}
