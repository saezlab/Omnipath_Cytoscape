package Germany.RWTH.JRCCombine.internal.Omnipath;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class LoadDataBasesTask extends AbstractTask {
	
	
	private CySwingAppAdapter adapter;
	private String database;
	private String organism;
	private CyApplicationManager applicationManager;
	private DualListBox dual;
	
	public LoadDataBasesTask(CySwingAppAdapter adapter,
			String database, String  organism, CyApplicationManager applicationManager, DualListBox dual) {
		
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;
		this.applicationManager = applicationManager; 
		this.dual = dual;
		
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		// TODO Auto-generated method stub
		
		taskMonitor.setTitle("Omnipath -- Querying list of Databases from the server...");
		dual.clearDestinationListModel();
		dual.clearSourceListModel();
		if (database.equals("PPInteraction") || database.equals("TF-target interactions") || database.equals("miRNA-mRNA")) {
			
			
			URL oracle = new URL("http://omnipathdb.org/databases/interactions/");
		    BufferedReader in = new BufferedReader(
		    new InputStreamReader(oracle.openStream()));
		    in.readLine();
		    String inputLine;
		    String[] split;
		    String[] databases;
		    Map<Object,ArrayList<String>> multiMap = new HashMap<>();
		    while ((inputLine = in.readLine()) != null) {
		    	split = inputLine.split("\t");
		    	databases = split[1].split(";");
		    	ArrayList<String> values = new ArrayList( Arrays.asList(databases));
		    	multiMap.put(split[0], values);
		    }
		    in.close();
		    
		    String[] array;
		    if (database.equals("PPInteraction")){
		    	
		    	array = new String[multiMap.get("PPI").size()];
		    	array = multiMap.get("PPI").toArray(array);
		    	
		    	
		    }else if (database.equals("TF-target interactions")) {
		    	array = new String[multiMap.get("TF").size()];
		    	array = multiMap.get("TF").toArray(array);
		    	
		    }else {
		    	array = new String[multiMap.get("MTI").size()];
		    	array = multiMap.get("MTI").toArray(array);
		    }
			Thread.sleep(2000);
			dual.addSourceElements(array);

			
		}else {
			
			URL oracle = new URL("http://omnipathdb.org/databases/ptms/");
		    BufferedReader in = new BufferedReader(
		    new InputStreamReader(oracle.openStream()));
		    in.readLine();
		    String inputLine;
		    String[] split;
		    String[] databases;
		    Map<Object,ArrayList<String>> multiMap = new HashMap<>();
		    while ((inputLine = in.readLine()) != null) {
		    	split = inputLine.split("\t");
		    	databases = split[1].split(";");
		    	ArrayList<String> values = new ArrayList(Arrays.asList( databases));
		    	multiMap.put(split[0], values);
		    }
		        
		    in.close();
		    String[] array;
		    if (database.equals("Enzyme-substrate interactions")){
		    	array = new String[multiMap.get("*").size()];
		    	array = multiMap.get("*").toArray(array);
		    	Thread.sleep(2000);
		    	dual.addSourceElements(array);
		    }
		    
		    
		    
		    
		}
		
	}
	
}
