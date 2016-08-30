package eu.cloudwave.ude.fcore.graphiti.export;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

public class CantExportPage extends WizardPage {

	/**
	 * Create the wizard.
	 */
	public CantExportPage() {
		super("wizardPage");
		setTitle("Export not possible");
		setDescription("");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Label lblPleaseSelectA = new Label(container, SWT.NONE);
		lblPleaseSelectA.setText("Please select a valid fcore diagram!");
	}

}
