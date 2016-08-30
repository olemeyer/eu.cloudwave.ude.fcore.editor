package eu.cloudwave.ude.fcore.graphiti.features.connections.featuretogroup;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureGroup;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureToGroupConnection;

public class CreateFeatureToFeatureGroupConnectionFeature extends
		AbstractCreateConnectionFeature {

	public CreateFeatureToFeatureGroupConnectionFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Feature To Group", "Create Feature To Group");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Feature source = getSourceFeature(context.getSourceAnchor());
        FeatureGroup target = getTargetFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
        	if (!(target.getFeatureToGroupConnection() != null))
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
 
        // get Feature and FeatureGroup which should be connected
        Feature source = getSourceFeature(context.getSourceAnchor());
        FeatureGroup target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
        	FeatureToGroupConnection featureToFeatureGroup = createFeatureToGroupConnection(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(featureToFeatureGroup);
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
     * Returns the Feature Group belonging to the anchor, or null if not available.
     */
    private FeatureGroup getTargetFeature(Anchor anchor) {
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
    * Creates a FeatureToFeatureGroupConnection between a Feature and a Solitary Feature.
    */
    private FeatureToGroupConnection createFeatureToGroupConnection(Feature source, FeatureGroup target) {
    	FeatureToGroupConnection featureGroupToFeature = FCOREFactory.eINSTANCE.createFeatureToGroupConnection();
        featureGroupToFeature.setSource(source);
        featureGroupToFeature.setTarget(target);
        getDiagram().eResource().getContents().add(featureGroupToFeature);
        return featureGroupToFeature;
   }

}
