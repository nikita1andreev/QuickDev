/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

/*-----------------------------------------------*/
import javax.swing.table.TableCellRenderer;
import javax.swing.JTextArea;
import javax.swing.JTable;
import java.awt.Component;

/**

Large text renderer with row height automatic expanding
 *

@author Andrey Brainin
@version 1.0
 */

public class JTextCellRenderer extends JTextArea
implements TableCellRenderer
{
    public JTextCellRenderer()
    {
        setWrapStyleWord(true);
        setLineWrap(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column)
    {

       if (isSelected)
        {
           setForeground(table.getSelectionForeground());
           setBackground(table.getSelectionBackground());
       }
       else
        {
           setForeground(table.getForeground());
           setBackground(table.getBackground());
       }
        setText((String) value);
        int height = (int) getPreferredSize().getHeight();
        if (table != null && height > 0 && table.getRowHeight(row) < height)
            table.setRowHeight(row, height);
       setFont(table.getFont());
       return this;
    }

}