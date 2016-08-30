package eu.cloudwave.ude.fcore.graphiti.features.connections.excludes;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.ExcludesFeatureConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;

public class CreateExcludesFeatureConstraintFeature extends
		AbstractCreateConnectionFeature {

	public CreateExcludesFeatureConstraintFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Excludes Constraint", "Create Excludes Constraint");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        // return true if both anchors belong to a Feature
        // and those Features are not identical
        Feature source = getFeature(context.getSourceAnchor());
        Feature target = getFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
            return true;
        }
        return false;
    }
 
    public boolean canStartConnection(ICreateConnectionContext context) {
        // return true if start anchor belongs to a Feature
        if (getFeature(context.getSourceAnchor()) != null) {
            return true;
        }
        return false;
    }
 
    public Connection create(ICreateConnectionContext context) {
        Connection newConnection = null;
 
        // get Features which should be connected
        Feature source = getFeature(context.getSourceAnchor());
        Feature target = getFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
            ExcludesFeatureConstraint excludesFeatureConstraint = createExcludesFeatureConstraint(source, target);
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
    private Feature getFeature(Anchor anchor) {
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
    * Creates a ExcludesFeatureConstraint between two Features.
    */
    private ExcludesFeatureConstraint createExcludesFeatureConstraint(Feature source, Feature target) {
    	ExcludesFeatureConstraint excludesFeatureConstraint = FCOREFactory.eINSTANCE.createExcludesFeatureConstraint();
        excludesFeatureConstraint.setFeatureStart(source);
        excludesFeatureConstraint.setFeatureEnd(target);
        getDiagram().eResource().getContents().add(excludesFeatureConstraint);
        return excludesFeatureConstraint;
   }

}
