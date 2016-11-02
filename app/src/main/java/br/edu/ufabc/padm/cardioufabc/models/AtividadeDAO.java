package br.edu.ufabc.padm.cardioufabc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AtividadeDAO {
    private static AtividadeDAO instance;
    public static AtividadeDAO getInstance() {
        if (instance == null) {
            instance = new AtividadeDAO();
            instance.init();
        }

        return instance;
    }

    private List<Atividade> atividades;

    private void init() {
        atividades = new ArrayList<>();

        loadTestData();
    }

    private void loadTestData() {
        Atividade atividadeA = new Atividade();
        atividadeA.setTitulo("Estudando PADM");
        atividadeA.setDescricao("Estudando PADM deitado");
        atividadeA.setDataHoraInicio(new Date(2016, 9, 20, 16, 0, 0));
        atividadeA.setDataHoraFim(new Date(2016, 9, 20, 17, 0, 0));
        atividadeA.setBpm(60);
        atividades.add(atividadeA);

        Atividade atividadeB = new Atividade();
        atividadeB.setTitulo("Estudando PADM");
        atividadeB.setDescricao("Estudando PADM sentado");
        atividadeB.setDataHoraInicio(new Date(2016, 9, 21, 16, 0, 0));
        atividadeB.setDataHoraFim(new Date(2016, 9, 21, 16, 30, 0));
        atividadeB.setBpm(80);
        atividades.add(atividadeB);

        Atividade atividadeC = new Atividade();
        atividadeC.setTitulo("Estudando PADM");
        atividadeC.setDescricao("Estudando PADM em pé");
        atividadeC.setDataHoraInicio(new Date(2016, 9, 22, 16, 0, 0));
        atividadeC.setDataHoraFim(new Date(2016, 9, 22, 16, 15, 0));
        atividadeC.setBpm(100);
        atividades.add(atividadeC);
    }

    public int size() {
        return atividades.size();
    }

    public Atividade get(int i) {
        return atividades.get(i);
    }

    public Atividade getLast() {
        return atividades.get(atividades.size() - 1);
    }
}
