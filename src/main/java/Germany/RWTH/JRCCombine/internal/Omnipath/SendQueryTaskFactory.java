package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

// Task factory for creating Query task 
// to download the dataset based on user selection
// dataset and organism



public class SendQueryTaskFactory implements TaskFactory {
	
	
	private String query;
	private boolean done = false;
	private CySwingAppAdapter adapter;
	private String database;
	private String  organism;
	private CyApplicationManager applicationManager;

	
	public SendQueryTaskFactory (String query, CySwingAppAdapter adapter,
			String database, String  organism, CyApplicationManager applicationManager) {
		
		this.query = query;
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;
		this.applicationManager = applicationManager;
		
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		
		return new TaskIterator(new SendQueryTask(query, adapter, database, organism, applicationManager));
		
	}

	@Override
	public boolean isReady() {
		return false;
	}
	
	
	

}




