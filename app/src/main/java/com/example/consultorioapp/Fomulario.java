package com.example.consultorioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Fomulario extends AppCompatActivity {

    private EditText editNome;
    private EditText editNascimento;
    private EditText editTelefone;
    private Spinner spinnerHorarios;
    private Button btSalvar;
    private String acao;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomulario);

        editNome = findViewById(R.id.editNome);
        editNascimento = findViewById(R.id.editNascimento);
        editTelefone = findViewById(R.id.editTelefone);
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

    }

    public void salvar() {
        String nome = editNome.getText().toString();
        String nascimento = editNascimento.getText().toString();
        String telefone = editTelefone.getText().toString();
        if(nome.isEmpty() || nascimento.isEmpty() || telefone.isEmpty() || spinnerHorarios.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Você deve preencher ambos os campos!!", Toast.LENGTH_LONG).show();
        }else{
            if(acao.equals("inserir")){
                paciente = new Paciente();
            }
            paciente.setNome(nome);
            paciente.setNascimento(nascimento);
            paciente.setTelefone(telefone);
            paciente.setHorario(spinnerHorarios.getSelectedItem().toString());
            if(acao.equals("inserir")){
                PacienteDAO.inserir(this, paciente);
                editNome.setText("");
                editNascimento.setText("");
                editTelefone.setText("");
                spinnerHorarios.setSelection(0, true);
            }
            else{
                PacienteDAO.editar(this, paciente);
                finish();
            }
        }
    }

    public void carregarFormulario() {

        Long id = getIntent().getLongExtra("idPaciente", 0);
        paciente = PacienteDAO.getPacienteById(this, id);
        editNome.setText(paciente.getNome());
        editNascimento.setText(paciente.getNascimento());
        editTelefone.setText(paciente.getTelefone());
        String[] horarios = getResources().getStringArray(R.array.strHorarios);
        for(int i = 0; i < horarios.length; i++){
            if(paciente.getHorario().equals(horarios[i])){
                spinnerHorarios.setSelection(i);
                break;
            }

        }
    }
}