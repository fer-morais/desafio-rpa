package br.com.fernanda.desafio.rpa.Automation;

import java.time.LocalDate;

public class Feriado {
    private String estado;
    private String cidade;
    private LocalDate data;
    private String tipo;
    private String feriado;

    // Construtor
    public Feriado(String estado, String cidade, LocalDate data, String tipo, String feriado) {
        this.estado = estado;
        this.cidade = cidade;
        this.data = data;
        this.tipo = tipo;
        this.feriado = feriado;
    }

    // Getters e Setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFeriado() {
        return feriado;
    }

    public void setFeriado(String feriado) {
        this.feriado = feriado;
    }

    @Override
    public String toString() {
        return "Feriado{" +
                "estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", data=" + data +
                ", tipo='" + tipo + '\'' +
                ", feriado='" + feriado + '\'' +
                '}';
    }
}
