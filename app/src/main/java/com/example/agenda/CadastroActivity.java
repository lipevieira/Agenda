package com.example.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda.dao.ClienteDAO;
import com.example.agenda.modelo.Cliente;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editCpf;
    private EditText editEndereco;
    private EditText editTelefone;
    private EditText editEmail;
    private EditText editSenha;
    private Button salvar;
    private ClienteDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.txtNome);
        editCpf = findViewById(R.id.txtCpf);
        editEndereco = findViewById(R.id.txtEndereco);
        editTelefone = findViewById(R.id.txtTelefone);
        editEmail = findViewById(R.id.txtEmail);
        editSenha = findViewById(R.id.txtSenha);
        salvar = findViewById(R.id.btnCadastrar);
        dao = new ClienteDAO(this);

    }

    public void salvar(View view) {
        Cliente c = new Cliente();

        String nome = editNome.getText().toString();
        String cpf = editCpf.getText().toString();
        String endereco = editEndereco.getText().toString();
        String telefone = editTelefone.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        boolean validateCampos = validaCampos();

        if (!validateCampos) {
            c.setNome(nome);
            c.setCpf(cpf);
            c.setEndereco(endereco);
            c.setTelefone(telefone);
            c.setEmail(email);
            c.setSenha(senha);

            try {
                // Salvando um cliente no Banco de dados
                dao.inseir(c);
                Toast.makeText(this, "Cliente Salvo com sucesso", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Erron ao Salvar Cliente " + e, Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * @descricao: Validando os campos
     * @return boolean
     * */
    private boolean validaCampos(){
        String nome = editNome.getText().toString();
        String cpf = editCpf.getText().toString();
        String endereco = editEndereco.getText().toString();
        String telefone = editTelefone.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        boolean res = false;

        if(res = isCampoVazio(nome)){
            editNome.requestFocus();
        }
        else
        if(res = isCampoVazio(cpf)){
            editCpf.requestFocus();
        }
        else
        if(res =  isCampoVazio(endereco)){
            editEndereco.requestFocus();
        }
        else
        if(res =  isCampoVazio(telefone)){
            editTelefone.requestFocus();
        }
        else
        if(res = !isEmailValido(email)){
            editEmail.requestFocus();
        }
        else
        if(res = isCampoVazio(senha)){
            editSenha.requestFocus();
        }

        // Se existe algum campo vazio então vai mostrar uma MSG com aviso
        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso!");
            dlg.setMessage("Há campos inválidos ou em branco");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        return res;
    }

    /**
     * @descricao: Criando as regras de validação
     * de campos vazios
     * @return boolean
     * @param:string
     * */
    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());

        return resultado;
    }

    private boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        return resultado;
    }

}