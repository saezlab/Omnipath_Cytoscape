package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.create.NewNetworkSelectedNodesOnlyTaskFactory;
import org.cytoscape.task.select.SelectFromFileListTaskFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;





public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	// declaration of necessary variables 
	private static final long serialVersionUID = 8292806967891823933L;
	// default selection of database
	private String database = "PPInteraction";
	private String organism = "";
	private CySwingAppAdapter adapter;
	private JButton confirm;
	private JComboBox<String> bookList;
	private JButton subnetSelector;
	private CyApplicationManager applicationManager;
	private DualListBox dual;
	private JRadioButton ConfidenceA;
	private JRadioButton ConfidenceB;
	private JRadioButton ConfidenceC;
	private JRadioButton ConfidenceD;
	private JRadioButton ConfidenceE;
	private JRadioButton ConfidenceAll;
	private ArrayList<String> selectedConfidence = new ArrayList<String>();
	private boolean isTF = false;
	
	
	
	
	// set up GUI control panel
	public MyControlPanel(CySwingAppAdapter adapter, CyApplicationManager applicationManager) {
		
		this.adapter =  adapter;
		this.applicationManager = applicationManager;
		
		JLabel lbXYZ = new JLabel("Omnipath Control Panel");
		Font font = new Font("Courier", Font.BOLD,20);
		lbXYZ.setFont(font);
		lbXYZ.setBorder(new EmptyBorder(20, 20, 20, 20));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{100, 110, 110};
		setSize(new Dimension(640, 640));
		setPreferredSize(new Dimension(640, 640));
		lbXYZ.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel website = new JLabel();
		goWebsite(website, "http://omnipathdb.org/info", "Omnipath");
		website.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(website);
		this.add(lbXYZ);
		this.setVisible(true);
		this.add(Box.createVerticalStrut(40));

		// create GUI components to allow users to choose datasets 
		// and organisms
		createOrganismselection();
		createDatabaseselection();
		createTFregulonConfidenceButtons();
		createSelectionTable();
		createConfirmationButton();
		
		
		//createConfidenceLevelButtons();
		//createDatasets();
		//createSubnetworkSelectorFromFile(this);
	}
	
	public void createOrganismselection() {
		
		// group of JRadioButton for organism selection
		JLabel l = new JLabel("Select organism: ");
		add(l);
		l.setBorder(new EmptyBorder(0, 5, 0, 0));
		JRadioButton option1 = new JRadioButton("Human");
        JRadioButton option2 = new JRadioButton("Mouse");
        JRadioButton option3 = new JRadioButton("Rat");
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
 
        add(option1);
        add(option2);
        add(option3);
        // implicit action listener
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JRadioButton){
                    JRadioButton radioButton = (JRadioButton) e.getSource();
                    if(radioButton.isSelected()){
                    	organism = radioButton.getText();
                    	
	                    if (!organism.equals("") && !database.equals("")) {
	        		    		database = (String) bookList.getSelectedItem();
	        			    	dual.enableAddButton();
	        					dual.enableRemoveButton();
	        					dual.enableSelectionButtons();
	        					updateList();
	        		    }
                    	
                    }
                }
            }
        };
        option1.addActionListener(actionListener);
        option2.addActionListener(actionListener);
        option3.addActionListener(actionListener);
        
		
	}
	
  public void createTFregulonConfidenceButtons() {
		
	  
	  	JLabel l = new JLabel("TF confidence:");
		add(l);
		l.setBorder(new EmptyBorder(0, 5, 0, 0));
	  	ConfidenceA = new JRadioButton("A");
	    ConfidenceB = new JRadioButton("B");
	    ConfidenceC = new JRadioButton("C");
	    ConfidenceD = new JRadioButton("D");
	    ConfidenceE = new JRadioButton("E");
	    ConfidenceAll = new JRadioButton("All");
	    
	    add(ConfidenceA);
        add(ConfidenceB);
        add(ConfidenceC);
        add(ConfidenceD);
        add(ConfidenceE);
        add(ConfidenceAll);
        
        
        ConfidenceA.setEnabled(false);
        ConfidenceB.setEnabled(false);    
        ConfidenceC.setEnabled(false);    
        ConfidenceD.setEnabled(false);    
        ConfidenceE.setEnabled(false);    
        ConfidenceAll.setEnabled(false);    
        
        ConfidenceAll.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceA.setSelected(false);
		    	ConfidenceB.setSelected(false);
		    	ConfidenceC.setSelected(false);
		    	ConfidenceD.setSelected(false);
		    	ConfidenceE.setSelected(false);
		    	
		    }
		});
        
        ConfidenceA.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceAll.setSelected(false);
		    	
		    }
		});
        
        ConfidenceB.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceAll.setSelected(false);
		    	
		    }
		});
        
        ConfidenceC.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceAll.setSelected(false);
		    	
		    }
		});
        
        ConfidenceD.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceAll.setSelected(false);
		    	
		    }
		});
        
        ConfidenceE.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	ConfidenceAll.setSelected(false);
		    	
		    }
		});
        
	  }
  
  	public void enableTFConfidence() {
  		
  		ConfidenceA.setEnabled(true);
        ConfidenceB.setEnabled(true);    
        ConfidenceC.setEnabled(true);    
        ConfidenceD.setEnabled(true);    
        ConfidenceE.setEnabled(true);    
        ConfidenceAll.setEnabled(true);    
  		
  	}
  	
  	public void disableTFConfidence() {
  		
  		ConfidenceA.setEnabled(false);
        ConfidenceB.setEnabled(false);    
        ConfidenceC.setEnabled(false);    
        ConfidenceD.setEnabled(false);    
        ConfidenceE.setEnabled(false);    
        ConfidenceAll.setEnabled(false);    
  		
  	}
  	
  	
	
	public void createDatabaseselection() {

		JLabel l = new JLabel("Select dataset: ");
		add(l);
		l.setBorder(new EmptyBorder(0,0, 0, 0));
		
		//create list of databases choices
		String[] choices = new String[] {"PPInteraction", "Enzyme-substrate interactions", "miRNA-mRNA",
				"TF-target interactions"};
		bookList = new JComboBox<>(choices);
		bookList.setBorder(new EmptyBorder(5, 5, 5, 5));
		// implicit action listener 
		bookList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	database = (String) bookList.getSelectedItem();
		    	if (database.equals("TF-target interactions")) {
	    			
	    			enableTFConfidence();
	    			
	    		} else disableTFConfidence();
		    	
		    	if (!organism.equals("")) {
		    		
			    	dual.enableAddButton();
					dual.enableRemoveButton();
					dual.enableSelectionButtons();
					updateList();
		    	}
		    }
		});

		add(bookList);
		
	}
	
	
	// interactive link to get Omnipath information from the control panel 
	private void goWebsite(JLabel website, final String url, String text) {
		
        website.setText("<html> Info: <a href=\"\">"+text+"</a></html>");
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                            Desktop.getDesktop().browse(new URI(url));
                    } catch (URISyntaxException | IOException ex) {
                            JOptionPane.showMessageDialog(null, "A problem occurred while opening URL",
                            		"Error Message", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
    }
	
	public void createSelectionTable() {
		
	    dual = new DualListBox();
	    dual.addSourceElements(new String[] { "Complete selection" });
	    dual.addDestinationElements((new String[] { "Complete selection" }));
	    add(dual);
	    
		
	}
	
	public void updateList() {
		
		if (!database.equals("") && !organism.equals("")) {
			
			if (database.equals("PPInteraction") && organism.equals("Human")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						"TRIP",
						"BioGRID",
						"HPRD",
						"MIMP",
						"PhosphoSite",
						"PhosphoSite_dir",
						"LMPID",
						"DIP",
						"IntAct",
						"SPIKE",
						"ELM",
						"Signor",
						"phosphoELM",
						"InnateDB",
						"SignaLink3",
						"MPPI",
						"PDZBase",
						"PhosphoSite_noref",
						"CA1",
						"PhosphoNetworks",
						"PhosphoPoint",
						"HPRD-phos",
						"KEGG",
						"CancerCellMap",
						"DEPOD",
						"ACSN",
						"Macrophage",
						"NRF2ome",
						"Li2012",
						"MatrixDB",
						"Guide2Pharma",
						"ARN",
						"DeathDomain"});
			}
			
			else if (database.equals("PPInteraction") && organism.equals("Mouse")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						"BioGRID",
						"HPRD",
						"TRIP",
						"MIMP",
						"PhosphoSite",
						"PhosphoSite_dir",
						"LMPID",
						"DIP",
						"IntAct",
						"SPIKE",
						"ELM",
						"Signor",
						"phosphoELM",
						"InnateDB",
						"SignaLink3",
						"MPPI",
						"PDZBase",
						"PhosphoSite_noref",
						"CA1",
						"PhosphoNetworks",
						"PhosphoPoint",
						"HPRD-phos",
						"KEGG",
						"CancerCellMap",
						"DEPOD",
						"ACSN",
						"Macrophage",
						"NRF2ome",
						"Li2012",
						"MatrixDB",
						"Guide2Pharma",
						"ARN",
						"DeathDomain"});
			}
			
			else if (database.equals("PPInteraction") && organism.equals("Rat")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						"BioGRID",
						"HPRD",
						"TRIP",
						"MIMP",
						"PhosphoSite",
						"PhosphoSite_dir",
						"LMPID",
						"Signor",
						"phosphoELM",
						"ELM",
						"InnateDB",
						"IntAct",
						"SignaLink3",
						"MPPI",
						"PDZBase",
						"SPIKE",
						"PhosphoSite_noref",
						"CA1",
						"PhosphoPoint",
						"HPRD-phos",
						"KEGG",
						"PhosphoNetworks",
						"CancerCellMap",
						"DEPOD",
						"ACSN",
						"DIP",
						"Macrophage",
						"Li2012",
						"NRF2ome",
						"MatrixDB",
						"Guide2Pharma",
						"ARN",
						"DeathDomain"});
			}
			
			else if (database.equals("Enzyme-substrate interactions") && organism.equals("Human")) {
				
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {

						"MIMP",
						"PhosphoSite",
						"Signor",
						"phosphoELM",
						"HPRD",
						"PhosphoNetworks",
						"dbPTM",
						"Li2012"});
			}
			
			else if (database.equals("Enzyme-substrate interactions") && organism.equals("Mouse")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {

						"PhosphoSite",
						"Signor",
						"MIMP",
						"HPRD",
						"phosphoELM",
						"PhosphoNetworks",
						"dbPTM",
						"Li2012"});
			}
			
			else if (database.equals("Enzyme-substrate interactions") && organism.equals("Rat")) {
				
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {

						"PhosphoSite",
						"MIMP",
						"Signor",
						"phosphoELM",
						"HPRD",
						"PhosphoNetworks",
						"dbPTM",
						"Li2012"});
			}
			else if (database.equals("TF-target interactions") && organism.equals("Human")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						
						"HTRIdb",
						"PAZAR",
						"kegg",
						"trrust_signed",
						"ARACNe-GTEx",
						"jaspar_v2018",
						"tfact_signed",
						"hocomoco_v11",
						"tred_via_RegNetwork",
						"TFe_signed",
						"ReMap",
						"IntAct",
						"oreganno_signed",
						"trrd_via_tfact_signed",
						"reviews",
						"NFIRegulomeDB",
						"fantom4"});
			}
			else if (database.equals("TF-target interactions") && organism.equals("Mouse")) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						
						"HTRIdb",
						"PAZAR",
						"kegg",
						"trrust_signed",
						"ARACNe-GTEx",
						"jaspar_v2018",
						"tfact_signed",
						"TFe_signed",
						"hocomoco_v11",
						"ReMap",
						"tred_via_RegNetwork",
						"oreganno_signed",
						"IntAct",
						"reviews",
						"trrd_via_tfact_signed",
						"NFIRegulomeDB",
						"fantom4"});
			}
			else if (database.equals("TF-target interactions") && organism.equals("Rat")) {
				
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						
						"kegg",
						"trrust_signed",
						"ARACNe-GTEx",
						"PAZAR",
						"jaspar_v2018",
						"tfact_signed",
						"tred_via_RegNetwork",
						"HTRIdb",
						"IntAct",
						"hocomoco_v11",
						"ReMap",
						"reviews",
						"trrd_via_tfact_signed",
						"TFe_signed",
						"NFIRegulomeDB",
						"oreganno_signed",
						"fantom4"});
			}
			
			else if (database.equals("miRNA-mRNA") && organism.equals("Human")) {
				
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements(new String[] {
						
						"miR2Disease",
						"miRecords",
						"miRTarBase",
						"miRDeathDB"});
			}
			
			else if (database.equals("miRNA-mRNA") && (organism.equals("Rat") || organism.equals("Mouse"))) {
				
				dual.clearDestinationListModel();
				dual.clearSourceListModel();
				dual.addSourceElements((new String[] {"0 resources"}));
				dual.disableAddButton();
				dual.disableRemoveButton();
				dual.disableSelectionButtons();
				
			}
			
		}
		
	}
	
	public void createConfirmationButton() {
		
		confirm = new JButton("Confirm selections");
		confirm.setEnabled(true);
		add(confirm);
		confirm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		// implicit action listener
		confirm.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	LoadDataOptions loader = new LoadDataOptions();
		    	try {
		    		if (database.equals("")) {
						
						JOptionPane.showMessageDialog(null, "Please select the database to import!",
                        		"Error Message", JOptionPane.ERROR_MESSAGE);
						
					}
					else if (organism.equals("")) {
						
						JOptionPane.showMessageDialog(null, "Please select organism and try again!",
                        		"Error Message", JOptionPane.ERROR_MESSAGE);
						
					}
					else if (database.equals("") && organism.equals("")) {
						
						JOptionPane.showMessageDialog(null, "Please select the database to import and the organism!",
                        		"Error Message", JOptionPane.ERROR_MESSAGE);
						
					}
					else if (dual.getDestList().getModel().getSize() == 0) {
						
						JOptionPane.showMessageDialog(null, "Please select the datasets you want to query!",
                        		"Error Message", JOptionPane.ERROR_MESSAGE);
					}
					else if ((!ConfidenceA.isSelected() && !ConfidenceB.isSelected() && !ConfidenceA.isSelected()
							&& !ConfidenceC.isSelected() && !ConfidenceD.isSelected() &&
							!ConfidenceE.isSelected() && !ConfidenceAll.isSelected()) && database.equals("TF-target interactions")) {
						
						JOptionPane.showMessageDialog(null, "Please select regulons confidence level to proceed!",
                        		"Error Message", JOptionPane.ERROR_MESSAGE);
						
					}
					else {
						// read user choices 
						if (database.equals("TF-target interactions")) isTF = true;
						else  isTF = false;
						Object selections[] = new Object[dual.getDestList().getModel().getSize()];
						for(int i=0; i < dual.getDestList().getModel().getSize(); i++){
							
							selections[i] =  dual.getDestList().getModel().getElementAt(i);  
						}
						
						if (database.equals("TF-target interactions")) {
							selectedConfidence = new ArrayList<String>();
							if (ConfidenceA.isSelected()) {
								selectedConfidence.add("A");
							}
							if (ConfidenceB.isSelected()) {
								selectedConfidence.add("B");
							}
							if (ConfidenceC.isSelected()) {
								selectedConfidence.add("C");
							}
							if (ConfidenceD.isSelected()) {
								selectedConfidence.add("D");
							}
							if (ConfidenceE.isSelected()) {
								selectedConfidence.add("E");
							}
							if (ConfidenceAll.isSelected()) {
								selectedConfidence.add("A");
								selectedConfidence.add("B");
								selectedConfidence.add("C");
								selectedConfidence.add("D");
								selectedConfidence.add("E");
							}
						}
						//JOptionPane.showMessageDialog(null, selectedConfidence);
						loader.getDatabse(database, organism, adapter, applicationManager, selections,
								selectedConfidence, isTF);
					}
		    		
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		
	}
	
	
	public void createSubnetworkSelectorFromFile(MyControlPanel panel) {

		
		subnetSelector = new JButton("Create Subnetwork from File");
		subnetSelector.setEnabled(true);
		add(subnetSelector);
		
		subnetSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
		subnetSelector.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		    	 JFileChooser chooser = new JFileChooser();
			     FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			     chooser.setFileFilter(filter);
			     int returnVal = chooser.showOpenDialog(panel);
			     if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	 File file = chooser.getSelectedFile();
			    	 CyNetwork network = applicationManager.getCurrentNetwork();			    	
			    	 SelectFromFileListTaskFactory Nodeselector = adapter.getCyServiceRegistrar().getService(SelectFromFileListTaskFactory.class);
			 		 adapter.getTaskManager().execute(Nodeselector.createTaskIterator(network, file));
			 		int reply = JOptionPane.showConfirmDialog(null, "Node correctly selected. Do you want to create"
			 				+ "a new network only using the selected nodes?", "Success", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			        	createSubselectedNetwork();
			        }
			        else {}
			 		 


			     }
		    }
		});
		
		
	}
	public void createSubselectedNetwork() {
		
		 CyNetwork subselectednetwork = applicationManager.getCurrentNetwork();
		 NewNetworkSelectedNodesOnlyTaskFactory subnet = adapter.getCyServiceRegistrar().getService(NewNetworkSelectedNodesOnlyTaskFactory.class);
		 adapter.getTaskManager().execute(subnet.createTaskIterator(subselectednetwork));
		
	}
	
	
	// necessary imports to override 
	public Component getComponent() {
		return this;
	}


	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}


	public String getTitle() {
		return "Omnipath";
	}


	public Icon getIcon() {
		return null;
	}
	
}
