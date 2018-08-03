package Germany.RWTH.JRCCombine.internal.Omnipath;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
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
	
	private static CySwingApplication cytoscapeDesktopService;
	private static CyApplicationManager applicationManager;
	private static CySwingAppAdapter adapter;
	private static MyControlPanel myControlPanel;
	private static ControlPanelAction controlPanelAction;
	
	
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
		
		this.cytoscapeDesktopService = getService(bc,CySwingApplication.class);
		this.applicationManager  = getService(bc, CyApplicationManager.class);
		this.adapter = getService(bc,CySwingAppAdapter.class);
		this.myControlPanel = new MyControlPanel(adapter, applicationManager);
		this.controlPanelAction = new ControlPanelAction(cytoscapeDesktopService, myControlPanel);
		
		registerService(bc,myControlPanel,CytoPanelComponent.class, new Properties());
		registerService(bc,controlPanelAction,CyAction.class, new Properties());
		
	}

	public static CySwingAppAdapter getAdapter() {return adapter;}
	public static CyApplicationManager getCyApplicationManager() {return applicationManager;}
	
	
}






