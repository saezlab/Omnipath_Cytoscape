package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.io.IOException;
import javax.swing.JOptionPane;
import org.cytoscape.app.swing.CySwingAppAdapter;
public class LoadDataOptions {


	private String query;
	private String database;
	private String organism;
	private CySwingAppAdapter adapter;

	public LoadDataOptions() {}
	
	
	public void getDatabse(String database, String organism, CySwingAppAdapter adapter) throws IOException{
		
		this.adapter = adapter;
		this.organism = organism;
		this.database = database;
		
		
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
		
		
		
		// remove all spaces from the query
		// in case there are any 
		query = query.replaceAll("\\s+","");
			
		//JOptionPane.showMessageDialog(null, query);
		//check for wrong user selection 
		if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
			
			JOptionPane.showMessageDialog(null, "miRNA are provided only for Humans. Please change selected organism to human or select a new database.",
            		"Error Message", JOptionPane.ERROR_MESSAGE);
		}
		
		// else create task to send the query 
		else {
			SendQueryTaskFactory queryTaskFactory = new SendQueryTaskFactory(query, adapter, database, organism);
			adapter.getTaskManager().execute(queryTaskFactory.createTaskIterator());
			
		}
		
		
	}
	

	
}
