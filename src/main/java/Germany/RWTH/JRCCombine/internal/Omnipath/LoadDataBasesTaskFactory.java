package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class LoadDataBasesTaskFactory implements TaskFactory{
	
	private String organism;
	private String database;
	private CyApplicationManager applicationManager;
	private CySwingAppAdapter adapter;
	private DualListBox dual;
	
	
	public LoadDataBasesTaskFactory (String database, String  organism, DualListBox dual) {
		
		
		this.organism = organism;
		this.database = database;
		applicationManager = CyActivator.getCyApplicationManager();
		adapter = CyActivator.getAdapter();
		this.dual = dual;
		
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		
		return new TaskIterator(new LoadDataBasesTask(adapter,database, organism, applicationManager, dual));
		
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

}
