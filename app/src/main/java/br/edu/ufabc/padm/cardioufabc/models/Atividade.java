package br.edu.ufabc.padm.cardioufabc.models;

import java.util.Date;

public class Atividade {
    private String titulo;
    private String descricao;
    private Date dataHoraInicio;
    private Date dataHoraFim;
    private int bpm; // batimentos por minuto
    // informação do gps (descobrir a estrutura de dados capturado pelo gps para armazenar o caminho)

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
