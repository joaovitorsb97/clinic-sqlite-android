package com.example.consultorioapp.connections;

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
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS pacientes("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "sexo TEXT NOT NULL, " +
                "modalidade TEXT, " +
                "modalidade2 TEXT, " +
                "horario TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
