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

public class CadastroEndereco extends AppCompatActivity {

    /** REPRESENTAÇÃO DOS CAMPOS DA ACTIVITY **/
    private EditText txtCep;
    private EditText txtNumero;
    private EditText txtComplemento;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        /** CAPTURA DOS COMPONENTES GRAFICOS DA ACTIVITY **/
        txtCep = findViewById(R.id.txtNome);
        txtNumero = findViewById(R.id.txtSobrenome);
        txtComplemento = findViewById(R.id.txtEmail);
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
                        String cep = txtCep.getText().toString();
                        String numero = txtNumero.getText().toString();
                        String complemento = txtComplemento.getText().toString();

                        /** DATA DE INSERÇÃO DO USUÁRIO **/
                        DateFormat df = new DateFormat();
                        String created_date = df.getDateFormat();

                        boolean cadastroUsuario = SQLHelper.getInstance(CadastroEndereco.this)
                                .addAddress(cep, numero, complemento, created_date);

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
                !txtCep.getText().toString().isEmpty() &&
                        !txtNumero.getText().toString().isEmpty() &&
                        !txtComplemento.getText().toString().isEmpty()
        );

    }

}//FINAL DA CLASSE