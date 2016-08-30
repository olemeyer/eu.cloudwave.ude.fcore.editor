package eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import eu.cloudwave.ude.fcore.graphiti.FCoreUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.CardinalityConnection;

public class UpdateCardinalityConnectionFeature extends AbstractUpdateFeature {

	public UpdateCardinalityConnectionFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a CardinalityConnection
        Object bo =
            getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof CardinalityConnection);
    }
 
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve cardinality from pictogram model
    	int pictogramMin = -1;
    	int pictogramMax = -1;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement instanceof FreeFormConnection) {
        	FreeFormConnection ffc = (FreeFormConnection) pictogramElement;
            for (ConnectionDecorator connectionDecorator : ffc.getConnectionDecorators()) {
                if (connectionDecorator.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) connectionDecorator.getGraphicsAlgorithm();
                    String textValue = text.getValue();
                    int minEnd = textValue.indexOf(".");
                    int maxStart = minEnd + 2;
                    int textValueEnd = textValue.length() - 1;
                    pictogramMin = Integer.parseInt(textValue.substring(1, minEnd));
                    pictogramMax = Integer.parseInt(textValue.substring(maxStart, textValueEnd));
                }
            }
        }
 
        // retrieve contribution from business model
        int businessMin = -1;
    	int businessMax = -1;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof CardinalityConnection) {
        	CardinalityConnection cardinalityConnection = (CardinalityConnection) bo;
            businessMin = cardinalityConnection.getMin();
            businessMax = cardinalityConnection.getMax();
        }
 
        // update needed, if cardinalitys are different
        boolean updateContributionNeeded = (pictogramMin != businessMin
        		|| pictogramMax != businessMax);
        if (updateContributionNeeded) {
            return Reason.createTrueReason("Cardinality is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }
 
    public boolean update(IUpdateContext context) {
        // retrieve cardinality from business model
    	int businessMin = -1;
    	int businessMax = -1;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof CardinalityConnection) {
        	CardinalityConnection cardinalityConnection = (CardinalityConnection) bo;
        	businessMin = cardinalityConnection.getMin();
            businessMax = cardinalityConnection.getMax();
        }
 
        // Set cardinality in pictogram model
        if (pictogramElement instanceof FreeFormConnection) {
        	FreeFormConnection ffc = (FreeFormConnection) pictogramElement;
        	for (ConnectionDecorator connectionDecorator : ffc.getConnectionDecorators()) {
                if (connectionDecorator.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) connectionDecorator.getGraphicsAlgorithm();
                    text.setValue(FCoreUtil.getCardinalityString(businessMin, businessMax));
                }
            }
        }
 
        return false;
    }

}
