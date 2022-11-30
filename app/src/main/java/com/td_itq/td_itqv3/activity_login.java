package com.td_itq.td_itqv3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class activity_login extends AppCompatActivity {

    EditText et_email, et_password;
    String passLog, emaillog;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://tarjetadigital2022.000webhostapp.com/api.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView username = (TextView) findViewById(R.id.login_email);
        TextView password = (TextView) findViewById(R.id.login_password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.btn_login);
        et_email = findViewById(R.id.login_email);
        et_password = findViewById(R.id.login_password);

        TextView lbl_a_registrarse = (TextView) findViewById(R.id.lbl_aviso_cuenta2_reg);
        lbl_a_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity_login.this, activity_register.class);
                startActivity(myIntent);
                finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {//correct
                } else {
                    //incorrect
                    Toast.makeText(activity_login.this, "Accesso incorrecto", Toast.LENGTH_SHORT).show();
                }*/
                entrar(v);
            }

        });


    }
    public void entrar(View view) {
        passLog = et_password.getText().toString().replace(" ", "%20");
        emaillog = et_email.getText().toString().replace(" ", "%20");
        String urlCommand;
        urlCommand = url + "REQUEST_METHOD=Get&Solicitud=login&email=" + emaillog;
        //Log.i("Tag", "Solicitud: " + urlCommand.toString());
        sendAndRequestResponse(urlCommand);
    }
    public void sendAndRequestResponse(String url) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                //Log.i("Tag", "Response: " +response);
                if (response.length()>2){
                    int pos1 = response.indexOf('{');
                    int pos2 = response.lastIndexOf('}');
                    String jsnres = response.substring(pos1,pos2+1);
                    //Log.i("TAG","jsnres :" + jsnres.toString());
                    validarUser(jsnres);
                }else {
                    MensajeToast("El usuario no existe");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.i("TAG","Error :" + error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    public void MensajeToast(String sms){
        Toast.makeText(this, sms,Toast.LENGTH_SHORT).show();
    }
    public void validarUser (String Response){
        String json = "["+Response+"]";
        String correo = "";
        String no_control = "";
        String password = "";
        String nombre="";
        String apellidos = "";
        String fecha_nacimiento="";
        String celular="";
        String semestre ="";
        String carrera ="";
        try {
            JSONArray array = new JSONArray(json);
            for(int i=0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                no_control = object.getString("no_control");
                password = object.getString("password");
                nombre = object.getString("nombre").replace("%20"," ");
                apellidos = object.getString("apellidos").replace("%20"," ");
                fecha_nacimiento = object.getString("fecha_nacimiento");
                celular = object.getString("celular");
                semestre = object.getString("semestre");
                carrera = object.getString("carrera").replace("%20"," ");
                correo = object.getString("correo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i("Tag", "Contraseña ingresada: '" +passLog +"' contraseña real: '"+password+"'");
        if (password.equals(passLog)){
            MensajeToast(nombre + " has iniciado sesión correctamente!");
            SharedPreferences preferences = activity_login.this.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            editor.putString("no_control",no_control);
            editor.putString("password",password);
            editor.putString("nombre",nombre);
            editor.putString("apellidos",apellidos);
            editor.putString("fecha_nacimiento",fecha_nacimiento);
            editor.putString("celular",celular);
            editor.putString("semestre",semestre);
            editor.putString("carrera",carrera);
            editor.putString("correo",correo);
            editor.commit();
            //Toast.makeText(activity_login.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
            Intent menu = new Intent(activity_login.this, MainActivity.class);
            startActivity(menu);
            finish();
            //Intent menu_ppal = new Intent(this, menu_ppal.class);
            //menu_ppal.putExtra("name",name+"");
            //menu_ppal.putExtra("user",user+"");
            //menu_ppal.putExtra("password",password+"");
            //menu_ppal.putExtra("type",type+"");
            //menu_ppal.putExtra("ID_Sucursal",id_sucursal+"");
            //menu_ppal.putExtra("nombresuc",nombreScursal+"");
            //startActivity(menu_ppal);
            //finish();
        }else {
            MensajeToast("Datos incorrectos");
        }

    }
}