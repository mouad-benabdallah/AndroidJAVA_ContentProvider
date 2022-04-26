package fr.doranco.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public final class DataBaseSingleton {

    private final String TAG = DataBaseSingleton.class.getSimpleName();

    private static DataBaseSingleton instance;
    private SQLiteDatabase database;

    private DataBaseSingleton(Context context) {
        try {
            MyDatabaseSQLite dbOpenHelper = new MyDatabaseSQLite(context);
            database = dbOpenHelper.getWritableDatabase();
        } catch (Exception e) {
            String errorMessage = "Erreur lors de la création ou montée en version de la base de données !";
            Log.e(TAG, errorMessage);
            Log.e(TAG, e.getMessage());
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG);
        }
    }

    public final static DataBaseSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseSingleton(context);
        }
        return instance;
    }

    public final SQLiteDatabase getDatabase() {
        return database;
    }
}
