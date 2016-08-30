package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;

public class ResizeAttributeFeature extends DefaultResizeShapeFeature {
	
	public ResizeAttributeFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public boolean canResizeShape(IResizeShapeContext context) {    	
    	return false;
    }
	
}
