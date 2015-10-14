/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

/*-------------------------------------------------------------*/
import java.awt.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class LocationTableCellEditor implements TableCellRenderer, TableCellEditor {

private JPanel panel = new JPanel(new GridLayout(1,1,2,1));
private EventListenerList listenerList = new EventListenerList();
private ChangeEvent event = new ChangeEvent(this);

private JTextArea ta;
private JScrollPane scrollPane;

public LocationTableCellEditor(String type) {

ta = new JTextArea();
scrollPane = new JScrollPane(ta);
scrollPane.setBorder(BorderFactory.createEmptyBorder());
ta.setLineWrap(true);
ta.setWrapStyleWord(true);
ta.setOpaque(true);

panel.add(scrollPane);
panel.setPreferredSize(new Dimension(80,15));
}

public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus,int row, int column) {
String s = (String)value;
ta.setText(s);
return panel;
}

public Component getTableCellEditorComponent(JTable table,
Object value, boolean isSelected, int row, int column) {
return getTableCellRendererComponent(table, value,isSelected, true, row, column);
}

public boolean isCellEditable(EventObject anEvent) {
return true;
}

public boolean shouldSelectCell(EventObject anEvent) {
return true;
}

public void cancelCellEditing() {
}

public boolean stopCellEditing() {
fireEditingStopped();
return true;
}

public Object getCellEditorValue() {
return ta.getText();
}

public void addCellEditorListener(CellEditorListener l){
listenerList.add(CellEditorListener.class, l);
}

public void removeCellEditorListener(CellEditorListener l) {
listenerList.remove(CellEditorListener.class, l);
}

protected void fireEditingStopped(){
Object[] listeners = listenerList.getListenerList();
for (int i = listeners.length - 2; i >= 0; i -= 2)
((CellEditorListener)listeners[i+1]).editingStopped(event);
}

protected void fireEditingCanceled() {
Object[] listeners = listenerList.getListenerList();
for (int i = listeners.length - 2; i >= 0; i -= 2)
((CellEditorListener)listeners[i+1]).editingCanceled(event);
}
void p(String s) {System.out.println(s);}
}