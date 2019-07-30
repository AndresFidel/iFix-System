package com.example.conexionprueba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder>{
    public static class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView marca,modelo,ano,fecha;
        ImageView imgAuto;

        public ViewHolder(View itemView){
            super(itemView);
            marca=(TextView)itemView.findViewById(R.id.txtMarca);
            modelo=(TextView)itemView.findViewById(R.id.txtModelo);
            ano=(TextView)itemView.findViewById(R.id.txtAno);
            fecha=(TextView)itemView.findViewById(R.id.txtFecha);
            imgAuto=(ImageView)itemView.findViewById(R.id.imgAuto);
        }
    }

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
    }

    @Override
    public int getItemCount(){
        return autoLista.size();
    }


}
