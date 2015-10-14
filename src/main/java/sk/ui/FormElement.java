package sk.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import org.hibernate.persister.entity.AbstractEntityPersister;

import a1.annotations.Synonym;
import a1.entity.Contacts;

import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class FormElement extends JFrame {

	public java.io.Serializable formObject;
	public HashMap EditButtons;
	public HashMap EditFields;

	JPanel panel_1;
	JPanel panel_2;
	JScrollPane scrollPane;
	
	public FormElement() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				GroupLayout layout = new GroupLayout(panel_2);
				// panel_1
				panel_2.setLayout(layout);

				// Turn on automatically adding gaps between components
				layout.setAutoCreateGaps(true);

				// Turn on automatically creating gaps between components that
				// touch
				// the edge of the container and the container.
				layout.setAutoCreateContainerGaps(true);
				GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

				ParallelGroup h1 = layout.createParallelGroup();
				ParallelGroup h2 = layout.createParallelGroup();
				ParallelGroup h3 = layout.createParallelGroup();

				// ParallelGroup v2 =
				// layout.createParallelGroup(Alignment.BASELINE);
				GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
				EditButtons = new HashMap<>();
				EditFields = new HashMap<>();
				for (Field d : formObject.getClass().getDeclaredFields()) {
					JLabel t = new JLabel();					 
					t.setText(ut.getLabel(d));
					// JPanel t0 = new JPanel();
					JTextFieldPlus t1 = new JTextFieldPlus();					
					JButton t2 = new JButton("...");
					ut.linkFormObjectElements(FormElement.this,d.getName(),t1,t2);
//					EditButtons.put(t2, t1);
//					EditFields.put(d, t1);
					if (d.getType().getAnnotation(Entity.class) != null) {
						// ((AbstractEntityPersister)Main.Factory.getClassMetadata(d.getClass().getName())).getTableName();
						// System.out.println(((AbstractEntityPersister)Main.Factory.getClassMetadata(d.getType().getName())).getTableName());
						t2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								FormList f = new FormList(
										"select * from " + ((AbstractEntityPersister) Main.Factory
												.getClassMetadata(d.getType().getName())).getTableName(),
										null, d.getType(), null);
								f.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
								ModalFrameUtil mf = new ModalFrameUtil();
								ArrayList<Object> a = mf.showAsModal((JFrame) f,
										(JFrame) SwingUtilities.windowForComponent((Component) e.getSource()));
								((JFrame) SwingUtilities.windowForComponent(((JButton) e.getSource()))).toFront();
								if (a.size()>0) {
									((JTextFieldPlus)EditButtons.get(e.getSource())).setValueObject(a.get(0));
								}

							}
						});
					};
					// t0.add(t1);
					// t0.add(t2);
					ParallelGroup v1 = layout.createParallelGroup(Alignment.BASELINE);
					h1.addComponent(t);
					h2.addComponent(t1);
					h3.addComponent(t2);
					v1.addComponent(t);
					v1.addComponent(t1);
					v1.addComponent(t2);
					vGroup.addGroup(v1);
					try {
						d.setAccessible(true);
						Object val = d.get(formObject);
						 
							if(d.getType().equals(String.class)||d.getType().equals(java.util.Date.class)||(val instanceof java.lang.Number))
								if (val==null) 
									t1.setText(""); 
								else 
									t1.setText(val.toString());
							else 
								t1.setValueObject(val);		
											
					} catch (IllegalArgumentException | IllegalAccessException e1) {
						Main.generateAndSendEmail(e1);
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
					
				}

				hGroup.addGroup(h1);
				hGroup.addGroup(h2);
				hGroup.addGroup(h3);
				layout.setHorizontalGroup(hGroup);
				layout.setVerticalGroup(vGroup);

				// GroupLayout.SequentialGroup vseq1 =
				// groupLayout.createSequentialGroup();

				// groupLayout.setHorizontalGroup(hseq1);
				// groupLayout.setVerticalGroup(vseq1);
				// panel_1.add(layout);

				// ((JFrame)SwingUtilities.windowForComponent(
				((JFrame) e.getSource())
						// ))
						.setSize(300, 300);
				// } catch (IntrospectionException e1) {
				// Main.generateAndSendEmail(e1);
				// }

			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE));

		JToolBar toolBar = new JToolBar();

		panel_1 = new JPanel();

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//for(Entry ent: EditFields)
				Iterator it = (Iterator) EditFields.entrySet().iterator();
				while(it.hasNext()){
					Entry<Field,JTextFieldPlus> ent = (Entry<Field,JTextFieldPlus>)it.next();
					try {
						ent.getKey().setAccessible(true);
						if (!ent.getValue().isSimpleType())
							ent.getKey().set(formObject, ent.getValue().getValueObject());
						else 
							ent.getKey().set(formObject, ut.toObject(ent.getKey().getType(),ent.getValue().getText()));
					} catch (IllegalArgumentException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				Main.save(formObject);
			}
		});
		toolBar.add(btnSave);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((JFrame) SwingUtilities.windowForComponent(((JButton) e.getSource()))).dispose();
			}
		});
		toolBar.add(btnClose);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)).addGap(10)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(6).addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE).addGap(11)));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE));

		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 203, Short.MAX_VALUE));
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
}
