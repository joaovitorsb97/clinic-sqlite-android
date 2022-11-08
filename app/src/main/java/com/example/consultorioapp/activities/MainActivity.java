package com.example.consultorioapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultorioapp.entities.Paciente;
import com.example.consultorioapp.connections.PacienteDAO;
import com.example.consultorioapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvPacientes;
    private ArrayAdapter adapter;
    private List<Paciente> listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPacientes = findViewById(R.id.listPacientes);
        carregarPacientes();
        FloatingActionButton fab = findViewById(R.id.fabIncluirPaciente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Fomulario.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer idPaciente = listaPacientes.get(i).getId();
                System.out.println("id::: " + listaPacientes.get(i).getId() + ".........");
                Intent intent = new Intent(MainActivity.this, Fomulario.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idPaciente", idPaciente);
                startActivity(intent);
            }
        });

        lvPacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Entrou clique longo");
                excluir(i);
                return true;
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
            lvPacientes.setEnabled(false);
        }else{
            lvPacientes.setEnabled(true);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listaPacientes);
        lvPacientes.setAdapter(adapter);
    }
}