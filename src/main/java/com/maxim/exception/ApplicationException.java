package com.maxim.exception;

import com.maxim.enums.ErrorTypes;

public class ApplicationException extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = -4404058310589677109L;
private ErrorTypes errorType;

public ApplicationException(ErrorTypes errorType,String message) {
	super(message);
	this.errorType = errorType;
}

public ApplicationException(ErrorTypes errorType,String message,Exception e) {
	super(message,e);
	this.errorType = errorType;
}

public ErrorTypes getErrorType() {
	return errorType;
}

public void setErrorType(ErrorTypes errorType) {
	this.errorType = errorType;
}


}
