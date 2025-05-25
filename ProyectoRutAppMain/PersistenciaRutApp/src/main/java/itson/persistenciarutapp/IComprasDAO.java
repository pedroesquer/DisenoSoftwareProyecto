/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.persistenciarutapp;

import Entidades.AsientoBoleto;
import Entidades.Compra;
import itson.rutappdto.UsuarioDTO;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author pedro
 */
public interface IComprasDAO {

    Compra agregarCompras(Compra nuevaCompra);

    List<Compra> consultarCompraPorUsuario(UsuarioDTO usuario);

    List<Compra> consultarComprasNoVencidasPorUsuario(String idUsuario);

    void cancelarCompra(String idCompraStr);

    public String obtenerIdDeCompra(String idUsuarioStr, Date fechaCompra);

    public Compra consultarCompraPorId(ObjectId idCompra);

    public boolean actualizarCompraParaReagenda(ObjectId idCompra, ObjectId nuevoViajeId, List<AsientoBoleto> nuevosAsientosEntities, Date nuevaFechaCompra, Double nuevoPrecioTotal);
}
