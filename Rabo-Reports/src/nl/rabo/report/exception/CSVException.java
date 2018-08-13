package nl.rabo.report.exception;

import java.io.IOException;

public class CSVException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public CSVException() {
		super();
	}
	
	public CSVException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public CSVException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
