package com.example.symbian_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;
import helpers.DateFormat;

public class CadastroUsuario extends AppCompatActivity {

    /** REPRESENTAÇÃO DOS CAMPOS DA ACTIVITY **/
    private EditText txtNome;
    private EditText txtSobreNome;
    private EditText txtEmail;
    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        /** CAPTURA DOS COMPONENTES GRAFICOS DA ACTIVITY **/
        txtNome = findViewById(R.id.txtNome);
        txtSobreNome = findViewById(R.id.txtSobrenome);
        txtLogin = findViewById(R.id.txtLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);

        /** TRATAMENTO DO EVENTO DE CLIQUE DO BOTÃO **/
        btnCadastrarUsuario.setOnClickListener( view->{

            if(!validate()){

                Toast.makeText(this, "PREENCHA TODOS OS CAMPOS!", Toast.LENGTH_SHORT).show();
                return;
            }

            /** PROCESSO DE GRAVAÇÃO DE USUÁRIO **/
            AlertDialog dialog = new AlertDialog.Builder(this)

                    .setTitle(getString(R.string.titulo_cadastro_usuario))
                    .setMessage(getString(R.string.mensagem_cadastro_usuario))
                    .setPositiveButton(R.string.salvar, (dialog1, which)->{
                        /** AÇÃO DO POSITIVE BUTTON **/
                        String nome = txtNome.getText().toString();
                        String sobreNome = txtSobreNome.getText().toString();
                        String email = txtEmail.getText().toString();
                        String login = txtLogin.getText().toString();
                        String senha = txtSenha.getText().toString();

                        /** DATA DE INSERÇÃO DO USUÁRIO **/
                        DateFormat df = new DateFormat();
                        String created_date = df.getDateFormat();

                        boolean cadastroUsuario = SQLHelper.getInstance(CadastroUsuario.this)
                                .addUser(nome, sobreNome, email, login, senha,
                                        created_date);

                        if(cadastroUsuario){

                            Toast.makeText(this,
                                    "CADASTRO REALIZADO COM SUCESSO!",
                                    Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(this,
                                    "HOUVE UM ERRO AO REALIZAR O CADASTRO DE USUÁRIO!",
                                    Toast.LENGTH_LONG).show();

                        }

                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, which)->{}).create();

            dialog.show();

        });

    }//FINAL DO MÉTODO ONCREATE

    /** MÉTODO DE VALIDAÇÃO **/
    private boolean validate(){

        return(
                !txtNome.getText().toString().isEmpty() &&
                        !txtSobreNome.getText().toString().isEmpty() &&
                        !txtEmail.getText().toString().isEmpty() &&
                        !txtLogin.getText().toString().isEmpty() &&
                        !txtSenha.getText().toString().isEmpty()
        );

    }

}//FINAL DA CLASSE