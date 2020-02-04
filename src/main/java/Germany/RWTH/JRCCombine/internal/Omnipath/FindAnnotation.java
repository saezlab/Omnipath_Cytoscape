package Germany.RWTH.JRCCombine.internal.Omnipath;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class FindAnnotation extends AbstractTask {
	
	
	private CySwingAppAdapter adapter;
	private CyApplicationManager applicationManager;
	private Object[] values;
	private String database;
	private String label;
	
	
	public FindAnnotation(CySwingAppAdapter adapter, CyApplicationManager applicationManager, String database, 
			String label, Object[] values) {
		
		this.adapter = adapter;
		this.applicationManager = applicationManager; 
		this.values = values;
		this.database = database;
		this.label = label;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		// TODO Auto-generated method stub
		
		taskMonitor.setTitle("Omnipath -- Annotating the network...");
		
		CyNetwork network = applicationManager.getCurrentNetwork();
        ArrayList<CyNode> nodelist = (ArrayList<CyNode>) network.getNodeList();
        ArrayList<String> nodes = new ArrayList<String>();
        
        for (CyNode n : nodelist) {
        	nodes.add(network.getRow(n).get("name", String.class));
        }
        
		
        String annotationQuery = ("http://omnipathdb.org/annotations?databases="+database);
        URL oracle = new URL(annotationQuery);
    	URLConnection con = oracle.openConnection();
        InputStream is =con.getInputStream();        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        in.readLine();
        
        ArrayList<String> data_uniprot = new ArrayList<String>();
        ArrayList<String> data_label = new ArrayList<String>();
        ArrayList<String> data_value = new ArrayList<String>();
        String inputLine;
    	String[] split2;
    	while ((inputLine = in.readLine()) != null) {
			
    		split2 = inputLine.split("\t");
    		if (nodes.contains(split2[0]) && split2[4].equals(label)) {
    		    		
    			data_uniprot.add(split2[0]);
    			data_label.add(split2[4]);
    			data_value.add(split2[5]);    	
    		} 	
    	}
        in.close();
		for (int k = 0; k < values.length; k++) {
			
			ArrayList<String> match = new ArrayList<String>();
			for (int h = 0; h < data_uniprot.size(); h++) {
				if (values[k].equals(data_value.get(h))) {
					match.add(data_uniprot.get(h));
				}
			}
	    	String annotationName = database+"_"+label+"_"+values[k];
	    	if (network.getDefaultNodeTable().getColumn(annotationName) == null) {
				network.getDefaultNodeTable().createColumn(annotationName, String.class, false);
			}
	    	for (CyNode n : nodelist) {
	    		
	    		String proteinName = network.getRow(n).get("name", String.class);
	    		if (match.contains(proteinName)) {
	    			network.getRow(n).set(annotationName, "True");
	    		} else network.getRow(n).set(annotationName, "False");
	    	
	    	}
		}
	}
}