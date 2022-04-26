package fr.doranco.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class UserProvider extends ContentProvider {

    private final static String TAG = UserProvider.class.getSimpleName();

    private SQLiteDatabase database;
    protected final static String TABLE_NAME = "user";
    protected final static String PROVIDER_NAME = "fr.doranco.contentprovider.UserProvider";
    protected final static Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + TABLE_NAME);

    public UserProvider() {
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        database = DataBaseSingleton.getInstance(context).getDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (sortOrder == null || sortOrder.trim().isEmpty()) {
            sortOrder = "id";
        }
        Cursor cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = database.insert(TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowId);
            return _uri;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}