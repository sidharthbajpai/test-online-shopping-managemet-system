package com.onlinemedicineshop.exception;

public class UnauthorizedAccessException extends RuntimeException {
	private static final long serialVersionUID = 4045658858790528752L;

	public UnauthorizedAccessException(String message) {
		super(message);
	}
}