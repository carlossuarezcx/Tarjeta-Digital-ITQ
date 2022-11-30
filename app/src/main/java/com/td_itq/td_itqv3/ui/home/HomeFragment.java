package com.td_itq.td_itqv3.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.td_itq.td_itqv3.MainActivity;
import com.td_itq.td_itqv3.R;
import com.td_itq.td_itqv3.databinding.FragmentHomeBinding;

import java.io.File;
import java.io.IOException;

public class HomeFragment extends Fragment {

    String nombre="";
    String apellidos="";
    String carrera="";
    String no_control="";
    ImageView imagen;
    ImageView foto;
    //Firebase
    StorageReference storageReference;
    Bitmap bitmapFoto;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cargarDatos();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        foto = binding.imageView3;
        imagen=binding.imageView4;
        final TextView txt_nombre = binding.infoNombre;
        txt_nombre.setText(nombre+" "+apellidos);

        final TextView txt_carrera = binding.infoCarrera;
        txt_carrera.setText(carrera);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try{
            Bitmap bitmap=barcodeEncoder.encodeBitmap(no_control,BarcodeFormat.QR_CODE,150,150);
            imagen.setBackgroundColor(0);
            imagen.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        //GET IMG
        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+no_control+".jpg");
        try {
            final File localfile = File.createTempFile(""+no_control,"jpg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmapFoto = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    //((ImageView)findViewById(R.id.imageView3)).setImageBitmap(bitmap);
                    foto.setImageBitmap(bitmapFoto);
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
        return root;
    }
    private void cargarDatos(){
        SharedPreferences preferences= this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        nombre =preferences.getString("nombre"," ");
        apellidos=preferences.getString("apellidos"," ");
        carrera=preferences.getString("carrera"," ");
        no_control=preferences.getString("no_control","");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}