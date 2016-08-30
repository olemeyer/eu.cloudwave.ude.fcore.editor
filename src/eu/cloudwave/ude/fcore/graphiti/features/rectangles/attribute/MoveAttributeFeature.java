package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;

public class MoveAttributeFeature extends DefaultMoveShapeFeature {
	
	public MoveAttributeFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public boolean canMoveShape(IMoveShapeContext context) {
    	return false;
    }
    
}
