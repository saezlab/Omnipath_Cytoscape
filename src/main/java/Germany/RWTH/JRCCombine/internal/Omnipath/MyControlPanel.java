package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
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
	private boolean isDirected = false;
	private boolean isSigned = false;
	private JLabel wronglabel;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	private JPanel panel8;
	private JCheckBox directedInteractionCB;
	private JCheckBox signedInteractionCB;
	
	

	
	// set up GUI control panel
	public MyControlPanel(CySwingAppAdapter adapter, CyApplicationManager applicationManager) {
		
		
		this.adapter =  adapter;
		this.applicationManager = applicationManager;
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        // initialise required panels
        panel1 = new JPanel();
        panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		panel7 = new JPanel();
		panel8 = new JPanel();


		// create GUI components to allow user selection		
		createMainBorder();
		createWebLabel();
		createOrganismselection();
		createDatabaseselection();
		createTFregulonConfidenceButtons();
		createSelectionTable();
		createConfirmationButton();
		createCheckBoxPanel();
		createWrongSelectionLabel();
		
		// add panels to the frame along with the defined 
		// constrains 
		add(panel1, gbc);
		add(panel2, gbc);
		add(panel3, gbc);
		add(panel4, gbc);
		add(panel5, gbc);
		add(panel6, gbc);
		add(panel7, gbc);
		add(panel8, gbc);
	
	
		
		// function to select a sublist of nodes from the network
		// and sub-select those 
		//createSubnetworkSelectorFromFile(this);
	}
	
	
	// function to create the border for the Omnipath 
	// Control Panel
	public void createMainBorder() {
		
		TitledBorder title;
		Border blackline;
		blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(blackline, "Omnipath Control Panel");
		title.setTitleJustification(TitledBorder.CENTER);
		Font titleFont = new Font("Courier", Font.BOLD, 20);
		title.setTitleFont(titleFont);
		setBorder(BorderFactory.createCompoundBorder(title, 
		          BorderFactory.createEmptyBorder(20, 3, 15, 3)));
		
	}
	
	public void createWebLabel() {
		
		JLabel website = new JLabel();
		// make the label responsive to user interaction
		goWebsite(website, "http://omnipathdb.org/info", "Omnipath");
		website.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(website);
		
	}
	
	public void createOrganismselection() {
		
		// group of JRadioButton for organism selection

		JRadioButton option1 = new JRadioButton("Human");
        JRadioButton option2 = new JRadioButton("Mouse");
        JRadioButton option3 = new JRadioButton("Rat");
        ButtonGroup group = new ButtonGroup();
        
        group.add(option1);
        group.add(option2);
        group.add(option3);
 
        panel2.add(option1);
        panel2.add(option2);
        panel2.add(option3);
        
        lowBorderMaker("Select Organism", panel2, 10, 20, 10, 20);
       
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
	        					checkBoxEnable(true);
	        					if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
		                			
		                    		WorngLabelVisibility(true);
		                			
		                		}else WorngLabelVisibility(false);
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
	// function to create Low border for the different panels 
	// in the control menu
	public void lowBorderMaker(String stringTitle, JPanel panel, int top, int left,
			int bottom, int right) {
		
		TitledBorder title;
		Border blackline;
		blackline = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		title = BorderFactory.createTitledBorder(
		                           blackline, stringTitle);
		title.setTitleJustification(TitledBorder.LEFT);
		Font titleFont = new Font("Courier", Font.BOLD,14);
		title.setTitleFont(titleFont);
        panel.setBorder(BorderFactory.createCompoundBorder(title, 
		          BorderFactory.createEmptyBorder(top, left, bottom, right)));
		
	}
	
	public void createTFregulonConfidenceButtons() {
	
	  	lowBorderMaker("Select TF Confidence Level", panel4, 10, 20, 10, 20);
	  
	  	ConfidenceA = new JRadioButton("A");
	    ConfidenceB = new JRadioButton("B");
	    ConfidenceC = new JRadioButton("C");
	    ConfidenceD = new JRadioButton("D");
	    ConfidenceE = new JRadioButton("E");
	    ConfidenceAll = new JRadioButton("All");
	    
	    panel4.add(ConfidenceA);
	    panel4.add(ConfidenceB);
	    panel4.add(ConfidenceC);
	    panel4.add(ConfidenceD);
	    panel4.add(ConfidenceE);
	    panel4.add(ConfidenceAll);
        
        ConfidenceA.setHorizontalAlignment(SwingConstants.CENTER);  
        ConfidenceB.setHorizontalAlignment(SwingConstants.CENTER);
        ConfidenceC.setHorizontalAlignment(SwingConstants.CENTER);  
        ConfidenceD.setHorizontalAlignment(SwingConstants.CENTER);
        ConfidenceE.setHorizontalAlignment(SwingConstants.CENTER);  
        ConfidenceAll.setHorizontalAlignment(SwingConstants.CENTER);
        
        ConfidenceA.setEnabled(false);
        ConfidenceB.setEnabled(false);    
        ConfidenceC.setEnabled(false);    
        ConfidenceD.setEnabled(false);    
        ConfidenceE.setEnabled(false);    
        ConfidenceAll.setEnabled(false);    
        
        
        // adding action listeners for the JRadioButtons
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
	
	
	// enabling TF selection according to user selections
  	public void enableTFConfidence() {
  		
  		ConfidenceA.setEnabled(true);
        ConfidenceB.setEnabled(true);    
        ConfidenceC.setEnabled(true);    
        ConfidenceD.setEnabled(true);    
        ConfidenceE.setEnabled(true);    
        ConfidenceAll.setEnabled(true);    
  		
  	}
  	// disabling TF selection according to user selections
  	public void disableTFConfidence() {
  		
  		ConfidenceA.setEnabled(false);
        ConfidenceB.setEnabled(false);    
        ConfidenceC.setEnabled(false);    
        ConfidenceD.setEnabled(false);    
        ConfidenceE.setEnabled(false);    
        ConfidenceAll.setEnabled(false);    
  		
  	}
  	
	public void createDatabaseselection() {


        lowBorderMaker("Select Dataset", panel3, 10, 20, 10, 20);		
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
					checkBoxEnable(true);
					if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
            			
                		WorngLabelVisibility(true);
            			
            		}else WorngLabelVisibility(false);
					updateList();
					
		    	}
		    }
		});

		panel3.add(bookList);
		
	}
	
	public void createCheckBoxPanel() {
		
		directedInteractionCB = new JCheckBox("Directed interactions only");
		signedInteractionCB = new JCheckBox("Signed interactions only");
		Box box = Box.createVerticalBox();
		box.add(directedInteractionCB);
		box.add(signedInteractionCB);
		panel6.add(box);
		
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
                            JOptionPane.showMessageDialog(null, "A problem occurred while opening URL. Try again later or check your internet connection",
                            		"Error Message", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });
    }
	
	public void createSelectionTable() {
		
	    dual = new DualListBox();
	    dual.addSourceElements(new String[] { "Complete selection" });
	    dual.addDestinationElements((new String[] { "Complete selection" }));
	    panel5.add(dual);
		
	}
	
	// update databse list according to the user selections
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
				checkBoxEnable(false);
				
			}
			
		}
		
	}
	
	public void checkBoxEnable(boolean flag) {
		
		directedInteractionCB.setEnabled(flag);
		signedInteractionCB.setEnabled(flag);
		
		
		
	}
	
	public void createConfirmationButton() {
		
		confirm = new JButton("Confirm selections");
		confirm.setEnabled(true);
		panel7.add(confirm);
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
						
						Object selections[] = new Object[dual.getDestList().getModel().getSize()];
						for(int i = 0; i < dual.getDestList().getModel().getSize(); i++){
							
							selections[i] =  dual.getDestList().getModel().getElementAt(i);  
						}
						
						if (database.equals("TF-target interactions")) {
							
							isTF = true;
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
						} else  isTF = false;

						if (directedInteractionCB.isSelected()) isDirected = true;
						else isDirected = false;
						if (signedInteractionCB.isSelected()) isSigned = true;
						else isSigned = false;
						loader.getDatabse(database, organism, adapter, applicationManager, selections,
								selectedConfidence, isTF, isDirected, isSigned);
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
	public void createWrongSelectionLabel() {
		
		wronglabel = new JLabel("miRNA are provided only for Humans");
		wronglabel.setVisible(false);
		wronglabel.setForeground (Color.red);
		panel8.add(wronglabel);
	}
	public void WorngLabelVisibility(boolean flag) {
		
		wronglabel.setVisible(flag);
		
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
