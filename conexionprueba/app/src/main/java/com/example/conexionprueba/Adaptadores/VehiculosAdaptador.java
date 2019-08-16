package com.example.conexionprueba.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.conexionprueba.R;
import com.example.conexionprueba.Constructores.tipoAuto;
import com.example.conexionprueba.controllers.mostrarDetalles;

import java.util.List;

public class VehiculosAdaptador extends RecyclerView.Adapter<VehiculosAdaptador.ViewHolder>{


    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        Context context; //Contexto
        private TextView noAuto,marca,modelo,ano;
        ImageView imgAuto;
        Button btnVer;


        ViewHolder(View itemView){
            super(itemView);
            context=itemView.getContext();
            noAuto=(TextView)itemView.findViewById(R.id.txtNoAuto);
            marca=(TextView)itemView.findViewById(R.id.txtMarca);
            modelo=(TextView)itemView.findViewById(R.id.txtModelo);
            ano=(TextView)itemView.findViewById(R.id.txtAno);
            imgAuto=(ImageView)itemView.findViewById(R.id.imgAuto);
            btnVer=(Button)itemView.findViewById(R.id.btnVer);
        }

        void setOnClickListeners(){
            btnVer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (view.getId() == R.id.btnVer) {
                Intent intent = new Intent(context, mostrarDetalles.class);
                intent.putExtra("id_auto", noAuto.getText());
                context.startActivity(intent);
            }
        }//Fin de los eventos

    } //Fin de Static Class

    private List<tipoAuto> autoLista;

    public VehiculosAdaptador(List<tipoAuto> autoLista) {
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
        holder.noAuto.setText(autoLista.get(position).getNoAuto());
        holder.marca.setText(autoLista.get(position).getMarca());
        holder.modelo.setText(autoLista.get(position).getModelo());
        holder.ano.setText(autoLista.get(position).getAno());
        holder.imgAuto.setImageResource(autoLista.get(position).getImgAuto());

        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount(){
        return autoLista.size();
    }

}
