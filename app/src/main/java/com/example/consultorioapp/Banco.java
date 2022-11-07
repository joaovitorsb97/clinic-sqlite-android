package com.example.consultorioapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "AppPersist";

    public Banco(@Nullable Context context){
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS paciente("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "nascimento text NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "horario TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
