package com.example.agenda;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.dao.AgendaDAO;
import com.example.agenda.modelo.Agenda;
import java.util.Calendar;

public class AgendaActivity extends AppCompatActivity {
    private CalendarView calendarDia;
    private EditText edtHorario, edtNomeSalao, edtObervacao;
    private String dataFormate;
    private AgendaDAO dao;
    private Agenda agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        calendarDia = findViewById(R.id.cdEscolherDia);
        edtNomeSalao = findViewById(R.id.txtNomeSalao);
        edtHorario = findViewById(R.id.txtHotario);
        edtObervacao = findViewById(R.id.obsercao);

        dao = new AgendaDAO(this);
        agenda = new Agenda();

        calendarDia.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dataFormate = (dayOfMonth) + "/" + month + "/" + year;
            }
        });

    }

    public void salvarAgenda(View view){
        String nome = edtNomeSalao.getText().toString().trim();
        String horario = edtHorario.getText().toString().trim();
        String observacao = edtObervacao.getText().toString().trim();
        boolean validateCampos = validaCampos();

        if(!validateCampos){
            agenda.setNomeSalao(nome);
            agenda.setHorario(horario);
            agenda.setDia(dataFormate);
            agenda.setObservacao(observacao);

            try {
                long validate =  dao.inseir(agenda);

                if(validate >= 1){
                    Toast.makeText(this, "Agendamento salvo com Sucesso!! " + validate , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AgendaActivity.this, HomeAgendaActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Aceonteceu um erro ao Salvar o Agendamento " + validate, Toast.LENGTH_LONG).show();

                }

            }catch (Exception e){
                System.out.print(e);
                Toast.makeText(this, "Erro ao Salvar Agendamento " + e , Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @descricao: Validando os campos
     * @return boolean
     * */
    private boolean validaCampos(){
        String salario = edtNomeSalao.getText().toString();
        String horario = edtHorario.getText().toString();
        String dia = dataFormate;

        boolean res = false;

        if(res = isCampoVazio(salario)){
            edtNomeSalao.requestFocus();
        }
        else
        if(res = isCampoVazio(horario)){
            edtHorario.requestFocus();
        }
        if(res = isCampoVazio(dia)){
            calendarDia.requestFocus();
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

}
