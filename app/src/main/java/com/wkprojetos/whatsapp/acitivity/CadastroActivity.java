package com.wkprojetos.whatsapp.acitivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.wkprojetos.whatsapp.R;
import com.wkprojetos.whatsapp.config.ConfiguracaoFirebase;
import com.wkprojetos.whatsapp.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.etNome);
        campoEmail = findViewById(R.id.etEmail);
        campoSenha = findViewById(R.id.etSenha);

    }

    public void validarCadastroUsuario(View view) {
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoEmail.getText().toString();

        if (!textoNome.isEmpty() || !textoEmail.isEmpty() || !textoSenha.isEmpty()){
            Usuario usuario = new Usuario();
            usuario.setNome(textoNome);
            usuario.setEmail(textoEmail);
            usuario.setSenha(textoSenha);
            cadastrarUsuario(usuario);
         }else{
            Toast.makeText(CadastroActivity.this, R.string.informe_todos_campos, Toast.LENGTH_SHORT).show();
        }

    }

    private void cadastrarUsuario(Usuario usuario){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword( usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, R.string.sucesso_cadastrar_usuario, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    StringBuilder excecao = null;
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao.append(R.string.informe_senha_mais_forte);
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao.append(R.string.informe_email_valido);
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao.append(R.string.email_ja_cadastrado);
                    }catch (Exception e) {
                        excecao.append(R.string.erro_cadastro).append(e.getMessage());
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
