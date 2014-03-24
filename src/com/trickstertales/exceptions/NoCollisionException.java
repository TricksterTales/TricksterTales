package com.trickstertales.exceptions;

public class NoCollisionException extends Exception {
	private static final long serialVersionUID = 1L;
	String msg;
    
    public NoCollisionException() {
        super();
        msg = "No Collision";
    }
    
    public NoCollisionException(String err) {
        super(err);
        msg = err;
    }
    
    public String getMessage() {
        return msg;
    }
}