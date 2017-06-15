package com.lnt.demo;

public class Component {

	private  String componentType;
	private  Bounds setBounds;
	private  String foreground;
	private String fontStyle;
	private String fontSize;
	private String textFieldColumn;
	private String passwordFieldColumn;
	
	public Component() {}
	
	public Component(String componentType, Bounds setBounds, String foreground, String textFieldColumn,
			String fontStyle,String fontSize,String passwordFieldColumn) {
		super();
		this.componentType = componentType;
		this.setBounds = setBounds;
		this.foreground = foreground;
		this.textFieldColumn = textFieldColumn;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.passwordFieldColumn = passwordFieldColumn;
	}
	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public  Bounds getSetBounds() {
		return setBounds;
	}
	public  void setSetBounds(Bounds setBounds) {
		this.setBounds = setBounds;
	}
	public  String getForeground() {
		return foreground;
	}
	public  void setForeground(String foreground) {
		this.foreground = foreground;
	}
	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getTextFieldColumn() {
		return textFieldColumn;
	}

	public void setTextFieldColumn(String textFieldColumn) {
		this.textFieldColumn = textFieldColumn;
	}

	public String getPasswordFieldColumn() {
		return passwordFieldColumn;
	}

	public void setPasswordFieldColumn(String passwordFieldColumn) {
		this.passwordFieldColumn = passwordFieldColumn;
	}	
	
}
