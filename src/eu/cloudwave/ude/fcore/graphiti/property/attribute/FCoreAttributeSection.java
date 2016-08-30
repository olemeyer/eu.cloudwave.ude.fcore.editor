package eu.cloudwave.ude.fcore.graphiti.property.attribute;

import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.impl.AbstractFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
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

import eu.cloudwave.ude.fcore.graphiti.FCORE.Attribute;

public class FCoreAttributeSection extends GFPropertySection implements ITabbedPropertyConstants {
	private Text nameText;
	private Text valueText;
	private Text minText;
	private Text maxText;

	private int oldValue;
	private int oldMin;
	private int oldMax;

	// initially true so that the old value ints will be set in refresh()
	private boolean setOlds;

	public FCoreAttributeSection() {
		setOlds = true;
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		Composite composite = factory.createFlatFormComposite(parent);

		createName(factory, composite);
		createValue(factory, composite);
		createMin(factory, composite);
		createMax(factory, composite);
	}

	private void createName(TabbedPropertySheetWidgetFactory factory, Composite composite) {
		FormData data;
		nameText = factory.createText(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 200);
		data.top = new FormAttachment(0, VSPACE);
		nameText.setLayoutData(data);

		CLabel nameValueLabel = factory.createCLabel(composite, "Name:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(nameText, 0);
		data.top = new FormAttachment(nameText, 0, SWT.CENTER);
		nameValueLabel.setLayoutData(data);

		nameText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String regex = "[a-zA-Z]\\w*"; // non digit followed by any
												// number of character
				if (nameText.getText().matches(regex)) {
					String nameValue = nameText.getText();
					PictogramElement pe = getSelectedPictogramElement();
					if (pe != null) {
						Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
						// the filter assured, that it is a FeatureGroup
						if (bo == null)
							return;
						String name = ((Attribute) bo).getName();
						if (nameValue.equals(name))
							return;
					}
					final String typedValue = nameValue;
					IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {

						@Override
						public void execute(IContext context) {
							PictogramElement pe = getSelectedPictogramElement();
							if (pe != null) {
								Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
								// the filter assured, that it is a FeatureGroup
								if (bo == null)
									return;
								Attribute attribute = (Attribute) bo;
								attribute.setName(typedValue);
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
		});
	}

	private void createValue(TabbedPropertySheetWidgetFactory factory, Composite composite) {
		FormData data;
		valueText = factory.createText(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 200);
		data.top = new FormAttachment(0, 8 * VSPACE);
		valueText.setLayoutData(data);

		CLabel valueValueLabel = factory.createCLabel(composite, "value:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(valueText, 0);
		data.top = new FormAttachment(valueText, 0, SWT.CENTER);
		valueValueLabel.setLayoutData(data);

		valueText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String regex = "\\d+"; // any positive number
				if (valueText.getText().matches(regex)) {
					int valueValue;
					try {
						valueValue = Integer.parseInt(valueText.getText());
					} catch (NumberFormatException numberFormatException) {
						valueValue = Integer.MAX_VALUE;
						valueText.setText(Integer.MAX_VALUE + "");
					}
					PictogramElement pe = getSelectedPictogramElement();
					if (pe != null) {
						Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
						// the filter assured, that it is a FeatureGroup
						if (bo == null)
							return;
						int value = ((Attribute) bo).getValue();
						if (valueValue == value)
							return;
					}
					final int typedValue = valueValue;
					IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {

						@Override
						public void execute(IContext context) {
							PictogramElement pe = getSelectedPictogramElement();
							if (pe != null) {
								Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
								// the filter assured, that it is a Attribute
								if (bo == null)
									return;
								Attribute attribute = (Attribute) bo;
								attribute.setValue(typedValue);
								oldValue = typedValue;
							}
						}

						@Override
						public boolean canExecute(IContext context) {
							return true;
						}
					};
					CustomContext context = new CustomContext();
					execute(feature, context);
					// }
				} else {
					if (!valueText.getText().equals(oldValue + ""))
						valueText.setText(oldValue + "");
				}
			}
		});
	}

	private void createMin(TabbedPropertySheetWidgetFactory factory, Composite composite) {
		FormData data;
		minText = factory.createText(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 200);
		data.top = new FormAttachment(0, 2 * 8 * VSPACE);
		minText.setLayoutData(data);

		CLabel minValueLabel = factory.createCLabel(composite, "min:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(minText, 0);
		data.top = new FormAttachment(minText, 0, SWT.CENTER);
		minValueLabel.setLayoutData(data);

		minText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String regex = "\\d+"; // any positive number
				if (minText.getText().matches(regex) && maxText.getText().matches(regex)) {
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
							// the filter assured, that it is a Attribute
							if (bo == null)
								return;
							int min = ((Attribute) bo).getMin();
							if (minValue == min)
								return;
						}
						final int typedValue = minValue;
						IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {

							@Override
							public void execute(IContext context) {
								PictogramElement pe = getSelectedPictogramElement();
								if (pe != null) {
									Object bo = Graphiti.getLinkService()
											.getBusinessObjectForLinkedPictogramElement(pe);
									// the filter assured, that it is a
									// Attribute
									if (bo == null)
										return;
									Attribute attribute = (Attribute) bo;
									attribute.setMin(typedValue);
									oldMin = typedValue;
									updatePictogramElement((Shape) pe);
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
		data.top = new FormAttachment(0, 3 * 8 * VSPACE);
		maxText.setLayoutData(data);

		CLabel maxValueLabel = factory.createCLabel(composite, "max:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(maxText, 0);
		data.top = new FormAttachment(maxText, 0, SWT.CENTER);
		maxValueLabel.setLayoutData(data);

		maxText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				String regex = "\\d+"; // any positive number
				if (minText.getText().matches(regex) && maxText.getText().matches(regex)) {
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
							// the filter assured, that it is a Attribute
							if (bo == null)
								return;
							int max = ((Attribute) bo).getMax();
							if (maxValue == max)
								return;
						}
						final int typedValue = maxValue;
						IFeature feature = new AbstractFeature(getDiagramTypeProvider().getFeatureProvider()) {

							@Override
							public void execute(IContext context) {
								PictogramElement pe = getSelectedPictogramElement();
								if (pe != null) {
									Object bo = Graphiti.getLinkService()
											.getBusinessObjectForLinkedPictogramElement(pe);
									// the filter assured, that it is a
									// Attribute
									if (bo == null)
										return;
									Attribute attribute = (Attribute) bo;
									attribute.setMax(typedValue);
									oldMax = typedValue;
									updatePictogramElement((Shape) pe);
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
			Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			// the filter assured, that it is a Attribute
			if (bo == null)
				return;
			String name = ((Attribute) bo).getName();
			nameText.setText(name);
			int value = ((Attribute) bo).getValue();
			valueText.setText(value + "");
			int min = ((Attribute) bo).getMin();
			minText.setText(min + "");
			int max = ((Attribute) bo).getMax();
			maxText.setText(max + "");
			if (setOlds) {
				oldValue = value;
				oldMin = min;
				oldMax = max;
				setOlds = false;
			}
		}
	}
}
