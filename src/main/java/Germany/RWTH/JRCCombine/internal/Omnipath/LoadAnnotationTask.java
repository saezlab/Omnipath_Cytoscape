package Germany.RWTH.JRCCombine.internal.Omnipath;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class LoadAnnotationTask extends AbstractTask {
	
	
	private CySwingAppAdapter adapter;
	private CyApplicationManager applicationManager;
	//private JPanel panel9;
	//private String database;
	//private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
	
	public LoadAnnotationTask(CySwingAppAdapter adapter, CyApplicationManager applicationManager) {
		
		this.adapter = adapter;
		this.applicationManager = applicationManager;
		//this.database = database;
		//this.panel9 = panel9;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		// TODO Auto-generated method stub
		taskMonitor.setTitle("Omnipath -- Querying annotations from server...");
		
		
		MyControlPanel.annotation.clearSourceListModel();
		MyControlPanel.annotation.clearDestinationListModel();
		ArrayList<Object> unique_annotation = new ArrayList<Object>();
		
		URL oracle = new URL("https://omnipathdb.org/annotations_summary?cytoscape=1");
    	URLConnection con = oracle.openConnection();
        InputStream is =con.getInputStream();        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        in.readLine();
    	String inputLine;
    	String[] split;
    	MyAnnotationTable table = new MyAnnotationTable();
    	String[] values;
    	while ((inputLine = in.readLine()) != null) {
    		    	split = inputLine.split("\t");
    		    	unique_annotation.add(split[0]+"/"+split[1]);
    		    	values = split[2].split("#");
    		    	table.setInstance(split[0]+"/"+split[1], values);
    	}
        in.close();  
        MyControlPanel.annotation.addSourceElements(unique_annotation.toArray());
		
	}
	
}
