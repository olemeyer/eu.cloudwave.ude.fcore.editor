package eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;

public class CreateInfluenceFeatureFeature extends
		AbstractCreateConnectionFeature {

	public CreateInfluenceFeatureFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Influence Feature", "Create Influence Feature");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Feature source = getSourceFeature(context.getSourceAnchor());
        Softgoal target = getTargetFeature(context.getTargetAnchor());
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
 
        // get Feature and Softgoal which should be connected
        Feature source = getSourceFeature(context.getSourceAnchor());
        Softgoal target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
            InfluenceFeature addInfluenceFeatureFeature = createInfluenceFeature(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(addInfluenceFeatureFeature);
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
     * Returns the Softgoal belonging to the anchor, or null if not available.
     */
    private Softgoal getTargetFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof Softgoal) {
                return (Softgoal) object;
            }
        }
        return null;
    }
 
    /**
    * Creates a InfluenceFeature between a feature and a softgoal.
    */
    private InfluenceFeature createInfluenceFeature(Feature source, Softgoal target) {
        InfluenceFeature influenceFeature = FCOREFactory.eINSTANCE.createInfluenceFeature();
        influenceFeature.setContribution(1);
        influenceFeature.setFeature(source);
        influenceFeature.setSoftgoal(target);
        getDiagram().eResource().getContents().add(influenceFeature);
        return influenceFeature;
   }

}
