/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rutappbo;

import excepciones.NegocioException;
import itson.rutappdto.AsientoBoletoDTO;
import itson.rutappdto.CompraDTO;
import itson.rutappdto.UsuarioDTO;
import itson.rutappdto.ViajeDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pedro
 */
public interface IComprasBO {

    void agregarCompra(UsuarioDTO usuarioDTO, ViajeDTO viajeDTO, List<AsientoBoletoDTO> asientosDTO);

    List<CompraDTO> obtenerComprasNoVencidasPorUsuario(UsuarioDTO usuarioDTO);

    void cancelarCompra(CompraDTO compraDTO);

    public CompraDTO obtenerCompraDTOPorId(String idCompraOriginal) throws NegocioException;

    public boolean actualizarCompraParaReagenda(String idCompraOriginal, String nuevoViajeId, List<AsientoBoletoDTO> nuevosAsientosDTO, Date nuevaFechaCompra, double nuevoPrecioTotal) throws NegocioException;

}
