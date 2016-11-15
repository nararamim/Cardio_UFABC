package br.edu.ufabc.padm.cardioufabc.models;

import java.util.Date;

public class Atividade {
    private long id;
    private String titulo;
    private String descricao;
    private Date dataHoraInicio;
    private Date dataHoraFim;
    private long bpm; // batimentos por minuto
    // informação do gps (descobrir a estrutura de dados capturado pelo gps para armazenar o caminho)

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public long getBpm() {
        return bpm;
    }

    public void setBpm(long bpm) {
        this.bpm = bpm;
    }
}
