package com.arnaldo.malezapp.recyclerViewGrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorRVG extends RecyclerView.Adapter<AdaptadorRVG.ViewHolder> implements View.OnClickListener {
    private ArrayList<ItemRVG> listaRVG;
    private Context context;
    private View.OnClickListener listener;



    public AdaptadorRVG(Context context, ArrayList<ItemRVG> listaRVG) {
        this.listaRVG = listaRVG;
        this.context = context;
    }

    @Override
    public AdaptadorRVG.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adaptador_rvg, viewGroup, false);

        //OnClick
        contentView.setOnClickListener(this);

        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(AdaptadorRVG.ViewHolder viewHolder, int i) {
        viewHolder.tvNombre.setText(listaRVG.get(i).getNombre());
        Picasso.get()
                .load(listaRVG.get(i).getImagenLocal()) //.load(android.get(i).getImagenURL()) Para online
                .resize(240, 160) //Tamaño de las imagenes
                .into(viewHolder.ivImagen);
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