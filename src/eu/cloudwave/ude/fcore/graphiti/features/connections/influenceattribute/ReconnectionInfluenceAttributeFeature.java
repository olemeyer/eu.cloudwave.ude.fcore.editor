package eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Shape;

import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;

public class ReconnectionInfluenceAttributeFeature extends DefaultReconnectionFeature {
	 
    public ReconnectionInfluenceAttributeFeature(IFeatureProvider fp) {
        super(fp);
    }

    @Override
    public boolean canReconnect(IReconnectionContext context) {
    	boolean ret = false;
    	if (context.getTargetPictogramElement() instanceof Shape) {
    	Shape targetShape = (Shape) context.getTargetPictogramElement();
    	Object bo = getBusinessObjectForPictogramElement(targetShape);
    	Connection connection = context.getConnection();
    	String type = context.getReconnectType();
		ret = (connection != null)
				&& (connection.getStart() != null)
				&& (connection.getEnd() != null)
				&& ((type.equals(ReconnectionContext.RECONNECT_SOURCE) && bo instanceof Attribute)
				|| (type.equals(ReconnectionContext.RECONNECT_TARGET) && bo instanceof Softgoal));
    	}
		return ret;
    }

}