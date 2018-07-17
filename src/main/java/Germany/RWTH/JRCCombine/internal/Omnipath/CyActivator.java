package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyAction;
import org.osgi.framework.BundleContext;
import Germany.RWTH.JRCCombine.internal.Omnipath.ControlPanelAction;
import Germany.RWTH.JRCCombine.internal.Omnipath.MyControlPanel;
import org.cytoscape.service.util.AbstractCyActivator;
import java.util.Properties;

/**
 * {@code CyActivator} is a class that is a starting point for OSGi bundles.
 */

public class CyActivator extends AbstractCyActivator {
	
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc) {
		
		/**
		 * This is the {@code start} method, which sets up the app. The
		 * {@code BundleContext} object allows to communicate with the OSGi
		 * environment. You use {@code BundleContext} to import services or ask OSGi
		 * about the status of some service.
		 */
		CySwingApplication cytoscapeDesktopService = getService(bc,CySwingApplication.class);
		CySwingAppAdapter adapter = getService(bc,CySwingAppAdapter.class);
		MyControlPanel myControlPanel = new MyControlPanel(adapter);
		ControlPanelAction controlPanelAction = new ControlPanelAction(cytoscapeDesktopService, myControlPanel);
		registerService(bc,myControlPanel,CytoPanelComponent.class, new Properties());
		registerService(bc,controlPanelAction,CyAction.class, new Properties());
		
	}
	
	
	
	
}






