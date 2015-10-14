package sk.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;

import a1.annotations.Forms;
import a1.annotations.Synonym;

public class ut {

	public static void TuneTreeColumns(JXTreeTable docTable2, ArrayList<HashMap> tt) {
		// StringTokenizer st = new StringTokenizer (s,",");
		DefaultTableColumnModelExt tcm = (DefaultTableColumnModelExt) docTable2.getColumnModel();
		DefaultTableColumnModelExt ntcm = new DefaultTableColumnModelExt();
		// while(ntcm.getColumnCount()>0) ntcm.removeColumn(ntcm.getColumn(0));

		// while(st.hasMoreElements()){
		TableColumnExt c = null, nc = null;
		// int j=0;
		if (tt != null) {
			for (HashMap hm : tt) {
				// String a = ;//(String) st.nextElement();
				// for (TableColumnExt c : Collections.list(tcm.getColumns())) {
				for (int i = 0; i < tcm.getColumnCount(true); i++) {
					c = tcm.getColumnExt(i);
					if (c.getIdentifier().equals((String) hm.get("name"))) {
						nc = new TableColumnExt();
						nc.setModelIndex(i);
						// j++;
						nc.setHeaderValue((String) hm.get("title"));
						
						if(hm.get("width")!=null)
							nc.setMaxWidth((int) hm.get("width"));

						nc.setIdentifier((String) hm.get("name"));
						nc.setPrototypeValue(c.getPrototypeValue());
						nc.setVisible((boolean) (hm.get("visible") == null ? true : hm.get("visible")));
						// nc.setcli(c.getPrototypeValue());
						if (ntcm.getColumnCount() == 0) {
							// c.setCellRenderer(tcm.getColumn(0).getCellRenderer());
							// c.setCellEditor(tcm.getColumn(0).getCellEditor());
							// c.setIdentifier(tcm.getColumn(i).getIdentifier());
							// c.setHeaderValue(tcm.getColumn(i).getHeaderValue());
							// c.
						}
						ntcm.addColumn(nc);

						break;
					}
				}
			}
		} else {

			for (Field e : ((BeanTreeTableModel) docTable2.getTreeTableModel()).getBeanClass().getDeclaredFields())
			// (PropertyDescriptor e:descriptors)
			{
				if (e.getType() == Set.class)
					continue;
				for (int i = 0; i < tcm.getColumnCount(true); i++) {
					c = tcm.getColumnExt(i);
					if (c.getIdentifier().equals((String) e.getName())) {
						nc = c;// .clone();//new TableColumnExt();
						nc.setModelIndex(i);
						// j++;
//						if (e.getDeclaredAnnotation(Synonym.class) != null)
//							nc.setHeaderValue((String) e.getDeclaredAnnotation(Synonym.class).text());
						nc.setHeaderValue(ut.getLabel(e));
						// nc.setMaxWidth((int) hm.get("width"));
						// nc.setIdentifier((String) hm.get("name"));
						// nc.setPrototypeValue(c.getPrototypeValue());
						// nc.setVisible((boolean)
						// (hm.get("visible")==null?true:hm.get("visible")));
						// nc.setcli(c.getPrototypeValue());
						// if(ntcm.getColumnCount()==0) {
						// c.setCellRenderer(tcm.getColumn(0).getCellRenderer());
						// c.setCellEditor(tcm.getColumn(0).getCellEditor());
						// c.setIdentifier(tcm.getColumn(i).getIdentifier());
						// c.setHeaderValue(tcm.getColumn(i).getHeaderValue());
						// c.
						// }
						ntcm.addColumn(nc);

						break;
					}

				}

			}

		}		
		if (ntcm.getColumnCount() > 0)
			((BeanTreeTableModel) docTable2.getTreeTableModel())
					.setHierarchicalColumn((String) ntcm.getColumn(0).getIdentifier());
		docTable2.setColumnModel(ntcm);
		docTable2.updateUI();
		
	}

	public static void TuneColumns(JTable t, ArrayList<HashMap> tt) {
		// StringTokenizer st = new StringTokenizer (s,",");
		TableColumnModel tcm = t.getColumnModel();
		TableColumnModel ntcm = new DefaultTableColumnModel();

		// while(ntcm.getColumnCount()>0) ntcm.removeColumn(ntcm.getColumn(0));

		// while(st.hasMoreElements()){
		if (tt != null) {
			for (HashMap hm : tt) {
				// String a = ;//(String) st.nextElement();
				for (TableColumn c : Collections.list(tcm.getColumns())) {
					if (c.getIdentifier().equals((String) hm.get("name"))) {
						c.setHeaderValue((String) hm.get("title"));
						if(hm.get("width")!=null)
							c.setMaxWidth((int) hm.get("width"));
						c.setIdentifier((String) hm.get("name"));
						if (ntcm.getColumnCount() == 0) {
							c.setCellRenderer(tcm.getColumn(0).getCellRenderer());
							c.setCellEditor(tcm.getColumn(0).getCellEditor());
						}
						ntcm.addColumn(c);

						break;
					}
				}
			}
		} else {
			TableColumn c = null, nc = null;
			for (Field e : ((BeanTableModel) t.getModel()).getBeanClass().getDeclaredFields())
			// (PropertyDescriptor e:descriptors)
			{
				if (e.getType() == Set.class)
					continue;
				for (int i = 0; i < tcm.getColumnCount(); i++) {
					c = tcm.getColumn(i);
					if (c.getIdentifier().equals((String) e.getName())) {
						nc = c;// .clone();//new TableColumnExt();
						nc.setModelIndex(i);
						// j++;
//						if (e.getDeclaredAnnotation(Synonym.class) != null)
//							nc.setHeaderValue((String) e.getDeclaredAnnotation(Synonym.class).text());
						nc.setHeaderValue(ut.getLabel(e));
						// nc.setMaxWidth((int) hm.get("width"));
						// nc.setIdentifier((String) hm.get("name"));
						// nc.setPrototypeValue(c.getPrototypeValue());
						// nc.setVisible((boolean)
						// (hm.get("visible")==null?true:hm.get("visible")));
						// nc.setcli(c.getPrototypeValue());
						// if(ntcm.getColumnCount()==0) {
						// c.setCellRenderer(tcm.getColumn(0).getCellRenderer());
						// c.setCellEditor(tcm.getColumn(0).getCellEditor());
						// c.setIdentifier(tcm.getColumn(i).getIdentifier());
						// c.setHeaderValue(tcm.getColumn(i).getHeaderValue());
						// c.
						// }
						ntcm.addColumn(nc);

						break;
					}

				}

			}

		}
		t.setColumnModel(ntcm);
		t.updateUI();
	}

	public static HashMap<String, Object> newHashMap(ArrayList<Object> a) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		StringTokenizer st = new StringTokenizer((String) a.get(0), ",");
		int i = 1;
		while (st.hasMoreElements()) {
			hm.put((String) st.nextElement(), a.get(i));
			i++;
		}
		return hm;
	}

	public static void TuneToolbar(JToolBar toolBar, JTable treeTable) {
		JButton Button_Select = new JButton("Выбрать");
		toolBar.removeAll();
		Button_Select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame form = ((JFrame) SwingUtilities.windowForComponent((Component) arg0.getSource()));
				JTable treeTable = null;;
				if (form.getClass()==FormList.class) treeTable =((FormList)form).getList();
					else treeTable = ut.getJTableInSameGroup((JButton) arg0.getSource());
				ArrayList selected = new ArrayList();
				for (int i : treeTable.getSelectedRows()) {
					if (treeTable.getClass() == JXTreeTable.class) {
						Object o = ((JXTreeTable) treeTable).getPathForRow(i).getLastPathComponent();
						selected.add(((BeanTreeTableModel) ((JXTreeTable) treeTable).getTreeTableModel())
								.getEntity((ArrayNode) o
						// treeTable.convertRowIndexToModel(i)
						));
					} else {
						if (treeTable.getClass() == JTable.class || treeTable.getClass() == JXTable.class) {
							// Object o = ((JTable)
							// treeTable).getPathForRow(i).getLastPathComponent();
							selected.add(((BeanTableModel) ((JTable) treeTable).getModel())
									.getEntity(treeTable.convertRowIndexToModel(i)));
						}
						;
					}
				}
				if (form.getClass()==FormList.class)((FormList)form).setSelected(selected);
				WindowEvent closingEvent = new WindowEvent(form, WindowEvent.WINDOW_CLOSING);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
				form.dispose();
			}
		});
		Button_Select.setFont(new Font("Tahoma", Font.BOLD, 11));
		toolBar.add(Button_Select);
		if(SwingUtilities.windowForComponent((Component)toolBar).getClass()!=FormList.class)
			Button_Select.setVisible(false);

		JButton Button_Add = new JButton("Добавить");
		Button_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame form = ((JFrame) SwingUtilities.windowForComponent((Component) arg0.getSource()));
				JTable treeTable = null;
				if (form.getClass()==FormList.class) treeTable =((FormList)form).getList();
					else treeTable = ut.getJTableInSameGroup((JButton) arg0.getSource());
				try {
					JFrame n;
					Class cl = null;
					if (treeTable.getClass() == JXTable.class || treeTable.getClass() == JTable.class) {
						BeanTableModel tm = (BeanTableModel) treeTable.getModel();
						cl = tm.getBeanClass();
					} else if (treeTable.getClass() == JXTreeTable.class) {
						BeanTreeTableModel tm = (BeanTreeTableModel) ((JXTreeTable)treeTable).getTreeTableModel();
						cl = tm.getBeanClass();
					}
					;
//					n = (JFrame) Class
//							.forName("sk.ui." + (String) ((Forms) cl.getDeclaredAnnotation(Forms.class)).element())
//							.getConstructor(null).newInstance(null);
					
					String formName = (String) ((Forms) cl.getDeclaredAnnotation(Forms.class)).element();
					if (formName ==null) formName = ""; 
					if (formName.equals("")) formName = "FormElement";
					n = (JFrame) Class.forName("sk.ui." + formName).getConstructor(null).newInstance(null);
					
					
					Field f = n.getClass().getField("formObject");
					f.set(n, cl.newInstance());
					n.setVisible(true);
					n.toFront();

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		toolBar.add(Button_Add);

		JButton Button_Edit = new JButton("Редактировать");
		Button_Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame form = ((JFrame) SwingUtilities.windowForComponent((Component) arg0.getSource()));
				JTable treeTable = null;;
				if (form.getClass()==FormList.class) treeTable =((FormList)form).getList();
					else treeTable = ut.getJTableInSameGroup((JButton) arg0.getSource());
				try {
					JFrame n;
					Class cl = null;
					if (treeTable.getClass() == JXTable.class || treeTable.getClass() == JTable.class) {
						BeanTableModel tm = (BeanTableModel) treeTable.getModel();
						cl = tm.getBeanClass();
					} else if (treeTable.getClass() == JXTreeTable.class) {
						BeanTreeTableModel tm = (BeanTreeTableModel) ((JXTreeTable)treeTable).getTreeTableModel();
						cl = tm.getBeanClass();
					};
					String formName = (String) ((Forms) cl.getDeclaredAnnotation(Forms.class)).element();
					if (formName ==null) formName = ""; 
					if (formName.equals("")) formName = "FormElement";
					n = (JFrame) Class.forName("sk.ui." + formName).getConstructor(null).newInstance(null);
					
					ArrayList selected = new ArrayList();
					for (int i : treeTable.getSelectedRows()) {
						if (treeTable.getClass() == JXTreeTable.class) {
							Object o = ((JXTreeTable) treeTable).getPathForRow(i).getLastPathComponent();
							selected.add(((BeanTreeTableModel) ((JXTreeTable) treeTable).getTreeTableModel())
									.getEntity((ArrayNode) o
							// treeTable.convertRowIndexToModel(i)
							));
						} else {
							if (treeTable.getClass() == JTable.class || treeTable.getClass() == JXTable.class) {
								// Object o = ((JTable)
								// treeTable).getPathForRow(i).getLastPathComponent();
								selected.add(((BeanTableModel) ((JTable) treeTable).getModel())
										.getEntity(treeTable.convertRowIndexToModel(i)));
							}
							;
						}
					}
					
					if (selected.size()>0) {
					Field f = n.getClass().getField("formObject");
					f.set(n, selected.get(0));
					n.setVisible(true);
					n.toFront();
					};
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException | NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});				
		toolBar.add(Button_Edit);

		JButton Button_Delete = new JButton("Удалить");
		Button_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame form = ((JFrame) SwingUtilities.windowForComponent((Component) arg0.getSource()));
				JTable treeTable = null;;
				if (form.getClass()==FormList.class) treeTable =((FormList)form).getList();
					else treeTable = ut.getJTableInSameGroup((JButton) arg0.getSource());
				
				ArrayList selected = new ArrayList();
				for (int i : treeTable.getSelectedRows()) {
					if (treeTable.getClass() == JXTreeTable.class) {
						Object o = ((JXTreeTable) treeTable).getPathForRow(i).getLastPathComponent();
						selected.add(((BeanTreeTableModel) ((JXTreeTable) treeTable).getTreeTableModel())
								.getEntity((ArrayNode) o
						// treeTable.convertRowIndexToModel(i)
						));
					} else {
						if (treeTable.getClass() == JTable.class || treeTable.getClass() == JXTable.class) {
							// Object o = ((JTable)
							// treeTable).getPathForRow(i).getLastPathComponent();
							selected.add(((BeanTableModel) ((JTable) treeTable).getModel())
									.getEntity(treeTable.convertRowIndexToModel(i)));
						}
						;
					}
				}
				for (Object e:selected){
					Main.delete(e);					
				}
				//form.setSelected(selected);
//				WindowEvent closingEvent = new WindowEvent(form, WindowEvent.WINDOW_CLOSING);
//				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
				//form.dispose();
			}
		});
		
		toolBar.add(Button_Delete);

		JButton Button_Update = new JButton("Обновить");
		Button_Update .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame form = ((JFrame) SwingUtilities.windowForComponent((Component) arg0.getSource()));
				JTable treeTable = null;;
				if (form.getClass()==FormList.class) treeTable =((FormList)form).getList();
					else treeTable = ut.getJTableInSameGroup((JButton) arg0.getSource());
				if (treeTable.getClass()==JXTreeTable.class){
					((BeanTreeTableModel)((JXTreeTable)treeTable).getTreeTableModel()).reload_data();
				}else if (treeTable.getClass()==JTable.class||treeTable.getClass()==JXTable.class){
					((BeanTableModel)((JTable)treeTable).getModel()).reload_data();
				}
			}
		});
		
		toolBar.add(Button_Update);

	}

	public static JTable getJTableInSameGroup(JButton b) {
		JComponent p = (JComponent) SwingUtilities.getUnwrappedParent(SwingUtilities.getUnwrappedParent((Component) b));
		//JTable treeTable = null;
		for (int i = 0; i < SwingUtilities.getAccessibleChildrenCount(p); i++) {
			if (SwingUtilities.getAccessibleChild(p, i).getClass() == JScrollPane.class) {
				JScrollPane sp = (JScrollPane) SwingUtilities.getAccessibleChild(p, i);
				for (int j = 0; j < SwingUtilities.getAccessibleChildrenCount(sp); j++) {
					if (SwingUtilities.getAccessibleChild(sp, j).getClass() == JViewport.class) {
						try {
						Object Cmp = ((JViewport) SwingUtilities.getAccessibleChild(sp, j)).getComponent(0);
						
						if (Cmp.getClass() == JXTreeTable.class || Cmp.getClass() == JXTable.class
								|| Cmp.getClass() == JTable.class) {
							return (JTable) Cmp;

						};
						} catch (Exception e) {} ;
						
					}
					;
				}
				;
			}
		}
		return null;
	}
	public static java.util.Date toDate(Object a){
		if(a==null) return null;
		if (a.getClass()==java.util.Date.class) return (java.util.Date)a;
    	if (a.getClass()==Timestamp.class) return Date.from(((Timestamp) a).toInstant());
    	if (a.getClass()==java.sql.Date.class) return new java.util.Date(((java.sql.Date)a).getTime());
    	if (a.getClass()==LocalDateTime.class) return Date.from(((LocalDateTime)a).atZone(ZoneId.systemDefault()).toInstant());  
    	if (a.getClass()==LocalDate.class) return Date.from(((LocalDate)a).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		return  null;
	}
	public static LocalDateTime toLdt(Date a){
		return a.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	public static Object toObject( Class clazz, String value ) {
		if( String.class == clazz ) return  value;
	    if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz ) return Byte.parseByte( value );
	    if( Short.class == clazz ) return Short.parseShort( value );
	    if( Integer.class == clazz || int.class == clazz ) return Integer.parseInt( value );
	    if( Long.class == clazz ) return Long.parseLong( value );
	    if( Float.class == clazz ) return Float.parseFloat( value );
	    if( Double.class == clazz ) return Double.parseDouble( value );
	    return value;
	}
	public static void linkFormObjectElements(Object form, String d,JTextFieldPlus t1,JButton t2){
		try {			
			Object formObject = form.getClass().getDeclaredField("formObject").get(form);
			Field f = formObject.getClass().getDeclaredField(d);
			HashMap  EditButtons = (HashMap)form.getClass().getField("EditButtons").get(form);
			HashMap  EditFields = (HashMap)form.getClass().getField("EditFields").get(form);
	
			EditButtons.put(t2, t1);
			EditFields.put(d, t1);
						 
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}			
	}
	public static String getLabel(Field d){
		if (!Main.props.containsKey("languge")) 
			return d.getAnnotation(Synonym.class).text();
		else {
			
				try {
					return (String)d.getAnnotation(Synonym.class).getClass().getMethod("text"+Main.props.getProperty("languge")).invoke(d.getAnnotation(Synonym.class));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return d.getName();
				}					
		}
	}
	public static String getLabel(Class d){
		if (!Main.props.containsKey("languge")) 
			return ((Synonym) d.getDeclaredAnnotation(Synonym.class)).text();
		else {
			
				try {
					return (String)d.getMethod("text"+Main.props.getProperty("languge")).invoke(((Synonym) d.getDeclaredAnnotation(Synonym.class)));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					return d.getName();//e.printStackTrace();					
				}			
		}
	}

}

