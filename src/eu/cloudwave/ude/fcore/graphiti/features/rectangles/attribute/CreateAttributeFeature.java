package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;

import eu.cloudwave.ude.fcore.graphiti.IDUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;
import eu.cloudwave.ude.fcore.graphiti.validation.NameInputValidator;

public class CreateAttributeFeature extends AbstractCreateFeature {

	private static final String TITLE = "Create Attribute";
	 
    private static final String USER_QUESTION = "Enter new Attribute name";
 
    public CreateAttributeFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Attribute", "Create Attribute");
    }
 
    public boolean canCreate(ICreateContext context) {
    	PictogramLink pl = null;
    	for (Object o : context.getTargetContainer().eContents()) {
    		if (o instanceof PictogramLink) {
    			pl = (PictogramLink) o;
    			if (pl.getBusinessObjects().get(0) instanceof Feature)
					return true;
				else
					return false;
    		}
    	}
    	return false;
    }
 
    public Object[] create(ICreateContext context) {
        // ask user for Attribute name
        String newName = IDUtil.askString(TITLE, USER_QUESTION,  "", new NameInputValidator());
        if (newName == null || newName.trim().length() == 0) {
            return EMPTY;
        }
 
        // create Attribute
        Attribute newAttribute = FCOREFactory.eINSTANCE.createAttribute();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newAttribute);
        newAttribute.setName(newName);
        
        // add attribute to the target business object
        PictogramLink pl = null;
    	for (Object o : context.getTargetContainer().eContents()) {
    		if (o instanceof PictogramLink) {
    			pl = (PictogramLink) o;
    			// the feature is always the first element of the business objects
    			Feature f = (Feature) pl.getBusinessObjects().get(0);
    			f.getAttributes().add(newAttribute);
    			break;
    		}
    	}
 
        // do the add
        addGraphicalRepresentation(context, newAttribute);
 
        // return newly created business object(s)
        return new Object[] { newAttribute };
    }

}
