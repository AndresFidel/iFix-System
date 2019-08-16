package com.example.conexionprueba;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conexionprueba.Database.TransNoTrans;
import com.example.conexionprueba.Database.connectionAzure;
import com.example.conexionprueba.Modales.AyudaGuardarCambios;
import com.example.conexionprueba.controllers.mostrarDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    //Declarando variables
    TextView txtUsuario;
    EditText txtPass, txtNewPass, txtConfirmar;
    ImageView imgAyuda;
    Button btnGuardar;

    //Conexion a la base de datos Azure
    connectionAzure conn = new connectionAzure();
    TransNoTrans TNT = new TransNoTrans(conn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (TextView) findViewById(R.id.txtUsuario); //Nombre del usuario iniciado
        txtPass = (EditText) findViewById(R.id.txtPass); //Para la contraseña actual
        txtNewPass = (EditText) findViewById(R.id.txtNewPass);  //Para la nueva contraseña
        txtConfirmar = (EditText) findViewById(R.id.txtConfirmar); //Para confirmar la contraseña
        imgAyuda = (ImageView) findViewById(R.id.imgAyuda); //Buscando la imagen para mostrar ayuda.
        btnGuardar = (Button) findViewById(R.id.btnGuardar); //Boton para guardar los cambios.

        mostrarUsuario(); //Mostrando el usuario actual. Paquetes : rdlc

        imgAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //Llamando el evento para llamar un modal y guardar los cambios de la contraseña.
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Guardar cambios");
                titulo.show();
            }
        });
    }

    //Invocando la funcion para abrir un modal de ayuda.
    public void openDialog() {
        AyudaGuardarCambios exampleDialog = new AyudaGuardarCambios();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //Funcion para mostrar el usuario logueado.
    public void mostrarUsuario() {
        try {
            Bundle miBundle = this.getIntent().getExtras();

            if (miBundle != null) {
                String nombre = miBundle.getString("usuario");
                txtUsuario.setText(nombre);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean comprobarUsuario() {
        String pass, passReal;
        boolean bandera = true;
        pass = txtPass.getText().toString();
        try {
            ResultSet rs = (ResultSet) TNT.verifyUser(txtUsuario.getText().toString());
            if (rs.next()) {
                passReal = rs.getString(4);
                if (pass.equals(passReal)) {
                    Log.e("Error", "Contraseña correcta");
                } else {
                    bandera = false;
                }
            } else {
                bandera = false;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
        }
        return bandera;
    }

    //Evento para guardar la nueva contraseña del usuario actual. DEMO
    public void guardarCambios() {
        boolean bandera = true;
        String vtxtPass, vtxtNewPass, vtxtConfirmar;
        vtxtPass = txtPass.getText().toString();
        vtxtNewPass = txtNewPass.getText().toString();
        vtxtConfirmar = txtConfirmar.getText().toString();
        try {
            ResultSet rs = (ResultSet) TNT.verifyUser(txtUsuario.getText().toString());

            if (vtxtPass.equals("") && vtxtConfirmar.equals("") && vtxtNewPass.equals("")) {
                Toast.makeText(getApplicationContext(), "Campos ingresados no válidos.", Toast.LENGTH_SHORT).show();
                txtPass.setError("Favor de completar el campo.");
                txtNewPass.setError("Favor de completar el campo.");
                txtPass.setError("Favor de completar el campo.");
                bandera = false;
            }

            if (!vtxtNewPass.equals(vtxtConfirmar)) {
                Toast.makeText(getApplicationContext(), "Verificar que la contraseña sean iguales.", Toast.LENGTH_SHORT).show();
                txtNewPass.setError("Vuelve a ingresar la nueva contraseña.");
                txtConfirmar.setError("Vuelve a ingresar la nueva contraseña.");
                txtNewPass.setText("");
                txtConfirmar.setText("");
                bandera = false;
            }

            if (!comprobarUsuario()) {
                Toast.makeText(getApplicationContext(), "La contraseña actual no es la correcta.", Toast.LENGTH_SHORT).show();
                txtPass.setError("La contraseña es incorrecta.");
                txtPass.setText("");
                bandera = false;
            }

            if (bandera == true) {
                if (rs.next()) {
                    //Se realiza la sentencia para modificar la contraseña
                    PreparedStatement pst = TNT.updatePassword(txtUsuario.getText().toString(), txtConfirmar.getText().toString());
                    pst.executeUpdate();

                    Toast.makeText(getApplicationContext(), "La contraseña ha sido actualizada!", Toast.LENGTH_SHORT).show();
                    //En caso de cambiar la contraseña, cambiaremos a la activity del menu.
                    Intent siguiente = new Intent(MainActivity.this, mostrarDatos.class);
                    startActivity(siguiente);

                }
            } else {
                Log.e("Exito", "Campos completados");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
