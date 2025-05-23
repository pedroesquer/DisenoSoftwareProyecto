package itson.persistenciarutapp.implementaciones;

import java.util.Date;
import org.bson.types.ObjectId;

/**
 * Representa una reseña realizada por un usuario hacia un camión específico.
 * Contiene información sobre el usuario, el camión, la fecha de la reseña,
 * una calificación numérica y un comentario textual.
 */
public class Reseña {

    /** Identificador único de la reseña. */
    private ObjectId id;

    /** Identificador del usuario que realizó la reseña. */
    private ObjectId usuario;

    /** Identificador del camión reseñado. */
    private ObjectId camion;

    /** Fecha en que se realizó la reseña. */
    private Date fecha;

    /** Calificación asignada al camión por el usuario (por ejemplo, entre 1 y 5). */
    private double calificacion;

    /** Comentario descriptivo del usuario sobre el camión. */
    private String comentario;

    /**
     * Constructor vacío necesario para frameworks de persistencia y serialización.
     */
    public Reseña() {
    }

    /**
     * Constructor que inicializa todos los campos, incluyendo el ID de la reseña.
     *
     * @param id identificador de la reseña.
     * @param usuario ID del usuario que hizo la reseña.
     * @param camion ID del camión reseñado.
     * @param fecha fecha en que se realizó la reseña.
     * @param calificacion calificación dada al camión.
     * @param comentario comentario del usuario.
     */
    public Reseña(ObjectId id, ObjectId usuario, ObjectId camion, Date fecha, double calificacion, String comentario) {
        this.id = id;
        this.usuario = usuario;
        this.camion = camion;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    /**
     * Constructor sin ID, utilizado normalmente antes de persistir la reseña.
     *
     * @param usuario ID del usuario.
     * @param camion ID del camión.
     * @param fecha fecha de la reseña.
     * @param calificacion calificación asignada.
     * @param comentario comentario textual.
     */
    public Reseña(ObjectId usuario, ObjectId camion, Date fecha, double calificacion, String comentario) {
        this.usuario = usuario;
        this.camion = camion;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUsuario() {
        return usuario;
    }

    public void setUsuario(ObjectId usuario) {
        this.usuario = usuario;
    }

    public ObjectId getCamion() {
        return camion;
    }

    public void setCamion(ObjectId camion) {
        this.camion = camion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Devuelve una representación en cadena del objeto Reseña.
     *
     * @return representación textual con todos los campos de la reseña.
     */
    @Override
    public String toString() {
        return "Reseña{" +
               "id=" + id +
               ", usuario=" + usuario +
               ", camion=" + camion +
               ", fecha=" + fecha +
               ", calificacion=" + calificacion +
               ", comentario=" + comentario +
               '}';
    }
}
