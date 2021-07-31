package com.arnaldo.malezapp.tipohoja;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorBuscarTipoHoja extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<ItemBuscarTipoHoja> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference(); //Abrir conexion con storage de firestore


    public AdaptadorBuscarTipoHoja(Context context, ArrayList<ItemBuscarTipoHoja> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.adaptador_buscar_tipohoja, null, false);
        //OnClick
        vista.setOnClickListener(this);

        return new ViewHolder(vista);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rvviewHolder, int position) {
        ItemBuscarTipoHoja item = listItems.get(position);
        ViewHolder viewHolder = (ViewHolder) rvviewHolder;
        viewHolder.tvCodigo.setText(item.getIdtipohoja());
        viewHolder.tvTituloListaTipoHoja.setText(item.getTituloTipoHoja());
        viewHolder.tvDetalleTipoHoja.setText(item.getDetalleTipoHoja());

        String rutaImagen = "tipos_hojas/imagen_" + item.getIdtipohoja() + ".jpg";
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


        //Al dar click en imagen
        viewHolder.ivImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                ImagenPopupPicasso(vista, viewHolder.ivImagen); //Abre la vista previa de la imagen clickeada
            }
        });
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
        TextView tvTituloListaTipoHoja;
        TextView tvDetalleTipoHoja;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            ivImagen = itemView.findViewById(R.id.ivImagen1);
            tvTituloListaTipoHoja = itemView.findViewById(R.id.tvTituloListaTipoHoja);
            tvDetalleTipoHoja = itemView.findViewById(R.id.tvDetalleTIpoHoja);
        }
    }

    public void ImagenPopupPicasso(View view, ImageView laImagen) {
        final ImagePopup imagePopup = new ImagePopup(view.getContext());
        imagePopup.setWindowHeight(900); // Optional
        imagePopup.setWindowWidth(900); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Color del fondo
        imagePopup.setFullScreen(false); // Pantalla completa
        imagePopup.setHideCloseIcon(true);  // Ocultar Boton cerrar
        imagePopup.setImageOnClickClose(false);  //Cerrar al tocar imagen
        imagePopup.initiatePopup(laImagen.getDrawable()); // Cargar la imagen
        imagePopup.viewPopup(); //Iniciar Popup
    }
}
