package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;


import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.model.CyEdge;

public class MyEdgeViewTask extends AbstractTask {

	private CyNetworkView networkView;
	private View<CyEdge> edgeView;
	private CyApplicationManager applicationManager = CyActivator.getCyApplicationManager();

	public MyEdgeViewTask(View<CyEdge> edgeView, CyNetworkView networkView){
		this.networkView = networkView;
		this.edgeView = edgeView;
	}
	
    @Override
	public void run(final TaskMonitor taskMonitor) throws HeadlessException, MalformedURLException {
		// Give the task a title.
		taskMonitor.setTitle("My Edge View Task");
		
//		CyNetwork network = applicationManager.getCurrentNetwork();
//		
//		List<CyEdge> edge = CyTableUtil.getEdgesInState(network, "selected", true);
//		CyEdge s = edge.get(0);
//		String source = network.getRow(s.getSource()).get("name", String.class);
//		String target = network.getRow(s.getTarget()).get("name", String.class);
//		
//		String uniqueName = network.getRow(s).get("shared name", String.class);
//		String references = network.getRow(s).get("references", String.class);
//		String uniqueReferences[] = references.split(";");
//		String links="";
//		for (String p : uniqueReferences) {
//			String tmp1 = "https://www.ncbi.nlm.nih.gov/pubmed/"+p;
//			String tmp2 = "<a href=\""+tmp1+"\">";
//			String tmp3 = tmp2 + tmp1 + "</a><br /><br />";
//			links = links + tmp3;
//			
//		}
//		
//		
//		 JEditorPane ep =new JEditorPane("text/html","<hml><body>Edge between "+ source + " and " + target + "<br /><br />"
//				 + "The following PubMed identifiers support this interaction:<br /><br />" 
//				 + links
//				 + "</body></html>");
//	      ep.setEditable(false);
//	      ep.addHyperlinkListener(new HyperlinkListener() {
//	          @Override
//	          public void hyperlinkUpdate(HyperlinkEvent e) {
//	              if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
//	      				try {
//							Desktop.getDesktop().browse(e.getURL().toURI());
//						} catch (IOException | URISyntaxException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//	              }
//	          }
//	         
//	      });
//	      Object[] buttons= {"Cancel"};
//	      JOptionPane.showOptionDialog(null,ep,"Edge Information",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,buttons,buttons[0]);
//	    }
		
		
    }
}
	


