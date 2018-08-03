package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class AddGeneNameTaskFactory implements TaskFactory {

	
	private String file;
	private CyApplicationManager applicationManager;
 
	
	public AddGeneNameTaskFactory (String file, CyApplicationManager applicationManager) {
		
		this.file = file;
		this.applicationManager = applicationManager;
		
		
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		
		return new TaskIterator(new AddGeneNameTask(file, applicationManager));
		
	}
	
	@Override
	public boolean isReady() {
		return false;
	}

}
