package com.maxim.beans;

public class ErrorBean {
	int errorNumber;
	String errorMessage;
	String errorName;

	public ErrorBean(int errorNumber, String errorMessage, String errorName) {
		super();
		this.errorNumber = errorNumber;
		this.errorMessage = errorMessage;
		this.errorName = errorName;
	}

	public ErrorBean() {
		super();
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

}
