package eu.cloudwave.ude.fcore.graphiti.features.rectangles.softgoal;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Softgoal;
import eu.cloudwave.ude.fcore.graphiti.validation.NameInputValidator;

public class CreateSoftgoalFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create softgoal";
	 
    private static final String USER_QUESTION = "Enter new softgoal name";
 
    public CreateSoftgoalFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Softgoal", "Create Softgoal");
    }
 
    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for Softgoal name
        String newClassName = IDUtil.askString(TITLE, USER_QUESTION,  "", new NameInputValidator());
        if (newClassName == null || newClassName.trim().length() == 0) {
            return EMPTY;
        }
 
        // create Softgoal
        Softgoal newSoftgoal = FCOREFactory.eINSTANCE.createSoftgoal();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newSoftgoal);
        newSoftgoal.setName(newClassName);
 
        // do the add
        addGraphicalRepresentation(context, newSoftgoal);
 
        // return newly created business object(s)
        return new Object[] { newSoftgoal };
    }

}
