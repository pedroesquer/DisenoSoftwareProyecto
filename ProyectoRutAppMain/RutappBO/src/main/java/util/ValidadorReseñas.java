/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import itson.rutappdto.ReseñaDTO;

/**
 *
 * @author multaslokas33
 */
public class ValidadorReseñas {

    public static void validar(ReseñaDTO reseña) throws Exception {
        String comentario = reseña.getComentario().trim();

        if (reseña.getCalificacion() <= 0 || reseña.getCalificacion() > 5) {
            throw new Exception("Por favor selecciona una calificación válida.");
        }

        if (comentario.isEmpty()) {
            throw new Exception("Por favor escribe un comentario.");
        }

        if (comentario.length() < 2) {
            throw new Exception("El comentario es demasiado corto. Mínimo 2 caracteres.");
        }

        if (comentario.length() > 150) {
            throw new Exception("El comentario es demasiado largo. Máximo 150 caracteres.");
        }

        String[] patronesProhibidos = {
            "p[e3]nd[e3]j[o0]", "c[uú]l[e3]r[o0]", "m[i1]e[r]d[a@]", "est[uú]p[i1]d[o0@]",
            "i[d]i[o0@]t[a@]", "imb[e3]c[i1]l", "h[i1]j[o0] ?d[e3] ?p[uú]t[a@]", "v[e3]rg[a@]",
            "ch[i1]ng[a@]d[a@]?", "ch[i1]ng[a@]r", "m[a@]m[o0]n", "p[uú]t[a@o0]", "j[o0]t[o0]",
            "z[o0]rr[a@]", "p[e3]rr[a@]", "n[a@]c[o0]", "c[o0]ñ[o0]", "b[a@]st[a@]rd[o0]",
            "g[i1]l[i1]p[o0]ll[a@]", "p[i1]ch[e3]", "cab[r]+[o0]n", "t[a@]r[a@]d[o0]",
            "r[e3]tr[a@]s[a@]d[o0]", "m[a@]rd[i1]t[o0]", "c[a4]br[a@]", "c[a4]br[o0]n[a@]",
            "s[a@]c[o0]n[a@]", "v[i1]dri[o0]l[a@]", "t[o0]nt[o0]l[o0]n[a@]", "c[o0]l[e3]r[a@]",
            "l[e3]pr[o0]s[o0]", "t[e3]rr[a@]t[e3]", "b[o0]b[o0]", "idi[o0]t[a@]", "b[uú]rr[o0]",
            "c[a@]g[a@][r]+", "c[a@]g[a@]d[o0]", "c[a@]br[o0]n[e3]s", "c[o0]j[o0]n[e3]s?",
            "m[a@]l[nn][a@]c[hhe3]t[o0]s?", "m[a@]l[pb]ar[i1]d[o0]" , "PUTO pepe pollo" 
        };

        for (String patron : patronesProhibidos) {
            if (comentario.toLowerCase().matches(".*" + patron + ".*")) {
                throw new Exception("El comentario contiene lenguaje inapropiado.");
            }
        }
    }
}
