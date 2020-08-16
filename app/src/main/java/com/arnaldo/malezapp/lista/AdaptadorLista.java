package com.arnaldo.malezapp.lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.malezapp.R;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<ItemLista> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;


    public AdaptadorLista(Context context, ArrayList<ItemLista> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View contenidoVista = LayoutInflater.from(context).inflate(R.layout.adaptador_lista, null, false);

        //OnClick
        contenidoVista.setOnClickListener(this);

        return new ViewHolder(contenidoVista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rvviewHolder, int position) {
        ItemLista item = listItems.get(position);
        ViewHolder viewHolder = (ViewHolder) rvviewHolder;
        viewHolder.tvCodigo.setText(item.getCodigo());
        viewHolder.ivImagen.setImageResource(item.getImagen());
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
            tvNombrecomun = itemView.findViewById(R.id.tvNombrecomun);
            tvNombrecientifico = itemView.findViewById(R.id.tvNombrecientifico);
        }
    }
}
