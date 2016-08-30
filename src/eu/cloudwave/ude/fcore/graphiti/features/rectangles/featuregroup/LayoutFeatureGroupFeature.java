package eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.internal.services.impl.UiLayoutService;
import org.eclipse.graphiti.ui.services.IUiLayoutService;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;

public class LayoutFeatureGroupFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 20;
	
	private static final int MIN_WIDTH = 100;
	
    public LayoutFeatureGroupFeature(IFeatureProvider fp) {
        super(fp);
    }
 
//    public boolean canLayout(ILayoutContext context) {
//       // return true, if pictogram element is linked to a FeatureGroup
//       PictogramElement pe = context.getPictogramElement();
//       if (!(pe instanceof ContainerShape))
//           return false;
//       EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
//       return businessObjects.size() == 1 
//              && businessObjects.get(0) instanceof FeatureGroup;
//    }
    
    public boolean canLayout(ILayoutContext context) {
        // return true, if pictogram element is a FeatureGroup shape
        PictogramElement pe = context.getPictogramElement();
        return IDUtil.isFeatureGroupShape(pe);
    }
 
    public boolean layout(ILayoutContext context) {
    	boolean anythingChanged = false;
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		for (Shape shape : containerShape.getChildren()) {
			GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			IGaService gaService = Graphiti.getGaService();

			if (graphicsAlgorithm instanceof Text) {
				Text text = (Text) graphicsAlgorithm;
				IUiLayoutService uiLayoutService = new UiLayoutService();
				IDimension size = uiLayoutService.calculateTextSize(text.getValue(), text.getFont());
				int containerWidth;
				int textWidth;
				if (size.getWidth() > MIN_WIDTH) {
					containerWidth = size.getWidth() + FeatureGroupConstants.FEATURE_GROUP_WIDTH_DIFF.getValue();
					textWidth = size.getWidth();
				} else {
					containerWidth = MIN_WIDTH + FeatureGroupConstants.FEATURE_GROUP_WIDTH_DIFF.getValue();
					textWidth = MIN_WIDTH;
				}
				gaService.setSize(containerGa, containerWidth, MIN_HEIGHT);
				gaService.setSize(text, textWidth, MIN_HEIGHT);
				gaService.setLocationAndSize(text, FeatureGroupConstants.FEATURE_GROUP_Y.getValue(), 0, textWidth, MIN_HEIGHT);
				anythingChanged = true;
			}
		}
		return anythingChanged;
    }

}
