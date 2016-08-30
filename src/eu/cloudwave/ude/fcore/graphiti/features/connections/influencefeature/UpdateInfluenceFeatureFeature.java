package eu.cloudwave.ude.fcore.graphiti.features.connections.influencefeature;

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
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceFeature;

public class UpdateInfluenceFeatureFeature extends AbstractUpdateFeature {

	public UpdateInfluenceFeatureFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a InfluenceFeature
        Object bo =
            getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof InfluenceFeature);
    }
 
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve contribution from pictogram model
        float pictogramContribution = 2;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement instanceof FreeFormConnection) {
        	FreeFormConnection ffc = (FreeFormConnection) pictogramElement;
            for (ConnectionDecorator connectionDecorator : ffc.getConnectionDecorators()) {
                if (connectionDecorator.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) connectionDecorator.getGraphicsAlgorithm();
                    String textValue = text.getValue();
                    if (textValue.startsWith("-"))
                    	pictogramContribution = Float.parseFloat(textValue);
                    else
                    	pictogramContribution = Float.parseFloat(textValue.substring(1));
                }
            }
        }
 
        // retrieve contribution from business model
        float businessContribution = 2;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof InfluenceFeature) {
        	InfluenceFeature influenceFeature = (InfluenceFeature) bo;
            businessContribution = influenceFeature.getContribution();
        }
 
        // update needed, if contributions are different
        boolean updateContributionNeeded = (pictogramContribution != businessContribution);
        if (updateContributionNeeded) {
            return Reason.createTrueReason("Contribution is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }
 
    public boolean update(IUpdateContext context) {
        // retrieve contribution from business model
        float businessContribution = 2;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof InfluenceFeature) {
        	InfluenceFeature influenceFeature = (InfluenceFeature) bo;
            businessContribution = influenceFeature.getContribution();
        }
 
        // Set contribution in pictogram model
        if (pictogramElement instanceof FreeFormConnection) {
        	FreeFormConnection ffc = (FreeFormConnection) pictogramElement;
        	for (ConnectionDecorator connectionDecorator : ffc.getConnectionDecorators()) {
                if (connectionDecorator.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) connectionDecorator.getGraphicsAlgorithm();
                    text.setValue(FCoreUtil.getContributionString(businessContribution));
                }
            }
        }
 
        return false;
    }

}
