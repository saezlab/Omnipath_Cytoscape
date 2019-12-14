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
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.task.read.LoadVizmapFileTaskFactory;

import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * {@code CyActivator} is a class that is a starting point for OSGi bundles.
 */

public class CyActivator extends AbstractCyActivator {
	
	private static CySwingApplication cytoscapeDesktopService;
	private static CyApplicationManager applicationManager;
	private static CySwingAppAdapter adapter;
	private static MyControlPanel myControlPanel;
	private static ControlPanelAction controlPanelAction;
	private static Resources edgesReferencesAction;
	public static CyServiceRegistrar cyServiceRegistrar;
//	public static final String visualStyleFile1 = "/styles1.xml";
//	public static final String visualStyleFile2 = "/styles2.xml";
//	public static final String visualStyleFile3 = "/styles3.xml";
//	public static final String visualStyleFile4 = "/styles4.xml";
//	public static final String visualStyleName1 = "Cytocopter";
//	public static final String visualStyleName2 = "Sample3";
//	public static final String visualStyleName3 = "Marquee";
//	public static final String visualStyleName4 = "default black";
	
	public static String visualStyleFile = "/OmnipathStyle.xml";
	public static String visualStyleName = "StyleOmnipath";
	
	
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc)  {
		
		/**
		 * This is the {@code start} method, which sets up the app. The
		 * {@code BundleContext} object allows to communicate with the OSGi
		 * environment. You use {@code BundleContext} to import services or ask OSGi
		 * about the status of some service.
		 */
		
		MyEdgeViewTaskFactory myEdgeViewTaskFactory = new MyEdgeViewTaskFactory();
		this.cyServiceRegistrar = getService(bc, CyServiceRegistrar.class);
		this.cytoscapeDesktopService = getService(bc,CySwingApplication.class);
		this.applicationManager  = getService(bc, CyApplicationManager.class);
		this.adapter = getService(bc,CySwingAppAdapter.class);
		this.myControlPanel = new MyControlPanel(adapter, applicationManager);
		this.controlPanelAction = new ControlPanelAction(cytoscapeDesktopService, myControlPanel);
		this.edgesReferencesAction = new Resources(cytoscapeDesktopService, myControlPanel);
	
				
		// Add right click listener to the node view
//		Properties myEdgeViewTaskFactoryProps = new Properties();		
//		myEdgeViewTaskFactoryProps.setProperty("preferredAction","OPEN");
//		myEdgeViewTaskFactoryProps.setProperty("preferredMenu","Apps.Samples");
//		myEdgeViewTaskFactoryProps.setProperty("title","Edge View Task");
		
		registerService(bc,myControlPanel,CytoPanelComponent.class, new Properties());
		registerService(bc,controlPanelAction,CyAction.class, new Properties());
		registerService(bc,edgesReferencesAction,CyAction.class, new Properties());
		//registerService(bc,myEdgeViewTaskFactory,EdgeViewTaskFactory.class, myEdgeViewTaskFactoryProps);
		loadVisualStyle();
		
		
		
	}
	private void loadVisualStyle () {
		InputStream in = getClass().getResourceAsStream(visualStyleFile);
		LoadVizmapFileTaskFactory loadVizmapFileTaskFactory =  cyServiceRegistrar.getService(LoadVizmapFileTaskFactory.class);
		loadVizmapFileTaskFactory.loadStyles(in);
	}

	public static CySwingAppAdapter getAdapter() {return adapter;}
	public static CyServiceRegistrar getCyServiceRegistrar() {return cyServiceRegistrar;}
	public static CyApplicationManager getCyApplicationManager() {return applicationManager;}
	
}






