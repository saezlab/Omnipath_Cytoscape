package Germany.RWTH.JRCCombine.internal.Omnipath;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;



public class DualListBox extends JPanel {

	  
	  private static final long serialVersionUID = 1L;

	  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

	  private static final String ADD_BUTTON_LABEL = "Add >>";

	  private static final String REMOVE_BUTTON_LABEL = "<< Remove";
	  
	  private static final String SELECTALL_BUTTON_LABEL = "Select all";

	  private static final String DESELECTALL_BUTTON_LABEL = "Deselect all";

	  private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Available choices";

	  private static final String DEFAULT_DEST_CHOICE_LABEL = "Your choices";
	  
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
	  
	  
	  

	  public DualListBox() {
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
	    TitledBorder title;
	    Border blackline;
	    blackline = BorderFactory.createLineBorder(Color.black);
	    title = BorderFactory.createTitledBorder(
	                           blackline, "Database Selection");
	    title.setTitleJustification(TitledBorder.CENTER);
	    Font titleFont = new Font("Courier", Font.BOLD,15);
	    title.setTitleFont(titleFont);
	    setBorder(BorderFactory.createCompoundBorder(title, 
	            BorderFactory.createEmptyBorder(20, 3, 15, 3)));
	    
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
	    // button to add selected items 
	    add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE,
	        EMPTY_INSETS, 0, 0));
	    
	    add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5,
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
	    add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0,
	        GridBagConstraints.CENTER, GridBagConstraints.NONE,
	        EMPTY_INSETS, 0, 0));
	    
	    add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 5, .5,
	        1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
	        EMPTY_INSETS, 0, 0));
//	    sourceList.setPreferredSize(new Dimension(100, 200));
//	    destList.setPreferredSize(new Dimension(110, 200));
	    sourceList.setFixedCellWidth(100);
	    sourceList.setFixedCellHeight(17);
	    destList.setFixedCellWidth(115);
	    destList.setFixedCellHeight(17);

	    addButton.setEnabled(FALSE);
	    removeButton.setEnabled(FALSE);
	    selectAll.setEnabled(FALSE);
	    deselectAll.setEnabled(FALSE);
	    
	  }
	
	  
	  
	  
private class AddListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (!sourceList.isSelectionEmpty()) {
		
		      Object selected[] = sourceList.getSelectedValues();
		      addDestinationElements(selected);
		      clearSourceSelected();
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


}
		


		
