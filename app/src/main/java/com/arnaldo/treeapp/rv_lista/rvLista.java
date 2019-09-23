package com.arnaldo.treeapp.rv_lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arnaldo.treeapp.R;

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
        Holder.ivImagen.setImageResource(item.getImagen());
        Holder.tv_identificador.setText(item.getIdentificador());
        Holder.tv_nombrecomun.setText(item.getNombrecomun());
        Holder.tv_nombrecientifico.setText(item.getNombrecientifico());
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
        ImageView ivImagen;
        TextView tv_identificador;
        TextView tv_nombrecomun;
        TextView tv_nombrecientifico;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.ivImagen);
            tv_identificador = itemView.findViewById(R.id.tv_identificador2);
            tv_nombrecomun = itemView.findViewById(R.id.tv_nombrecomun2);
            tv_nombrecientifico = itemView.findViewById(R.id.tv_nombrecientifico2);
        }
    }
}
