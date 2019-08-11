package com.example.conexionprueba.Database;

import java.sql.Connection;
import java.sql.SQLException;

public interface connectionRules {
    Connection connect();
    void close() throws SQLException;
}
