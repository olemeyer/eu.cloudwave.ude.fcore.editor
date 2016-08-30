package eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureGroup;

public class UpdateFeatureGroupFeature extends AbstractUpdateFeature {

	public UpdateFeatureGroupFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a FeatureGroup
        Object bo =
            getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof FeatureGroup);
    }
 
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve cardinality from pictogram model
        String pictogramCardinality = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement  instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    pictogramCardinality = text.getValue();
                }
            }
        }
 
        // retrieve cardinality from business model
        String cardinality = null;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof FeatureGroup) {
        	FeatureGroup featureGroup = (FeatureGroup) bo;
            cardinality = "<" + featureGroup.getMin() + ".." + featureGroup.getMax() + ">";
        }
 
        // update needed, if cardinalities are different
        boolean updateCardinalityNeeded =
            ((pictogramCardinality == null && cardinality != null) || 
                (pictogramCardinality != null && !pictogramCardinality.equals(cardinality)));
        if (updateCardinalityNeeded) {
            return Reason.createTrueReason("Cardinality is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }
 
    public boolean update(IUpdateContext context) {
        // retrieve cardinality from business model
        String businessCardinality = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof FeatureGroup) {
        	FeatureGroup featureGroup = (FeatureGroup) bo;
            businessCardinality = "<" + featureGroup.getMin() + ".." + featureGroup.getMax() + ">";
        }
 
        // Set cardinality in pictogram model
        if (pictogramElement instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    text.setValue(businessCardinality);
                    layoutPictogramElement(pictogramElement);
                    return true;
                }
            }
        }
 
        return false;
    }

}
