package com.example.consultorioapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consultorioapp.R;
import com.example.consultorioapp.entities.Paciente;
import com.example.consultorioapp.utils.recyclerview.RecyclerViewInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements RecyclerViewInterface {

    private RecyclerViewInterface recyclerViewInterface;
    private Context context;
    List<Paciente> listPaciente = new ArrayList<>();

    public MyAdapter(List<Paciente> listPaciente, Context context) {
        this.listPaciente = listPaciente;
        this.context = context;
    }

    public MyAdapter(List<Paciente> listPaciente, RecyclerViewInterface recyclerViewInterface) {
        this.listPaciente = listPaciente;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(listPaciente.get(position).getNome());
        holder.sexo.setText(listPaciente.get(position).getSexo());
        holder.modalidade.setText(listPaciente.get(position).getModalidade());
        holder.horario.setText(listPaciente.get(position).getHorario());
    }

    @Override
    public int getItemCount() {
        return listPaciente.size();
    }

    @Override
    public void OnItemCLick(int position) {

    }

    @Override
    public boolean OnItemLongClick(int position) {
        return false;
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
         TextView nome;
         TextView sexo;
         TextView modalidade;
         TextView horario;



        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.displayName);
            sexo = (TextView) itemView.findViewById(R.id.displaySex);
            modalidade = (TextView) itemView.findViewById(R.id.displayModality);
            horario = (TextView) itemView.findViewById(R.id.displayHours);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnItemCLick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        return recyclerViewInterface.OnItemLongClick(pos);
                    }
                    return false;
                }
            });



        }
    }

}
