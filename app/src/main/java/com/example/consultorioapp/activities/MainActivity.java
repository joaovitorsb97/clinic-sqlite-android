package com.example.consultorioapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultorioapp.adapters.MyAdapter;
import com.example.consultorioapp.entities.Paciente;
import com.example.consultorioapp.daos.PacienteDAO;
import com.example.consultorioapp.R;
import com.example.consultorioapp.utils.recyclerview.RecyclerViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView rvPacientes;
    private List<Paciente> listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPacientes = (RecyclerView) findViewById(R.id.rvPacientes);
        rvPacientes.setLayoutManager(new LinearLayoutManager(this));
        carregarPacientes();
        FloatingActionButton fab = findViewById(R.id.fabIncluirPaciente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FomularioActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });
    }

    public void excluir(int i) {
        Paciente paciente = listaPacientes.get(i);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Excluir...");
        alert.setIcon(android.R.drawable.ic_delete);
        alert.setMessage("Confirma excluir o paciente " + paciente.getNome() + "?");
        alert.setNeutralButton("Cancelar", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PacienteDAO.excluir(MainActivity.this, paciente.getId());
                carregarPacientes();
            }
        });
        alert.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarPacientes();
    }

    public void carregarPacientes() {
        listaPacientes = PacienteDAO.getPaciente(this);
        if(listaPacientes.size() == 0){
            Paciente obj = new Paciente("Lista vazia...", "", "", "", "");
            listaPacientes.add(obj);
            rvPacientes.setEnabled(false);
        }else{
            rvPacientes.setEnabled(true);
        }
        MyAdapter adapter = new MyAdapter(listaPacientes, this);
        rvPacientes.setAdapter(adapter);
    }

    @Override
    public void OnItemCLick(int position) {
        Integer idPaciente = listaPacientes.get(position).getId();
        System.out.println("id::: " + listaPacientes.get(position).getId() + ".........");
        Intent intent = new Intent(MainActivity.this, FomularioActivity.class);
        intent.putExtra("acao", "editar");
        intent.putExtra("idPaciente", idPaciente);
        startActivity(intent);
    }

    @Override
    public boolean OnItemLongClick(int position) {
        System.out.println("Entrou clique longo");
        excluir(position);
        return true;
    }
}