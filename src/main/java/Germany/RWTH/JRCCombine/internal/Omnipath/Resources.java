package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.*;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTableUtil;


public class Resources extends AbstractCyAction {

	private static final long serialVersionUID = 1L;
	private CySwingApplication desktopApp;
	private final CytoPanel cytoPanelWest;
	private MyControlPanel myControlPanel;
	
	
	public Resources(CySwingApplication desktopApp,
			MyControlPanel myCytoPanel)
	
	{
		
		super("PubMed references");
		setPreferredMenu("Omnipath");

		this.desktopApp = desktopApp;
	
		// myControlPanel is bean we defined and registered as a service
		this.cytoPanelWest = this.desktopApp.getCytoPanel(CytoPanelName.WEST);
		this.myControlPanel = myCytoPanel;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		CyNetwork network = CyActivator.getCyApplicationManager().getCurrentNetwork();
		List<CyEdge> edge = CyTableUtil.getEdgesInState(network, "selected", true);
		if (edge.size()==1) {
			CyEdge s = edge.get(0);
			String source = network.getRow(s.getSource()).get("name", String.class);
			String target = network.getRow(s.getTarget()).get("name", String.class);
			
			String uniqueName = network.getRow(s).get("shared name", String.class);
			String references = network.getRow(s).get("references", String.class);
			List<String> uniqueReferences = Arrays.asList(references.split(";"));
			ArrayList<String> p = new ArrayList<String>();
			for (String x : uniqueReferences) {
				String refs[] = x.split(":");
				if (refs.length == 2) {
					p.add(refs[1]);
				}else 
					p.add(refs[0]);
			}
			Set<String> temp = new LinkedHashSet<String>(p);
			String[] result = temp.toArray( new String[temp.size()] );

			String links="";
			for (String x : result) {
				String tmp1 = "https://www.ncbi.nlm.nih.gov/pubmed/"+x;
				String tmp2 = "<a href=\""+tmp1+"\">";
				String tmp3 = tmp2 + tmp1 + "</a><br /><br />";
				links = links + tmp3;
				
			}
			
			 JEditorPane ep =new JEditorPane("text/html","<hml><body>Edge between "+ source + " and " + target + "<br /><br />"
					 + "The following PubMed identifiers support this interaction:<br /><br />" 
					 + links
					 + "</body></html>");
		      ep.setEditable(false);
		      ep.addHyperlinkListener(new HyperlinkListener() {
		          @Override
		          public void hyperlinkUpdate(HyperlinkEvent e) {
		              if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
		      				try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (IOException | URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		              }
		          }
		         
		      });
		      Object[] buttons= {"Cancel"};
		      JOptionPane.showOptionDialog(null,ep,"Edge Information",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,buttons,buttons[0]);
			
		}else JOptionPane.showMessageDialog(null, "Please select one edge to continue!",
        		"Error Message", JOptionPane.ERROR_MESSAGE);
		
	    }
	}

