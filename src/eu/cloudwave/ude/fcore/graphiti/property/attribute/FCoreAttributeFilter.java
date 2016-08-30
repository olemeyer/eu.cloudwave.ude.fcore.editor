package eu.cloudwave.ude.fcore.graphiti.property.attribute;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;

public class FCoreAttributeFilter extends AbstractPropertySectionFilter {
	
	@Override
    protected boolean accept(PictogramElement pe) {
        EObject eObject =
            Graphiti.getLinkService()
            .getBusinessObjectForLinkedPictogramElement(pe);
        if (eObject instanceof Attribute) {
            return true;
        }
        return false;
    }

}
