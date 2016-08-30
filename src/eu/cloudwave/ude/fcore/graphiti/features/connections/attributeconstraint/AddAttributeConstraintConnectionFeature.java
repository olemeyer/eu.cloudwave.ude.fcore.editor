package eu.cloudwave.ude.fcore.graphiti.features.connections.attributeconstraint;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraintConnection;

public class AddAttributeConstraintConnectionFeature extends AbstractAddFeature {

	private static final IColorConstant ATTRIBUTE_CONSTRAINT_FOREGROUND = new ColorConstant(98, 131, 167);
	 
    public AddAttributeConstraintConnectionFeature (IFeatureProvider fp) {
        super(fp);
    }
 
    public PictogramElement add(IAddContext context) {
        IAddConnectionContext addConContext = (IAddConnectionContext) context;
        AttributeConstraintConnection addedAttributeConstraint = (AttributeConstraintConnection) context.getNewObject();
        IPeCreateService peCreateService = Graphiti.getPeCreateService();
        
        // CONNECTION WITH POLYLINE
        Connection connection = peCreateService
            .createFreeFormConnection(getDiagram());
        connection.setStart(addConContext.getSourceAnchor());
        connection.setEnd(addConContext.getTargetAnchor());
 
        IGaService gaService = Graphiti.getGaService();
        Polyline polyline = gaService.createPolyline(connection);
        polyline.setLineWidth(2);
        polyline.setForeground(manageColor(ATTRIBUTE_CONSTRAINT_FOREGROUND));
//        polyline.setLineStyle(StylesPackage.);
 
        // create link and wire it
        link(connection, addedAttributeConstraint);
 
        return connection;
    }
 
    public boolean canAdd(IAddContext context) {
        // return true if given business object is an AttributeConstraintConnection
        // note, that the context must be an instance of IAddConnectionContext
        if (context instanceof IAddConnectionContext
            && context.getNewObject() instanceof AttributeConstraintConnection) {
            return true;
        }
        return false;
    }

}
