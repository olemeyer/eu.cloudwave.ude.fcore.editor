package eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
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
import eu.cloudwave.ude.fcore.graphiti.FCORE.RootFeature;

public class AddRootFeatureFeature extends AbstractAddShapeFeature {

	private static final IColorConstant ROOT_FEATURE_TEXT_FOREGROUND =
	        IColorConstant.BLACK;
	 
	    private static final IColorConstant ROOT_FEATURE_FOREGROUND =
	        new ColorConstant(98, 131, 167);

	    private static final IColorConstant  ROOT_FEATURE_BACKGROUND =
	        new ColorConstant(187, 218, 247);
	 
	    public AddRootFeatureFeature(IFeatureProvider fp) {
	        super(fp);
	    }
	 
	    public boolean canAdd(IAddContext context) {
	        // check if user wants to add a RootFeature
	        if (context.getNewObject() instanceof RootFeature) {
	            // check if user wants to add to a diagram
	            if (context.getTargetContainer() instanceof Diagram) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
	    public PictogramElement add(IAddContext context) {
	    	final RootFeature addedRootFeature = (RootFeature) context.getNewObject();
	        final Diagram targetDiagram = (Diagram) context.getTargetContainer();
	  
	        // CONTAINER SHAPE WITH ROUNDED RECTANGLE
	        final IPeCreateService peCreateService = Graphiti.getPeCreateService();
	        final ContainerShape containerShape =
	            peCreateService.createContainerShape(targetDiagram, true);
	        IDUtil.setFeatureShape(containerShape);
	  
	        // check whether the context has a size (e.g. from a create feature)
	        // otherwise define a default size for the shape
	        final int width = context.getWidth() <= 0 ? 100 : context.getWidth();
	        final int height = context.getHeight() <= 0 ? 50 : context.getHeight();
	        IGaService gaService = Graphiti.getGaService();
	        Rectangle rectangle; // need to access it later
	 
	        {
	            // create and set graphics algorithm
	            rectangle =
	                gaService.createPlainRectangle(containerShape);
	            rectangle.setForeground(manageColor(ROOT_FEATURE_FOREGROUND));
	            rectangle.setBackground(manageColor(ROOT_FEATURE_BACKGROUND));
	            rectangle.setLineWidth(2);
	            gaService.setLocationAndSize(rectangle,
	                context.getX(), context.getY(), width, height);
	 
	            // if added RootFeature has no resource we add it to the resource 
	            // of the diagram
	            // in a real scenario the business model would have its own resource
	            if (addedRootFeature.eResource() == null) {
	                     getDiagram().eResource().getContents().add(addedRootFeature);
	            }
	            // create link and wire it
	            link(containerShape, addedRootFeature);
	        }
	 
	        // SHAPE WITH LINE
	        {
	            // create shape for line
	            Shape shape = peCreateService.createShape(containerShape, false);
	 
	            // create and set graphics algorithm
	            Polyline polyline =
	                gaService.createPolyline(shape, new int[] { 0, 20, width, 20 });
	            polyline.setForeground(manageColor(ROOT_FEATURE_FOREGROUND));
	            polyline.setLineWidth(2);
	        }
	 
	        // SHAPE WITH TEXT
	        {
	            // create shape for text
	            Shape shape = peCreateService.createShape(containerShape, false);
	 
	            // create and set text graphics algorithm
	            Text text = gaService.createText(shape, addedRootFeature.getName());
	            text.setForeground(manageColor(ROOT_FEATURE_TEXT_FOREGROUND));
	            text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER ); 
	            // vertical alignment has as default value "center"
	            text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
	            gaService.setLocationAndSize(text, 0, 0, width, 20);
	 
	            // create link and wire it
	            link(shape, addedRootFeature);
	        }
	        
	        // add a chopbox anchor to the shape 
	        peCreateService.createChopboxAnchor(containerShape);
	        
	        // call the layout feature
	        layoutPictogramElement(containerShape);
	 
	        return containerShape;
	    }

}
