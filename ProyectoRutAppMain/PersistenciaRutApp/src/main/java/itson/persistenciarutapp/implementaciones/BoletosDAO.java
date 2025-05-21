/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp.implementaciones;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import itson.persistenciarutapp.IBoletoDAO;
import itson.rutappdto.BoletoDTO;

/**
 *
 * @author chris
 */
public class BoletosDAO implements IBoletoDAO {

    private final String COLECCION = "boletos";

    public BoletosDAO() {
    }

    @Override
    public Boleto agregarBoleto(Boleto boletoNuevo) {
        MongoDatabase db = ManejadorConexiones.obtenerBaseDatos();
        MongoCollection<Boleto> coleccion = db.getCollection(COLECCION, Boleto.class);

        coleccion.insertOne(boletoNuevo);
        return boletoNuevo;
    }

    @Override
    public Boleto obtenerBoleto(String numero) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
