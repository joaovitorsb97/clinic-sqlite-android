package com.example.consultorioapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.consultorioapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEmail;
    private TextInputEditText textInputSenha;
    private Button btEntrar;
    private Button btCadastrar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputSenha = findViewById(R.id.textInputSenha);
        btEntrar = findViewById(R.id.btEntrar);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar();
            }
        });

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                usuario = auth.getCurrentUser();
                if(usuario != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        auth.addAuthStateListener(authStateListener);
    }

    private void entrar() {
        String email = textInputEmail.getText().toString();
        String senha = textInputSenha.getText().toString();
        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        usuario = auth.getCurrentUser();
                    }
                    else{
                        task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Erro ao logar", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void cadastrar() {
        String email = textInputEmail.getText().toString();
        String senha = textInputSenha.getText().toString();
        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        usuario = auth.getCurrentUser();
                    }
                    else{
                        task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Erro ao criar usuário", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}