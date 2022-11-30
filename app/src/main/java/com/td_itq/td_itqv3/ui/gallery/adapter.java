package com.td_itq.td_itqv3.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.td_itq.td_itqv3.R;

import java.util.List;

public class adapter extends RecyclerView.Adapter <adapter.NoticiaViewHolder> {

    private List<noticias> noticiasList;
    public adapter(List<noticias>noticiasList){
            this.noticiasList=noticiasList;
        }
    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,null,false);
        return new NoticiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder holder,int position) {
        holder.textViewtitulo.setText(noticiasList.get(position).getTitulo());
        holder.textViewcontenido.setText(noticiasList.get(position).getContenido());
        holder.textViewfecha.setText(noticiasList.get(position).getFecha());
        holder.textViewautor.setText(noticiasList.get(position).getAutor());
        Picasso.with(holder.imageView.getContext()).load(noticiasList.get(position).getImagen()).into(holder.imageView);
    }
    public int getItemCount(){return noticiasList.size();}

    class NoticiaViewHolder extends RecyclerView.ViewHolder{
        // declaramos los TextView Según nuestra interfaz list
        TextView textViewtitulo, textViewcontenido,textViewautor, textViewfecha ;
        ImageView imageView;
        public NoticiaViewHolder(View itemView) {
            super(itemView);
            textViewtitulo=itemView.findViewById(R.id.txt_titulo);
            textViewcontenido=itemView.findViewById(R.id.txt_contenido);
            textViewautor=itemView.findViewById(R.id.txt_autor);
            textViewfecha=itemView.findViewById(R.id.txt_fecha);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}