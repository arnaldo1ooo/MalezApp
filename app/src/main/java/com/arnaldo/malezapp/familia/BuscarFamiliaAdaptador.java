package com.arnaldo.malezapp.familia;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuscarFamiliaAdaptador extends RecyclerView.Adapter<BuscarFamiliaAdaptador.ViewHolder> implements View.OnClickListener {
    private ArrayList<BuscarFamiliaItem> listaRVG;
    private Context context;
    private View.OnClickListener listener;



    public BuscarFamiliaAdaptador(Context context, ArrayList<BuscarFamiliaItem> listaRVG) {
        this.listaRVG = listaRVG;
        this.context = context;
    }

    @Override
    public BuscarFamiliaAdaptador.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buscar_familia_adaptador, viewGroup, false);

        //OnClick
        contentView.setOnClickListener(this);

        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(BuscarFamiliaAdaptador.ViewHolder viewHolder, int i) {
        viewHolder.tvNombre.setText(listaRVG.get(i).getNombre());

        if (listaRVG.get(i).getImagenLocal() !=0){ //Cargar lista de imagenes local si no esta vacio
            Picasso.get()
                    .load(listaRVG.get(i).getImagenLocal()) //.load(android.get(i).getImagenURL()) Para online
                    .resize(240, 160) //Tamaño de las imagenes
                    .into(viewHolder.ivImagen);
        }else{
            if (listaRVG.get(i).getImagenURL().equals(null) == false){//Cargar lista de imagenes onlnie de firestore si no esta vacio
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore
                //Obtener imagen1 desde internet
                String rutaImagen = "familias/"+listaRVG.get(i).getImagenURL();
                storageReference.child(rutaImagen).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri laUri) {
                        System.out.println("Suceso " + rutaImagen);
                        Picasso.get() //Cargar desde internet
                                .load(laUri) //Link de la imagen
                                .placeholder(R.drawable.cargando) //La imagen que aparecera mientras se carga la imagen del link
                                .error(R.drawable.imagen_0) //La imagen que aparecera en caso de error
                                .into(viewHolder.ivImagen); //El ImageView que recibira la imagen
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        System.out.println("Fallo " + rutaImagen);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaRVG.size();
    }


    //Onclick
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private ImageView ivImagen;

        public ViewHolder(View view) {
            super(view);
            tvNombre = (TextView) view.findViewById(R.id.tvNombreRVG);
            ivImagen = (ImageView) view.findViewById(R.id.ivImagenRVG);
        }
    }
}