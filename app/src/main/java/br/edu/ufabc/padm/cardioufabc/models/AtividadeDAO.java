package br.edu.ufabc.padm.cardioufabc.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufabc.padm.cardioufabc.R;
import br.edu.ufabc.padm.cardioufabc.helpers.Util;

public class AtividadeDAO extends SQLiteOpenHelper {
    private static AtividadeDAO instance;

    private static final String DB_NAME = "cardio.db";
    private static final int DB_VERSION = 1;

    private static final String LOGTAG = AtividadeDAO.class.getName();

    public static AtividadeDAO getInstance(Context c) {
        if (instance == null) {
            instance = new AtividadeDAO(c);
        } else {
            instance.context = c;
        }

        return instance;
    }

    private Context context;
    private SQLiteDatabase db;
    private LocationDAO locationDAO;
    private HeartRateDAO heartRateDAO;

    protected AtividadeDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.db = getWritableDatabase();
        this.locationDAO = LocationDAO.getInstance(context);
        this.heartRateDAO = HeartRateDAO.getInstance(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = context.getString(R.string.create_table_atividade);

        try {
            db.execSQL(query);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to create table atividade", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: here we must implement a proper way to migrate the schema, version by version
        // currently, it only recreates the database
        String queryStr = context.getString(R.string.drop_table_atividade);

        try {
            db.execSQL(queryStr);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to drop table atividade", e);
        }
    }

    public int size() {
        String queryStr = context.getString(R.string.count_atividade);
        int count = -1;

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to count atividade in the database", e);
        }

        return count;
    }

    // retorna o id do registro inserido
    public long create(Atividade atividade) {
        String queryStr = context.getString(R.string.insert_atividade);
        long id = 0;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, atividade.getTitulo());
            statement.bindString(2, atividade.getDescricao());
            statement.bindString(3, Util.formatFromDate(atividade.getDataHoraInicio(), context.getString(R.string.format_datetime_global)));
            statement.bindString(4, Util.formatFromDate(atividade.getDataHoraFim(), context.getString(R.string.format_datetime_global)));
            id = statement.executeInsert();
            locationDAO.create(id, atividade.getLocations());
            heartRateDAO.create(id, atividade.getHeartRates());
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to insert atividade in the database", e);
        }

        return id;
    }

    public Atividade getById(long id) {
        Atividade atividade = null;
        String queryStr = context.getString(R.string.get_atividade_by_id);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[] { String.valueOf(id) } );

            cursor.moveToFirst();
            atividade = convert(cursor);
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get atividade from the database", e);
        }

        return atividade;
    }

    public boolean update(Atividade atividade) {
        String queryStr = context.getString(R.string.update_atividade);
        boolean status = true;

        try {
            SQLiteStatement statement = db.compileStatement(queryStr);

            statement.bindString(1, atividade.getTitulo());
            statement.bindString(2, atividade.getDescricao());
            statement.bindString(3, Util.formatFromDate(atividade.getDataHoraInicio(), context.getString(R.string.format_datetime_global)));
            statement.bindString(4, Util.formatFromDate(atividade.getDataHoraFim(), context.getString(R.string.format_datetime_global)));
            statement.bindLong(5, atividade.getId());
            statement.execute();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to update atividade in the database", e);
            System.out.println(e);
            status = false;
        }

        return status;
    }

    public List<Atividade> list() {
        ArrayList<Atividade> atividades = new ArrayList<>();
        String queryStr = context.getString(R.string.list_atividade);

        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                atividades.add(convert(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list atividade from the database", e);
        }

        return atividades;
    }

    private Atividade convert(Cursor cursor) {
        Atividade atividade = new Atividade();
        atividade.setId(cursor.getLong(0));
        atividade.setTitulo(cursor.getString(1));
        atividade.setDescricao(cursor.getString(2));
        atividade.setDataHoraInicio(Util.formatToDate(cursor.getString(3), context.getString(R.string.format_datetime_global)));
        atividade.setDataHoraFim(Util.formatToDate(cursor.getString(4), context.getString(R.string.format_datetime_global)));
        atividade.setLocations(locationDAO.getByAtividadeId(atividade.getId()));
        atividade.setHeartRates(heartRateDAO.getByAtividadeId(atividade.getId()));
        return atividade;
    }
}
