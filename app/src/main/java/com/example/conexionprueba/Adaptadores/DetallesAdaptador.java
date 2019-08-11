package com.example.conexionprueba.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conexionprueba.Constructores.tipoDetalle;
import com.example.conexionprueba.R;

import java.util.List;

public class DetallesAdaptador extends RecyclerView.Adapter<DetallesAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, fecha, costo;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreDetalle);
            fecha = (TextView) itemView.findViewById(R.id.txtFechaDetalle);
            costo = (TextView) itemView.findViewById(R.id.txtCostoDetalle);

        }
    }//Fin clase estatica

    //Variable tipo lista
    public List<tipoDetalle> detalleLista;

    //Constructor del adaptador que contendra la lista creada
    public DetallesAdaptador(List<tipoDetalle> detalleLista) {
        this.detalleLista = detalleLista;
    }

    //Cargando pantalla item_detalle
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //Buscando los objetos de la pantalla item_detalle
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(detalleLista.get(position).getNombreDetalle());
        holder.fecha.setText(detalleLista.get(position).getFechaDetalle());
        holder.costo.setText(detalleLista.get(position).getCostoDetalle());
    }

    //Permite determinar al adaptador la cantidad de elementos
    @Override
    public int getItemCount() {
        return detalleLista.size();
    }
}
