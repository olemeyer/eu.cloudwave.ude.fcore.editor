package eu.cloudwave.ude.fcore.graphiti.features.rectangles.rootfeature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.RootFeature;
import eu.cloudwave.ude.fcore.graphiti.validation.NameInputValidator;

public class CreateRootFeatureFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create Root Feature";
	 
    private static final String USER_QUESTION = "Enter new Root Feature name";
 
    public CreateRootFeatureFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Root Feature", "Create Root Feature");
    }
 
    public boolean canCreate(ICreateContext context) {
    	if (context.getTargetContainer() instanceof Diagram) {
    		Diagram diagram = (Diagram) context.getTargetContainer();
    		for (Object o : diagram.getChildren()) {
    			ContainerShape cs = (ContainerShape) o;
    			PictogramLink pl = (PictogramLink) cs.getLink();
    			Object object = getBusinessObjectForPictogramElement(pl.getPictogramElement());
    			if (object instanceof RootFeature)
    				return false;
    		}
    		return true;
    	}
        return false;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for RootFeature name
        String newName = IDUtil.askString(TITLE, USER_QUESTION,  "", new NameInputValidator());
        if (newName == null || newName.trim().length() == 0) {
            return EMPTY;
        }
 
        // create RootFeature
        RootFeature newRootFeature = FCOREFactory.eINSTANCE.createRootFeature();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newRootFeature);
        newRootFeature.setName(newName);
 
        // do the add
        addGraphicalRepresentation(context, newRootFeature);
 
        // return newly created business object(s)
        return new Object[] { newRootFeature };
    }

}
