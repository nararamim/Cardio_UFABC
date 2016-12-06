package br.edu.ufabc.padm.cardioufabc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.helpers.Util;
import br.edu.ufabc.padm.cardioufabc.models.Atividade;
import br.edu.ufabc.padm.cardioufabc.models.AtividadeDAO;

public class AtividadeAdapter extends BaseAdapter {
    private Context context;
    private AtividadeDAO dao;
    private List<Atividade> atividades;

    public AtividadeAdapter(Context context) {
        this.context = context;
        this.dao = AtividadeDAO.getInstance(context);
        this.atividades = this.dao.list();
    }

    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public Object getItem(int position) { return atividades.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Atividade atividade = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.atividade_item, null);
        }

        atividade = atividades.get(position);
        ((TextView)convertView.findViewById(R.id.data)).setText(Util.formatFromDate(atividade.getDataHoraInicio(), context.getString(R.string.format_date)));
        ((TextView)convertView.findViewById(R.id.horaInicio)).setText(Util.formatFromDate(atividade.getDataHoraInicio(), context.getString(R.string.format_time)));
        ((TextView)convertView.findViewById(R.id.horaFim)).setText(Util.formatFromDate(atividade.getDataHoraFim(), context.getString(R.string.format_time)));

        ((TextView)convertView.findViewById(R.id.titulo)).setText(atividade.getTitulo());
        ((TextView)convertView.findViewById(R.id.bpm)).setText(atividade.getBpm() + " " + context.getString(R.string.bpm));

        return convertView;
    }
}
