package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskObserver;


public class QuerySentTaskObserver implements TaskObserver{
	
	private boolean taskComplete = false;
	private CyApplicationManager applicationManager;

	@Override
	public void taskFinished(ObservableTask task) {
		// TODO Auto-generated method stub
		CyApplicationManager applicationManager = CyActivator.getCyApplicationManager();
		CySwingAppAdapter adapter = CyActivator.getAdapter();
		FileName singleFile = FileName.getInstance();
		String filename = singleFile.s;
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		StartRServeTaskObservable taskObserver = new StartRServeTaskObservable();  
		LoadNetworkFileTaskFactory NodeFile = adapter.getCyServiceRegistrar().getService(LoadNetworkFileTaskFactory.class);
		adapter.getTaskManager().execute(NodeFile.createTaskIterator(new File(filename)), taskObserver);
		
		
		
		
		
		
		
		
		
		//JOptionPane.showMessageDialog(null, annotationQuery);
		
		
		
		
	}

	@Override
	public void allFinished(FinishStatus finishStatus) {
		// TODO Auto-generated method stub
		
	}

}
