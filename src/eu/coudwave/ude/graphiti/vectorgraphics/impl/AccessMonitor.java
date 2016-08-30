package eu.coudwave.ude.graphiti.vectorgraphics.impl;

import eu.cloudwave.ude.graphiti.vectorgraphics.IAccessMonitor;

public class AccessMonitor implements IAccessMonitor {
	
	private boolean allowed=false;
	
	@Override
	public synchronized void getAccess() {
		while(!allowed){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public synchronized void allowAccess(boolean allow) {
		this.allowed=allow;
		notifyAll();
	}

}
