package sk.ui;

import javax.swing.JTextField;

public class JTextFieldPlus extends JTextField{
	private Object valueObject;
	private boolean simpleType = true;

	public Object getValueObject() {
		return valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
		this.setText(valueObject==null?"":valueObject.toString());
		this.simpleType = false;
	}

	public boolean isSimpleType() {
		return simpleType;
	}

	public void setSimpleType(boolean simpleType) {
		this.simpleType = simpleType;
	} 
}
