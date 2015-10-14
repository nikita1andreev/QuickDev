/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.ui;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BeanTableModel extends AbstractTableModel {

	protected List list;

	private BeanInfo beanInfo = null;
	private Class beanClass;
	private String qtext;
	private List arg;
	

	private PropertyDescriptor[] descriptors = null;
	
	public Class getBeanClass()
	{
		return this.beanClass;
	}
	
	
	public BeanTableModel(List list, Class beanClass) {
		this.list = list;
		this.beanClass = beanClass;

		introspect( beanClass );
	}

	public BeanTableModel(String qtext, List arg, Class beanClass) {
		this.qtext = qtext;
		this.arg = arg;
		this.beanClass = beanClass;
		this.list = Main.SQLQuery(this.qtext, this.arg, this.beanClass);// list;
		this.beanClass = beanClass;
		introspect( beanClass );
	}
	
	public void reload_data() {
		if (this.qtext != null) {
			this.list = Main.SQLQuery(this.qtext, this.arg, this.beanClass);// list;
		};
	}
	
	
	
	private void introspect(Class beanClass) {
		try {
			this.beanInfo = Introspector.getBeanInfo( beanClass,
					Introspector.USE_ALL_BEANINFO );
			//descriptor = beanInfo.getBeanDescriptor();
			descriptors = beanInfo.getPropertyDescriptors();
		}
		catch (IntrospectionException ie) {
			// ignore
		}
		
		List v = new ArrayList(descriptors.length);
		for (int i = 0; i < descriptors.length; i++) {
			if(!descriptors[i].getName().equals("class")) {
				v.add( descriptors[i] );
			}			
		}
		descriptors = (PropertyDescriptor[]) v.toArray( new PropertyDescriptor[v.size()] );

	}

	boolean isSingle() {
		return false;//list.size()<=1;
	}
	
	public int getRowCount() {
		return isSingle() ? descriptors.length : list.size();
	}

	public int getColumnCount() {
		return isSingle() ? list.size() + 1 : (descriptors != null ? descriptors.length : 0);
	}

	public Object getValueAt(int row, int col) {
		if(isSingle()) {
			if(col==0) {
				return descriptors[row].getDisplayName();
			} else {
				return getValue(0, row);
			}
		} else {
			return getValue( row, col );
		}
	}

	private Object getValue(int row, int col) {
		Object bean = list.get( row );
		Object result = null;
		try {
			result = descriptors[col].getReadMethod().invoke( bean, null );
		}
		catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
		catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return result;
	}
	public Object getEntity(int row) {	
		return list.get(row);
}

	
	public String getColumnName(int col) {
		if(isSingle()) {
			if(col==0) {
				return "Name";
			} else {
				return "Value";
			}
		} else {
			return descriptors[col].getDisplayName();
		}
	}

	public Class getColumnClass(int c) {
		if(isSingle()) {
			return String.class;
		} else {
			Class propertyType = descriptors[c].getPropertyType();

			if(propertyType.isPrimitive()) {
				return String.class; // to avoid jtable complain about null table renderer.
			} else {
				return propertyType;
			}
		}
	}
}