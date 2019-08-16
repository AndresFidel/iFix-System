package com.example.conexionprueba;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;
import com.example.conexionprueba.controllers.mostrarDatos;

import java.sql.ResultSet;

//Clase login
public class login extends AppCompatActivity {

    //Objeto barra de carga
    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressAnimator;

    //Conexion a la base de datos Azure
    connectionAzure conn = new connectionAzure();
    TransNoTrans TNT = new TransNoTrans(conn);

    //Declaracion de las variables.
    EditText usuarioTxt, passTxt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializando barra de carga
        init();
        progressAnimator.setDuration(4000);

        //Delay para mostrar la barra de carga y despues lanzar el metodo iniciar sesion
        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressAnimator.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iniciarSesion();
                    }
                }, 3000);
            }
        });

        //Proceso de finalizacion de barra de carga
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBarAnimation.setVisibility(View.GONE); //Quitar la barra de carga
            }
        });


        usuarioTxt = (EditText) findViewById(R.id.usuarioTxt); //Buscando el ID para guardar nombre del usuario.
        passTxt = (EditText) findViewById(R.id.passTxt); //Buscando el ID para guardar la contraseña del usuario.
        loginBtn = (Button) findViewById(R.id.loginBtn); //Buscando el ID del boton Iniciar sesion.

        verificarActividad(); //Verificar si el usuario no ha cerrado sesión.
    }

    //Localizando la barra de carga e inicializando sus valores
    private void init() {
        progressBarAnimation = findViewById(R.id.ProgressBar);
        progressAnimator = ObjectAnimator.ofInt(progressBarAnimation, "progress", 0, 100);
    }

    //Funcion para el inicio de sesión
    public void iniciarSesion() {
        try {

            String usuario = usuarioTxt.getText().toString();
            String pass = passTxt.getText().toString();
            boolean bandera = true;

            //Verificando campos vacios.
            if (usuario.isEmpty()) {
                usuarioTxt.setError("Usuario no ingresado.");
                progressBarAnimation.setVisibility(View.VISIBLE);
                bandera = false;
            }
            if (pass.isEmpty()) {
                passTxt.setError("Contraseña no ingresada.");
                progressBarAnimation.setVisibility(View.VISIBLE);
                bandera = false;
            }


            //Bandera para permitir el acceso de login.
            if (bandera == true) {
                //Realizando busqueda del usuario mediante una consulta.
                ResultSet rs = (ResultSet) TNT.verifyUser(usuarioTxt.getText().toString());

                //Comprobando usuario existenete
                if (rs.next()) {
                    String passVer = passTxt.getText().toString(); //Guardando valor del textview en una variable
                    String passReal = rs.getString(4); //Guardando el valor de la columna cuatro de la tabla cliente a la variable
                    if (passReal.equals(passVer)) {
                        Toast.makeText(getApplicationContext(), "Accediendo al usuario " + usuarioTxt.getText(), Toast.LENGTH_SHORT).show();

                        TNT.logIn(usuarioTxt.getText().toString(), passTxt.getText().toString()).executeUpdate();
                        //Dirigiendonos a la siguiente activity
                        Intent siguiente = new Intent(login.this, mostrarDatos.class);
                        startActivity(siguiente);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "El usuario no existe.", Toast.LENGTH_SHORT).show();
                        progressBarAnimation.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } //Fin metodo iniciarSesion


    //Metodo para verificar si el usuario aun sigue logeado.
    public void verificarActividad() {
        try {

            ResultSet rs = TNT.verifyPreviousLogIn();

            if (rs.next()) {
                Intent siguiente = new Intent(login.this, mostrarDatos.class);
                startActivity(siguiente);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Bienvenido a la APP iFix.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }//Fin metodo verificarActividad

} //Fin clase login
