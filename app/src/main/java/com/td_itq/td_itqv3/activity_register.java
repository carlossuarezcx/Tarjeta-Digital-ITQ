package com.td_itq.td_itqv3;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.td_itq.td_itqv3.databinding.ActivityMainBinding;

import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.UUID;
public class activity_register extends AppCompatActivity {
    EditText et_email, et_nocontrol, et_contraseña1, et_contraseña2;
    private String url = "https://tarjetadigital2022.000webhostapp.com/api.php?";

    String no_control="";
    String email="";
    String contraseña1="";
    String contraseña2="";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");


    private final int PICK_IMAGE_REQUEST = 22;
    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        TextView lbl_a_log = (TextView) findViewById(R.id.lbl_aviso_cuenta2_login);
        MaterialButton regbtn = (MaterialButton) findViewById(R.id.regbtn);
        MaterialButton  btn_pickimg = (MaterialButton) findViewById(R.id.btn_pickimg);
        et_email = findViewById(R.id.et_reg_email);
        et_nocontrol = findViewById(R.id.et_reg_nodecontrol);
        et_contraseña1 = findViewById(R.id.et_reg_password);
        et_contraseña2 = findViewById(R.id.et_reg_password_repetir);

        storage = FirebaseStorage.getInstance();
        lbl_a_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(activity_register.this, activity_login.class);
                startActivity(myIntent);
                finish();
            }
        });

        btn_pickimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg();
            }

        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {//correct
                } else {
                    //incorrect
                    Toast.makeText(activity_login.this, "Accesso incorrecto", Toast.LENGTH_SHORT).show();
                }*/
                registrar(v);
            }

        });
    }
    private void selectImg(){
        getContent.launch("image/*");
    }
    private  void uploadImage(){
        if (imageUri!=null){
            StorageReference reference = storage.getReference().child("images/"+no_control+".jpg");
            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful() ){
                        MensajeToast("Imagen subida");
                    }else{
                        MensajeToast("No se ha podido subir la imagen");
                    }
                }
            });
        }
    }
    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
                if(result !=null){
                    imageUri=result;
                }
        }
        /*
         @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    } */
    });

    /*
    private void uploadImage()
    {
        if (imageUri != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "image"
                                    + UUID.randomUUID().toString());
            // adding listeners on upload
            // or failure of image
            ref.putFile(imageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(activity_register.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(activity_register.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }*/
    public void MensajeToast (String sms){
        Toast.makeText(this, sms, Toast.LENGTH_SHORT).show();
    }
    public void registrar(View view) {
         no_control=et_nocontrol.getText().toString().replace(" ","%20");
         email=et_email.getText().toString().replace(" ","%20");
         contraseña1=et_contraseña1.getText().toString().replace(" ","%20");
         contraseña2=et_contraseña2.getText().toString().replace(" ","%20");
         Matcher mat = pattern.matcher(email);

        if (contraseña1.equals(contraseña2)){
            if(no_control.length()==8){
                if(mat.matches()){
                    String urlCommand;
                    urlCommand = url + "Solicitud=registrar&email=" + email+"&no_control="+no_control+"&password="+contraseña1;
                    Log.i("Tag", "Solicitud: " + urlCommand.toString());
                    sendAndRequestResponse(urlCommand);
                }else{
                    MensajeToast("Dirección de correo inválida");
                }
            }else{
                MensajeToast("Número de control incorrecto");
            }
        }else{
            MensajeToast("Las contraseñas no coinciden");
        }

    }

    public void sendAndRequestResponse(String url) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("Tag", "Respuesta: " + response);
                if (response.contains("true")){
                    Log.i("response : ",response);
                    MensajeToast("Registro exitoso");
                    uploadImage();
                    et_email.setText("");
                    et_nocontrol.setText("");
                    et_contraseña1.setText("");
                    et_contraseña2.setText("");

                }else {
                    MensajeToast("Registro no exitoso");
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

}