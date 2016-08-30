package eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREFactory;
import eu.cloudwave.ude.fcore.graphiti.FCORE.FeatureGroup;

public class CreateFeatureGroupFeature extends AbstractCreateFeature {
	
    public CreateFeatureGroupFeature(IFeatureProvider fp) {
        // set name and description of the creation feature
        super(fp, "Feature Group", "Create Feature Group");
    }
 
    public boolean canCreate(ICreateContext context) {
        return context.getTargetContainer() instanceof Diagram;
    }
 
    public Object[] create(ICreateContext context) {
        
        // create FeatureGroup
        FeatureGroup newFeatureGroup = FCOREFactory.eINSTANCE.createFeatureGroup();
        // Add model element to resource.
        // We add the model element to the resource of the diagram for
        // simplicity's sake. Normally, a customer would use its own
        // model persistence layer for storing the business model separately.
        getDiagram().eResource().getContents().add(newFeatureGroup);
        newFeatureGroup.setMin(1);
        newFeatureGroup.setMax(1);
 
        // do the add
        addGraphicalRepresentation(context, newFeatureGroup);
 
        // return newly created business object(s)
        return new Object[] { newFeatureGroup };
    }

}
