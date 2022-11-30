package com.td_itq.td_itqv3.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.td_itq.td_itqv3.R;
import com.td_itq.td_itqv3.databinding.FragmentGalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private String url = "https://tarjetadigital2022.000webhostapp.com/api.php?";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private FragmentGalleryBinding binding;
    ArrayList<noticias> noticiasList;
    RecyclerView recycler_noticias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View  vista = inflater.inflate(R.layout.fragment_gallery, container, false);
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        noticiasList = new ArrayList<>();
        recycler_noticias=(RecyclerView) vista.findViewById(R.id.recycler_noticias);
        recycler_noticias.setLayoutManager(new LinearLayoutManager(getContext()));

        loadnews();

        return vista;
    }

    private void loadnews() {
        //noticiasList.add(new noticias("Hola","holaaaa", "2022-05-22","Yo","uwu",3));
        String urlCommand;
        urlCommand = url + "REQUEST_METHOD=Get&Solicitud=getNews";
        //Log.i("Tag", "Solicitud: " + urlCommand.toString());
        sendAndRequestResponse(urlCommand);

    }
    public void sendAndRequestResponse(String url) {
        mRequestQueue = Volley.newRequestQueue(getContext());
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                //Log.i("Tag", "Response: " +response);
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++) {
                        JSONObject noticia = array.getJSONObject(i);
                        String titulo = noticia.getString("titulo");
                        String fecha = noticia.getString("fecha");
                        String contenido = noticia.getString("contenido");
                        String autor = noticia.getString("autor");
                        String imagen = noticia.getString("imagen");
                        int id = noticia.getInt("id_noticia");

                        noticiasList.add(new noticias(titulo, contenido, fecha, autor, imagen, id));
                        adapter adapter = new adapter(noticiasList);
                        recycler_noticias.setAdapter(adapter);
                    }

                }catch (JSONException e){
                        e.printStackTrace();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}