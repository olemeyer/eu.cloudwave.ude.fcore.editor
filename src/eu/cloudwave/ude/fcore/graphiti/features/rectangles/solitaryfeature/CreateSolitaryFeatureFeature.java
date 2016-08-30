package eu.cloudwave.ude.fcore.graphiti.features.rectangles.solitaryfeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.SolitaryFeature;
import eu.cloudwave.ude.fcore.graphiti.validation.NameInputValidator;

public class CreateSolitaryFeatureFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create Solitary Feature";
	 
    private static final String USER_QUESTION = "Enter new Solitary Feature name";
 
    public CreateSolitaryFeatureFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Solitary Feature", "Create Solitary Feature");
    }
 
    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for SolitaryFeature name
        String newName = IDUtil.askString(TITLE, USER_QUESTION,  "", new NameInputValidator());
        if (newName == null || newName.trim().length() == 0) {
            return EMPTY;
        }
 
        // create SolitaryFeature
        SolitaryFeature newSolitaryFeature = FCOREFactory.eINSTANCE.createSolitaryFeature();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newSolitaryFeature);
        newSolitaryFeature.setName(newName);
 
        // do the add
        addGraphicalRepresentation(context, newSolitaryFeature);
 
        // return newly created business object(s)
        return new Object[] { newSolitaryFeature };
    }

}
