package utilerias;

import javax.swing.text.*;

/**
 * Filtro de documento personalizado que restringe un JTextField a aceptar solo
 * entradas numéricas en formato de fecha dd/MM.
 *
 * Inserta automáticamente una barra '/' después de dos dígitos, y no permite
 * más de 4 dígitos numéricos en total.
 *
 * Ejemplo: Entrada: 0105 → Visualización automática: 01/05
 *
 * Este filtro es útil para campos de entrada de fechas simples.
 */
public class FechaDocumentFilter extends DocumentFilter {

    /**
     * Maneja la inserción de nuevos caracteres al documento. Inserta
     * automáticamente '/' si se han escrito más de dos dígitos.
     *
     * @param fb El bypass del filtro
     * @param offset Posición de inserción
     * @param string Texto a insertar
     * @param attr Atributos del texto
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) {
            return;
        }

        // Construye el texto completo como si ya se hubiera insertado
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);

        String resultado = formatear(sb.toString());

        // Reemplaza el contenido solo si es válido
        if (resultado != null) {
            fb.replace(0, fb.getDocument().getLength(), resultado, attr);
        }
    }

    /**
     * Maneja el reemplazo de texto existente en el documento. Se asegura de
     * mantener el formato dd/MM.
     *
     * @param fb El bypass del filtro
     * @param offset Posición inicial del reemplazo
     * @param length Longitud del texto a reemplazar
     * @param text Texto nuevo a insertar
     * @param attrs Atributos del texto
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) {
            return;
        }

        // Simula el texto resultante si se realiza el reemplazo
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);

        String resultado = formatear(sb.toString());

        if (resultado != null) {
            fb.replace(0, fb.getDocument().getLength(), resultado, attrs);
        }
    }

    /**
     * Maneja la eliminación de texto del documento. Aplica el formato restante
     * tras eliminar caracteres.
     *
     * @param fb El bypass del filtro
     * @param offset Posición de inicio de eliminación
     * @param length Longitud del texto a eliminar
     */
    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        // Simula el texto resultante si se elimina parte del contenido
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);

        String resultado = formatear(sb.toString());

        fb.replace(0, fb.getDocument().getLength(), resultado != null ? resultado : "", null);
    }

    /**
     * Aplica formato dd/MM al texto dado, insertando una barra automáticamente
     * después de los dos primeros dígitos.
     *
     * @param texto Texto a formatear
     * @return Texto con formato dd/MM o null si es inválido
     */
    private String formatear(String texto) {
        // Elimina todo carácter que no sea dígito
        String limpio = texto.replaceAll("[^\\d]", "");

        // Si hay más de 4 dígitos, no se permite
        if (limpio.length() > 4) {
            return null;
        }

        // Inserta la barra si hay al menos 3 dígitos
        if (limpio.length() > 2) {
            return limpio.substring(0, 2) + "/" + limpio.substring(2);
        } else {
            return limpio;
        }
    }
}
