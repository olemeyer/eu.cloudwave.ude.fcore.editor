package eu.cloudwave.ude.fcore.graphiti.features.rectangles.featuregroup;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;

public class ResizeFeatureGroupFeature extends DefaultResizeShapeFeature {
	
	public ResizeFeatureGroupFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public boolean canResizeShape(IResizeShapeContext context) {    	
    	return false;
    }
	
}
