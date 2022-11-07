package com.example.consultorioapp;

import java.io.Serializable;
import java.util.Objects;

public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String nascimento;
    private String telefone;
    private String horario;

    public Paciente() {
    }

    public Paciente(String nome, String nascimento, String telefone, String horario) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.horario = horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return id.equals(paciente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
