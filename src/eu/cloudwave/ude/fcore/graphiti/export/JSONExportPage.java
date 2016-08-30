package eu.cloudwave.ude.fcore.graphiti.export;

import java.io.File;

import javax.swing.JFileChooser;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class JSONExportPage extends WizardPage {
	private Text text;

	/**
	 * Create the wizard.
	 */
	public JSONExportPage() {
		super("wizardPage");
		setTitle("Export features to JSON");
		setDescription("Export features from a fcore model as JSON file");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));
		
		Label lblChooseAFile = new Label(container, SWT.NONE);
		lblChooseAFile.setText("Choose a file:");
		new Label(container, SWT.NONE);
		
		text = new Text(container, SWT.BORDER);
		text.setEditable(true);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog=new FileDialog(button.getShell(),SWT.OPEN);
				fileDialog.setFilterExtensions(new String[]{"json"});
				fileDialog.setText("Choose a JSON file");
				String path=fileDialog.open();
				if(path!=null){
					text.setText(path);
				}
			}
		});
		button.setText("...");
	}
	
	public File getFile(){
		return new File(text.getText());
		
	}
}
