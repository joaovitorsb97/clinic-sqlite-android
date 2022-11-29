package com.example.consultorioapp.activities;

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
import com.example.consultorioapp.daos.PacienteDAO;
import com.example.consultorioapp.R;

public class FomularioActivity extends AppCompatActivity {

    private EditText editNome;
    private RadioButton radioButtonMasculino;
    private RadioButton radioButtonFeminino;
    private CheckBox checkBoxPresencial;
    private CheckBox checkBoxOnline;
    private Spinner spinnerHorarios;
    private Button btSalvar;
    private String acao;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fomulario);

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
            if(checkBoxOnline.isChecked()){
                paciente.setModalidade2(checkBoxOnline.getText().toString());
            }
            paciente.setHorario(spinnerHorarios.getSelectedItem().toString());
            if(acao.equals("inserir"))  {
                PacienteDAO.inserir(this, paciente);
                editNome.setText("");
                radioButtonMasculino.setText("");
                radioButtonMasculino.setText("");
                checkBoxPresencial.setText("");
                checkBoxOnline.setText("");
                spinnerHorarios.setSelection(0, true);
                Toast.makeText(getApplicationContext(), "Dados inseridos com sucesso", Toast.LENGTH_LONG).show();

            }
            else{
                PacienteDAO.editar(this, paciente);
                Toast.makeText(getApplicationContext(), "Dados editados com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void carregarFormulario() {

        Integer id = getIntent().getIntExtra("idPaciente", 0);
        paciente = PacienteDAO.getPacienteById(this, id);
        editNome.setText(paciente.getNome());
        if(paciente.getSexo().equals(radioButtonMasculino.getText().toString())){
            radioButtonMasculino.setText(paciente.getSexo());
        }else {
            radioButtonFeminino.setText(paciente.getSexo());
        }
        checkBoxPresencial.setText(paciente.getModalidade());
        checkBoxOnline.setText(paciente.getModalidade2());
        String[] horarios = getResources().getStringArray(R.array.strHorarios);
        for(int i = 0; i < horarios.length; i++){
            if(paciente.getHorario().equals(horarios[i])){
                spinnerHorarios.setSelection(i);
                break;
            }

        }
    }
}