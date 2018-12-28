package com.wkprojetos.whatsapp.acitivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wkprojetos.whatsapp.R;
import com.wkprojetos.whatsapp.config.ConfiguracaoFirebase;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail;
    private TextInputEditText campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.etEmailLogin);
        campoEmail = findViewById(R.id.etSenhaLogin);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    private void logarUsuario(View view){
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if (!textoEmail.isEmpty() || !textoSenha.isEmpty()){
            autenticacao.signInWithEmailAndPassword(textoEmail, textoSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        abrirTelaPrincipal();
                    }else{
                        Toast.makeText(LoginActivity.this, R.string.erro_autenticao_usuario, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, R.string.informe_email_senha, Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirTelaCadastro(View view){
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

    private void abrirTelaPrincipal(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
