package com.example.conexionprueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conexionprueba.Adaptadores.RecyclerViewAdaptador;
import com.example.conexionprueba.Constructores.tipoAuto;
import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;
import com.example.conexionprueba.Modales.MostrarAyuda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class mostrarDatos extends AppCompatActivity {

    private RecyclerView recyclerViewAuto;
    private RecyclerViewAdaptador adaptadorAuto;
    String user;
    //Declarando variables para el encabezado
    TextView usuarioTxt, btnCambiar;
    Button salirBtn;
    ImageView imgAyuda;

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
        usuarioTxt = (TextView) findViewById(R.id.usuarioTxt); //Nombre del usuario iniciado
        imgAyuda = (ImageView) findViewById(R.id.imgAyuda); //Buscando la imagen para mostrar ayuda.

        //      btnVer=(Button)findViewById(R.id.btnVer);
        mostrarUsuario();
        adaptadorAuto = new RecyclerViewAdaptador(obtenerAutoBD());


        //Cuerpo del evento para la vista.
        /*
        adaptadorAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Seleccion: ",Toast.LENGTH_SHORT).show();
            }
        });*/
        recyclerViewAuto.setAdapter(adaptadorAuto);
/*
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checar();
            }
        });
*/
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

    public List<tipoAuto> obtenerAutoBD() {
        List<tipoAuto> sc = new ArrayList<>();
        try {
            ResultSet rs = TNT.getClienteVehicles(user);
            while (rs.next()) {
                sc.add(new tipoAuto(rs.getString("marca"), rs.getString("modelo"), rs.getString("anio"), rs.getString("num_serie"), R.drawable.auto, rs.getString("id_vehiculo")));
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return sc;
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
            PreparedStatement pst = TNT.logOut();
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


    public void checar() {
        Toast.makeText(getApplicationContext(), "El usuario no existe.", Toast.LENGTH_SHORT).show();
    }

}
