package com.example.conexionprueba.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.conexionprueba.Constructores.tipoDetalle;
import com.example.conexionprueba.R;

import java.util.List;

public class DetallesAdaptador extends RecyclerView.Adapter<DetallesAdaptador.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre, fecha, costo;

        //Contenido de los detalles
        private TextView txtNoServicio, txtServicio, txtsEntrada, txtsSalida, txtsTerminacion, txtEstado, txtDesc, txtCosto;

        ViewHolder(View itemView) {
            super(itemView);
            //nombre=(TextView)itemView.findViewById(R.id.txtNoServicio);
            //fecha=(TextView)itemView.findViewById(R.id.txtServicio);
            //costo=(TextView)itemView.findViewById(R.id.txtsEntrada);
            txtNoServicio=(TextView)itemView.findViewById(R.id.txtNoServicio);
            txtServicio=(TextView)itemView.findViewById(R.id.txtServicio);
            txtsEntrada=(TextView)itemView.findViewById(R.id.txtsEntrada);
            txtsSalida=(TextView)itemView.findViewById(R.id.txtsSalida);
            txtsTerminacion=(TextView)itemView.findViewById(R.id.txtsTerminacion);
            txtEstado=(TextView)itemView.findViewById(R.id.txtEstado);
            txtDesc=(TextView)itemView.findViewById(R.id.txtDesc);
            txtCosto=(TextView)itemView.findViewById(R.id.txtCosto);
        }
    }//Fin clase estatica

    //Variable tipo lista
    private List<tipoDetalle> detalleLista;

    //Constructor del adaptador que contendra la lista creada
    public DetallesAdaptador(List<tipoDetalle> detalleLista){
        this.detalleLista=detalleLista;
    }

    //Cargando pantalla item_detalle
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    //Buscando los objetos de la pantalla item_detalle
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        //holder.nombre.setText(detalleLista.get(position).getNombreDetalle());
        //holder.fecha.setText(detalleLista.get(position).getFechaDetalle());
        //holder.costo.setText(detalleLista.get(position).getCostoDetalle());
        holder.txtNoServicio.setText(detalleLista.get(position).getNoServicio());
        holder.txtServicio.setText(detalleLista.get(position).getServicio());
        holder.txtsEntrada.setText(detalleLista.get(position).getFechaEntrada());
        holder.txtsSalida.setText(detalleLista.get(position).getFechaSalida());
        holder.txtsTerminacion.setText(detalleLista.get(position).getFechaTerminacion());
        holder.txtEstado.setText(detalleLista.get(position).getEstado());
        holder.txtDesc.setText(detalleLista.get(position).getDesc());
        holder.txtCosto.setText(detalleLista.get(position).getCosto());
    }

    //Permite determinar al adaptador la cantidad de elementos
    @Override
    public int getItemCount(){
        return detalleLista.size();
    }
}
