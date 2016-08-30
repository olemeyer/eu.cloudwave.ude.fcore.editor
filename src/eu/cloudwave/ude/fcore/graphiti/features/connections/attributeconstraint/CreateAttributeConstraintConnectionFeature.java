package eu.cloudwave.ude.fcore.graphiti.features.connections.attributeconstraint;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraintConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;

public class CreateAttributeConstraintConnectionFeature extends
		AbstractCreateConnectionFeature {

	public CreateAttributeConstraintConnectionFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Attribute Constraint", "Create Attribute Constraint");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Feature source = getSourceFeature(context.getSourceAnchor());
        AttributeConstraint target = getTargetFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
            return true;
        }
        return false;
    }
 
    public boolean canStartConnection(ICreateConnectionContext context) {
        // return true if start anchor belongs to a Feature
        if (getSourceFeature(context.getSourceAnchor()) != null) {
            return true;
        }
        return false;
    }
 
    public Connection create(ICreateConnectionContext context) {
        Connection newConnection = null;
 
        // get Features which should be connected
        Feature source = getSourceFeature(context.getSourceAnchor());
        AttributeConstraint target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
        	AttributeConstraintConnection excludesFeatureConstraint = createAttributeConstraintConnection(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(excludesFeatureConstraint);
            newConnection =
                (Connection) getFeatureProvider().addIfPossible(addContext);
        }
        
        return newConnection;
    }
 
    /**
     * Returns the Feature belonging to the anchor, or null if not available.
     */
    private Feature getSourceFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof Feature) {
                return (Feature) object;
            }
        }
        return null;
    }
    
    /**
     * Returns the AttributeConstraint belonging to the anchor, or null if not available.
     */
    private AttributeConstraint getTargetFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof AttributeConstraint) {
                return (AttributeConstraint) object;
            }
        }
        return null;
    }
 
    /**
    * Creates a AttributeConstraintConnection between two Features.
    */
    private AttributeConstraintConnection createAttributeConstraintConnection(Feature source, AttributeConstraint target) {
    	AttributeConstraintConnection attributeConstraintConnection = FCOREFactory.eINSTANCE.createAttributeConstraintConnection();
    	attributeConstraintConnection.setSource(source);
    	attributeConstraintConnection.setTarget(target);
    	getDiagram().eResource().getContents().add(attributeConstraintConnection);
        return attributeConstraintConnection;
   }

}
