/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.exception;

/**
 *
 * @author Djordje Gligorijevic
 */
public class NoFileSelectedException extends Exception {

    public NoFileSelectedException() {
    }

    public NoFileSelectedException(String msg) {
        super(msg);
    }
}
