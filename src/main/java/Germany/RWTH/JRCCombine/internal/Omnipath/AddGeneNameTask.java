package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class AddGeneNameTask extends AbstractTask{
	
	
	private String file;
	private CyApplicationManager applicationManager;

	
	public AddGeneNameTask(String file, CyApplicationManager applicationManager) {
		
		this.file = file;
		this.applicationManager = applicationManager;
		
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		
		
		taskMonitor.setTitle("Omnipath -- Mapping UniprotID to gene symbol...");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		br.readLine();
		ArrayList<String> keySource = new ArrayList<String>();
		ArrayList<String> valuesSource = new ArrayList<String>();
		ArrayList<String> keyTarget = new ArrayList<String>();
		ArrayList<String> valuesTarget = new ArrayList<String>();
		
		// for the time being assume that column 0 is always source UNIPROTID 
		// col 1 is always target UNIPROTID 
		// col 2 is always source GENESYMBOL 
		// col 3 is always target GENESYMBOL 
		while((line = br.readLine()) != null) {
			
			
			String[] columns = line.split("\t",-1);
			keySource.add(columns[0]);
			valuesSource.add(columns[2]);
			keyTarget.add(columns[1]);
			valuesTarget.add(columns[3]);

			
		}
		
		// merge all the keys together
		ArrayList<String> keys = new ArrayList<String>(keySource);
		keys.addAll(keyTarget);
		
		// merge all the values together
		ArrayList<String> values = new ArrayList<String>(valuesSource);
		values.addAll(valuesTarget);
		
		Map<String, String> dictionary = new HashMap<String, String>();
		for (int i = 0; i < values.size(); i++ ) {
			
			dictionary.put(keys.get(i), values.get(i));
			
		}
		
		
		CyNetwork network = applicationManager.getCurrentNetwork();	
		ArrayList<CyNode> list = (ArrayList<CyNode>) network.getNodeList();
		if (network.getDefaultNodeTable().getColumn("gene_symbol") == null)
		      network.getDefaultNodeTable().createColumn("gene_symbol", String.class, false);
		
		for (CyNode n : list) {
			
			String uniID = network.getRow(n).get("name", String.class);
			String gene_name = dictionary.get(uniID);
			//JOptionPane.showMessageDialog(null, gene_name);
			network.getRow(n).set("gene_symbol", gene_name);
			
		}
		
	}
	
		
}


