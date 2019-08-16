package com.example.conexionprueba.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conexionprueba.Adaptadores.VehiculosAdaptador;
import com.example.conexionprueba.Constructores.tipoAuto;
import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;
import com.example.conexionprueba.MainActivity;
import com.example.conexionprueba.Modales.MostrarAyuda;
import com.example.conexionprueba.R;
import com.example.conexionprueba.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class mostrarDatos extends AppCompatActivity {

    private RecyclerView recyclerViewAuto;
    private VehiculosAdaptador adaptadorAuto;

    String user;
    //Declarando variables para el encabezado
    TextView usuarioTxt, btnCambiar, txtBuscar;
    Button salirBtn;
    ImageView imgAyuda, btnLupa;

    //Conexion a la base de datos Azure;
    connectionAzure conn = new connectionAzure();
    TransNoTrans TNT = new TransNoTrans(conn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        recyclerViewAuto = (RecyclerView) findViewById(R.id.recyclerAuto);
        recyclerViewAuto.setLayoutManager(new LinearLayoutManager(this));
        salirBtn = (Button) findViewById(R.id.btnGuardar); //Boton para cerrar sesión
        btnCambiar = (TextView) findViewById(R.id.btnCambiar); //Para cambiar la contraseña del usuario actual.
        btnLupa = (ImageView) findViewById(R.id.btnLupa); //Boton para realizar busqueda
        txtBuscar = (TextView) findViewById(R.id.txtBuscar); //Texto para buscar vehiculos
        usuarioTxt = (TextView) findViewById(R.id.usuarioTxt); //Nombre del usuario iniciado
        imgAyuda = (ImageView) findViewById(R.id.imgAyuda); //Buscando la imagen para mostrar ayuda.

        mostrarUsuario();
        adaptadorAuto = new VehiculosAdaptador(obtenerAutoBD());
        recyclerViewAuto.setAdapter(adaptadorAuto);


        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarBusqueda();
            }
        });

        //Evento del boton para cerrar sesión.
        salirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarContrasena();
            }
        });

        imgAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    //Invocando la funcion para abrir un modal de ayuda.
    public void openDialog() {
        MostrarAyuda exampleDialog = new MostrarAyuda();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //Metodo que retornara todos los automoviles del cliente
    public List<tipoAuto> obtenerAutoBD() {
        String usuario = usuarioTxt.getText().toString();
        List<tipoAuto> sc = new ArrayList<>();

        try {
            ResultSet us = TNT.getCliente(usuario);
            if (us.next()) {
                String id;
                id = us.getString(1);
                ResultSet rs = TNT.verVehiculos(id);
                while (rs.next()) {
                    sc.add(new tipoAuto(rs.getString("id_vehiculo"), rs.getString("marca"), rs.getString("modelo"), rs.getString("anio"), R.drawable.auto));
                }
            }

        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return sc;
    }

    //Metodo que retornara todas las busquedas del cliente
    public List<tipoAuto> obtenerBusquedaBD() {
        List<tipoAuto> busqueda = new ArrayList<>();
        try {
            String usuario;
            String entrada;
            usuario = usuarioTxt.getText().toString();
            entrada = txtBuscar.getText().toString();
            ResultSet rs = TNT.getCliente(usuario);
            if (rs.next()) {
                int id = rs.getInt(1);
                ResultSet cc = TNT.buscarVehiculo(id, entrada);
                while (cc.next()) {
                    busqueda.add(new tipoAuto(cc.getString("id_vehiculo"), cc.getString("marca"), cc.getString("modelo"), cc.getString("anio"), R.drawable.auto));
                }
            } else {
                Log.e("Error", "Ocurrio Algo");
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return busqueda;
    }

    //Metodo para visualizar las busquedas
    public void realizarBusqueda() {
        adaptadorAuto = new VehiculosAdaptador(obtenerBusquedaBD());
        recyclerViewAuto.setAdapter(adaptadorAuto);
    }


    //Funcion para mostrar el usuario logueado.
    public void mostrarUsuario() {
        try {
            ResultSet rs = TNT.verifyPreviousLogIn();

            if (rs.next()) {
                usuarioTxt.setText(rs.getString(1));
                user = rs.getString(1);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Funcion para cerrar sesion
    public void cerrarSesion() {
        try {
            String usuario = usuarioTxt.getText().toString();
            PreparedStatement pst = TNT.logOut(usuario);
            pst.executeUpdate();


            Toast.makeText(getApplicationContext(), "El usuario cerrado sesión.", Toast.LENGTH_SHORT).show();
            //Dirigiendonos a la activity de login.
            Intent inicio = new Intent(mostrarDatos.this, login.class);
            startActivity(inicio);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cambiarContrasena() {
        Intent sc = new Intent(mostrarDatos.this, MainActivity.class);

        //Creando instancia para guardar los valores que seran enviados a otra activity.
        Bundle miBundle = new Bundle();
        miBundle.putString("usuario", usuarioTxt.getText().toString());

        //Enviando datos a otra activity.
        sc.putExtras(miBundle);
        //Cambiando a la activity cambiar la contraseña.
        startActivity(sc);
    }

} //Fin clase mostrarDatos
