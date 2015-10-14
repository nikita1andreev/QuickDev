/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

import a1.entity.Events;
import a1.entity.Events.event_status;
import a1.entity.Responsible;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import javax.swing.JFrame;
import org.hibernate.Session;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author nnn
 */
public class VEvent extends javax.swing.JFrame {

    /**
     * Creates new form VEvent
     */
    public Events event;
    //public JFrame Owner;
    
    public VEvent() {                
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JTextField();
        version = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        start = new javax.swing.JTextField();
        end = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        type = new javax.swing.JTextField();
        Responsible = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Мероприятие");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        name.setText("jTextField1");

        version.setText("jTextField1");
        version.setEnabled(false);

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        jLabel1.setText("Наименование");

        jLabel2.setText("Начало");

        jLabel3.setText("Конец");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Привязка");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel5.setText("Статус");

        jToolBar2.setRollover(true);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("  Записать  ");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jToolBar2.add(jButton3);

        jButton4.setText("  Закрыть  ");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jToolBar2.add(jButton4);

        start.setText("jTextField1");

        end.setText("jTextField2");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Тип");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        type.setText("jTextField1");
        type.setEnabled(false);

        Responsible.setText("jTextField1");
        Responsible.setEnabled(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Назначено");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        
        status_combobox = new JComboBox(Events.event_status.values());
        
        JLabel label = new JLabel();
        label.setText("Описание");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jToolBar2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(0, 251, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGap(43)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel1, Alignment.TRAILING)
        						.addComponent(jLabel2, Alignment.TRAILING)
        						.addComponent(jLabel3, Alignment.TRAILING)
        						.addComponent(jLabel4, Alignment.TRAILING)
        						.addComponent(jLabel6, Alignment.TRAILING)
        						.addComponent(jLabel7, Alignment.TRAILING)
        						.addComponent(jLabel5, Alignment.TRAILING))
        					.addGap(7))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(label, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jScrollPane1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
        				.addComponent(status_combobox, 0, 267, Short.MAX_VALUE)
        				.addComponent(Responsible, 267, 267, Short.MAX_VALUE)
        				.addComponent(name, 267, 267, Short.MAX_VALUE)
        				.addComponent(version, 267, 267, Short.MAX_VALUE)
        				.addComponent(start, 267, 267, Short.MAX_VALUE)
        				.addComponent(end, 267, 267, Short.MAX_VALUE)
        				.addComponent(type, 267, 267, Short.MAX_VALUE))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jToolBar2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(13)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(start, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel2))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(26)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(version, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel4)))
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(end, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jLabel3)))
        			.addGap(7)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(type, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel6))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(Responsible, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel7))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel5)
        				.addComponent(status_combobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(8)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        				.addComponent(label, Alignment.LEADING))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        event.setName(name.getText());
        event.setDescription(description.getText());
        event.setStart_ldt(LocalDateTime.parse(start.getText(),DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));//dateTimeStyle)ofPattern("dd.MM.yyyy' 'HH:mm:ss")));
        event.setEnd_ldt(LocalDateTime.parse(end.getText(),DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))); 
        event.setName(name.getText());
        event.setStatus((event_status) status_combobox.getSelectedItem());
          
        Main.save(event);
    }//GEN-LAST:event_jButton3MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.name.setText(event.getName());
        this.start.setText(event.getStart().toLocaleString());
        this.end.setText(event.getEnd().toLocaleString());
        this.description.setText(event.getDescription());
        this.type.setText(event.getType().toString());
        Responsible r = event.getResponsible();
        this.Responsible.setText(r==null?"":r.toString());
        this.status_combobox.setSelectedItem(event.getStatus());
    }//GEN-LAST:event_formWindowOpened

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jButton4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            Main.generateAndSendEmail(ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            Main.generateAndSendEmail(ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            Main.generateAndSendEmail(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            Main.generateAndSendEmail(ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VEvent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Responsible;
    private javax.swing.JTextArea description;
    private javax.swing.JTextField end;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextField name;
    private javax.swing.JTextField start;
    private javax.swing.JTextField type;
    private javax.swing.JTextField version;
    private JComboBox status_combobox;
}