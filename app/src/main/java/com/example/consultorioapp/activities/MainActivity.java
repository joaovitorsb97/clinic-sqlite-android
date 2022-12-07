package com.example.consultorioapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.consultorioapp.adapters.MyAdapter;
import com.example.consultorioapp.entities.Paciente;
import com.example.consultorioapp.R;
import com.example.consultorioapp.utils.recyclerview.RecyclerViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    private RecyclerView rvPacientes;
    private List<Paciente> listaPacientes;
    private MyAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fabIncluirPaciente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FomularioActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        rvPacientes = (RecyclerView) findViewById(R.id.rvPacientes);
        rvPacientes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(listaPacientes, this);
        listaPacientes = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser() == null){
                    finish();
                }
            }
        };
        auth.addAuthStateListener(authStateListener);
    }


    public void excluir(int position) {
        Paciente pacienteSelecionado = listaPacientes.get(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Excluir...");
        alert.setIcon(android.R.drawable.ic_delete);
        alert.setMessage("Confirma excluir o paciente " + pacienteSelecionado.getNome() + "?");
        alert.setNeutralButton("Cancelar", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reference.child("pacientes").child(pacienteSelecionado.getId()).removeValue();
            }
        });
        alert.show();
    }

    @Override
    public void OnItemCLick(int position) {
        Intent intent = new Intent(MainActivity.this, FomularioActivity.class);
        intent.putExtra("acao", "editar");
        Paciente pacienteSelecionado = listaPacientes.get(position);
        intent.putExtra("idPaciente", pacienteSelecionado.getId());
        intent.putExtra("nome", pacienteSelecionado.getNome());
        intent.putExtra("sexo", pacienteSelecionado.getSexo());
        intent.putExtra("modalidade", pacienteSelecionado.getModalidade());
        intent.putExtra("horario", pacienteSelecionado.getHorario());
        startActivity(intent);
    }

    @Override
    public boolean OnItemLongClick(int position) {
        System.out.println("Entrou clique longo");
        excluir(position);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        listaPacientes.clear();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        query = reference.child("pacientes").orderByChild("nome");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try{
                    String idUsuarioPaciente = snapshot.child("idUsuario").getValue(String.class);
                    if(idUsuarioPaciente.equals(auth.getCurrentUser().getUid())){
                        Paciente paciente = new Paciente();
                        paciente.setId(snapshot.getKey());
                        paciente.setNome(snapshot.child("nome").getValue(String.class));
                        paciente.setSexo(snapshot.child("sexo").getValue(String.class));
                        paciente.setModalidade(snapshot.child("modalidade").getValue(String.class));
                        paciente.setHorario(snapshot.child("horario").getValue(String.class));
                        paciente.setIdUsuario(idUsuarioPaciente);

                        listaPacientes.add(paciente);

                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (Paciente p : listaPacientes){
                    if(p.getId().equals(snapshot.getKey())){
                        p.setNome(snapshot.child("nome").getValue(String.class));
                        p.setSexo(snapshot.child("sexo").getValue(String.class));
                        p.setModalidade(snapshot.child("modalidade").getValue(String.class));
                        p.setHorario(snapshot.child("horario").getValue(String.class));

                        adapter.notifyDataSetChanged();;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                for (Paciente p : listaPacientes){
                    if (p.getId().equals(snapshot.getKey())){
                        listaPacientes.remove(p);
                        adapter.notifyDataSetChanged();                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addChildEventListener(childEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(childEventListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarLista();
    }

    private void carregarLista() {
        if(listaPacientes.size() == 0){
            Paciente fake = new Paciente("Lista vazia", "", "", "", "");
            listaPacientes.add(fake);
            rvPacientes.setEnabled(false);
        }else{
                rvPacientes.setEnabled(true);
        }
    }
}