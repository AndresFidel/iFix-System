package com.example.conexionprueba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conexionprueba.Modales.AyudaGuardarCambios;
import com.example.conexionprueba.Modales.MostrarAyuda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    //Declarando variables
    TextView txtUsuario, txtPass, txtNewPass, txtConfirmar;
    ImageView imgAyuda;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario=(TextView)findViewById(R.id.txtUsuario); //Nombre del usuario iniciado
        txtPass=(TextView)findViewById(R.id.txtPass); //Para la contraseña actual
        txtNewPass=(TextView)findViewById(R.id.txtNewPass);  //Para la nueva contraseña
        txtConfirmar=(TextView)findViewById(R.id.txtConfirmar); //Para confirmar la contraseña
        imgAyuda=(ImageView)findViewById(R.id.imgAyuda); //Buscando la imagen para mostrar ayuda.
        btnGuardar=(Button)findViewById(R.id.btnGuardar); //Boton para guardar los cambios.

        mostrarUsuario(); //Mostrando el usuario actual. Paquetes : rdlc

        imgAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //Llamando el evento para llamar un modal y guardar los cambios de la contraseña.
        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder alerta=new AlertDialog.Builder(MainActivity.this);
                alerta.setMessage("¿Está seguro que desea guardar los cambios realizados?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                guardarCambios();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog titulo=alerta.create();
                titulo.setTitle("Guardar cambios");
                titulo.show();
            }
        });
    }

    //Invocando la funcion para abrir un modal de ayuda.
    public void openDialog(){
        AyudaGuardarCambios exampleDialog=new AyudaGuardarCambios();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }

    //Funcion para realizar la conexion con SQL remoto.
    public Connection conexionBD(){
        Connection conexion=null;
        try{
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //Direccion de la base de datos remotamente.
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://ifix.database.windows.net;databaseName=ifixdb;user=raulballeza;password=ifixdb_ads_68;");

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
        return conexion;
    }

//Funcion para mostrar el usuario logueado.
    public void mostrarUsuario(){
        try{
            Bundle miBundle=this.getIntent().getExtras();

            if(miBundle!=null){
                String nombre=miBundle.getString("usuario");
                txtUsuario.setText(nombre);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Evento para guardar la nueva contraseña del usuario actual. DEMO
    public void guardarCambios(){
        try{
            Statement cc=conexionBD().createStatement();
            ResultSet rs= cc.executeQuery("SELECT usuario FROM cliente WHERE usuario='"+txtUsuario.getText().toString()+"'");
            if(txtPass.getText()=="" || txtNewPass.getText()=="" || txtConfirmar.getText()=="")
            {
                Toast.makeText(getApplicationContext(),"Campos ingresados no válidos.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(rs.next()){
                    //Se realiza la sentencia para modificar la contraseña.
                    PreparedStatement pst=conexionBD().prepareStatement("UPDATE cliente SET contrasena='"+txtConfirmar.getText().toString()+"' WHERE usuario='"+txtUsuario.getText().toString()+"'");
                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"La contraseña ha sido actualizada!",Toast.LENGTH_SHORT).show();
                    txtPass.setText("");
                    txtNewPass.setText("");
                    txtConfirmar.setText("");
                    //En caso de cambiar la contraseña, cambiaremos a la activity del menu.
                    Intent siguiente=new Intent(MainActivity.this,mostrarDatos.class);
                    startActivity(siguiente);

                }
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
