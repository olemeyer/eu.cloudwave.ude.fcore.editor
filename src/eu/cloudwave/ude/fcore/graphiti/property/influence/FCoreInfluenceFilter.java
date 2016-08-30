package eu.cloudwave.ude.fcore.graphiti.property.influence;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import eu.cloudwave.ude.fcore.graphiti.FCORE.Influence;

public class FCoreInfluenceFilter extends AbstractPropertySectionFilter {
	
	@Override
    protected boolean accept(PictogramElement pe) {
        EObject eObject =
            Graphiti.getLinkService()
            .getBusinessObjectForLinkedPictogramElement(pe);
        if (eObject instanceof Influence) {
            return true;
        }
        return false;
    }

}
