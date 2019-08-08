package com.example.conexionprueba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder>{


    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        Context context; //Contexto
        private TextView marca,modelo,ano,fecha;
        ImageView imgAuto;
        Button btnVer;


        public ViewHolder(View itemView){
            super(itemView);
            context=itemView.getContext();
            marca=(TextView)itemView.findViewById(R.id.txtMarca);
            modelo=(TextView)itemView.findViewById(R.id.txtModelo);
            ano=(TextView)itemView.findViewById(R.id.txtAno);
            fecha=(TextView)itemView.findViewById(R.id.txtFecha);
            imgAuto=(ImageView)itemView.findViewById(R.id.imgAuto);
            btnVer=(Button)itemView.findViewById(R.id.btnVer);
        }

        void setOnClickListeners(){
            btnVer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.btnVer:
                    Intent intent = new Intent(context, verDetalles.class);
                    context.startActivity(intent);
                    break;
            }
        }//Fin de los eventos

    } //Fin de Static Class

    public List<tipoAuto> autoLista;

    public RecyclerViewAdaptador(List<tipoAuto> autoLista) {
        this.autoLista = autoLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.marca.setText(autoLista.get(position).getMarca());
        holder.modelo.setText(autoLista.get(position).getModelo());
        holder.ano.setText(autoLista.get(position).getAno());
        holder.fecha.setText(autoLista.get(position).getFecha());
        holder.imgAuto.setImageResource(autoLista.get(position).getImgAuto());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount(){
        return autoLista.size();
    }

}
