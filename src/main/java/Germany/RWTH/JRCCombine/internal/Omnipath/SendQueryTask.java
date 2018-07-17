package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;




public class SendQueryTask extends AbstractTask {
	
	// declaring necessary variables 
	private FileOutputStream fos;
	private URL website = null;
	private File filePath = null;
	private String filename = null;
	private String query;
	private CySwingAppAdapter adapter;
	private String database;
	private String organism;
	
	
	public SendQueryTask(String query, CySwingAppAdapter adapter,
			String database, String  organism) {
		
		this.query = query;
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;

		
		
	}
	
	// run thread 
	// this thread download and store the database in temporary file in memory 
	// it also allows user to visualise the dataset table as 
	// network in Cytoscape
	public void run(TaskMonitor monitor) throws IOException, InterruptedException {
		
		monitor.setTitle("Omnipath -- Querying dataset...");
		website = new URL(query);
		String tmp = database+"_"+organism+"_";
		filePath = File.createTempFile(tmp, ".txt");
		filename = filePath.toString();
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		fos = new FileOutputStream(filename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		// Plotting network using the cytoscape adapter 
		LoadNetworkFileTaskFactory NodeFile = adapter.getCyServiceRegistrar().getService(LoadNetworkFileTaskFactory.class);
		adapter.getTaskManager().execute(NodeFile.createTaskIterator(new File(filename)));

	
		
	}
	
	
	

}
