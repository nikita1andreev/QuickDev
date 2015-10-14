package sk.ui;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

public class ArrayNode extends DefaultMutableTreeTableNode {
	
	
    //private Object[] userObject;//this.getColumnCount()data;

	public ArrayNode(Object[] data) {
        super(data);       
        setUserObject(data);
    }

    @Override
    public Object getValueAt(int column) {
    	if (getUserObject()==null) return null;
        return getUserObject()[column];
    }

    @Override
    public void setValueAt(Object aValue, int column) {
        getUserObject()[column] = aValue;
    }

    @Override
    public int getColumnCount() {
        //return super.getColumnCount();//((Object[])this.getColumnCount();//4;
    	if (getUserObject()==null) return 0;
        return getUserObject().length;
    }

    @Override
    public Object[] getUserObject() {
        return (Object[]) super.getUserObject();
    }

    @Override
    public boolean isEditable(int column) {
        return true;
    }

}