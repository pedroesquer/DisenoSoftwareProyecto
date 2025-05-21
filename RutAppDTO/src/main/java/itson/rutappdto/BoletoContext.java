
package itson.rutappdto;

/**
 *
 * @author BusSoft®
 */
    public class BoletoContext {
    
    private static BoletoDTO boletoActual;
    
    /** 
     * Constructor privado para que no se pueda instanciar desde fueras
     */
    private BoletoContext() {
    }
    
    
    /**
     * Método que obtiene la instancia actual de un boletoDTO, y si no existe la crea.
     * @return 
     */
    public static BoletoDTO getBoleto(){
        if (boletoActual == null) {
            boletoActual = new BoletoDTO();
        }
        return boletoActual;
    }
    
    /**
     * Método que reincia la instancia del BoletoDTO asignando una nueva instancia de BoletoDTO a boletoActual para reutilizarlo desde 0.
     */
    public static void limpiarBoleto() {
        boletoActual = new BoletoDTO();
    }
}
