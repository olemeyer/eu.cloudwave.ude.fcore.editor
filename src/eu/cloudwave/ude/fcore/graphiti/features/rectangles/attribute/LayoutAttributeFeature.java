package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;

public class LayoutAttributeFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 20;
	 
    private static final int MIN_WIDTH = 50;
 
    public LayoutAttributeFeature(IFeatureProvider fp) {
        super(fp);
    }
 
//    public boolean canLayout(ILayoutContext context) {
//       // return true, if pictogram element is linked to a Attribute
//       PictogramElement pe = context.getPictogramElement();
//       if (!(pe instanceof ContainerShape))
//           return false;
//       EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
//       return businessObjects.size() == 1 
//              && businessObjects.get(0) instanceof Attribute;
//    }
    
    public boolean canLayout(ILayoutContext context) {
        // return true, if pictogram element is a Attribute shape
        PictogramElement pe = context.getPictogramElement();
        return IDUtil.isAttributeShape(pe);
    }
 
    public boolean layout(ILayoutContext context) {
        boolean anythingChanged = false;
        ContainerShape containerShape =
            (ContainerShape) context.getPictogramElement();
        GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();
 
        // height
        if (containerGa.getHeight() < MIN_HEIGHT) {
            containerGa.setHeight(MIN_HEIGHT);
            anythingChanged = true;
        }
 
        // width
        if (containerGa.getWidth() < MIN_WIDTH) {
            containerGa.setWidth(MIN_WIDTH);
            anythingChanged = true;
        }
 
        int containerWidth = containerGa.getWidth();
        
        for (Shape shape : containerShape.getChildren()){
            GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
            IGaService gaService = Graphiti.getGaService();
            IDimension size = 
                 gaService.calculateSize(graphicsAlgorithm);
            if (containerWidth != size.getWidth()) {
                if (graphicsAlgorithm  instanceof Polyline) {
                    Polyline polyline = (Polyline) graphicsAlgorithm;
                    Point secondPoint = polyline.getPoints().get(1);
                    Point newSecondPoint =
                        gaService.createPoint(containerWidth, secondPoint.getY()); 
                    polyline.getPoints().set(1, newSecondPoint);
                    anythingChanged = true;
                } else {
                    gaService.setWidth(graphicsAlgorithm,
                        containerWidth);
                    anythingChanged = true;
                }
            }
        }
        return anythingChanged;
    }

}
