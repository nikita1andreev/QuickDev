/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

/*-----------------------------------------------*/
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import javax.swing.border.*;

public class ScrollCellRend implements TableCellRenderer {

private JTextArea ta;
private JScrollPane scrollPane;

public ScrollCellRend() {

ta = new JTextArea();
scrollPane = new JScrollPane(ta);
scrollPane.setBorder(BorderFactory.createEmptyBorder());
ta.setLineWrap(true);
ta.setWrapStyleWord(true);
ta.setOpaque(true);
}

public Component getTableCellRendererComponent(JTable table, Object value,
boolean isSelected, boolean hasFocus, int row, int column) {
if (isSelected) {
ta.setForeground(table.getSelectionForeground());
ta.setBackground(table.getSelectionBackground());
} else {
ta.setForeground(table.getForeground());
ta.setBackground(table.getBackground());
}
ta.setFont(table.getFont());
if (hasFocus) {
if (isSelected)
ta.setBorder(BorderFactory.createLineBorder(Color.blue));

if (table.isCellEditable(row, column)) {
ta.setForeground(UIManager.getColor("Table.focusCellForeground"));
ta.setBackground(UIManager.getColor("Table.focusCellBackground"));
}
} else {
ta.setBorder(new EmptyBorder(1, 2, 1, 2));
}

ta.setText((value == null) ? "" : value.toString());
return scrollPane;
}
}