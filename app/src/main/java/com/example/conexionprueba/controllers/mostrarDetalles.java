package com.example.conexionprueba.controllers;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conexionprueba.Adaptadores.DetallesAdaptador;
import com.example.conexionprueba.Constructores.tipoDetalle;
import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;
import com.example.conexionprueba.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class mostrarDetalles extends AppCompatActivity {

    private RecyclerView recyclerDetalles;
    private DetallesAdaptador adaptadorDetalle;

    //Conexion a la base de datos Azure
    connectionAzure conn = new connectionAzure();
    TransNoTrans TNT = new TransNoTrans(conn);

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

    //Consultando datos en la base de datos remota
    public List<tipoDetalle> obtenerDetallesBD(){
        List<tipoDetalle> detalles=new ArrayList<>();
        try{
            //Obteniendo el ID del auto desde la activity de Mostrar_datos
            String idAuto;
            Bundle extras=getIntent().getExtras();
            if(extras!=null)
            {
                idAuto=extras.getString("id_auto");
                Log.e("auto","el auto id es= "+idAuto);
                ResultSet rs = (ResultSet) TNT.mostrarDetalles(idAuto);
            while (rs.next()){
                detalles.add(new tipoDetalle(rs.getString("id"), rs.getString("nombre"), rs.getString("fecha_entrada"), rs.getString("fecha_salida"), rs.getString("fecha_terminacion"), rs.getString("estado"), rs.getString("descripcion"), rs.getString("costo")));
            }
            }
        }catch(SQLException e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return detalles;
    }


}
