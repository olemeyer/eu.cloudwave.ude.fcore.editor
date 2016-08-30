package eu.cloudwave.ude.fcore.graphiti.features.rectangles.groupfeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.GroupFeature;
import eu.cloudwave.ude.fcore.graphiti.validation.NameInputValidator;

public class CreateGroupFeatureFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create Group Feature";
	 
    private static final String USER_QUESTION = "Enter new Group Feature name";
 
    public CreateGroupFeatureFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Group Feature", "Create Group Feature");
    }
 
    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for GroupFeature name
        String newName = IDUtil.askString(TITLE, USER_QUESTION,  "", new NameInputValidator());
        if (newName == null || newName.trim().length() == 0) {
            return EMPTY;
        }
 
        // create GroupFeature
        GroupFeature newGroupFeature = FCOREFactory.eINSTANCE.createGroupFeature();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newGroupFeature);
        newGroupFeature.setName(newName);
 
        // do the add
        addGraphicalRepresentation(context, newGroupFeature);
 
        // return newly created business object(s)
        return new Object[] { newGroupFeature };
    }

}
