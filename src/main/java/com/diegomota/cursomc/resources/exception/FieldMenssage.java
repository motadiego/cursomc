package com.diegomota.cursomc.resources.exception;

import java.io.Serializable;

public class FieldMenssage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String menssage;
	
	public FieldMenssage() {
		
	}

	public FieldMenssage(String fieldName, String menssage) {
		super();
		this.fieldName = fieldName;
		this.setMenssage(menssage);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMenssage() {
		return menssage;
	}

	public void setMenssage(String menssage) {
		this.menssage = menssage;
	}
}
