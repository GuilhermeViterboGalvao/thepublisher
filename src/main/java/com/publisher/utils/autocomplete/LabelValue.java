package com.publisher.utils.autocomplete;

public class LabelValue {

	public LabelValue(String label, String value) {
		this(label, value, null);
	}	
	public LabelValue(String label, String value, Object extra) {
		this.label = label;
		this.value = value;
		this.extra = extra;
	}
	
	private String value;
	
	private String label;
	
	private Object extra;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Object getExtra() {
		return extra;
	}
	
	public void setExtra(Object extra) {
		this.extra = extra;
	}
}