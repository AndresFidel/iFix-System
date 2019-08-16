package com.example.conexionprueba.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransNoTrans {
    private static connectionRules connection;

    public TransNoTrans(connectionRules connection) {
        TransNoTrans.connection = connection;
    }

    public static ResultSet verifyUser(String user) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC buscarUsuario @p_usuario='"+user+"'");
    }

    public static PreparedStatement logIn(String user, String password) throws SQLException {
        return connection.connect().prepareStatement("EXEC iniciarSesion @p_usuario='" + user + "', @p_pass='" + password + "'");
    }

    public static ResultSet verifyPreviousLogIn() throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC usuarioActivo");
    }

    public static ResultSet getCliente(String user) throws SQLException{
        return connection.connect().createStatement().executeQuery("EXEC buscarUsuario @p_usuario='"+user+"'");
    }

    public static ResultSet verVehiculos(String id) throws SQLException{
        return connection.connect().createStatement().executeQuery("EXEC verVehiculos @p_id="+id);
    }

    public static ResultSet buscarVehiculo(int id, String entrada) throws SQLException{
        return connection.connect().createStatement().executeQuery("EXEC buscarVehiculo @p_id_cliente="+id+", @p_entrada='"+entrada+"'");
    }

    public static ResultSet getClienteVehicles(String user) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC obtenerVehiculos_Cliente @user=" + user);
    }

    public static ResultSet mostrarDetalles(String idAuto) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC mostrarDetalles @p_id_vehiculo="+idAuto);
    }

    public static PreparedStatement logOut(String user) throws SQLException {
        return connection.connect().prepareStatement("EXEC cerrarSesion @p_usuario='"+user+"'");
    }

    public static PreparedStatement updatePassword(String user, String newPass) throws SQLException {
        return connection.connect().prepareStatement("EXEC changePass @p_usuario='"+user+"', @p_newPass='"+newPass+"'");
    }

    public static ResultSet getServicesVehicle(String id) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC obtenerServicios_vehiculo @p_id_vehiculo=" + id);
    }

}