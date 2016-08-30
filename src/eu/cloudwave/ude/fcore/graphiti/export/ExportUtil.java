package eu.cloudwave.ude.fcore.graphiti.export;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.mm.MmPackage;

import eu.cloudwave.ude.fcore.graphiti.FCORE.FCOREPackage;

public class ExportUtil {
	public static List<EObject> getModel(File file){
		 ResourceSet resourceSet = new ResourceSetImpl();
		 resourceSet.getPackageRegistry().put(MmPackage.eNS_URI, MmPackage.eINSTANCE);
         resourceSet.getPackageRegistry().put(FCOREPackage.eNS_URI, FCOREPackage.eINSTANCE);
		 resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		 URI uri=URI.createFileURI(file.getAbsolutePath());
		 
		 Resource r=resourceSet.getResource(uri, true);
		 try {
			r.load(null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		r.getContents().remove(0);
		return r.getContents();
	}
	
	
	
}
