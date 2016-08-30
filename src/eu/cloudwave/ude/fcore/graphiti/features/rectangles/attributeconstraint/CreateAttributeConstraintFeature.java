package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.AttributeConstraint;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.validation.AttributeContraintInputValidator;

public class CreateAttributeConstraintFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create Attribute Constraint";
	 
    private static final String USER_QUESTION = "Enter new Attribute Constraint";
 
    public CreateAttributeConstraintFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Attribute Constraint", "Create Attribute Constraint");
    }
 
    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for AttributeConstraint name
        String newEquation = IDUtil.askString(TITLE, USER_QUESTION,  "", new AttributeContraintInputValidator());
        if (newEquation == null || newEquation.trim().length() == 0) {
            return EMPTY;
        }
 
        // create AttributeConstraint
        AttributeConstraint newAttributeConstraint = FCOREFactory.eINSTANCE.createAttributeConstraint();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newAttributeConstraint);
        newAttributeConstraint.setEquation(newEquation);
 
        // do the add
        addGraphicalRepresentation(context, newAttributeConstraint);
 
        // return newly created business object(s)
        return new Object[] { newAttributeConstraint };
    }

}
