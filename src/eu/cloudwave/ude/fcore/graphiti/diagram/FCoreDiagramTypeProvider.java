package eu.cloudwave.ude.fcore.graphiti.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;

public class FCoreDiagramTypeProvider extends AbstractDiagramTypeProvider {
	
	public  FCoreDiagramTypeProvider() {
		super();
		setFeatureProvider(new FCoreFeatureProvider(this));
	}
	
}
