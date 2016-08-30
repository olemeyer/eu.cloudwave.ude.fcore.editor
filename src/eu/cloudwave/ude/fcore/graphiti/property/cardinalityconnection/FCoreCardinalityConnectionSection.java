package eu.cloudwave.ude.fcore.graphiti.property.cardinalityconnection;

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

import eu.cloudwave.ude.fcore.graphiti.FCORE.CardinalityConnection;

public class FCoreCardinalityConnectionSection extends GFPropertySection implements
		ITabbedPropertyConstants {
    private Text minText;
    private Text maxText;

	private int oldMin;
	private int oldMax;
	
	// initially true so that the old value ints will be set in refresh()
	private boolean setOlds;
    
	public FCoreCardinalityConnectionSection() {
		setOlds = true;
	}
	
    @Override
    public void createControls(Composite parent,
        TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
 
        TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
        Composite composite = factory.createFlatFormComposite(parent);
        
        createMin(factory, composite);
        createMax(factory, composite);
    }
    
    private void createMin(TabbedPropertySheetWidgetFactory factory, Composite composite) {
    	FormData data;
    	minText = factory.createText(composite, "");
    	data = new FormData();
    	data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
    	data.right = new FormAttachment(0, 200);
    	data.top = new FormAttachment(0, VSPACE);
    	minText.setLayoutData(data);
    	
    	CLabel minValueLabel = factory.createCLabel(composite,  "min:");
    	data = new FormData();
    	data.left = new FormAttachment(0, 0);
    	data.right = new FormAttachment(minText, 0);
    	data.top = new FormAttachment(minText, 0, SWT.CENTER);
    	minValueLabel.setLayoutData(data);
    	
    	minText.addModifyListener(new ModifyListener() {

    		@Override
    		public void modifyText(ModifyEvent e) {
    			String regex = "\\d+";  //any positive number
    			if (minText.getText().matches(regex)
    					&& maxText.getText().matches(regex)) {
    				int minValue;
					try {
						minValue = Integer.parseInt(minText.getText());
					} catch (NumberFormatException numberFormatException) {
						minValue = Integer.MAX_VALUE;
						minText.setText(Integer.MAX_VALUE + "");
					}
					int maxValue;
					try {
						maxValue = Integer.parseInt(maxText.getText());
					} catch (NumberFormatException numberFormatException) {
						maxValue = Integer.MAX_VALUE;
						maxText.setText(Integer.MAX_VALUE + "");
					}
    				if (minValue <= maxValue & minValue >= 0) {
    					PictogramElement pe = getSelectedPictogramElement();
    					if (pe != null) {
    						Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
    						// the filter assured, that it is a CardinalityConnection
    						if (bo == null)
    							return;
    						int min = ((CardinalityConnection) bo).getMin();
    						if (minValue == min)
    							return;
    					}
    					final int typedValue = minValue;
    					IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {
    						
    						@Override
    						public void execute(IContext context) {
    							PictogramElement pe = getSelectedPictogramElement();
    							if (pe != null) {
    								Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
    								// the filter assured, that it is a CardinalityConnection
    								if (bo == null)
    									return;
    								CardinalityConnection cardinalityConnection = (CardinalityConnection) bo;
    								cardinalityConnection.setMin(typedValue);
    								oldMin = typedValue;
    							}
    						}
    						
    						@Override
    						public boolean canExecute(IContext context) {
    							return true;
    						}
    					};
    					CustomContext context = new CustomContext();
    					execute(feature, context);
    				} else {
						minText.setText(maxValue + "");
					}
    			} else {
    				if (!minText.getText().equals(oldMin + ""))
						minText.setText(oldMin + "");
    			}
    		}
    	});
    }

	private void createMax(TabbedPropertySheetWidgetFactory factory, Composite composite) {
		FormData data;
		maxText = factory.createText(composite, "");
        data = new FormData();
        data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(0, 200);
        data.top = new FormAttachment(0, 8*VSPACE);
        maxText.setLayoutData(data);
 
        CLabel maxValueLabel = factory.createCLabel(composite,  "max:");
        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(maxText, 0);
        data.top = new FormAttachment(maxText, 0, SWT.CENTER);
        maxValueLabel.setLayoutData(data);
        
        maxText.addModifyListener(new ModifyListener() {

    		@Override
    		public void modifyText(ModifyEvent e) {
    			String regex = "\\d+"; //any positive number
    			if (minText.getText().matches(regex)
    					&& maxText.getText().matches(regex)) {
    				int minValue;
					try {
						minValue = Integer.parseInt(minText.getText());
					} catch (NumberFormatException numberFormatException) {
						minValue = Integer.MAX_VALUE;
						minText.setText(Integer.MAX_VALUE + "");
					}
					int maxValue;
					try {
						maxValue = Integer.parseInt(maxText.getText());
					} catch (NumberFormatException numberFormatException) {
						maxValue = Integer.MAX_VALUE;
						maxText.setText(Integer.MAX_VALUE + "");
					}
    				if (minValue <= maxValue & minValue >= 0) {
    					PictogramElement pe = getSelectedPictogramElement();
    					if (pe != null) {
    						Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
    						// the filter assured, that it is a CardinalityConnection
    						if (bo == null)
    							return;
    						int max = ((CardinalityConnection) bo).getMax();
    						if (maxValue == max)
    							return;
    					}
    					final int typedValue = maxValue;
    					IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {
    						
    						@Override
    						public void execute(IContext context) {
    							PictogramElement pe = getSelectedPictogramElement();
    							if (pe != null) {
    								Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
    								// the filter assured, that it is a CardinalityConnection
    								if (bo == null)
    									return;
    								CardinalityConnection cardinalityConnection = (CardinalityConnection) bo;
    								cardinalityConnection.setMax(typedValue);
    								oldMax = typedValue;
    							}
    						}
    						
    						@Override
    						public boolean canExecute(IContext context) {
    							return true;
    						}
    					};
    					CustomContext context = new CustomContext();
    					execute(feature, context);
    				} else {
						maxText.setText(minValue + "");
					}
    			} else {
					if (!maxText.getText().equals(oldMax + ""))
						maxText.setText(oldMax + "");
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
            // the filter assured, that it is a CardinalityConnection
            if (bo == null)
                return;
            int min = ((CardinalityConnection) bo).getMin();
            minText.setText(min + "");
            int max = ((CardinalityConnection) bo).getMax();
            maxText.setText(max + "");
            if (setOlds) {
				oldMin = min;
				oldMax = max;
				setOlds = false;
			}
        }
    }
}
