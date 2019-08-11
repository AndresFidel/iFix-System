package com.example.conexionprueba.Database;

import java.sql.*;

public class TransNoTrans {
    private static connectionRules connection;

    public TransNoTrans(connectionRules connection) {
        TransNoTrans.connection = connection;
    }

    public static ResultSet verifyUser(String user) throws SQLException {
        return connection.connect().createStatement().executeQuery("SELECT * FROM cliente WHERE usuario='" + user + "'");
    }

    public static PreparedStatement logIn(String user, String password) throws SQLException {
        return connection.connect().prepareStatement("EXEC iniciarSesion @p_usuario='" + user + "', @p_pass='" + password + "'");
    }

    public static ResultSet verifyPreviousLogIn() throws SQLException {
        return connection.connect().createStatement().executeQuery("SELECT * FROM sesion");
    }

    public static ResultSet getClienteVehicles(String user) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC obtenerVehiculos_Cliente @user=" + user);
    }

    public static PreparedStatement logOut() throws SQLException {
        return connection.connect().prepareStatement("DELETE sesion");
    }

    public static PreparedStatement updatePassword(String user, String confirm) throws SQLException {
        return connection.connect().prepareStatement("UPDATE cliente SET contrasena='" + confirm + "' WHERE usuario='" + user + "'");
    }

    public static ResultSet getServicesVehicle(String id) throws SQLException {
        return connection.connect().createStatement().executeQuery("EXEC obtenerServicios_vehiculo @p_id_vehiculo=" + id);
    }

}
