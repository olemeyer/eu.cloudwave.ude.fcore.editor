package eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;
import eu.cloudwave.ude.graphiti.vectorgraphics.SVG2GraphitiConverter;

public class AddSoftgoalFeature extends AbstractAddShapeFeature {

	private static final IColorConstant SOFTGOAL_TEXT_FOREGROUND =
	        IColorConstant.BLACK;
	 
	    private static final IColorConstant SOFTGOAL_FOREGROUND =
	        new ColorConstant(98, 131, 167);

	    private static final IColorConstant  SOFTOAL_BACKGROUND =
	        new ColorConstant(187, 218, 247);
	 
	    public AddSoftgoalFeature(IFeatureProvider fp) {
	        super(fp);
	    }
	 
	    public boolean canAdd(IAddContext context) {
	        // check if user wants to add a Softgoal
	        if (context.getNewObject() instanceof Softgoal) {
	            // check if user wants to add to a diagram
	            if (context.getTargetContainer() instanceof Diagram) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
	    public PictogramElement add(IAddContext context) {
	    	final Softgoal addedSoftgoal = (Softgoal) context.getNewObject();
	        final Diagram targetDiagram = (Diagram) context.getTargetContainer();
	  
	        // CONTAINER SHAPE WITH ROUNDED RECTANGLE
	        final IPeCreateService peCreateService = Graphiti.getPeCreateService();
	        final ContainerShape containerShape =
	            peCreateService.createContainerShape(targetDiagram, true);
	        IDUtil.setSoftgoalShape(containerShape);
	  
	        // check whether the context has a size (e.g. from a create feature)
	        // otherwise define a default size for the shape
	        int width = context.getWidth() <= 0 ? 100 : context.getWidth();
	        int height = context.getHeight() <= 0 ? 50 : context.getHeight();
	        IGaService gaService = Graphiti.getGaService();
	        RoundedRectangle roundedRectangle; // need to access it later
	        
	        {
	        	GraphicsAlgorithm[] gas=SVG2GraphitiConverter.getInstance().get("cloud", containerShape, gaService, peCreateService, 100, 100);
	        	for(GraphicsAlgorithm ga:gas){
	        		ga.setLineWidth(2);
	        		ga.setForeground(manageColor(SOFTGOAL_FOREGROUND));
	        		ga.setBackground(manageColor(SOFTOAL_BACKGROUND));
	        		link(ga.getPictogramElement(), addedSoftgoal);
	        		IDimension tmpdimension=gaService.calculateSize(ga);
	        		if(tmpdimension.getHeight()>height)height=tmpdimension.getHeight();
	        		if(tmpdimension.getWidth()>width)width=tmpdimension.getWidth();
	        	}
	        }
	        
//	        {
//	            // create and set graphics algorithm
//	            roundedRectangle =
//	                gaService.createRoundedRectangle(containerShape, 20, 20);
//	            roundedRectangle.setForeground(manageColor(SOFTGOAL_FOREGROUND));
//	            roundedRectangle.setBackground(manageColor(SOFTOAL_BACKGROUND));
//	            roundedRectangle.setLineWidth(2);
//	            gaService.setLocationAndSize(roundedRectangle,
//	                context.getX(), context.getY(), width, height);
//	 
//	            // if added Class has no resource we add it to the resource 
//	            // of the diagram
//	            // in a real scenario the business model would have its own resource
//	            if (addedSoftgoal.eResource() == null) {
//	                     getDiagram().eResource().getContents().add(addedSoftgoal);
//	            }
//	            // create link and wire it
//	            link(containerShape, addedSoftgoal);
//	        }
	 
	        // SHAPE WITH TEXT
	        {
	            // create shape for text
	            Shape shape = peCreateService.createShape(containerShape, false);
	 
	            // create and set text graphics algorithm
	            Text text = gaService.createText(shape, addedSoftgoal.getName());
	            text.setForeground(manageColor(SOFTGOAL_TEXT_FOREGROUND));
	            text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER ); 
	            // vertical alignment has as default value "center"
	            text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
//	            gaService.setLocationAndSize(text, 0, 0, width, 20);
	            gaService.setSize(text, width, 20);
	 
	            // create link and wire it
	            link(shape, addedSoftgoal);
	            
	            System.out.println("height: " + text.getHeight());
	            System.out.println("width: " + text.getWidth());
	        }
	        
	        // add a chopbox anchor to the shape 
	        peCreateService.createChopboxAnchor(containerShape);
	        
	        // call the layout feature
	        layoutPictogramElement(containerShape);
	        
	        return containerShape;
	    }

}
