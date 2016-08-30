package eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.internal.services.impl.UiLayoutService;
import org.eclipse.graphiti.ui.services.IUiLayoutService;

import eu.cloudwave.ude.fcore.graphiti.FCoreUtil;
import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.AttributeConstants;

public class LayoutGroupFeatureFeature extends AbstractLayoutFeature {

	private static final int MIN_HEIGHT = 30;
	 
    private static final int MIN_WIDTH = 50;
 
    public LayoutGroupFeatureFeature(IFeatureProvider fp) {
        super(fp);
    }
 
//    public boolean canLayout(ILayoutContext context) {
//       // return true, if pictogram element is linked to a GroupFeature
//       PictogramElement pe = context.getPictogramElement();
//       if (!(pe instanceof ContainerShape))
//           return false;
//       EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
//       return businessObjects.size() == 1 
//              && businessObjects.get(0) instanceof GroupFeature;
//    }
    
    public boolean canLayout(ILayoutContext context) {
        // return true, if pictogram element is a GroupFeature shape
        PictogramElement pe = context.getPictogramElement();
        return IDUtil.isFeatureShape(pe);
    }
 
    public boolean layout(ILayoutContext context) {
		boolean anythingChanged = false;
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

		Feature feature = null;
		if (getBusinessObjectForPictogramElement(containerShape) instanceof Feature) {
			feature = (Feature) getBusinessObjectForPictogramElement(containerShape);
		}
		IGaService gaService = Graphiti.getGaService();
		int attributeCount = 0;
		int minHeight = MIN_HEIGHT;
		int minWidth = MIN_WIDTH;
		if (feature != null) {
			if (!(feature.getAttributes().isEmpty())) {
				attributeCount = feature.getAttributes().size() + 1;
				minHeight = attributeCount * AttributeConstants.ATTRIBUTE_Y.getValue()
						+ 2 * AttributeConstants.ATTRIBUTE_Y_ADD.getValue();
			}
		}

		// height
		if (containerGa.getHeight() < minHeight) {
			containerGa.setHeight(minHeight);
			anythingChanged = true;
		}

		// set textWidth to the value of the Attribute with the longest text
		for (Shape shape : containerShape.getChildren()) {
			if (shape.getGraphicsAlgorithm() instanceof Text) {
				Text text = (Text) shape.getGraphicsAlgorithm();
				IUiLayoutService uiLayoutService = new UiLayoutService();
				IDimension size = uiLayoutService.calculateTextSize(text.getValue(), text.getFont());
				int textWidth =  size.getWidth();
				if (textWidth > minWidth - AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue())
					minWidth = textWidth + AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue();
			} else {
				Collection<PictogramElement> childrenPEs = Graphiti.getPeService().getPictogramElementChildren(shape);
				for (PictogramElement childPE : childrenPEs) {
					if (childPE.getGraphicsAlgorithm() instanceof Text) {
						Text text = (Text) childPE.getGraphicsAlgorithm();
						IUiLayoutService uiLayoutService = new UiLayoutService();
						IDimension size = uiLayoutService.calculateTextSize(text.getValue(), text.getFont());
						int textWidth =  size.getWidth();
						if (textWidth > minWidth - AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue())
							minWidth = textWidth + AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue();
					}
				}
			}
		}
		
		// width
		if (containerGa.getWidth() < minWidth) {
			containerGa.setWidth(minWidth);
			anythingChanged = true;
		}
		
		int containerWidth = containerGa.getWidth();

		List<Shape> shapeList = new ArrayList<Shape>();
		int yMultiplier = 0;
		for (Shape shape : containerShape.getChildren()) {
			if (getBusinessObjectForPictogramElement(shape) instanceof Attribute) {
				yMultiplier++;
				FCoreUtil.setAttributeLocationAndSize(shape, containerWidth, yMultiplier);
				anythingChanged = true;
			} else {
				shapeList.add(shape);
			}
		}

		for (Shape shape : shapeList) {
			GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
			IDimension size = gaService.calculateSize(graphicsAlgorithm);
			if (containerWidth != size.getWidth()) {
				if (graphicsAlgorithm instanceof Polyline) {
					Polyline polyline = (Polyline) graphicsAlgorithm;
					Point secondPoint = polyline.getPoints().get(1);
					Point newSecondPoint = gaService.createPoint(containerWidth, secondPoint.getY());
					polyline.getPoints().set(1, newSecondPoint);
					anythingChanged = true;
				} else {
					gaService.setWidth(graphicsAlgorithm, containerWidth);
					anythingChanged = true;
				}
			}
		}

		return anythingChanged;
	}

}
