package br.com.garage.auth.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String message) {
		super(message);
	}

}
