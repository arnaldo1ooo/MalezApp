package com.arnaldo.malezapp.lista;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListaAdaptador extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<ListaItem> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore


    public ListaAdaptador(Context context, ArrayList<ListaItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.lista_adaptador, null, false);

        //OnClick
        vista.setOnClickListener(this);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rvviewHolder, int position) {
        ListaItem item = listItems.get(position);
        ViewHolder viewHolder = (ViewHolder) rvviewHolder;
        viewHolder.tvCodigo.setText(item.getCodigo());

        //viewHolder.ivImagen.setImageResource(item.getImagen());

        String rutaImagen = "imagenes_malezas/imagen_" + item.getCodigo() + "a.jpg";
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
            public void onFailure(@NonNull Exception exception) {
                System.out.println("Fallo");
            }
        });

        viewHolder.tvNombrecomun.setText(item.getNombrecomun());
        viewHolder.tvNombrecientifico.setText(item.getNombrecientifico());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    //Onclick
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo;
        ImageView ivImagen;
        TextView tvNombrecomun;
        TextView tvNombrecientifico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            ivImagen = itemView.findViewById(R.id.ivImagen1);
            tvNombrecomun = itemView.findViewById(R.id.tvTituloListaTipoHoja);
            tvNombrecientifico = itemView.findViewById(R.id.tvDetalleTIpoHoja);
        }
    }
}
