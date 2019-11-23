package com.example.agenda.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String nome = "banco2.db";
    private static final int versao = 1;

    public Conexao(@Nullable Context context) {
        super(context,nome,null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists cliente(id integer primary key autoincrement, " +
                "nome varchar(100) not null," +
                "cpf varchar (15) not null," +
                "endereco varchar(100) not null," +
                "telefone varchar(100) not null," +
                "email varchar(100) not null," +
                "senha varchar(100) not null);");

        // Criando a tabela de agenda
        db.execSQL("create table if not exists agenda (id integer primary key autoincrement, " +
                "nome_salao varchar(100) not null," +
                " horario varchar(10) not null," +
                " dia varchar(20) not null, observacao text  );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table if not exists cliente(id integer primary key autoincrement, " +
                "nome varchar(100) not null," +
                "cpf varchar (15) not null," +
                "endereco varchar(100) not null," +
                "telefone varchar(100) not null," +
                "email varchar(100) not null," +
                "senha varchar(100) not null);");


        db.execSQL("create table if not exists agenda (id integer primary key autoincrement, " +
                "nome_salao varchar(100) not null," +
                " horario varchar(10) not null," +
                " dia varchar(20) not null, observacao text  );");
    }


}