package com.example.consultorioapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public static void inserir(Context context, Paciente paciente){
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("nascimento", paciente.getNascimento());
        values.put("telefone", paciente.getTelefone());
        values.put("horario", paciente.getHorario());

        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.insert("paciente", null, values);
    }

    public static void editar(Context context, Paciente paciente){
        System.out.println("Entrou no Editar DAO");
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("nascimento", paciente.getNascimento());
        values.put("telefone", paciente.getTelefone());
        values.put("horario", paciente.getHorario());

        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.update("paciente", values, "id = " + paciente.getId(), null);
    }

    public static void excluir(Context context, Long id){
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.delete("paciente", "id = " + id, null);
    }

    public static List<Paciente> getPaciente(Context context){
        List<Paciente> list = new ArrayList<>();
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM paciente ORDER BY nome", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Paciente paciente = new Paciente();
                paciente.setId(cursor.getLong(0));
                paciente.setNome(cursor.getString(1));
                paciente.setNascimento(cursor.getString(2));
                paciente.setTelefone(cursor.getString(3));
                paciente.setHorario(cursor.getString(4));
                list.add(paciente);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public static Paciente getPacienteById(Context context, Long id){
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM paciente WHERE id = " + id, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Paciente paciente = new Paciente();
            paciente.setId(cursor.getLong(0));
            paciente.setNome(cursor.getString(1));
            paciente.setNascimento(cursor.getString(2));
            paciente.setTelefone(cursor.getString(3));
            paciente.setHorario(cursor.getString(4));
            return paciente;
        }
        else{
            return null;
        }
    }
}
