package eu.cloudwave.ude.fcore.graphiti.features.connections.cardinality;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import eu.cloudwave.ude.fcore.graphiti.FCoreUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.CardinalityConnection;

public class AddCardinalityConnectionFeature extends AbstractAddFeature {

	private static final IColorConstant CARDINALITY_CONNECTION_FOREGROUND = new ColorConstant(98, 131, 167);
	 
    public AddCardinalityConnectionFeature (IFeatureProvider fp) {
        super(fp);
    }
 
    public PictogramElement add(IAddContext context) {
        IAddConnectionContext addConContext = (IAddConnectionContext) context;
        CardinalityConnection addedCardinalityConnection = (CardinalityConnection) context.getNewObject();
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        // CONNECTION WITH POLYLINE
        Connection connection = peCreateService
            .createFreeFormConnection(getDiagram());
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
 
        IGaService gaService = Graphiti.getGaService();
        Polyline polyline = gaService.createPolyline(connection);
        polyline.setLineWidth(2);
        polyline.setForeground(manageColor(CARDINALITY_CONNECTION_FOREGROUND));
//        polyline.setLineStyle(StylesPackage.);
 
        // create link and wire it
        link(connection, addedCardinalityConnection);
        
        // add dynamic text decorator for the association name 
        ConnectionDecorator textDecorator =
            peCreateService.createConnectionDecorator(connection, true,
            0.8, true);
        Text text = gaService.createDefaultText(getDiagram(),textDecorator);
        text.setForeground(manageColor(IColorConstant.BLACK));
        gaService.setLocation(text, 10, 0);
        // set reference name in the text decorator
        CardinalityConnection cardinalityConnection = (CardinalityConnection) context.getNewObject();
        text.setValue(FCoreUtil.getCardinalityString(cardinalityConnection.getMin(), cardinalityConnection.getMax()));
 
        return connection;
    }
 
    public boolean canAdd(IAddContext context) {
        // return true if given business object is an CardinalityConnection
        // note, that the context must be an instance of IAddConnectionContext
        if (context instanceof IAddConnectionContext
            && context.getNewObject() instanceof CardinalityConnection) {
            return true;
        }
        return false;
    }

}
