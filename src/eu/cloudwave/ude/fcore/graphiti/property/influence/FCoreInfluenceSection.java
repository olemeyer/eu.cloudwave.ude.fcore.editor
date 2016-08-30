package eu.cloudwave.ude.fcore.graphiti.property.influence;

import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.impl.AbstractFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import eu.cloudwave.ude.fcore.graphiti.FCORE.Influence;

public class FCoreInfluenceSection extends GFPropertySection implements
		ITabbedPropertyConstants {
    private Text contributionText;
    
    private float oldContribution;
    
    // initially true so that the old value ints will be set in refresh()
 	private boolean setOlds;
    
    public FCoreInfluenceSection() {
		setOlds = true;
	}
    
    @Override
    public void createControls(Composite parent,
        TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
 
        TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
        Composite composite = factory.createFlatFormComposite(parent);
        
        createcontribution(factory, composite);
    }
    
    private void createcontribution(TabbedPropertySheetWidgetFactory factory, Composite composite) {
    	FormData data;
    	contributionText = factory.createText(composite, "");
    	data = new FormData();
    	data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
    	data.right = new FormAttachment(0, 200);
    	data.top = new FormAttachment(0, VSPACE);
    	contributionText.setLayoutData(data);
    	
    	CLabel contributionValueLabel = factory.createCLabel(composite,  "contribution:");
    	data = new FormData();
    	data.left = new FormAttachment(0, 0);
    	data.right = new FormAttachment(contributionText, 0);
    	data.top = new FormAttachment(contributionText, 0, SWT.CENTER);
    	contributionValueLabel.setLayoutData(data);
    	
    	contributionText.addModifyListener(new ModifyListener() {

    		@Override
    		public void modifyText(ModifyEvent e) {
    			String contributionTextValue = contributionText.getText();
    			if (contributionTextValue.length() >= 3 || contributionTextValue.length() <= 5) {
    				String regex = "[-]?(1\\.0|0\\.[0-9]?[1-9])"; // number between -1.0 and 1.0
					if (contributionTextValue.matches(regex)) {
						float contributionValue = Float.parseFloat(contributionText.getText());
						if (contributionValue >= -1 && contributionValue <= 1 && contributionValue != 0) {
							PictogramElement pe = getSelectedPictogramElement();
							if (pe != null) {
								Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
								// the filter assured, that it is a Influence
								if (bo == null)
									return;
								float contribution = ((Influence) bo).getContribution();
								if (contributionValue == contribution)
									return;
							}
							final float typedValue = contributionValue;
							IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {

								@Override
								public void execute(IContext context) {
									PictogramElement pe = getSelectedPictogramElement();
									if (pe != null) {
										Object bo = Graphiti.getLinkService()
												.getBusinessObjectForLinkedPictogramElement(pe);
										// the filter assured, that it is a
										// Influence
										if (bo == null)
											return;
										Influence influence = (Influence) bo;
										influence.setContribution(typedValue);
										oldContribution = typedValue;
									}
								}

								@Override
								public boolean canExecute(IContext context) {
									return true;
								}
							};
							CustomContext context = new CustomContext();
							execute(feature, context);
						}
	    			}
    			} else {
    				if (!contributionText.getText().equals(oldContribution + "") &&
    						(contributionText.getText().length() >= 3 || contributionText.getText().length() <= 5))
    					contributionText.setText(oldContribution + "");
    			}
    		}
    	});
    }
 
    @Override
    public void refresh() {
        PictogramElement pe = getSelectedPictogramElement();
        if (pe != null) {
            Object bo = Graphiti.getLinkService()
                 .getBusinessObjectForLinkedPictogramElement(pe);
            // the filter assured, that it is a Influence
            if (bo == null)
                return;
            float contribution = ((Influence) bo).getContribution();
            contributionText.setText(contribution + "");
            if (setOlds) {
				oldContribution = contribution;
				setOlds = false;
			}
        }
    }
}
