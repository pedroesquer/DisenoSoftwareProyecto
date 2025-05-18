///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package itson.persistenciarutapp.implementaciones;
//
//import itson.rutappdto.AccesoUsuarioDTO;
//import itson.rutappdto.UsuarioDTO;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author pedro
// */
//public class UsuariosDAOTest {
//    
//    public UsuariosDAOTest() {
//    }
//
//    /**
//     * Test of agregarUsuario method, of class UsuariosDAO.
//     */
//    @Test
//    public void testAgregarUsuario() {
//        UsuariosDAO usuariosDAO = new UsuariosDAO();
//        UsuarioDTO nuevoUsuario = new UsuarioDTO("Pedro Esquer", "6441157277", "mb");
//        usuariosDAO.agregarUsuario(nuevoUsuario);
//    }
//
//    /**
//     * Test of consultarUsuarioPorNumeroTelefonico method, of class UsuariosDAO.
//     */
//    @Test
//    public void testConsultarUsuarioPorNumeroTelefonico() {
//        UsuariosDAO usuariosDAO = new UsuariosDAO();
//       
//        System.out.println(usuariosDAO.consultarUsuarioPorNumeroTelefonico("64421558877"));
//    }
//    
//    @Test
//    public void testConsultarUsuarioyContrasena() {
//        UsuariosDAO usuariosDAO = new UsuariosDAO();
//        
//        System.out.println(usuariosDAO.validarLogin("64421558877", "ana"));
//    }
//    
//}
