package itson.persistenciarutapp.entidades;

import java.util.Date;
import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;

/**
 * Representa una reseña realizada por un usuario hacia un camión específico.
 * Contiene información sobre el usuario, el camión, la fecha de la reseña, una
 * calificación numérica y un comentario textual.
 */
public class Reseña {

    /**
     * Identificador único de la reseña (mapeado a _id de MongoDB).
     */
    @BsonId
    private ObjectId id;

    /**
     * Identificador del usuario que realizó la reseña.
     */
    private ObjectId usuario;

    /**
     * Identificador del camión reseñado.
     */
    private ObjectId camion;

    /**
     * Fecha en que se realizó la reseña.
     */
    private Date fecha;

    /**
     * Calificación asignada al camión por el usuario (por ejemplo, entre 1 y
     * 5).
     */
    private double calificacion;

    /**
     * Comentario descriptivo del usuario sobre el camión.
     */
    private String comentario;

    public Reseña() {
    }

    public Reseña(ObjectId id, ObjectId usuario, ObjectId camion, Date fecha, double calificacion, String comentario) {
        this.id = id;
        this.usuario = usuario;
        this.camion = camion;
        this.fecha = fecha;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

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

    @BsonIgnore
    public String obtenerIdComoString() {
        return id != null ? id.toHexString() : null;
    }

    @BsonIgnore
    public void asignarIdDesdeString(String idStr) {
        this.id = (idStr != null && !idStr.isBlank()) ? new ObjectId(idStr) : null;
    }

    @BsonIgnore
    public String obtenerIdUsuarioComoString() {
        return usuario != null ? usuario.toHexString() : null;
    }

    @BsonIgnore
    public void asignarUsuarioDesdeString(String usuarioId) {
        this.usuario = (usuarioId != null && !usuarioId.isBlank()) ? new ObjectId(usuarioId) : null;
    }

    @BsonIgnore
    public String obtenerIdCamionComoString() {
        return camion != null ? camion.toHexString() : null;
    }

    @BsonIgnore
    public void asignarCamionDesdeString(String camionId) {
        this.camion = (camionId != null && !camionId.isBlank()) ? new ObjectId(camionId) : null;
    }

    @Override
    public String toString() {
        return "Reseña{"
                + "id=" + id
                + ", usuario=" + usuario
                + ", camion=" + camion
                + ", fecha=" + fecha
                + ", calificacion=" + calificacion
                + ", comentario='" + comentario + '\''
                + '}';
    }
}
