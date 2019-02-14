package com.diegomota.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {


	private static final long serialVersionUID = 1L;
	
	private List<FieldMenssage> erros = new ArrayList<FieldMenssage>();
	
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}


	public List<FieldMenssage> getErros() {
		return erros;
	}


	public void addError(String fieldName,String mensagem) {
		erros.add(new FieldMenssage(fieldName, mensagem));
	}
	
	
	
}
