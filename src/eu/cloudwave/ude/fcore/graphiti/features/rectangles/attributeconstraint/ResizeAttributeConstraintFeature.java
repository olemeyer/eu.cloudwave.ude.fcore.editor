package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attributeconstraint;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;

public class ResizeAttributeConstraintFeature extends DefaultResizeShapeFeature {
	
	public ResizeAttributeConstraintFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public boolean canResizeShape(IResizeShapeContext context) {    	
    	return false;
    }
	
}
