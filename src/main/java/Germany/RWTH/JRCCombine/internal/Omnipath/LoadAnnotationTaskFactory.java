package Germany.RWTH.JRCCombine.internal.Omnipath;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class LoadAnnotationTaskFactory implements TaskFactory{
	
	
	private CyApplicationManager applicationManager;
	private CySwingAppAdapter adapter;
	//private JPanel panel9;
	//private String database;
	
	public LoadAnnotationTaskFactory () {
		
		
		applicationManager = CyActivator.getCyApplicationManager();
		adapter = CyActivator.getAdapter();
		//this.panel9 = panel9;
		//this.database = database;
		
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		
		return new TaskIterator(new LoadAnnotationTask(adapter, applicationManager));
		
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

}