package com.td_itq.td_itqv3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.td_itq.td_itqv3.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //Firebase
    StorageReference storageReference;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    String no_control = "";
    String password = "";
    String nombre="";
    String apellidos = "";
    String fecha_nacimiento="";
    String celular="";
    String semestre ="";
    String carrera ="";
    String imagen ="";
    //Firebase
    Bitmap bitmapFoto;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarDatos();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateHead();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cerrar_sesion:
                cerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void updateNav(){

    }
    private void cerrarSesion(){
        Intent login = new Intent(MainActivity.this, activity_login.class);
        startActivity(login);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }
    private void cargarDatos(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        no_control=preferences.getString("no_control"," ");
        password=preferences.getString("password"," ");
        nombre =preferences.getString("nombre"," ");
        apellidos=preferences.getString("apellidos"," ");
        fecha_nacimiento=preferences.getString("fecha_nacimiento"," ");
        celular=preferences.getString("celular"," ");
        semestre=preferences.getString("semestre"," ");
        carrera=preferences.getString("carrera"," ");
        imagen=preferences.getString("imagen"," ");
    }
    public  void  updateHead(){
        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        cargarDatos();
        View headerView = navigation.getHeaderView(0);
        TextView lbl_carrera ;
        TextView lbl_nombre ;
        lbl_nombre =  headerView.findViewById(R.id.label_nav_nombre);
        lbl_carrera = headerView.findViewById(R.id.label_nav_carrera);
        lbl_nombre.setText(nombre+" "+apellidos);
        lbl_carrera.setText(carrera);

        ImageView img;
        img = headerView.findViewById(R.id.imageView);
        img.setImageBitmap(bitmapFoto);

        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+no_control+".jpg");
        try {
            final File localfile = File.createTempFile(""+no_control,"jpg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmapFoto = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    //((ImageView)findViewById(R.id.imageView3)).setImageBitmap(bitmap);
                    img.setImageBitmap(bitmapFoto);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Error
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}