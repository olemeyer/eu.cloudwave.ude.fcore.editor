package eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.graphiti.vectorgraphics.SVG2GraphitiConverter;

public class LayoutSoftgoalFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 30;
	 
    private static final int MIN_WIDTH = 50;
 
    public LayoutSoftgoalFeature(IFeatureProvider fp) {
        super(fp);
    }
 
//    public boolean canLayout(ILayoutContext context) {
//       // return true, if pictogram element is linked to an Softgoal
//       PictogramElement pe = context.getPictogramElement();
//       if (!(pe instanceof ContainerShape))
//           return false;
//       EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
//       return businessObjects.size() == 1 
//              && businessObjects.get(0) instanceof Softgoal;
//    }
    
    public boolean canLayout(ILayoutContext context) {
        // return true, if pictogram element is a Softgoal shape
        PictogramElement pe = context.getPictogramElement();
        return IDUtil.isSoftgoalShape(pe);
    }
 
    public boolean layout(ILayoutContext context) {
    	
        boolean anythingChanged = false;
        ContainerShape containerShape =
            (ContainerShape) context.getPictogramElement();
        GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();
        IGaService gaService=Graphiti.getGaService();
        
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
        
        //Layout Cloud
        SVG2GraphitiConverter.getInstance().resize("cloud", containerShape, containerGa.getWidth(), containerGa.getHeight());
        
        
        for (Shape shape : containerShape.getChildren()){
            GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
            IDimension size = 
                 gaService.calculateSize(graphicsAlgorithm);
            if (containerWidth != size.getWidth()) {
                if (graphicsAlgorithm  instanceof Text) {
                    gaService.setWidth(graphicsAlgorithm,
                        containerWidth);
                    //
                    gaService.setHeight(graphicsAlgorithm, containerGa.getHeight());
                    anythingChanged = true;
                }
            }
        }
        return anythingChanged;
    }

}
