package fr.doranco.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseSQLite extends SQLiteOpenHelper {

    private final static String TAG = MyDatabaseSQLite.class.getSimpleName();

    private final static String DATABASE_NAME = "MyDataBase";
    private final static int DATABASE_VERSION = 1;

    private final static String REQUEST_CREATE_TABLE_USER = "CREATE TABLE " + UserProvider.TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "nom TEXT NOT NULL, prenom TEXT NOT NULL);";

    private final static String REQUEST_CREATE_TABLE_ADRESSE = "CREATE TABLE " + AdresseProvider.TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "ville TEXT NOT NULL, code_postal TEXT NOT NULL, user_id Integer UNIQUE NOT NULL, " +
            "FOREIGN KEY (user_id) REFERENCES user(id));";

    public MyDatabaseSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUEST_CREATE_TABLE_USER);
        db.execSQL(REQUEST_CREATE_TABLE_ADRESSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // NB :
        // il ne faut pas oublier de récupérer d'abord les données des tables existantes
        // avant de les supprimer (si ces tables sont à recréer tout en gardant les données existantes)

        db.execSQL("DROP TABLE IF EXISTS " + UserProvider.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AdresseProvider.TABLE_NAME);
        db.execSQL(REQUEST_CREATE_TABLE_USER);
        db.execSQL(REQUEST_CREATE_TABLE_ADRESSE);
    }

}
