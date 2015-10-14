/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

class NumRowRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {

        setText(Integer.toString(row + 1));
        setVerticalAlignment(SwingConstants.TOP);//VerticalTextPosition(SwingConstants.TOP);
        setHorizontalAlignment(SwingConstants.RIGHT);
        if (isSelected) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        } else {
            setBorder(BorderFactory.createEmptyBorder());
        };
        //setBackground(Color.GREEN);
        //this.setAlignmentX(0);
        return this;
    }
}

class MyTextRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {

        //setText(getText());
        setText(color.toString());
        setVerticalAlignment(SwingConstants.TOP);//VerticalTextPosition(SwingConstants.TOP);
        setHorizontalAlignment(SwingConstants.LEFT);
        if (isSelected) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        } else {
            setBorder(BorderFactory.createEmptyBorder());
        };
        //setBackground(Color.GREEN);
        //this.setAlignmentX(0);
        return this;
    }
}
