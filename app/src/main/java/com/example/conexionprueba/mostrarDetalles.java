package com.example.conexionprueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.conexionprueba.Adaptadores.DetallesAdaptador;
import com.example.conexionprueba.Constructores.tipoDetalle;
import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class mostrarDetalles extends AppCompatActivity {

    private RecyclerView recyclerDetalles;
    private DetallesAdaptador adaptadorDetalle;

    String id;

    //Conexion a la base de datos Azure;
    connectionAzure conn = new connectionAzure();
    TransNoTrans TNT = new TransNoTrans(conn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //Buscando el objeto recycles view.
        recyclerDetalles = (RecyclerView) findViewById(R.id.recyclerDetalles);
        //Llamando a la pantalla externa item_detalles
        recyclerDetalles.setLayoutManager(new LinearLayoutManager(this));

        //Toast.makeText(getApplicationContext(), id+"dETALLE", Toast.LENGTH_LONG).show();
        //Enviando los datos solicitados al adaptador.
        adaptadorDetalle = new DetallesAdaptador(obtenerDetallesBD());
        //Agregando los datos obtenidos al objeto recyclerview
        recyclerDetalles.setAdapter(adaptadorDetalle);

    }

    //Consultando datos en la base de datos remota
    public List<tipoDetalle> obtenerDetallesBD() {
        List<tipoDetalle> detalles = new ArrayList<>();
        try {
            ResultSet rs = TNT.getServicesVehicle(id);
            while (rs.next()) {
                detalles.add(new tipoDetalle(rs.getString("nombre"), rs.getString("fecha_salida"), rs.getString("costo"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return detalles;
    }

}
