package eu.cloudwave.ude.fcore.graphiti.features.connections.grouptofeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureGroup;
import eu.cloudwave.ude.fcore.graphiti.FCORE.GroupFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.GroupToFeatureConnection;

public class CreateFeatureGroupToFeatureConnectionFeature extends
		AbstractCreateConnectionFeature {

	public CreateFeatureGroupToFeatureConnectionFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Group To Feature", "Create Group To Feature");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        FeatureGroup source = getSourceFeature(context.getSourceAnchor());
        GroupFeature target = getTargetFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
        	if (!(target.getGroupToFeatureConnection() != null))
        		return true;
        }
        return false;
    }
 
    public boolean canStartConnection(ICreateConnectionContext context) {
        // return true if start anchor belongs to a FeatureGroup
        if (getSourceFeature(context.getSourceAnchor()) != null) {
            return true;
        }
        return false;
    }
 
    public Connection create(ICreateConnectionContext context) {
        Connection newConnection = null;
 
        // get FeatureGroup and GroupFeature which should be connected
        FeatureGroup source = getSourceFeature(context.getSourceAnchor());
        GroupFeature target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
        	GroupToFeatureConnection featureGroupToFeature = createGroupToFeatureConnection(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(featureGroupToFeature);
            newConnection =
                (Connection) getFeatureProvider().addIfPossible(addContext);
        }
        
        return newConnection;
    }
 
    /**
     * Returns the Feature Group belonging to the anchor, or null if not available.
     */
    private FeatureGroup getSourceFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof FeatureGroup) {
                return (FeatureGroup) object;
            }
        }
        return null;
    }
    
    /**
     * Returns the Group Feature belonging to the anchor, or null if not available.
     */
    private GroupFeature getTargetFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof GroupFeature) {
            	return (GroupFeature) object;
            }
        }
        return null;
    }
 
    /**
    * Creates a GroupToFeatureConnection between a Feature and a Solitary Feature.
    */
    private GroupToFeatureConnection createGroupToFeatureConnection(FeatureGroup source, GroupFeature target) {
    	GroupToFeatureConnection featureGroupToFeature = FCOREFactory.eINSTANCE.createGroupToFeatureConnection();
        featureGroupToFeature.setSource(source);
        featureGroupToFeature.setTarget(target);
        getDiagram().eResource().getContents().add(featureGroupToFeature);
        return featureGroupToFeature;
   }

}
