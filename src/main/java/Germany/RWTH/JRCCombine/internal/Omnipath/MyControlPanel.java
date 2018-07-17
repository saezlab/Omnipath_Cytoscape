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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
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
	
	// set up GUI control panel
	public MyControlPanel(CySwingAppAdapter adapter) {
		
		this.adapter =  adapter;
		
		JLabel lbXYZ = new JLabel("Omnipath Control Panel");
		Font font = new Font("Courier", Font.BOLD,20);
		lbXYZ.setFont(font);
		lbXYZ.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{70, 110, 110};
		setSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(500, 500));
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
		    	//JOptionPane.showMessageDialog(null, database);
		    }
		});

		add(bookList);
		
	
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
					else loader.getDatabse(database, organism, adapter);
		    		
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
		    }
		});
		
		
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
                    	
                    }
                }
            }
        };
        option1.addActionListener(actionListener);
        option2.addActionListener(actionListener);
        option3.addActionListener(actionListener);
        
        
		
		
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
