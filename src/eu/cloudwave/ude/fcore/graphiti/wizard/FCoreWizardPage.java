package eu.cloudwave.ude.fcore.graphiti.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class FCoreWizardPage extends WizardNewFileCreationPage {

	protected FCoreWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle("New FCore Diagram");
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		setPageComplete(validatePage());
	}

}
