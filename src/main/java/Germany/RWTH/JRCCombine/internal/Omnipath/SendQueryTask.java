package Germany.RWTH.JRCCombine.internal.Omnipath;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;





public class SendQueryTask extends AbstractTask implements ObservableTask {
	
	// declaring necessary variables 
	private FileOutputStream fos;
	private URL website = null;
	private File filePath = null;
	private String filename = null;
	private String query;
	private CySwingAppAdapter adapter;
	private String database;
	private String organism;
	private CyApplicationManager applicationManager;
	
	
	public SendQueryTask(String query, CySwingAppAdapter adapter,
			String database, String  organism, CyApplicationManager applicationManager) {
		
		this.query = query;
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;
		this.applicationManager = applicationManager; 
		
	}
	
	
	// run thread 
	// this thread download and store the database in a temporary file in memory 
	public void run(TaskMonitor monitor) throws IOException, InterruptedException {
		
		
		//JOptionPane.showMessageDialog(null, "Starting");
		monitor.setTitle("Omnipath -- Querying dataset...");
		website = new URL(query);
		String tmp = database+"_"+organism+"_";
		
		// get OS temporary directory and there
		// save the file in the format:
		// database name_organism name_date and time of creation
		String property = "java.io.tmpdir";
		String tempDir = System.getProperty(property);
		String out = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss'.txt'").format(new Date());
		
		filename = tempDir+tmp+out;
		filename = getWindowsCorrectPath(filename);
		filePath = new File(filename);
		
		JOptionPane.showMessageDialog(null, filePath.toString());
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		fos = new FileOutputStream(filename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		// save the file name in a singleton class
		// so it can be accessed later 
		FileName singleFile = FileName.getInstance();
		singleFile.setInstance(filename);
		
		
		// Plotting network using the cytoscape adapter
		// and an observable task to check when it gets completed 
		StartRServeTaskObservable taskObserver = new StartRServeTaskObservable();  
		LoadNetworkFileTaskFactory NodeFile = adapter.getCyServiceRegistrar().getService(LoadNetworkFileTaskFactory.class);
		adapter.getTaskManager().execute(NodeFile.createTaskIterator(new File(filename)), taskObserver);
		
	}
	
	public static String getWindowsCorrectPath(String filePath)
	{
		String filePath2 = filePath;
		//if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
			filePath2 = filePath.replace("/", "\\");

		return filePath2;
	}


	@Override
	public <R> R getResults(Class<? extends R> type) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
