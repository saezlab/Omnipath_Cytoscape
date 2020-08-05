package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
import javax.swing.JList;
import javax.swing.JOptionPane;





public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	
	// declaration of necessary variables 
	private static final long serialVersionUID = 8292806967891823933L;
	// default selection of database
	private String database = "";
	private String organism = "";
	private CySwingAppAdapter adapter;
	private JButton confirm;
	private JButton annotationButton;
	private JButton queryAnnotation;
	private JComboBox<String> bookList;
	private JButton subnetSelector;
	private CyApplicationManager applicationManager;
	private DualListBox dual;
	public static AnnotationTable annotation;
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
	private JPanel panel9;
	private JPanel panel10;
	private JPanel panel11;
	private JCheckBox directedInteractionCB;
	private JCheckBox signedInteractionCB;
	private String previousDataBaseSelection;
	private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
	private static Object[] resources;
	private static Object[] pathways;
	public static JList<String> list;
	
	

	
	public static Object[] getPathways() {
		return pathways;
		
	}
	public static Object[] getResources() {
		return resources;
		
	}
	public void setList (JList<String> list) {
		this.list = list;
	}

	
	// set up GUI control panel
	public MyControlPanel(CySwingAppAdapter adapter, CyApplicationManager applicationManager)  {
		
		
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
		panel9 = new JPanel();
		panel10 = new JPanel();
		panel11 = new JPanel();


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
		createAnnotation();
		cretateLunchQueryButton();
		createQueryAnnotationButton();
		
		// add panels to the frame along with the defined 
		// constrains 
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 0;
		add(panel1, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 1;
		add(panel2, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(panel3, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(panel4, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 4;
		add(panel5, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 10;
		gbc.gridx = 2;
		gbc.gridy = 8;
		add(panel9, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 6;
		add(panel6, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 8;
		add(panel8, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 10;
		add(panel7, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 7;
		add(panel10, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 2;
		gbc.gridy = 9;
		add(panel11, gbc);
	
	
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
		title = BorderFactory.createTitledBorder(blackline, "OmniPath Control Panel");
		title.setTitleJustification(TitledBorder.CENTER);
		Font titleFont = new Font("Courier", Font.BOLD, 20);
		title.setTitleFont(titleFont);
		setBorder(BorderFactory.createCompoundBorder(title, 
		          BorderFactory.createEmptyBorder(5, 3, 15, 3)));
		
	}
	
	public void createWebLabel() {
		
		JLabel website = new JLabel();
		// make the label responsive to user interaction
		goWebsite(website, "https://omnipathdb.org/info", "OmniPath");
		website.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel1.add(website);
		
	}
	
	public void createAnnotation(){
		
		
		//DefaultListModel<String> model = new DefaultListModel<>();
		
		annotation = new AnnotationTable(adapter);
		annotation.addSourceElements(new String[] { "0 annotations" });
		annotation.addDestinationElements(new String[] { "0 features" });
		panel9.add(annotation);
		
	}
	
	public void createQueryAnnotationButton() {
		
		queryAnnotation = new JButton("Query annotations");
		queryAnnotation.setEnabled(true);
		panel11.add(queryAnnotation);
		queryAnnotation.setAlignmentX(Component.RIGHT_ALIGNMENT);
		//implicit action listener
		queryAnnotation.addActionListener (new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				   
				    	LoadAnnotationTaskFactory annotation = new LoadAnnotationTaskFactory();	
			    		adapter.getTaskManager().execute(annotation.createTaskIterator());
			    		annotationButton.setEnabled(true);
				    }
				});
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
	                    		previousDataBaseSelection = database;
	        		    		database = (String) bookList.getSelectedItem();
	        			    	dual.enableAddButton();
	        					dual.enableRemoveButton();
	        					dual.enableSelectionButtons();
	        					if (database.equals("Signaling networks") || database.equals("miRNA-mRNA") || database.equals("TF-target interactions")) {
	        						checkBoxEnable(true);
	        					}else checkBoxEnable(false);
	        					
	        					if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
		                			
		                    		WorngLabelVisibility(true);
		                			
		                		}else WorngLabelVisibility(false);
	        					try {
									updateList();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	        				
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
        panel.setBorder(BorderFactory.createCompoundBorder(title, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		
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
		String[] choices = new String[] {"Signaling networks", "Enzyme-substrate interactions", "miRNA-mRNA",
				"TF-target interactions"};
		
		bookList = new JComboBox<>(choices);
		bookList.setBorder(new EmptyBorder(5, 5, 5, 5));
		bookList.setSelectedItem(null);
		// implicit action listener 
		bookList.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	previousDataBaseSelection = database;
		    	database = (String) bookList.getSelectedItem();
		    	if (database.equals("TF-target interactions")) {
	    			
	    			enableTFConfidence();
	    			
	    		} else disableTFConfidence();
		    	
		    	if (!organism.equals("")) {
		    		
			    	dual.enableAddButton();
					dual.enableRemoveButton();
					dual.enableSelectionButtons();
					if (database.equals("Signaling networks") || database.equals("miRNA-mRNA") || database.equals("TF-target interactions")) {
						checkBoxEnable(true);
					}else checkBoxEnable(false);
					if ((database.equals("miRNA-mRNA")) && (organism.equals("Mouse") || organism.equals("Rat"))) {
            			
                		WorngLabelVisibility(true);
            			
            		}else WorngLabelVisibility(false);
					try {
						updateList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
		    	}
		    }
		});

		panel3.add(bookList);
		
	}
	
	public void createCheckBoxPanel() {
		
		directedInteractionCB = new JCheckBox("Directed interactions only");
		signedInteractionCB = new JCheckBox("Signed interactions only");
		Box box = Box.createHorizontalBox();
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
	
	// update database list according to the user selections
	public void updateList() throws IOException  {
		
		if (database.equals("miRNA-mRNA") && (organism.equals("Rat") || organism.equals("Mouse"))) {
			
			dual.clearDestinationListModel();
			dual.clearSourceListModel();
			dual.addSourceElements((new String[] {"0 resources"}));
			dual.disableAddButton();
			dual.disableRemoveButton();
			dual.disableSelectionButtons();
			checkBoxEnable(false);
			
		}
		else {
			
			if ((!previousDataBaseSelection.equals(database)) || ((previousDataBaseSelection.equals("miRNA-mRNA")) && organism.equals("Human")) &&
					database.equals("miRNA-mRNA")) {
				
				LoadDataBasesTaskFactory databasesTF = new LoadDataBasesTaskFactory(database, organism, dual);	
				adapter.getTaskManager().execute(databasesTF.createTaskIterator());
			
			}
			
		}
			
	}
	
		
		
	
	public void checkBoxEnable(boolean flag) {
		
		if(flag==false) {
			directedInteractionCB.setSelected(false);
			signedInteractionCB.setSelected(false);
			
		}
		directedInteractionCB.setEnabled(flag);
		signedInteractionCB.setEnabled(flag);
		
	}
	
public void cretateLunchQueryButton() {
		
		confirm = new JButton("Launch query");
		confirm.setEnabled(true);
		panel10.add(confirm);
		confirm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		
		// implicit action listener
		confirm.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	//pathways = annotation.getPathways();
				//resources = annotation.getResource();
		    	
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
						
						// loader.getDatabse(database, organism, adapter, applicationManager, selections,
						//		selectedConfidence, isTF, isDirected, isSigned, pathways, resources);
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
	
	public void createConfirmationButton() {
		
		annotationButton = new JButton("Annotate network");
		annotationButton.setEnabled(false);
		panel7.add(annotationButton);
		annotationButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		// implicit action listener
		annotationButton.addActionListener (new ActionListener () {
				    public void actionPerformed(ActionEvent e) {
				   
				    	
				    	CyNetwork network = applicationManager.getCurrentNetwork();
				    	if (network != null) {
				    		
				    		
				    		Object values[] = annotation.getDestList().getSelectedValues();
				    		String selected = (String)annotation.getSourceList().getSelectedValue();
				    		
				    		if (selected!=null && values.length!=0) {
				    			String database = selected.split("/")[0];
					    		String label = selected.split("/")[1];
						    	FindAnnotationTaskFactory findAnnotation = new FindAnnotationTaskFactory(database, label, values );	
								adapter.getTaskManager().execute(findAnnotation.createTaskIterator());
				    		}
				    		else {
					    		JOptionPane.showMessageDialog(null, "Please make a selection for source and features to proceed!",
		                        		"Error Message", JOptionPane.ERROR_MESSAGE);
					    	}
				    		
				    	}
				    	else {
				    		JOptionPane.showMessageDialog(null, "Please import a network to proceed!",
	                        		"Error Message", JOptionPane.ERROR_MESSAGE);
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