package excepciones;

/**
 *
 * @author pedro
 */
public class PagoBoletoException extends Exception {

    public PagoBoletoException(String message) {
        super(message);
    }

    public PagoBoletoException(String message, Throwable cause) {
        super(message, cause);
    }

}
