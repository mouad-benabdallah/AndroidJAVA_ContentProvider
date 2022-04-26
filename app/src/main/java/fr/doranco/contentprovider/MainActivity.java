package fr.doranco.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.doranco.contentprovider.R;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private EditText editTextLastname, editTextFirstname, editTextVille, editTextCodePostal;
    private Button boutonAjouter, boutonAfficher;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLastname = findViewById(R.id.editTextLastname);
        editTextFirstname = findViewById(R.id.editTextFirstname);
        editTextVille = findViewById(R.id.editTextVille);
        editTextCodePostal = findViewById(R.id.editTextCodePostal);
        boutonAjouter = findViewById(R.id.buttonAjouter);
        boutonAfficher = findViewById(R.id.buttonAfficher);
        listView = findViewById(R.id.listView);
    }

    public void ajouter(View view) {
        String nom = editTextLastname.getText().toString();
        String prenom = editTextFirstname.getText().toString();
        String ville = editTextVille.getText().toString();
        String codePostal = editTextCodePostal.getText().toString();

        if (nom == null || nom.trim().isEmpty()
                || prenom == null || prenom.trim().isEmpty()
                || ville == null || ville.trim().isEmpty()
                || codePostal == null || codePostal.trim().isEmpty()) {

            Toast.makeText(this, "Tous les champs sont obligatoires !", Toast.LENGTH_LONG).show();
            return;
        }
        if (codePostal.length() != 5) {
            Toast.makeText(this, "Le code postal doit être numérique composé de 5 chiffres !", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Integer.parseInt(codePostal);
        } catch(Exception e) {
            Toast.makeText(this, "Le code postal doit être numérique !", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User();

        nom = nom.toUpperCase(Locale.FRANCE);
        prenom = prenom.substring(0, 1).toUpperCase(Locale.FRANCE).concat(prenom.substring(1).toLowerCase(Locale.FRANCE));
        ContentValues valuesUser = new ContentValues();
        valuesUser.put("nom", nom);
        valuesUser.put("prenom", prenom);

        Uri uriUser = getContentResolver().insert(UserProvider.CONTENT_URI, valuesUser);
        String userId = uriUser.toString().replace(UserProvider.CONTENT_URI.toString() + "/", "");
        user.setId(Integer.parseInt(userId));
        user.setNom(nom);
        user.setPrenom(prenom);

        ville = ville.substring(0, 1).toUpperCase(Locale.FRANCE).concat(ville.substring(1).toLowerCase(Locale.FRANCE));

        ContentValues valuesAdresse = new ContentValues();
        valuesAdresse.put("ville", ville);
        valuesAdresse.put("code_postal", codePostal);
        valuesAdresse.put("user_id", userId);

        Uri uriAdresse = getContentResolver().insert(AdresseProvider.CONTENT_URI, valuesAdresse);
        String adresseId = uriAdresse.toString().replace(AdresseProvider.CONTENT_URI.toString() + "/", "");
        Adresse adresse = new Adresse();
        adresse.setId(Integer.parseInt(adresseId));
        adresse.setVille(ville);
        adresse.setCodePostal(codePostal);

        user.setAdresse(adresse);

        String message = "Utilisateur créé avec succès";
        Log.i(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        editTextLastname.setText("");
        editTextFirstname.setText("");
        editTextVille.setText("");
        editTextCodePostal.setText("");
    }

    public void afficher(View view) {

        Cursor cursorUser = getContentResolver().query(UserProvider.CONTENT_URI, null, null, null, "nom");

        List<User> users = new ArrayList<User>();
        if (cursorUser != null && cursorUser.moveToFirst()) {

            while(!cursorUser.isAfterLast()) {

                User user = new User();

                int indexId = cursorUser.getColumnIndex("id");
                Integer id = cursorUser.getInt(indexId);

                int indexNom = cursorUser.getColumnIndex("nom");
                String nom = cursorUser.getString(indexNom);

                int indexPrenom = cursorUser.getColumnIndex("prenom");
                String prenom = cursorUser.getString(indexPrenom);

                user.setId(id);
                user.setNom(nom);
                user.setPrenom(prenom);

                String userId = String.valueOf(id);
                String selection = "user_id = ?";
                String[] selectionArgs = new String[]{userId};
                Cursor cursorAdresse = getContentResolver().query(AdresseProvider.CONTENT_URI, null, selection, selectionArgs, null);
                if (cursorAdresse != null && cursorAdresse.moveToFirst()) {
                    int indexIdAdresse = cursorAdresse.getColumnIndex("id");
                    Integer idAdresse = cursorAdresse.getInt(indexIdAdresse);
                    int indexVille = cursorAdresse.getColumnIndex("ville");
                    String ville = cursorAdresse.getString(indexVille);
                    int indexCodePostal = cursorAdresse.getColumnIndex("code_postal");
                    String codePostal = cursorAdresse.getString(indexCodePostal);

                    Adresse adresse = new Adresse();
                    adresse.setId(idAdresse);
                    adresse.setVille(ville);
                    adresse.setCodePostal(codePostal);
                    user.setAdresse(adresse);

                }
                users.add(user);
                cursorUser.moveToNext();
            }
            ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(arrayAdapter);

        } else {
            Toast.makeText(this, "Pas d'utilisateurs dans la BDD.", Toast.LENGTH_SHORT).show();
        }
    }


}