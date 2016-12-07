package br.edu.ufabc.padm.cardioufabc.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public class Atividade {
    private long id;
    private String titulo;
    private String descricao;
    private Date dataHoraInicio;
    private Date dataHoraFim;
    private List<LatLng> locations;
    private List<Integer> heartRates;

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

    public List<LatLng> getLocations() { return locations; }

    public void setLocations(List<LatLng> locations) { this.locations = locations; }

    public List<Integer> getHeartRates() { return heartRates; }

    public void setHeartRates(List<Integer> heartRates) {
        this.heartRates = heartRates;
    }

    public int getBpm() {
        if (heartRates.size() > 0) {
            int sum = 0;

            for (int heartRate : heartRates) {
                sum += heartRate;
            }

            return sum / heartRates.size();
        } else {
            return 0;
        }
    }

    public int getMaxBpm() {
        int max = 0;

        for (int heartRate : heartRates) {
            max = (heartRate > max) ? heartRate : max;
        }

        return max;
    }

    public int getMinBpm() {
        int min = 0;

        for (int heartRate : heartRates) {
            min = (min == 0 || min > heartRate) ? heartRate : min;
        }

        return min;
    }
}
