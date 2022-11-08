package com.example.consultorioapp.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.consultorioapp.connections.Banco;
import com.example.consultorioapp.entities.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {


    public static void inserir(Context context, Paciente paciente){
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("sexo", paciente.getSexo());
        values.put("modalidade", paciente.getModalidade());
        values.put("modalidade2", paciente.getModalidade2());
        values.put("horario", paciente.getHorario());

        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.insert("pacientes", null, values);
    }

    public static void editar(Context context, Paciente paciente){
        System.out.println("Entrou no Editar DAO");
        ContentValues values = new ContentValues();
        values.put("nome", paciente.getNome());
        values.put("sexo", paciente.getSexo());
        values.put("modalidade", paciente.getModalidade());
        values.put("modalidade2", paciente.getModalidade2());
        values.put("horario", paciente.getHorario());

        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.update("pacientes", values, "id = " + paciente.getId(), null);
    }

    public static void excluir(Context context, Integer id){
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        sqLiteDatabase.delete("pacientes", "id = " + id, null);
    }

    public static List<Paciente> getPaciente(Context context){
        List<Paciente> list = new ArrayList<>();
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM pacientes ORDER BY nome", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Paciente paciente = new Paciente();
                paciente.setId(cursor.getInt(0));
                paciente.setNome(cursor.getString(1));
                paciente.setSexo(cursor.getString(2));
                paciente.setModalidade(cursor.getString(3));
                paciente.setModalidade2(cursor.getString(4));
                paciente.setHorario(cursor.getString(5));
                list.add(paciente);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public static Paciente getPacienteById(Context context, Integer id){
        Banco conn = new Banco(context);
        SQLiteDatabase sqLiteDatabase = conn.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM pacientes WHERE id = " + id, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Paciente paciente = new Paciente();
            paciente.setId(cursor.getInt(0));
            paciente.setNome(cursor.getString(1));
            paciente.setSexo(cursor.getString(2));
            paciente.setModalidade(cursor.getString(3));
            paciente.setModalidade2(cursor.getString(4));
            paciente.setHorario(cursor.getString(5));
            return paciente;
        }
        else{
            return null;
        }
    }
}
