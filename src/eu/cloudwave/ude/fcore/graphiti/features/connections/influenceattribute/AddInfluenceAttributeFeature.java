package eu.cloudwave.ude.fcore.graphiti.features.connections.influenceattribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
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
import eu.cloudwave.ude.fcore.graphiti.FCORE.InfluenceAttribute;

public class AddInfluenceAttributeFeature extends AbstractAddFeature {

	private static final IColorConstant INFLUENCE_ATTRIBUTE_FOREGROUND = new ColorConstant(98, 131, 167);
	 
    public AddInfluenceAttributeFeature (IFeatureProvider fp) {
        super(fp);
    }
 
    public PictogramElement add(IAddContext context) {
        IAddConnectionContext addConContext = (IAddConnectionContext) context;
        InfluenceAttribute addedInfluenceAttribute = (InfluenceAttribute) context.getNewObject();
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        // CONNECTION WITH POLYLINE
        Connection connection = peCreateService
            .createFreeFormConnection(getDiagram());
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
 
        IGaService gaService = Graphiti.getGaService();
        Polyline polyline = gaService.createPolyline(connection);
        polyline.setLineWidth(2);
        polyline.setForeground(manageColor(INFLUENCE_ATTRIBUTE_FOREGROUND));
//        polyline.setLineStyle(StylesPackage.);
 
        // create link and wire it
        link(connection, addedInfluenceAttribute);
        
        // add dynamic text decorator for the association name 
        ConnectionDecorator textDecorator =
            peCreateService.createConnectionDecorator(connection, true,
            0.8, true);
        Text text = gaService.createDefaultText(getDiagram(),textDecorator);
        text.setForeground(manageColor(IColorConstant.BLACK));
        gaService.setLocation(text, 10, 0);
        // set reference name in the text decorator
        InfluenceAttribute influenceAttribute = (InfluenceAttribute) context.getNewObject();
        float contribution = influenceAttribute.getContribution();
        text.setValue(FCoreUtil.getContributionString(contribution));
    
        // add static graphical decorator (composition and navigable)
        ConnectionDecorator cd;
        cd = peCreateService
              .createConnectionDecorator(connection, false, 1.0, true);
        createArrow(cd);
 
        return connection;
    }
 
    public boolean canAdd(IAddContext context) {
        // return true if given business object is an InfluenceAttribute
        // note, that the context must be an instance of IAddConnectionContext
        if (context instanceof IAddConnectionContext
            && context.getNewObject() instanceof InfluenceAttribute) {
            return true;
        }
        return false;
    }
    
    private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
        IGaService gaService = Graphiti.getGaService();
        Polyline polyline =
            gaService.createPolyline(gaContainer, new int[] { -15, 10, 0, 0, -15,
                    -10 });
        polyline.setForeground(manageColor(INFLUENCE_ATTRIBUTE_FOREGROUND));
        polyline.setLineWidth(2);
        return polyline;
    }

}
