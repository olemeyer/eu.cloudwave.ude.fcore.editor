package eu.cloudwave.ude.fcore.graphiti.wizard;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.examples.common.FileService;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class FCoreWizard extends Wizard implements INewWizard {

	private FCoreWizardPage page;

	@Override
	public boolean performFinish() {
		String fileName = page.getFileName();
		if (fileName.contains("."))
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		else
			fileName = fileName.substring(0, fileName.length());
		String fileExtension = "diagram";
		String diagramTypeID = "eu.cloudwave.ude.fcore.graphiti.diagram.fCoreDiagramType";
		// Create the diagram
		final Diagram newDiagram = Graphiti.getPeCreateService().createDiagram(diagramTypeID, fileName, true);

		// Create the file
		
		final URI uri = URI.createPlatformResourceURI(page.getContainerFullPath().append(fileName + "." + fileExtension).toString(), true);
		FileService.createEmfFileForDiagram(uri, newDiagram);
		
		final URIEditorInput editorInput = new URIEditorInput(uri);
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(editorInput, DiagramEditor.DIAGRAM_EDITOR_ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		addPages(selection);
	}

	public void addPages(IStructuredSelection selection) {
		page = new FCoreWizardPage("New FCore Diagram Wizard Page", selection);
		addPage(page);
	}

}
