package eu.cloudwave.ude.fcore.graphiti.export;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.emfjson.jackson.resource.JsonResourceFactory;


import eu.cloudwave.ude.fcore.graphiti.FCORE.Feature;

public class JSONExporter extends Wizard implements IExportWizard {
	
	private JSONExportPage jsonExportPage=new JSONExportPage();
	private List<EObject> eobjects;

	public JSONExporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		try{
			if(selection.getFirstElement() instanceof IFile){
				IFile selected=(IFile) selection.getFirstElement();
				File file=selected.getLocation().toFile();
				eobjects=ExportUtil.getModel(file);
				addPage(jsonExportPage);
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		addPage(new CantExportPage());
	}

	@Override
	public boolean performFinish() {
		List<EObject> features=new LinkedList<>();
		for(EObject eobj:eobjects){
			if(eobj instanceof Feature)features.add(eobj);
		}
		File file=jsonExportPage.getFile();
		ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap()
            .put("*", new JsonResourceFactory());
        Resource r=resourceSet.createResource(URI.createFileURI(file.getAbsolutePath()));
        r.getContents().addAll(features);
        try {
			r.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	

}
