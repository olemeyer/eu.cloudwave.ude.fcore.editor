package eu.cloudwave.ude.fcore.graphiti.features.connections.mandatory;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import eu.cloudwave.ude.fcore.graphiti.FCORE.MandatoryConnection;

public class AddMandatoryConnectionFeature extends AbstractAddFeature {

	private static final IColorConstant MANDATORY_CONNECTION_FOREGROUND = new ColorConstant(98, 131, 167);
	 
    public AddMandatoryConnectionFeature (IFeatureProvider fp) {
        super(fp);
    }
 
    public PictogramElement add(IAddContext context) {
        IAddConnectionContext addConContext = (IAddConnectionContext) context;
        MandatoryConnection addedMandatoryConnection = (MandatoryConnection) context.getNewObject();
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        // CONNECTION WITH POLYLINE
        Connection connection = peCreateService
            .createFreeFormConnection(getDiagram());
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
 
        IGaService gaService = Graphiti.getGaService();
        Polyline polyline = gaService.createPolyline(connection);
        polyline.setLineWidth(2);
        polyline.setForeground(manageColor(MANDATORY_CONNECTION_FOREGROUND));
//        polyline.setLineStyle(StylesPackage.);
 
        // create link and wire it
        link(connection, addedMandatoryConnection);
 
        // add static graphical decorator (composition and navigable)
        ConnectionDecorator cd;
        cd = peCreateService
              .createConnectionDecorator(connection, false, 1.0, true);
        createEllipse(cd);
        
        return connection;
    }
 
    public boolean canAdd(IAddContext context) {
        // return true if given business object is an MandatoryConnection
        // note, that the context must be an instance of IAddConnectionContext
        if (context instanceof IAddConnectionContext
            && context.getNewObject() instanceof MandatoryConnection) {
            return true;
        }
        return false;
    }

    private Ellipse createEllipse(GraphicsAlgorithmContainer gaContainer) {
        IGaService gaService = Graphiti.getGaService();
        Ellipse ellipse = gaService.createEllipse(gaContainer);
        int diameter = 15;
        ellipse.setWidth(diameter);
        ellipse.setHeight(diameter);
        ellipse.setTransparency(0.0);
        ellipse.setFilled(true);
        ellipse.setBackground(manageColor(MANDATORY_CONNECTION_FOREGROUND));
        ellipse.setForeground(manageColor(MANDATORY_CONNECTION_FOREGROUND));
        ellipse.setLineWidth(2);
        return ellipse;
    }
}
