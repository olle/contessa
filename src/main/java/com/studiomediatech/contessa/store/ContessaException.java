/*
 * J.Arraszu
 */
package com.studiomediatech.contessa.store;

/**
 * Wrapper for the concrete Exceptions of the underlying Storeimpls.
 * 
 * @author Joachim Arrasz <arrasz@synyx.de>
 */
public class ContessaException extends Exception {
    
    private Exception cause;
    
    /**
     * Needs a cause exception. 
     * 
     * @param ex the originator
     */
    public ContessaException(Exception ex) {
        this.cause = ex;
    }

    /**
     * @return the cause
     */
    public Exception getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(Exception cause) {
        this.cause = cause;
    }
    
}
