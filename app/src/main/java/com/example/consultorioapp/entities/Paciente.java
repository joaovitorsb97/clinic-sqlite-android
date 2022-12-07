package com.example.consultorioapp.entities;

import java.io.Serializable;
import java.util.Objects;

public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String idUsuario;
    private String nome;
    private String sexo;
    private String modalidade;
    private String modalidade2;
    private String horario;

    public Paciente() {
    }

    public Paciente(String id, String nome, String sexo, String modalidade, String horario) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.modalidade = modalidade;
        this.horario = horario;
    }

    public Paciente(String id, String nome, String sexo, String modalidade, String modalidade2, String horario, String idUsuario) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.modalidade = modalidade;
        this.modalidade2 = modalidade2;
        this.horario = horario;
        this.idUsuario = idUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return nome + " | " + sexo + " | " + modalidade + " | " + modalidade2 + " | " + horario;
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
