package eu.cloudwave.ude.fcore.graphiti;

import java.util.Collection;

import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

import eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute.AttributeConstants;

public class FCoreUtil {
	
	public static String getContributionString(float contribution) {
		if (contribution > 0 && contribution <= 1)
        	return "+" + contribution;
        else if (contribution <= 0 && contribution >= -1)
        	return contribution + "";
		return null;
	}
	
	public static String getCardinalityString(int min, int max) {
		return "[" + min + ".." + max + "]";
	}
	
	public static String getAttributStringString(String name, int min, int max) {
		return name + " [" + min + ".." + max + "]";
	}
	
	public static void setAttributeLocationAndSize(Shape shape, int containerWidth, int yMultiplier) {
		GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
		IGaService gaService = Graphiti.getGaService();
		int width = containerWidth - AttributeConstants.ATTRIBUTE_WIDTH_DIFF.getValue();
    	int height = AttributeConstants.ATTRIBUTE_HEIGHT.getValue();
    	int x = AttributeConstants.ATTRIBUTE_X.getValue();
    	int y = (AttributeConstants.ATTRIBUTE_Y.getValue() * yMultiplier)
        		+ AttributeConstants.ATTRIBUTE_Y_ADD.getValue();
        gaService.setLocationAndSize(graphicsAlgorithm, x, y, width, height);
        Collection<PictogramElement> childrenPEs = Graphiti.getPeService().getPictogramElementChildren(shape);
		for (PictogramElement childPE : childrenPEs) {
			if (childPE.getGraphicsAlgorithm() instanceof Text) {
				Text text = (Text) childPE.getGraphicsAlgorithm();
				gaService.setWidth(text, width);
			}
		}
	}
}
