package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraint;

public class UpdateAttributeConstraintFeature extends AbstractUpdateFeature {

	public UpdateAttributeConstraintFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a AttributeConstraint
        Object bo =
            getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof AttributeConstraint);
    }
 
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve equation from pictogram model
        String pictogramEquation = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement  instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    pictogramEquation = text.getValue();
                }
            }
        }
 
        // retrieve equation from business model
        String businessEquation = null;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof AttributeConstraint) {
        	AttributeConstraint attributeConstraint = (AttributeConstraint) bo;
            businessEquation = attributeConstraint.getEquation();
        }
 
        // update needed, if equations are different
        boolean updateNameNeeded =
            ((pictogramEquation == null && businessEquation != null) || 
                (pictogramEquation != null && 
                !pictogramEquation.substring(1, pictogramEquation.length() - 1).equals(businessEquation)));
        if (updateNameNeeded) {
            return Reason.createTrueReason("Equation is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }
 
    public boolean update(IUpdateContext context) {
        // retrieve equation from business model
        String businessEquation = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof AttributeConstraint) {
        	AttributeConstraint attributeConstraint = (AttributeConstraint) bo;
            businessEquation = attributeConstraint.getEquation();
        }
 
        // Set equation in pictogram model
        if (pictogramElement instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    text.setValue("{" + businessEquation + "}");
                    layoutPictogramElement(pictogramElement);
                    return true;
                }
            }
        }
        
        layoutPictogramElement(pictogramElement);
        
        return false;
    }

}
