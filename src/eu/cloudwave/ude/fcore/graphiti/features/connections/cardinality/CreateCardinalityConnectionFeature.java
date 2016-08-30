package eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.SolitaryFeature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.CardinalityConnection;

public class CreateCardinalityConnectionFeature extends
		AbstractCreateConnectionFeature {

	public CreateCardinalityConnectionFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Cardinality", "Create Cardinality");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Feature source = getFeature(context.getSourceAnchor());
        SolitaryFeature target = getSolitaryFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
        	if (!(target.getIncomingSingleFeatureConnection() != null))
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
 
        // get Feature and SolitaryFeature which should be connected
        Feature source = getFeature(context.getSourceAnchor());
        SolitaryFeature target = getSolitaryFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
            CardinalityConnection addCardinalityConnectionFeature = createCardinalityConnection(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(addCardinalityConnectionFeature);
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
     * Returns the SolitaryFeature belonging to the anchor, or null if not available.
     */
    private SolitaryFeature getSolitaryFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof SolitaryFeature) {
                return (SolitaryFeature) object;
            }
        }
        return null;
    }
 
    /**
    * Creates a CardinalityConnection between a attribute and a softgoal.
    */
    private CardinalityConnection createCardinalityConnection(Feature source, SolitaryFeature target) {
        CardinalityConnection cardinalityConnection = FCOREFactory.eINSTANCE.createCardinalityConnection();
        cardinalityConnection.setSource(source);
        cardinalityConnection.setTarget(target);
//        cardinalityConnection.setMin(1);
//        cardinalityConnection.setMax(2);
        getDiagram().eResource().getContents().add(cardinalityConnection);
        return cardinalityConnection;
   }

}
