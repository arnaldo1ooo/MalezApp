package com.arnaldo.malezapp.rv_lista;

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

public class rvLista extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<itemLista> listItems; //Aca se cargaran los datos
    private View.OnClickListener listener;


    public rvLista(Context context, ArrayList<itemLista> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.rv_lista, null, false);

        //OnClick
        contentView.setOnClickListener(this);

        return new Holder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        itemLista item = listItems.get(position);
        Holder Holder = (Holder) holder;
        Holder.tvCodigo.setText(item.getCodigo());
        Holder.ivImagen.setImageResource(item.getImagen());
        Holder.tvNombrecomun.setText(item.getNombrecomun());
        Holder.tvNombrecientifico.setText(item.getNombrecientifico());
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

    public static class Holder extends RecyclerView.ViewHolder {
        TextView tvCodigo;
        ImageView ivImagen;
        TextView tvNombrecomun;
        TextView tvNombrecientifico;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tvNombrecomun = itemView.findViewById(R.id.tvNombrecomun);
            tvNombrecientifico = itemView.findViewById(R.id.tvNombrecientifico);
        }
    }
}