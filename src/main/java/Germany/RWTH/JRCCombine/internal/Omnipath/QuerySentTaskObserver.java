package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.io.File;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskObserver;

public class QuerySentTaskObserver implements TaskObserver{
	
	private boolean taskComplete = false;

	@Override
	public void taskFinished(ObservableTask task) {
		// TODO Auto-generated method stub
		CySwingAppAdapter adapter = CyActivator.getAdapter();
		FileName singleFile = FileName.getInstance();
		String filename = singleFile.s;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StartRServeTaskObservable taskObserver = new StartRServeTaskObservable();  
		LoadNetworkFileTaskFactory NodeFile = adapter.getCyServiceRegistrar().getService(LoadNetworkFileTaskFactory.class);
		adapter.getTaskManager().execute(NodeFile.createTaskIterator(new File(filename)), taskObserver);
		
		
		
	}

	@Override
	public void allFinished(FinishStatus finishStatus) {
		// TODO Auto-generated method stub
		
	}

}
