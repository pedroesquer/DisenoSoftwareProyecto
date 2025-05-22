/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import itson.persistenciarutapp.IComprasDAO;
import itson.rutappdto.UsuarioDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public class ComprasDAO implements IComprasDAO {

    private final String COLECCION = "compras";

    @Override
    public Compra agregarCompras(Compra nuevaCompra) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);

        coleccion.insertOne(nuevaCompra);
        return nuevaCompra;
    }

    /**
     * Método para consultar las compras de un usuario.
     * @param usuario usuario el cual se le va a consultar sus compra.
     * @return la lista de compras hechas por el usuario.
     */
    @Override
    public List<Compra> consultarCompraPorUsuario(UsuarioDTO usuario) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Compra> coleccion = db.getCollection(COLECCION, Compra.class);
        
        List<Compra> compras = new ArrayList<>();
        
        coleccion.find(Filters.eq("usuario", new ObjectId(usuario.getId()))).into(compras);
        
        return compras;
        
    }

    /**
     * Método para consultar las compras que aun no hayan vencido de un usuario.
     * @param usuario usuario el cual se le va a consultar sus compras.
     * @return la lista de compras del usuario que aun no hayan vencido.
     */
//    @Override
//    public List<Compra> consultarComprasNoVencidasPorUsuario(UsuarioDTO usuario) {
//        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
//        MongoCollection<Document> coleccion = db.getCollection("compras");
//        
//        //Calculamos la fecha de ayer para solo mostrar compras con viajes activos
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, -1);
//        
//        //Ahora hacemos un pipeline ya que la consulta sera con aggregate y no find
//        List<Bson> pipeline = Arrays.asList(
//                Aggregates.match(Filters.eq("usuario", new ObjectId(usuario.getId()))),
//                Aggregates.lookup("viajes", "viaje", "_id", "viajeInfo"),
//                Aggregates.unwind("$viajeInfo"),
//                Aggregates.match(Filters.gt("viajeInfo.fechaHora", calendar))
//        
//        );
//        
//        AggregateIterable<Document> resultados = coleccion.aggregate(pipeline);
//
//    }
    
    
}
