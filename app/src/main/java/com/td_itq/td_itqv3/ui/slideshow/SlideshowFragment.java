package com.td_itq.td_itqv3.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.td_itq.td_itqv3.MainActivity;
import com.td_itq.td_itqv3.R;
import com.td_itq.td_itqv3.activity_login;
import com.td_itq.td_itqv3.databinding.FragmentSlideshowBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlideshowFragment extends Fragment implements View.OnClickListener {
    private String url = "https://tarjetadigital2022.000webhostapp.com/api.php?";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

    String no_control = "";
    String password = "";
    String correo = "";
    String nombre="";
    String apellidos="";
    String fecha_nacimiento="";
    String celular="";
    String semestre ="";
    String carrera ="";

    EditText et_nocontrol ;
    EditText et_correo ;
    EditText et_nombre;
    EditText et_apellidos ;
    EditText et_fecha ;
    EditText et_celular ;
    EditText et_semestre;
    EditText et_carrera;
    EditText et_password;
    MainActivity ma;

    Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;


    private void cargarDatos(){
        SharedPreferences preferences= this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        no_control=preferences.getString("no_control"," ");
        correo=preferences.getString("correo"," ");
        password=preferences.getString("password"," ");
        nombre =preferences.getString("nombre"," ");
        apellidos=preferences.getString("apellidos"," ");
        fecha_nacimiento=preferences.getString("fecha_nacimiento"," ");
        celular=preferences.getString("celular"," ");
        semestre=preferences.getString("semestre"," ");
        carrera=preferences.getString("carrera"," ");
    }
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cargarDatos();
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MaterialButton cambiardatos = (MaterialButton) root.findViewById(R.id.btn_perfil_cambiar_datos);
        MaterialButton  btn_pickimg = (MaterialButton) root.findViewById(R.id.btn_perfil_cambiar_foto);
        cambiardatos.setOnClickListener(this);
        btn_pickimg.setOnClickListener(this);
        et_nocontrol = binding.etPerfilNodecontrol;
        et_nocontrol.setText(no_control);
        et_correo = binding.etPerfilEmail;
        et_correo.setText(correo);
        et_nombre = binding.etPerfilNombre;
        et_nombre.setText(nombre);
        et_apellidos = binding.etPerfilApellidos;
        et_apellidos.setText(apellidos);
        et_fecha = binding.etPerfilFechanacimiento;
        et_fecha.setText(fecha_nacimiento);
        et_celular = binding.etPerfilCelular;
        et_celular.setText(celular);
        et_semestre = binding.etPerfilSemestre;
        et_semestre.setText(semestre);
        et_carrera = binding.etPerfilCarrera;
        et_carrera.setText(carrera);
        et_password = binding.etPerfilPassword;

        storage = FirebaseStorage.getInstance();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_perfil_cambiar_datos:
                cambiardatos();
                uploadImage();
                break;

            case R.id.btn_perfil_cambiar_foto:
                selectImg();
                break;
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ma = (MainActivity) context;
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
    private void selectImg(){
        getContent.launch("image/*");
    }
    private  void uploadImage(){
        if (imageUri!=null){
            StorageReference storageRef = storage.getReference();
            StorageReference desertRef = storageRef.child("images/"+no_control+".jpg");
            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void aVoid) {
                    StorageReference reference = storage.getReference().child("images/"+no_control+".jpg");
                    reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful() ){
                                MensajeToast("Imagen subida");
                                ma.updateHead();
                            }else{
                                MensajeToast("No se ha podido subir la imagen");
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });
        }
    }

    public void cambiardatos(){
         String nnocontrol = et_nocontrol.getText().toString().replace(" ", "");
         String ncorreo =  et_correo.getText().toString().replace(" ", "");
         String nnombre = et_nombre.getText().toString().replace(" ", "%20");;
         String napellidos= et_apellidos.getText().toString().replace(" ", "%20");;
         String nfecha =et_fecha.getText().toString().replace(" ", "");
         String ncelular =et_celular.getText().toString().replace(" ", "");
         String nsemestre =et_semestre.getText().toString().replace(" ", "");
         String ncarrera= et_carrera.getText().toString().replace(" ", "%20");
         String npassword= et_password.getText().toString().replace(" ", "%20");
         Matcher mat = pattern.matcher(ncorreo);

        if(mat.matches()){
            if(npassword.length()==0){
                npassword=password;
            }
            if(ncorreo == correo){
                ncorreo=correo;
            }
            String urlCommand;
            urlCommand = url + "REQUEST_METHOD=Get&Solicitud=actualizar" +
                    "&password="+ npassword+
                    "&no_control="+ nnocontrol+
                    "&correo="+ ncorreo+
                    "&apellidos="+ napellidos+
                    "&nombre="+ nnombre+
                    "&fecha_nacimiento="+ nfecha+
                    "&celular="+ ncelular+
                    "&semestre="+ nsemestre+
                    "&imagen="+ "imagen"+
                    "&carrera="+ ncarrera;
            //Log.i("Tag", "Solicitud: " + urlCommand.toString());
            sendAndRequestResponse(urlCommand,nnocontrol,ncorreo,nnombre,napellidos,nfecha,ncelular,nsemestre,ncarrera,npassword);
        }else{
            MensajeToast("Correo inválido");
        }

        ma.updateHead();

    }
    public void sendAndRequestResponse(String url,String nnocontrol,String ncorreo,String nnombre,String napellidos,String nfecha,String ncelular,String nsemestre,String ncarrera,String npassword) {
        mRequestQueue = Volley.newRequestQueue(getContext());
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                //Log.i("Tag", "Response: " +response);
                if (response.contains("true")){
                    int pos1 = response.indexOf('{');
                    int pos2 = response.lastIndexOf('}');
                    String jsnres = response.substring(pos1,pos2+1);
                    //Log.i("TAG","jsnres :" + jsnres.toString());
                    MensajeToast("Cambio correcto");
                    et_password.setText("");
                    SharedPreferences preferences=getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    editor.putString("no_control",nnocontrol);
                    editor.putString("password",npassword);
                    editor.putString("nombre",nnombre.replace("%20"," "));
                    editor.putString("apellidos",napellidos.replace("%20"," "));
                    editor.putString("fecha_nacimiento",nfecha);
                    editor.putString("celular",ncelular);
                    editor.putString("semestre",nsemestre);
                    editor.putString("carrera",ncarrera.replace("%20"," "));
                    editor.putString("correo",ncorreo);
                    editor.commit();


                }else {
                    MensajeToast("No se pudieron aplicar los cambios: " );
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
    public void actualizarPreferences(){
    }
    public void MensajeToast(String sms){
        Toast.makeText(getActivity(), sms,Toast.LENGTH_SHORT).show();
    }
}