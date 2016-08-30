package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint;

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

public class LayoutAttributeConstraintFeature extends AbstractLayoutFeature {

	public LayoutAttributeConstraintFeature(IFeatureProvider fp) {
		super(fp);
	}

	// public boolean canLayout(ILayoutContext context) {
	// // return true, if pictogram element is linked to a AttributeConstraint
	// PictogramElement pe = context.getPictogramElement();
	// if (!(pe instanceof ContainerShape))
	// return false;
	// EList<EObject> businessObjects = pe.getLink().getBusinessObjects();
	// return businessObjects.size() == 1
	// && businessObjects.get(0) instanceof AttributeConstraint;
	// }

	public boolean canLayout(ILayoutContext context) {
		// return true, if pictogram element is a AttributeConstraint shape
		PictogramElement pe = context.getPictogramElement();
		return IDUtil.isAttributeConstraintShape(pe);
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
				gaService.setSize(containerGa, size.getWidth(), size.getHeight());
				gaService.setSize(text, size.getWidth(), size.getHeight());
				anythingChanged = true;
			}
		}
		return anythingChanged;
	}

}
