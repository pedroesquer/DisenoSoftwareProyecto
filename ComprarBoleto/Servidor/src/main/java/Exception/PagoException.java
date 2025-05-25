/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 *
 * @author mmax2
 */
public class PagoException extends Exception{

    public PagoException(String message) {
        super(message);
    }

    public PagoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
