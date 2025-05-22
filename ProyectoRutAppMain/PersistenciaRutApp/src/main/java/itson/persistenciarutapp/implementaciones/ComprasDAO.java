/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itson.persistenciarutapp.IComprasDAO;

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
}
