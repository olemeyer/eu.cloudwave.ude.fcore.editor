package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import eu.cloudwave.ude.fcore.graphiti.FCoreUtil;
import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;

public class UpdateAttributeFeature extends AbstractUpdateFeature {

	public UpdateAttributeFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a Attribute
        Object bo =
            getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof Attribute);
    }
 
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve name from pictogram model
        String pictogramName = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement  instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    String textValue = text.getValue();
                    if (textValue.length() > 7) {
                    	int end = textValue.lastIndexOf("[");
                    	pictogramName = textValue.substring(0, end - 1);
                    } else {
                    	pictogramName = textValue;
                    }
                }
            }
        }
 
        // retrieve name from business model
        String businessName = null;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof Attribute) {
        	Attribute attribute = (Attribute) bo;
            businessName = attribute.getName();
        }
 
        // update needed, if names are different
        boolean updateNameNeeded =
            ((pictogramName == null && businessName != null) || 
                (pictogramName != null && !pictogramName.equals(businessName)));
        if (updateNameNeeded) {
            return Reason.createTrueReason("Name is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }
 
    public boolean update(IUpdateContext context) {
        // retrieve name from business model
        String businessName = null;
        int businessMin = 0;
        int businessMax = 0;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof Attribute) {
        	Attribute attribute = (Attribute) bo;
            businessName = attribute.getName();
            businessMin = attribute.getMin();
            businessMax = attribute.getMax();
        }
 
        // Set name in pictogram model
        if (pictogramElement instanceof ContainerShape) {
            ContainerShape cs = (ContainerShape) pictogramElement;
            for (Shape shape : cs.getChildren()) {
                if (shape.getGraphicsAlgorithm() instanceof Text) {
                    Text text = (Text) shape.getGraphicsAlgorithm();
                    text.setValue(FCoreUtil.getAttributStringString(businessName, businessMin, businessMax));
                    PictogramElement featurePictogramElement = cs.getContainer();
                    layoutPictogramElement(featurePictogramElement);
                    return true;
                }
            }
        }
        
        return false;
    }

}
