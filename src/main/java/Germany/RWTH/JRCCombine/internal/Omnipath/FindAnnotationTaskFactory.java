package Germany.RWTH.JRCCombine.internal.Omnipath;

import javax.swing.JPanel;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;


public class FindAnnotationTaskFactory implements TaskFactory{
	
	
	private CyApplicationManager applicationManager;
	private CySwingAppAdapter adapter;
	private Object[] values;
	private String database;
	private String label;
	
	public FindAnnotationTaskFactory (String database, String label, Object[] values) {
		
		
		applicationManager = CyActivator.getCyApplicationManager();
		adapter = CyActivator.getAdapter();
		this.database = database;
		this.label = label;
		this.values = values;
		
		
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		
		return new TaskIterator(new FindAnnotation(adapter, applicationManager, database, label, values));
		
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

}