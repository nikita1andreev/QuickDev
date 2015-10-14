/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;

import java.awt.Component;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;

import org.hibernate.SQLQuery;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.mozilla.javascript.tools.debugger.downloaded.TreeTableModel;
import org.mozilla.javascript.tools.debugger.downloaded.TreeTableModelAdapter;

import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;

import a1.annotations.Synonym;

public class BeanTreeTableModel extends DefaultTreeTableModel// AbstractTableModel
{
	DefaultMutableTreeTableNode root;// = new DefaultMutableTreeTableNode();;
	protected List list;
	private BeanInfo beanInfo = null;
	private PropertyDescriptor[] descriptors = null;
	private int idDesriptor;
	private int HierarchicalColumn;
	private Class beanClass;
	private HashMap ListNodes;
	private String qtext;
	private List arg;

	public Class getBeanClass() {
		return this.beanClass;
	}

	public BeanTreeTableModel(List list, Class beanClass, ArrayNode root) {
		super(root);
		this.root = root;
		HierarchicalColumn = 0;
		this.beanClass = beanClass;
		this.list = list;
		introspect(beanClass);
		root.setUserObject(new Object[descriptors.length]);
		ListNodes = new HashMap<>();
		addChildren(null, root);
	}

	public BeanTreeTableModel(String qtext, List arg, Class beanClass, ArrayNode root) {
		super(root);
		HierarchicalColumn = 0;
		this.beanClass = beanClass;
		introspect(beanClass);

		this.root = root;
		root.setUserObject(new Object[descriptors.length]);
		this.qtext = qtext;
		this.arg = arg;

		this.list = Main.SQLQuery(this.qtext, this.arg, this.beanClass);// list;
		ListNodes = new HashMap<>();
		addChildren(null, (ArrayNode) this.root);
	}

	public void reload_data() {
		if (this.qtext != null) {
			this.list = Main.SQLQuery(this.qtext, this.arg, this.beanClass);// list;
			ListNodes = new HashMap<>();
			
			while(getChildCount(this.root)>0)
					removeNodeFromParent((MutableTreeTableNode) getChild(this.root, 0));
			addChildren(null, (ArrayNode) this.root);
		};
	}

	private void introspect(Class beanClass) {
		try {
			this.beanInfo = Introspector.getBeanInfo(beanClass, Introspector.USE_ALL_BEANINFO);
			descriptors = beanInfo.getPropertyDescriptors();
		} catch (IntrospectionException ie) {
			// ignore
		}

		List v = new ArrayList(descriptors.length);
		for (int i = 0; i < descriptors.length; i++) {
			if (descriptors[i].getName().equals("id"))
				idDesriptor = i;
			if (!descriptors[i].getName().equals("class"))
				v.add(descriptors[i]);
		}
		descriptors = (PropertyDescriptor[]) v.toArray(new PropertyDescriptor[v.size()]);
	}

	public PropertyDescriptor getDescriptor(String name) {
		for (int i = 0; i < descriptors.length; i++)
			if (descriptors[i].getName().equals(name))
				return descriptors[i];
		return null;
	}

	boolean isSingle() {
		return false;// list.size()<=1;
	}

	public int getRowCount() {
		return isSingle() ? descriptors.length : list.size();
	}

	public int getColumnCount() {
		return isSingle() ? list.size() + 1 : (descriptors != null ? descriptors.length : 0);
	}

	public Object getValueAt(int row, int col) {
		throw new IllegalArgumentException();
	}

	public Object getValueAt(ArrayNode node// int row
	, int col) {
		// if (isSingle()) {
		// if (col == 0) {
		// return descriptors[row].getDisplayName();
		// } else {
		// return getValue(0, row);
		// }
		// } else {
		return getValue(node// row
				, col);
		// }
	}

	public Object getValueAt(ArrayNode node// int row
	, String col) {
		// if(isSingle()) {
		// if(col==0) {
		// return descriptors[row].getDisplayName();
		// } else {
		// return getValue(0, row);
		// }
		// } else {
		int i = 0;
		for (PropertyDescriptor e : descriptors) {
			if (e.getName().equals(col))
				return getValue(node// row
						, i);
			i++;
		}
		;
		return null;
		// }
	}

	public Object getColumnValue(Object bean, int col) {
		// Object bean = list.get( row );
		Object result = null;
		try {
			result = descriptors[col].getReadMethod().invoke(bean, null);
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return result;
	}

	public Object getEntityId(ArrayNode node// int row
	) {
		try {
			return descriptors[idDesriptor].getReadMethod().invoke(ListNodes.get(node)// list.get(row)
			, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Object getEntityId(int row) {
		throw new IllegalArgumentException();
	}

	public Object getEntity(ArrayNode o) {
		return ListNodes.get(o);// list.get(row);
	}

	public Object getValue(int row, int col) {
		throw new IllegalArgumentException();
	}

	public Object getValue(ArrayNode node// int row
	, int col) {
		Object bean = ListNodes.get(node);// list.get(row);
		Object result = null;
		try {
			result = descriptors[col].getReadMethod().invoke(bean, null);
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return result;
	}

	public String getColumnName(int col) {
		if (isSingle()) {
			if (col == 0) {
				return "Name";
			} else {
				return "Value";
			}
		} else {
			return descriptors[col].getDisplayName();
		}
	}

	public Class getColumnClass(int c) {
		if (isSingle()) {
			return String.class;
		} else {
			Class propertyType = descriptors[c].getPropertyType();

			if (propertyType.isPrimitive()) {
				return String.class; // to avoid jtable complain about null
										// table renderer.
			} else {
				return propertyType;
			}
		}
	}

	public void addChildren(Object parent, ArrayNode into) {
		try {
			// Object[] a = (Object[]) ((ArrayList<?>)
			// list).stream().filter(p->(getDescriptor("parent").getReadMethod().invoke(p,
			// null).equals(parent))).toArray();			
			for (Object e : list) {
				if (getDescriptor("parent") != null)
					if (getDescriptor("parent").getReadMethod().invoke(e) != parent)
						continue;
				Object n[] = new Object[descriptors.length];
				for (int i = 0; i < descriptors.length; i++)
					n[i] = getColumnValue(e, i);
				ArrayNode node = new ArrayNode(n);
				ListNodes.put(node, e);
				this.insertNodeInto(node, into, 0);
				if (getDescriptor("parent") != null)
					addChildren((Object) e, node);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		// int rootChildCount = root.getChildCount();
		// ArrayNode newChild = new ArrayNode(new Object[] {"dfds",
		// "sfddsf","dfsfsd","fdsfsd"});
		// this.insertNodeInto(newChild, root, rootChildCount);
		// ArrayNode newChild1 = new ArrayNode(new Object[] {"dfds",
		// "sfddsf","dfsfsd","fdsfsd"});
		// this.insertNodeInto(newChild1, newChild, 0);

	}

	@Override
	public int getHierarchicalColumn() {
		// TODO Auto-generated method stub
		// int i=0;
		// for (PropertyDescriptor e:descriptors){
		// if (e.getName().equals("id")) return
		// i;//super.getHierarchicalColumn();
		// i++;}
		return this.HierarchicalColumn;// 0;
	}

	public void setHierarchicalColumn(String name) {
		// TODO Auto-generated method stub
		int i = 0;
		for (PropertyDescriptor e : descriptors) {
			if (e.getName().equals(name))
				HierarchicalColumn = i;// super.getHierarchicalColumn();
			i++;
		}
		// this.updateHierarchicalRendererEditor();

		// return 0;
	}

	public void setHierarchicalColumn(int i) {
		// TODO Auto-generated method stub
		this.HierarchicalColumn = i;// super.getHierarchicalColumn();
		// return 0;
	}

}