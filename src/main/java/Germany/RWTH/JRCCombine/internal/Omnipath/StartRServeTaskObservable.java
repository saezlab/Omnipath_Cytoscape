package Germany.RWTH.JRCCombine.internal.Omnipath;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.cytoscape.app.swing.CySwingAppAdapter;
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
		// Once the network has been imported 
		// add the gene names 
		
		//JOptionPane.showMessageDialog(null, "RUNNING...");
		CyApplicationManager applicationManager = CyActivator.getCyApplicationManager();
		//CySwingAppAdapter adapter = CyActivator.getAdapter();
		FileName singleFile = FileName.getInstance();
		String filename = singleFile.s;
//		AddGeneNameTaskFactory addNamesTaskFactory = new AddGeneNameTaskFactory(filename, applicationManager);
//		adapter.getTaskManager().execute(addNamesTaskFactory.createTaskIterator()); 
		
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
		} catch (IOException e) {
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
			network.getDefaultNodeTable().createColumn("gene_symbol", String.class, true);
			//JOptionPane.showMessageDialog(null, "null");
			
		}
		
//		else {
//			network.getDefaultNodeTable().deleteColumn("gene_symbol");
//			network.getDefaultNodeTable().createColumn("gene_symbol", String.class, true);
//			//JOptionPane.showMessageDialog(null, "It exists!");
//		}
		      
		
		for (CyNode n : list) {
			
			String uniID = network.getRow(n).get("name", String.class);
			String gene_name = dictionary.get(uniID);
			//JOptionPane.showMessageDialog(null, gene_name);
			network.getRow(n).set("gene_symbol", gene_name);
			
		}
		
	}

	@Override
	public void allFinished(FinishStatus finishStatus) {
		// TODO Auto-generated method stub
		
	}
	
	 public boolean isComplete() { return taskComplete; }
	 
	 
	 public void reset() { taskComplete = false; }

}
