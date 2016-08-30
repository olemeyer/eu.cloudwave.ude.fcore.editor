package eu.cloudwave.ude.fcore.graphiti.features.rectangles.attribute;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

public class RemoveAttributeFeature extends DefaultRemoveFeature {

//	private static final String NAME = "Remove Attribute";
//	private static final String DESC = "Removes an Attribute from the Diagram";
	private PictogramElement parentPictogramElement = null;
	
	public RemoveAttributeFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public void preRemove(IRemoveContext context) {
		PictogramElement pe = context.getPictogramElement();
		IPeService peService = Graphiti.getPeService();
		parentPictogramElement = peService.getPictogramElementParent(pe);
	}

	public void postRemove(IRemoveContext context) {
		layoutPictogramElement(parentPictogramElement);
	}
	
}
