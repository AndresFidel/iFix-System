package com.example.conexionprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class login extends AppCompatActivity {

    //Declaracion de las variables.
    EditText usuarioTxt, passTxt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioTxt=(EditText) findViewById(R.id.usuarioTxt); //Buscando el ID para guardar nombre del usuario.
        passTxt=(EditText) findViewById(R.id.passTxt); //Buscando el ID para guardar la contraseña del usuario.
        loginBtn=(Button)findViewById(R.id.loginBtn); //Buscando el ID para almacenar la accion del Iniciar sesion.

        verificarActividad(); //Verificar si el usuario no ha cerrado sesión.

        //Evento clic del boton
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iniciarSesion();
            }
        });
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

    public void iniciarSesion(){
        try{

            String usuario=usuarioTxt.getText().toString();
            String pass=passTxt.getText().toString();
            boolean bandera=true;

            if(usuario.isEmpty())
            {
                usuarioTxt.setError("Usuario no ingresado.");
                bandera=false;
            }

            if(pass.isEmpty()){
                passTxt.setError("Contraseña no ingresada.");
                bandera=false;
            }


            if(bandera==true) {
                //Realizando busqueda mediante una consulta en sql server.
                Statement stm = conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM cliente WHERE usuario='" + usuarioTxt.getText().toString() + "'");
                //Comprobando usuario existenete
                if (rs.next()) {
                    Toast.makeText(getApplicationContext(), "Accediendo al usuario " + usuarioTxt.getText(), Toast.LENGTH_SHORT).show();
                    PreparedStatement pst = conexionBD().prepareStatement("INSERT INTO sesion VALUES(?,?)");
                    pst.setString(1, usuarioTxt.getText().toString());
                    pst.setString(2, passTxt.getText().toString());
                    pst.executeUpdate();

                    //Dirigiendonos a la siguiente activity
                    Intent siguiente = new Intent(login.this, mostrarDatos.class);
                    startActivity(siguiente);
                } else {
                    Toast.makeText(getApplicationContext(), "El usuario no existe.", Toast.LENGTH_SHORT).show();
                }
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void verificarActividad(){
        try{
            Statement stm=conexionBD().createStatement();
            ResultSet rs= stm.executeQuery("SELECT * FROM sesion");

            if(rs.next()){
                Toast.makeText(getApplicationContext(),"Bienvenido a la APP iFix.",Toast.LENGTH_SHORT).show();
                Intent siguiente=new Intent(login.this,mostrarDatos.class);
                startActivity(siguiente);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Bienvenido a la APP iFix.",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }




}
