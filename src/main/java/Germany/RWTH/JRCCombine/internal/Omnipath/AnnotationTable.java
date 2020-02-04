package Germany.RWTH.JRCCombine.internal.Omnipath;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorListener;

import org.cytoscape.app.swing.CySwingAppAdapter;



public class AnnotationTable extends JPanel implements ActionListener {

	  
	  private static final long serialVersionUID = 1L;

	  private CySwingAppAdapter adapter; 
	  
	  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

	  private static final String ADD_BUTTON_LABEL = "Query pathways";

	  private static final String REMOVE_BUTTON_LABEL = "<< Remove";
	  
	  private static final String SELECTALL_BUTTON_LABEL = "Select all";

	  private static final String DESELECTALL_BUTTON_LABEL = "Deselect all";

	  private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Resources";

	  private static final String DEFAULT_DEST_CHOICE_LABEL = "Features";
	  
	  private static final boolean FALSE = false;

	  private static final boolean TRUE = true;

	  private JLabel sourceLabel;

	  private JList sourceList;

	  private SortedListModel sourceListModel;

	  private JList destList;

	  private SortedListModel destListModel;

	  private JLabel destLabel;

	  private JButton addButton;

	  private JButton removeButton;
	  
	  private JButton selectAll;
	  
	  private JButton deselectAll;
	  
	  

	  public AnnotationTable(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	    initScreen();
	  }
	  
	  public JList getSourceList() {
		  return sourceList;
	  }
	  
	  public JList getDestList() {
		  return destList;
	  }
	  
	  public void enableAddButton() {
		  addButton.setEnabled(TRUE);
	  }
	  
	  public void enableRemoveButton() {
		  removeButton.setEnabled(TRUE);
	  }
	  
	  public void enableSelectionButtons() {
		  selectAll.setEnabled(TRUE);
		  deselectAll.setEnabled(TRUE);
	  }
	  
	  public void disableSelectionButtons() {
		  selectAll.setEnabled(FALSE);
		  deselectAll.setEnabled(FALSE);
	  }
	  
	  public void disableAddButton() {
		  addButton.setEnabled(FALSE);
	  }
	  
	  public void disableRemoveButton() {
		  removeButton.setEnabled(FALSE);
	  }
	  
	  public String getSourceChoicesTitle() {
	    return sourceLabel.getText();
	  }
	  
	  public void setSourceChoicesTitle(String newValue) {
	    sourceLabel.setText(newValue);
	  }

	  public String getDestinationChoicesTitle() {
	    return destLabel.getText();
	  }

	  public void setDestinationChoicesTitle(String newValue) {
	    destLabel.setText(newValue);
	  }
	  public Object[] getResource() {
		  @SuppressWarnings("deprecation")
		  Object selected[] = sourceList.getSelectedValues();
		  return selected;
	  }
	  public Object[] getPathways() {
		  @SuppressWarnings("deprecation")
		  Object selected[] = destList.getSelectedValues();
		  return selected;
	  }

	  public void clearSourceListModel() {
	    sourceListModel.clear();
	  }

	  public void clearDestinationListModel() {
	    destListModel.clear();
	  }

	  public void addSourceElements(ListModel newValue) {
	    fillListModel(sourceListModel, newValue);
	  }

	  public void setSourceElements(ListModel newValue) {
	    clearSourceListModel();
	    addSourceElements(newValue);
	  }

	  public void addDestinationElements(ListModel newValue) {
	    fillListModel(destListModel, newValue);
	  }

	  private void fillListModel(SortedListModel model, ListModel newValues) {
	    int size = newValues.getSize();
	    for (int i = 0; i < size; i++) {
	      model.add(newValues.getElementAt(i));
	    }
	  }

	  public void addSourceElements(Object newValue[]) {
	    fillListModel(sourceListModel, newValue);
	  }

	  public void setSourceElements(Object newValue[]) {
	    clearSourceListModel();
	    addSourceElements(newValue);
	  }
	  public void setDestinationElements(Object newValue[]) {
		    clearDestinationListModel();
		    addDestinationElements(newValue);
	  }

	  public void addDestinationElements(Object newValue[]) {
	    fillListModel(destListModel, newValue);
	  }

	  private void fillListModel(SortedListModel model, Object newValues[]) {
	    model.addAll(newValues);
	  }

	  public Iterator sourceIterator() {
	    return sourceListModel.iterator();
	  }

	  public Iterator destinationIterator() {
	    return destListModel.iterator();
	  }

	  public void setSourceCellRenderer(ListCellRenderer newValue) {
	    sourceList.setCellRenderer(newValue);
	  }

	  public ListCellRenderer getSourceCellRenderer() {
	    return sourceList.getCellRenderer();
	  }

	  public void setDestinationCellRenderer(ListCellRenderer newValue) {
	    destList.setCellRenderer(newValue);
	  }

	  public ListCellRenderer getDestinationCellRenderer() {
	    return destList.getCellRenderer();
	  }

	  public void setVisibleRowCount(int newValue) {
	    sourceList.setVisibleRowCount(newValue);
	    destList.setVisibleRowCount(newValue);
	  }

	  public int getVisibleRowCount() {
	    return sourceList.getVisibleRowCount();
	  }

	  public void setSelectionBackground(Color newValue) {
	    sourceList.setSelectionBackground(newValue);
	    destList.setSelectionBackground(newValue);
	  }

	  public Color getSelectionBackground() {
	    return sourceList.getSelectionBackground();
	  }

	  public void setSelectionForeground(Color newValue) {
	    sourceList.setSelectionForeground(newValue);
	    destList.setSelectionForeground(newValue);
	  }

	  public Color getSelectionForeground() {
	    return sourceList.getSelectionForeground();
	  }

	  private void clearSourceSelected() {
	    @SuppressWarnings("deprecation")
		Object selected[] = sourceList.getSelectedValues();
	    for (int i = selected.length - 1; i >= 0; --i) {
	      sourceListModel.removeElement(selected[i]);
	    }
	    sourceList.getSelectionModel().clearSelection();
	    
	  }

	  private void clearDestinationSelected() {
	    @SuppressWarnings("deprecation")
		Object selected[] = destList.getSelectedValues();
	    for (int i = selected.length - 1; i >= 0; --i) {
	      destListModel.removeElement(selected[i]);
	    }
	    destList.getSelectionModel().clearSelection();
	  }
	  

	  private void initScreen() {
		  
	    // set up the title and frame of the lists 

	    Border blackline = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	    TitledBorder title = BorderFactory.createTitledBorder(blackline, "Annotation");
	    title.setTitleJustification(TitledBorder.CENTER);
	    Font titleFont = new Font("Courier", Font.BOLD,15);
	    title.setTitleFont(titleFont);
	    setBorder(BorderFactory.createCompoundBorder(title, 
	              BorderFactory.createEmptyBorder(3, 3, 3, 3)));
	    
	    // layout 
	    setLayout(new GridBagLayout());
	    // source and destination labels settings 
	    sourceLabel = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
	    destLabel = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
	    Font font = new Font("Courier", Font.BOLD,14);
	    sourceLabel.setFont(font);
	    destLabel.setFont(font);
	    
	    sourceListModel = new SortedListModel();
	    sourceList = new JList(sourceListModel);
	    
	    JScrollPane source_scrollPane = new JScrollPane(sourceList);
	    source_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    source_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    // button to add selected items 
	    add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE,
	        EMPTY_INSETS, 0, 0));
	    
	    add(source_scrollPane, new GridBagConstraints(0, 1, 1, 5, .5,
	        1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
	        EMPTY_INSETS, 0, 0));

	    addButton = new JButton(ADD_BUTTON_LABEL);
	    add(addButton, new GridBagConstraints(1, 2, 1, 2, 0, .25,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE,
	        EMPTY_INSETS, 0, 0));
	    
	    selectAll = new JButton(SELECTALL_BUTTON_LABEL);
	    deselectAll = new JButton(DESELECTALL_BUTTON_LABEL);
	    
	    add(selectAll, new GridBagConstraints(0, 6, 1, 5, .5,
		        1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
		        EMPTY_INSETS, 0, 0));
	    
	    add(deselectAll, new GridBagConstraints(2, 6, 1, 5, .5,
		        1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
		        EMPTY_INSETS, 0, 0));
	    
	    addButton.addActionListener(new AddListener());
	    selectAll.addActionListener(new selectActionListener());
	    deselectAll.addActionListener(new deselectActionListener());
	    
	    // button to remove selected items 
	    removeButton = new JButton(REMOVE_BUTTON_LABEL);
	    add(removeButton, new GridBagConstraints(1, 4, 1, 2, 0, .25,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
	            0, 5, 0, 5), 0, 0));
	    
	    removeButton.addActionListener(new RemoveListener());
	   


	    destListModel = new SortedListModel();
	    destList = new JList(destListModel);
	    
	    sourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    destList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    
	    
	    JScrollPane dest_scrollPane = new JScrollPane(destList);
	    dest_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    dest_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE,
	        EMPTY_INSETS, 0, 0));
	    
	    add(dest_scrollPane, new GridBagConstraints(2, 1, 1, 5, .5,
	        1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
	        EMPTY_INSETS, 0, 0));
	    
	    
//	    sourceList.setPreferredSize(new Dimension(100, 200));
//	    destList.setPreferredSize(new Dimension(110, 200));
	    sourceList.setFixedCellWidth(160);
	    sourceList.setFixedCellHeight(14);
	    destList.setFixedCellWidth(160);
	    destList.setFixedCellHeight(14);
	    

	    
	    sourceList.addMouseListener(new MouseAdapter(){

	        //Called when you click the JList
	        public void mouseClicked(MouseEvent e) {

	            JList list = (JList)e.getSource();
	            //Makes sure it only registers for single clicks(always registers even on double clicks, just registers twice.)
	            if (e.getClickCount() == 1) {
	              
	              clearDestinationListModel();
		   		  Object selected = sourceList.getSelectedValue();
		   		  String s = (String)selected;
		   		  //String database = s.split("-")[0];
		   		  HashMap table = MyAnnotationTable.getInstance(); 
		   		  String[] values = (String[]) table.get(s);
		   		  MyControlPanel.annotation.addDestinationElements(values);
		   		  
	              //LoadAnnotationTaskFactory annotation = new LoadAnnotationTaskFactory(database);	
	        	  //adapter.getTaskManager().execute(annotation.createTaskIterator());
	            	
	                
	            }
	        }
	    });
	    

	    addButton.setEnabled(FALSE);
	    removeButton.setEnabled(FALSE);
	    selectAll.setEnabled(TRUE);
	    deselectAll.setEnabled(TRUE);
	    addButton.setVisible(FALSE);
	    removeButton.setVisible(FALSE);
	    selectAll.setVisible(FALSE);
	    deselectAll.setVisible(FALSE);
	    setMinimumSize(new Dimension(170,170));
	    
	    
	  }
	
	  
	  
	  
private class AddListener implements ActionListener  {
	public void actionPerformed(ActionEvent e){
		if (!sourceList.isSelectionEmpty()) {
			
//			  clearDestinationListModel();
//		      Object selected = sourceList.getSelectedValue();
//		      String s = (String)selected;
//		      String database = s.split("-")[0];
//		      ArrayList<Object> unique_annotation = new ArrayList<Object>();
//		     
//		      try {
//			      URL oracle = new URL("http://omnipathdb.org/annotations_summary?databases="+database);
//				  URLConnection con = oracle.openConnection();
//				  InputStream is =con.getInputStream();
//				  BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
//				  in.readLine();
//				  String[] values;
//				  String[] split;
//				  String inputLine;
//				  while ((inputLine = in.readLine()) != null) {
//				    		    split = inputLine.split("\t");
//				    		    
//				    		    if (split[1].equals("pathway")) {
//				    		    	
//				    		    	values = split[2].split("#");
//				    		    	for (int k = 0; k<values.length;k++) {
//					    		    	unique_annotation.add(values[k]);
//				    		    	}
//				    		    }
//				    		    
//				    		    
//				    		    
//				    		   
//				    	}
//				    	in.close(); 
//		      } catch (Exception e1) {
//		    	  e1.printStackTrace();
//		      }
//		      addDestinationElements(unique_annotation.toArray());
		      
		}
		
	}

}


private class RemoveListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
		if (!destList.isSelectionEmpty()) {
			  Object selected[] = destList.getSelectedValues();
		      addSourceElements(selected);
		      clearDestinationSelected();
		}
		      
	}
}
		

private class selectActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
			
		    Object selected[] = new Object[sourceList.getModel().getSize()];
			for(int i = 0; i < sourceList.getModel().getSize();i++){
				
				selected[i] = sourceList.getModel().getElementAt(i);
				
			}
			
			addDestinationElements(selected);
			clearSourceListModel();
			
		    }
	}

private class selectSource implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
			
		    //JOptionPane.showMessageDialog(null,"HERE");
			
		    }
	}

private class deselectActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
			
			
			    Object selected[] = new Object[destList.getModel().getSize()];
				for(int i = 0; i < destList.getModel().getSize();i++){
					
					selected[i] = destList.getModel().getElementAt(i);
				}
				
				addSourceElements(selected);
				clearDestinationListModel();
			}
		    
	}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	//JOptionPane.showMessageDialog(null, "HERE");
	
}


}