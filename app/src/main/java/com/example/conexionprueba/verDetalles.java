package com.example.conexionprueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.conexionprueba.Adaptadores.DetallesAdaptador;
import com.example.conexionprueba.Constructores.tipoDetalle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class verDetalles extends AppCompatActivity {

    private RecyclerView recyclerDetalles;
    private DetallesAdaptador adaptadorDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles);

        //Buscando el objeto recycles view.
        recyclerDetalles=(RecyclerView)findViewById(R.id.recyclerDetalles);
        //Llamando a la pantalla externa item_detalles
        recyclerDetalles.setLayoutManager(new LinearLayoutManager(this));

        //Enviando los datos solicitados al adaptador.
        adaptadorDetalle=new DetallesAdaptador(obtenerDetallesBD());
        //Agregando los datos obtenidos al objeto recyclerview
        recyclerDetalles.setAdapter(adaptadorDetalle);

    }

    //Funcion para realizar la conexion con SQL server
    public Connection conexionBD(){
        Connection conexion=null;
        try{
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://ifix.database.windows.net;databaseName=ifixdb;user=raulballeza;password=ifixdb_ads_68;");

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        return conexion;
    }

    //Consultando datos en la base de datos remota
    public List<tipoDetalle> obtenerDetallesBD(){
        List<tipoDetalle> detalles=new ArrayList<>();
        try{
            Statement st=conexionBD().createStatement();
            ResultSet rs=st.executeQuery("Select * from cliente");
            while (rs.next()){
                detalles.add(new tipoDetalle(rs.getString("nombre"),rs.getString("usuario"),rs.getString("contrasena")));
            }
        }catch(SQLException e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return detalles;
    }

    //Prueba de manera Local
    public List<tipoDetalle> obtenerDetalles(){
        List<tipoDetalle> detalle=new ArrayList<>();
        detalle.add(new tipoDetalle("El Johnny","12Febrero2019","29134"));
        return detalle;
    }


}
