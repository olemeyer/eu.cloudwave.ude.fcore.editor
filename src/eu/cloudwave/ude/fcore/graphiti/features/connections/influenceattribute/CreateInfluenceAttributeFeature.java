package eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceAttribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;

public class CreateInfluenceAttributeFeature extends
		AbstractCreateConnectionFeature {

	public CreateInfluenceAttributeFeature (IFeatureProvider fp) {
        // provide name and description for the UI, e.g. the palette
        super(fp, "Influence Attribute", "Create Influence Attribute");
    }
 
    public boolean canCreate(ICreateConnectionContext context) {
        Attribute source = getSourceFeature(context.getSourceAnchor());
        Softgoal target = getTargetFeature(context.getTargetAnchor());
        if (source != null && target != null && source != target) {
            return true;
        }
        return false;
    }
 
    public boolean canStartConnection(ICreateConnectionContext context) {
        // return true if start anchor belongs to a Attribute
        if (getSourceFeature(context.getSourceAnchor()) != null) {
            return true;
        }
        return false;
    }
 
    public Connection create(ICreateConnectionContext context) {
        Connection newConnection = null;
 
        // get Attribute and Softgoal which should be connected
        Attribute source = getSourceFeature(context.getSourceAnchor());
        Softgoal target = getTargetFeature(context.getTargetAnchor());
 
        if (source != null && target != null) {
            // create new business object 
            InfluenceAttribute addInfluenceAttributeFeature = createInfluenceAttribute(source, target);
            // add connection for business object
            AddConnectionContext addContext =
                new AddConnectionContext(context.getSourceAnchor(), context
                    .getTargetAnchor());
            addContext.setNewObject(addInfluenceAttributeFeature);
            newConnection =
                (Connection) getFeatureProvider().addIfPossible(addContext);
        }
        
        return newConnection;
    }
 
    /**
     * Returns the Attribute belonging to the anchor, or null if not available.
     */
    private Attribute getSourceFeature(Anchor anchor) {
        if (anchor != null) {
            Object object =
                getBusinessObjectForPictogramElement(anchor.getParent());
            if (object instanceof Attribute) {
                return (Attribute) object;
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
    * Creates a InfluenceAttribute between a attribute and a softgoal.
    */
    private InfluenceAttribute createInfluenceAttribute(Attribute source, Softgoal target) {
        InfluenceAttribute influenceAttribute = FCOREFactory.eINSTANCE.createInfluenceAttribute();
        influenceAttribute.setContribution(1);
        influenceAttribute.setAttribute(source);
        influenceAttribute.setSoftgoal(target);
        getDiagram().eResource().getContents().add(influenceAttribute);
        return influenceAttribute;
   }

}
