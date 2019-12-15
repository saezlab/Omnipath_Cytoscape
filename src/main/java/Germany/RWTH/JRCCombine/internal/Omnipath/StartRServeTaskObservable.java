package Germany.RWTH.JRCCombine.internal.Omnipath;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskObserver;





public class StartRServeTaskObservable implements TaskObserver {
	
    private boolean taskComplete = false;
    

	@Override
	public void taskFinished(ObservableTask task){
		

		FileName singleFile = FileName.getInstance();
		String filename = singleFile.s;
//		if (filename.contains("Signalingnetworks")) {
//			CyActivator.visualStyleName = "Cytocopter2";
//			CyActivator.visualStyleFile = "/styles1.xml";
//			
//		}
//		else if (filename.contains("Enzyme-substrate")) {
//			CyActivator.visualStyleName = "Sample3";
//			CyActivator.visualStyleFile = "/styles2.xml";
//		}
//		else if (filename.contains("miRNA")) {
//			CyActivator.visualStyleName = "Marquee";
//			CyActivator.visualStyleFile = "/styles3.xml";
//		}
//		else {
//			CyActivator.visualStyleName = "default black";
//			CyActivator.visualStyleFile = "/styles4.xml";
//		}
		//JOptionPane.showMessageDialog(null, "RUNNING...");
		CyApplicationManager applicationManager = CyActivator.getCyApplicationManager();
		//CySwingAppAdapter adapter = CyActivator.getAdapter();
		String applyVisualStyleCommand = "vizmap apply styles=" + CyActivator.visualStyleName;
        //JOptionPane.showMessageDialog(null, applyVisualStyleCommand);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        CommandExecutor.execute(applyVisualStyleCommand, CyActivator.getCyServiceRegistrar());
        
        
      
        String layoutCommand = "layout grid";
		CommandExecutor.execute(layoutCommand, CyActivator.getCyServiceRegistrar());
		
		
		
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ArrayList<String> keySource = new ArrayList<String>();
		ArrayList<String> valuesSource = new ArrayList<String>();
		ArrayList<String> keyTarget = new ArrayList<String>();
		ArrayList<String> valuesTarget = new ArrayList<String>();
		
		// for the time being assume that column 0 is always source UNIPROTID 
		// col 1 is always target UNIPROTID 
		// col 2 is always source GENESYMBOL 
		// col 3 is always target GENESYMBOL 
		
		try {
			
			while((line = br.readLine()) != null) {
				
				
				String[] columns = line.split("\t",-1);
				keySource.add(columns[0]);
				valuesSource.add(columns[2]);
				keyTarget.add(columns[1]);
				valuesTarget.add(columns[3]);

			}
		} 
		
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// merge all the keys together
		ArrayList<String> keys = new ArrayList<String>(keySource);
		keys.addAll(keyTarget);
		
		// merge all the values together
		ArrayList<String> values = new ArrayList<String>(valuesSource);
		values.addAll(valuesTarget);
		
		
		// create dictionary mapping uniprot id to gene symbol
		Map<String, String> dictionary = new HashMap<String, String>();
		for (int i = 0; i < values.size(); i++ ) {
			
			dictionary.put(keys.get(i), values.get(i));
			
		}
		
		CyNetwork network = applicationManager.getCurrentNetwork();	
		ArrayList<CyNode> list = (ArrayList<CyNode>) network.getNodeList();

		
		if (network.getDefaultNodeTable().getColumn("gene_symbol") == null) {
			network.getDefaultNodeTable().createColumn("gene_symbol", String.class, false);
			
		}
		if (network.getDefaultNodeTable().getColumn("node_type") == null) {
			network.getDefaultNodeTable().createColumn("node_type", String.class, false);
		}
		
		
		for (CyNode n : list) {
			
			String uniID = network.getRow(n).get("name", String.class);
			String gene_name = dictionary.get(uniID);
			//JOptionPane.showMessageDialog(null, gene_name);
			network.getRow(n).set("gene_symbol", gene_name);
			
			if (filename.contains("Signalingnetworks")) {
			
				network.getRow(n).set("node_type", "protein");
			}
			else if (filename.contains("Enzyme-substrate")) {
				
				network.getRow(n).set("node_type", "protein");
			}
			else if (filename.contains("miRNA")) {
				if (keySource.contains(uniID)) {
					network.getRow(n).set("node_type", "miRNA");
				}
				else  {
					network.getRow(n).set("node_type", "mRNA");
				}
			}
			else {
				if (keySource.contains(uniID)) {
					network.getRow(n).set("node_type", "TF");
				}
				if (keyTarget.contains(uniID)) {
					network.getRow(n).set("node_type", "target");
				}
				if (keySource.contains(uniID) && keyTarget.contains(uniID)) {
					network.getRow(n).set("node_type", "TF");
				}
			}
			
			
			
			
			
			
			
		}
		
		
		
		//add annotation after the network is imported according to user choices
		//network = applicationManager.getCurrentNetwork();	
//		String annotationQuery = "http://omnipathdb.org/annotations?databases=";
//		Object[] pathways = MyControlPanel.getPathways();
//		Object[] resources = MyControlPanel.getResources();
//		for (int i = 0; i< resources.length; i++) {
//			
//			
//				annotationQuery = annotationQuery +resources[i].toString();
//				annotationQuery = annotationQuery+"&proteins=";
//				String name;
//				for (int j = 0; j < list.size(); j++) {
//					
//					if (j == 0) {
//						name = network.getRow(list.get(j)).get("name", String.class);
//						annotationQuery = annotationQuery + name.toString();
//					}
//					else {
//						name = network.getRow(list.get(j)).get("name", String.class);
//						annotationQuery = annotationQuery + ","+name.toString();
//					}
//				}
//				//JOptionPane.showMessageDialog(null, annotationQuery);
//				URL url;
//				URLConnection con;
//				InputStream is; 
//				BufferedReader bra = null;
//				
//				try {
//					
//					url = new URL(annotationQuery);
//					con = url.openConnection();
//			        is = con.getInputStream();
//			        bra = new BufferedReader(new InputStreamReader(is));
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(null, e.toString());
//				} catch (IOException e) {
//					//JOptionPane.showMessageDialog(null, e.toString());
//					JOptionPane.showMessageDialog(null, "A problem occurred while opening URL. The request is too large (too many nodes in the network) "
//							+ "and the HTTP request failed (ERROR 414)",
//                    		"Error Message", JOptionPane.ERROR_MESSAGE);
//					
//					//JOptionPane.showMessageDialog(null, e.toString());
//				}catch (Exception e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(null, e.toString());
//				}
//		    	
//
//		        String newLine;
//		        String[] words;
//		        HashMap multiMap = new HashMap<String,ArrayList<String>>();
//		        int c = 0;
//		        String uniprot;
//		        String value;
//		        
//		        try {
//					while ((newLine = bra.readLine()) != null) {
//						
//						if (c==0) {
//							c++;
//							continue;
//						}
//						//JOptionPane.showMessageDialog(null, newLine);
//					    words = newLine.split("\\s+");
//					    
//					    uniprot = words[0];
//					    //JOptionPane.showMessageDialog(null, uniprot);
//					    value = words[4];
//					    
//					    if (multiMap.containsKey(uniprot)) {
//					    	
//					    
//					    	ArrayList<String> v = (ArrayList<String>) multiMap.get(uniprot);
//					    	v.add(value);
//					    	multiMap.put(uniprot, v);
//					    }
//					    
//					    else {
//					    	ArrayList<String> valuelist = new ArrayList<String>();
//					    	valuelist.add(value);
//					    	//JOptionPane.showMessageDialog(null, uniprot+" "+value);
//					    	//JOptionPane.showMessageDialog(null, value);
//					    	multiMap.put(uniprot, valuelist);
//					    }
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					JOptionPane.showMessageDialog(null, "ERROR");
//				}
//		        annotationQuery = "http://omnipathdb.org/annotations?databases=";
//		        for (int k = 0;k<pathways.length;k++) {
//		        	String newName = resources[i]+"_"+pathways[k];
//		        	if (network.getDefaultNodeTable().getColumn(newName) == null) {
//						network.getDefaultNodeTable().createColumn(newName, String.class, false);
//					}
//		        	for (CyNode n : list) {
//		        		
//						String proteinName = network.getRow(n).get("name", String.class);
//						
//						if (multiMap.containsKey(proteinName)) {
//							
//						
//							ArrayList<String> s = (ArrayList<String>) multiMap.get(proteinName);
//							
//							
//							if (s.contains(pathways[k])) {
//								network.getRow(n).set(newName, "True");
//							} else {
//								network.getRow(n).set(newName, "False");
//								
//							}
//
//						} else network.getRow(n).set(newName, "NA");
//		        	}
//		        }
//		}


}

@Override
public void allFinished(FinishStatus finishStatus) {
// TODO Auto-generated method stub
	

}

public boolean isComplete() { return taskComplete; }


public void reset() { taskComplete = false; }

}