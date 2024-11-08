package com.controleprojetos.exception;

public class ProjetoNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjetoNotFoundException(String message) {
        super(message);
    }
}
