package com.example.consultorioapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.consultorioapp.entities.Paciente;

import com.example.consultorioapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {

    private EditText editNome;
    private RadioButton radioButtonMasculino;
    private RadioButton radioButtonFeminino;
    private CheckBox checkBoxPresencial;
    private CheckBox checkBoxOnline;
    private Spinner spinnerHorarios;
    private Button btSalvar;
    private String acao;
    private Paciente paciente;


    //**************FIREBASE ***********************//
    private FirebaseDatabase firebaseDatabase;
    //classe que faz referencia ao banco de dados
    private DatabaseReference reference;
    // classe que aponta para um nó do banco
    private FirebaseAuth auth;
    //classe responsável pela autenticação
    private FirebaseAuth.AuthStateListener authStateListener;
    //Classe responsável por ficar "ouvindo" as mudanças na autenticação
//**************FIREBASE ***********************//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomulario);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        editNome = findViewById(R.id.editNome);
        radioButtonMasculino = findViewById(R.id.radioButtonMasculino);
        radioButtonFeminino = findViewById(R.id.radioButtonFeminino);
        checkBoxPresencial = findViewById(R.id.checkBoxPresencial);
        checkBoxOnline = findViewById(R.id.checkBoxOnline);
        spinnerHorarios = findViewById(R.id.spinnerHorario);
        btSalvar = findViewById(R.id.buttonConfirmar);

        acao = getIntent().getStringExtra("acao");

        if(acao.equals("editar")){
            carregarFormulario();
        }
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        //**************FIREBASE ***********************//
        auth = FirebaseAuth.getInstance();
        //Fica "escutando" as mudanças
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if( auth.getCurrentUser() == null ){
                    finish();
                }
            }
        };
        auth.addAuthStateListener( authStateListener ); //adiciona um ouvinte
        //**************FIREBASE ***********************//
    }



    public void salvar() {

        if(editNome.getText().toString().isEmpty()|| spinnerHorarios.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Você deve preencher ambos os campos!!", Toast.LENGTH_LONG).show();
        }
        if(!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()){
            Toast.makeText(this, "Você deve selecionar o sexo!!", Toast.LENGTH_LONG).show();
        }
        if(!checkBoxPresencial.isChecked() && !checkBoxOnline.isChecked()){
            Toast.makeText(this, "Você deve selecionar a modalidade!!", Toast.LENGTH_LONG).show();
        }
        else{
            if(acao.equals("inserir")){
                paciente = new Paciente();
            }
            paciente.setNome(editNome.getText().toString());
            if(radioButtonMasculino.isChecked()){
                paciente.setSexo(radioButtonMasculino.getText().toString());
            }
            else{
                paciente.setSexo(radioButtonFeminino.getText().toString());
            }
            if(checkBoxPresencial.isChecked()){
                paciente.setModalidade(checkBoxPresencial.getText().toString());
            }
            else{
                paciente.setModalidade(checkBoxOnline.getText().toString());
            }
            paciente.setHorario(spinnerHorarios.getSelectedItem().toString());
            paciente.setIdUsuario(auth.getCurrentUser().getUid());
            if(acao.equals("inserir"))  {
                reference.child("pacientes").push().setValue(paciente);
                editNome.setText("");
                radioButtonMasculino.setText("");
                radioButtonMasculino.setText("");
                checkBoxPresencial.setText("");
                checkBoxOnline.setText("");
                spinnerHorarios.setSelection(0, true);
                Toast.makeText(getApplicationContext(), "Dados inseridos com sucesso", Toast.LENGTH_LONG).show();

            }
            else{
                String idPaciente = paciente.getId();
                paciente.setId(null);
                reference.child("pacientes").child(idPaciente).setValue(paciente);
                finish();
            }
        }
    }

    public void carregarFormulario() {

        String id = getIntent().getStringExtra("idPaciente");
        paciente = new Paciente();
        paciente.setId(id);
        paciente.setId(getIntent().getStringExtra("nome"));
        paciente.setSexo(getIntent().getStringExtra("sexo"));
        paciente.setHorario(getIntent().getStringExtra("horario"));
        paciente.setModalidade(getIntent().getStringExtra("modalidade"));
        editNome.setText(paciente.getNome());
        if(paciente.getSexo().equals(radioButtonMasculino.getText().toString())){
            radioButtonMasculino.setText(paciente.getSexo());
        }else {
            radioButtonFeminino.setText(paciente.getSexo());
        }
        if(paciente.getModalidade().equals(checkBoxPresencial.getText().toString())){
            checkBoxPresencial.setText(paciente.getModalidade());
        }else {
            checkBoxOnline.setText(paciente.getModalidade());
        }
        String[] horarios = getResources().getStringArray(R.array.strHorarios);
        for(int i = 0; i < horarios.length; i++){
            if(paciente.getHorario().equals(horarios[i])){
                spinnerHorarios.setSelection(i);
                break;
            }

        }
    }
}