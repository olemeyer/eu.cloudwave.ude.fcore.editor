package eu.cloudwave.ude.fcore.graphiti.features.connections.optional;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.FCORE.OptionalConnection;
import eu.cloudwave.ude.fcore.graphiti.FCORE.SolitaryFeature;

public class CreateOptionalConnectionFeature extends
		AbstractCreateConnectionFeature {

	public CreateOptionalConnectionFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Optional", "Create Optional");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Feature source = getSourceFeature(context.getSourceAnchor());
        SolitaryFeature target = getTargetFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
        	if (!(target.getIncomingSingleFeatureConnection() != null))
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
        SolitaryFeature target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
            OptionalConnection optional = createOptionalConnection(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(optional);
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
     * Returns the Solitary Feature belonging to the anchor, or null if not available.
     */
    private SolitaryFeature getTargetFeature(Anchor anchor) {
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
    * Creates a OptionalConnection between a Feature and a Solitary Feature.
    */
    private OptionalConnection createOptionalConnection(Feature source, SolitaryFeature target) {
    	OptionalConnection optional = FCOREFactory.eINSTANCE.createOptionalConnection();
        optional.setSource(source);
        optional.setTarget(target);
        getDiagram().eResource().getContents().add(optional);
        return optional;
   }

}
