package Germany.RWTH.JRCCombine.internal.Omnipath;



import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;



public class LoadDataOptions {


	private String query;
	private String database;
	private String organism;
	private CySwingAppAdapter adapter;
	private CyApplicationManager applicationManager;
	private Object[] selected;
	private ArrayList<String> selectedConfidence;
	private boolean isTF;

	public LoadDataOptions() {}
	
	
	public void getDatabse(String database, String organism, CySwingAppAdapter adapter, 
						   CyApplicationManager applicationManager,
						   Object[] selected, ArrayList<String> selectedConfidence, boolean isTF) 
						   throws IOException, InterruptedException {
		
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;
		this.applicationManager = applicationManager;
		this.selected = selected;
		this.selectedConfidence = selectedConfidence;
		this.isTF = isTF;
		
		
		//start query formulation for interactions or ptms 
		// interactions query
		if (database.equals("PPInteraction") || database.equals("miRNA-mRNA") || database.equals("TF-target interactions")) {
			
			query = "http://omnipathdb.org/interactions?fields=sources&fields=references&genesymbols=1";
			//add extra text if it is TF query
			if (database.equals("TF-target interactions")) {
				
				query= query +"&types=TF";
				
			}
			//add extra text if it is miRNA query
			if (database.equals("miRNA-mRNA")) {
				
				query = query +"&datasets=mirnatarget";
				
			}
		
		}
		//ptms query
		else {
			
			query = "http://omnipathdb.org/ptms/?fields=sources&fields=references&genesymbols=1";
			
		}
		
		//start query formulation for organism type
		if (organism.equals("Mouse")) {
			
			query = query + "&organisms=10090";
			
		}
		else if (organism.equals("Rat")) {
			
			query = query + "&organisms=10116";
			
		}
		// human organism by default
		// if human then do not add extra fields to the query 
		else {}
		
		// add database selected
		query = query + "&databases=";
		for (int i = 0; i< selected.length; i++) {
			
			if (i == 0)
				query = query +selected[i].toString();
			else 
				query = query + ","+selected[i].toString();
			
		}
		
		
		// if TF interactions, add the confidence levels 
		if (isTF) {
			query = query + "&tfregulons_levels=";
			for (int i = 0; i< selectedConfidence.size(); i++) {
				
				if (i == 0)
					query = query +selectedConfidence.get(i).toString();
				else 
					query = query + ","+selectedConfidence.get(i).toString();
				
			}
		}
		
		
		// remove all spaces from the query
		// in case there are any 
		query = query.replaceAll("\\s+","");
			
		//JOptionPane.showMessageDialog(null, query);
		
		//check for wrong user selection 
		if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
			
			JOptionPane.showMessageDialog(null, "miRNA are provided only for Humans. Please change selected organism to human or select a new database.",
            		"Error Message", JOptionPane.ERROR_MESSAGE);
		}
		
		// else create task to execute the query 
		// and plot the network in Cytoscape
		else {
			
			SendQueryTaskFactory queryTaskFactory = new SendQueryTaskFactory(query, adapter, database, organism, applicationManager);	
			adapter.getTaskManager().execute(queryTaskFactory.createTaskIterator());
			
		}
		
		
	}
	
	
}