package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import eu.cloudwave.ude.fcore.graphiti.FCoreUtil;
import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;

public class AddAttributeFeature extends AbstractAddShapeFeature {

		private static final IColorConstant ATTRIBUTE_TEXT_FOREGROUND =
	        IColorConstant.BLACK;
    
		private static final IColorConstant ATTRIBUTE_COLOR =
    		new ColorConstant(187, 218, 247);
	 
	    public AddAttributeFeature(IFeatureProvider fp) {
	        super(fp);
	    }
	 
	    public boolean canAdd(IAddContext context) {
	    	PictogramLink pl = null;
	    	for (Object o : context.getTargetContainer().eContents()) {
	    		if (o instanceof PictogramLink) {
	    			pl = (PictogramLink) o;
	    			if (pl.getBusinessObjects().get(0) instanceof Feature)
						return true;
					else
						return false;
	    		}
	    	}
	    	return false;
	    }
	 
	    public PictogramElement add(IAddContext context) {
	    	final Attribute addedAttribute = (Attribute) context.getNewObject();
	        final ContainerShape targetContainerShape = context.getTargetContainer();
	  
	        // CONTAINER SHAPE WITH ROUNDED RECTANGLE
	        final IPeCreateService peCreateService = Graphiti.getPeCreateService();
	        final ContainerShape containerShape =
		            peCreateService.createContainerShape(targetContainerShape, true);
	        IDUtil.setAttributeShape(containerShape);
	  
	        IGaService gaService = Graphiti.getGaService();
	        Rectangle rectangle; // need to access it later

	        {
	            // create and set graphics algorithm
	            rectangle =
	                gaService.createPlainRectangle(containerShape);
	            rectangle.setForeground(manageColor(ATTRIBUTE_COLOR));
	            rectangle.setBackground(manageColor(ATTRIBUTE_COLOR));
//	            rectangle.setLineWidth(2);
	            int yMultiplier = getYMultiplier(context);
	            int containerWidth = context.getTargetContainer().getGraphicsAlgorithm().getWidth();
	            FCoreUtil.setAttributeLocationAndSize(containerShape, containerWidth, yMultiplier);
	 
	            // if added Attribute has no resource we add it to the resource 
	            // of the diagram
	            // in a real scenario the business model would have its own resource
	            if (addedAttribute.eResource() == null) {
	                     getDiagram().eResource().getContents().add(addedAttribute);
	            }
	            // create link and wire it
	            link(containerShape, addedAttribute);
	        }
	 
	        // SHAPE WITH TEXT
	        {
	        	// create shape for text
	        	Shape shape = peCreateService.createShape(containerShape, false);
	        	
	        	int width = context.getTargetContainer().getGraphicsAlgorithm().getWidth()
	            		- AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue();
	        	
	        	// create and set text graphics algorithm
	        	Text text = gaService.createText(shape, addedAttribute.getName());
	        	text.setForeground(manageColor(ATTRIBUTE_TEXT_FOREGROUND));
	        	text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT); 
	        	// vertical alignment has as default value "center"
	        	text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
	        	gaService.setLocationAndSize(text, 0, 0, width, 20);
	        	
	        	// create link and wire it
	        	link(shape, addedAttribute);
	        }
	        
	        // add a chopbox anchor to the shape 
	        peCreateService.createChopboxAnchor(containerShape);
	 
	        updatePictogramElement((Shape) containerShape);
	        
	        // call the layout feature
	        layoutPictogramElement((Shape) targetContainerShape);
	        
	        return containerShape;
	    }

		private int getYMultiplier(IAddContext context) {
			PictogramLink pl = null;
			int yMultiplier = 0;
			for (Object o : context.getTargetContainer().eContents()) {
				if (o instanceof PictogramLink) {
					pl = (PictogramLink) o;
					Feature f = (Feature) pl.getBusinessObjects().get(0);
					yMultiplier = f.getAttributes().size();
					break;
				}
			}
			return yMultiplier;
		}

}
