package utilerias;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
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
     * Maneja la inserción de nuevos caracteres en el documento.
     * Inserta '/' automáticamente después de los dos primeros dígitos
     * y valida que la fecha tenga un formato lógico y no esté vencida.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;

        // Construye el texto resultante al insertar
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);
        String resultado = formatear(sb.toString());

        // Reemplaza solo si el resultado es válido
        if (resultado != null) {
            fb.replace(0, fb.getDocument().getLength(), resultado, attr);
        }
    }

    /**
     * Maneja la sustitución de texto existente en el documento.
     * Aplica validaciones de formato y valores lógicos.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) return;

        // Simula el texto con el reemplazo
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);
        String resultado = formatear(sb.toString());

        // Aplica el reemplazo solo si es válido
        if (resultado != null) {
            fb.replace(0, fb.getDocument().getLength(), resultado, attrs);
        }
    }

    /**
     * Maneja la eliminación de texto del documento.
     * Revalida y reformatea el texto restante después de eliminar.
     */
    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        // Simula el texto tras eliminar
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.delete(offset, offset + length);
        String resultado = formatear(sb.toString());

        // Aplica el nuevo texto formateado o limpia si es inválido
        fb.replace(0, fb.getDocument().getLength(), resultado != null ? resultado : "", null);
    }

    /**
     * Aplica formato MM/YY a una cadena numérica.
     * También valida que el mes sea válido (1-12) y que la fecha no esté vencida.
     *
     * @param texto Entrada numérica del usuario
     * @return Cadena formateada o null si es inválida
     */
    private String formatear(String texto) {
        // Elimina caracteres no numéricos
        String limpio = texto.replaceAll("[^\\d]", "");

        // Limita a 4 dígitos numéricos (MMYY)
        if (limpio.length() > 4) {
            return null;
        }

        // Inserta el slash automáticamente
        String formateado;
        if (limpio.length() > 2) {
            formateado = limpio.substring(0, 2) + "/" + limpio.substring(2);
        } else {
            formateado = limpio;
        }

        // Validar mes y año si ya están completos
        if (limpio.length() == 4) {
            try {
                int mes = Integer.parseInt(limpio.substring(0, 2));
                int anio = Integer.parseInt("20" + limpio.substring(2));

                // Validar que el mes esté en el rango correcto
                if (mes < 1 || mes > 12) {
                    return null;
                }

                // Verificar que la fecha no sea pasada
                YearMonth fechaIngresada = YearMonth.of(anio, mes);
                YearMonth actual = YearMonth.now();
                if (fechaIngresada.isBefore(actual)) {
                    return null;
                }

            } catch (NumberFormatException | DateTimeParseException e) {
                return null;
            }
        }

        return formateado;
    }
}