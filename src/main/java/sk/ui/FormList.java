package sk.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTreeTable;

import a1.annotations.Synonym;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FormList extends JFrame {

	private JPanel contentPane;
	private Class beanClass;
	//public Object Owner;
	private JXTreeTable treeTable;
	private ArrayList selected;
	
	public JXTreeTable getList(){
		return treeTable;
	};
	
	public ArrayList<Object> getSelected(){
		return this.selected;
	}
	
	public void setSelected(ArrayList<Object> s){
		this.selected = s;
	}

	public FormList( String query, List arg , Class beanClass,ArrayList<HashMap>tm) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				FormList form = (FormList)arg0.getSource();
				form.setSelected(new ArrayList<>());
			}
		});
		
		this.beanClass = beanClass;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//FormList form = ((FormList)SwingUtilities.windowForComponent((Component) arg0.getSource()));
		this.selected = new ArrayList<Object>();
		
		JToolBar toolBar = new JToolBar();		
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(102, Short.MAX_VALUE))
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
		);		
		treeTable = new JXTreeTable();
		//scrollPane.setColumnHeaderView(treeTable);
		contentPane.setLayout(gl_contentPane);
		
		ArrayNode root = new ArrayNode(new Object[0]);
		BeanTreeTableModel btm = new BeanTreeTableModel(query, arg, beanClass,root);
		treeTable.setTreeTableModel(btm); 
		ut.TuneTreeColumns(treeTable, null);
		ut.TuneToolbar(toolBar,treeTable);
		scrollPane.setViewportView(treeTable);
		treeTable.setEditable(false);
		
//    	if (this.beanClass.getDeclaredAnnotation(Synonym.class)!=null) 
//    		setTitle((String) ((Synonym) this.beanClass.getDeclaredAnnotation(Synonym.class)).text());
		ut.getLabel(this.beanClass);

		//scrollPane.setColumnHeaderView(treeTable);
		
	}
}
