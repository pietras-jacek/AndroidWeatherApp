package com.example.weatherapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.weatherapp.Model.Weather;

import java.util.ArrayList;
import java.util.List;

public class Datalist extends AppCompatActivity implements View.OnClickListener {

    //Deklarowanie widoków, pól z pliku data_list.xml
    private EditText editTextNote;
    private Button buttonSaveNote;
    private ListView listViewNotes;

    // array list do przechowywania wszystkich notatek
    private ArrayList<String> notes;

    //Adapter dla listview
    private ArrayAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list);

        //inicjalizowanie widoków
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        buttonSaveNote = (Button) findViewById(R.id.buttonSaveNote);
        listViewNotes = (ListView) findViewById(R.id.listViewNotes);

        //inicjalizowanie arraylist
        notes = new ArrayList<>();

        //dodanie listenera do przycisku
        buttonSaveNote.setOnClickListener(this);

        //wywoływanie metody wyswietlajacej liste notatek
        showNoteList();
    }

    private List<Weather> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Weather.class)
                .orderBy("Info ASC")
                .execute();
    }



    private void showNoteList() {
        //stworzenie listy i przypisanie do niej wszystkich notatekw postaci obiektu z bazy danych
        List<Weather> elements = getAll();

        //dodanie wszystkich notatek do arraylist
        for (int i = 0; i < elements.size(); i++) {
            Weather weather = elements.get(i);
            notes.add(weather.info);
        }

        //tworzenie adaptera
        noteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);

        //dodanie adaptera do listview
        listViewNotes.setAdapter(noteAdapter);

    }

    private void saveInventory() {
        //Pobieranie teksty z editText
        String note = editTextNote.getText().toString().trim();

        //sprawdzenie czy jest podany tekst
        if (note.equalsIgnoreCase("")) {
            Toast.makeText(this, "Podaj treść notatki", Toast.LENGTH_LONG).show();
            return;
        }

        //jezeli tekst jest podany tworze nowy obiekt
        Weather weather = new Weather();
        //dodanie podanej notatki do obiektu
        weather.info = note;
        //zapisanie notatki w bazie danych sqlite
        weather.save();
        notes.add(note);

        //aktualizowanie listy notatek
        updateNoteList();

        //czyszczenie inputu, ze starej notatki
        editTextNote.setText("");

        Toast.makeText(this, "zapisano notatkę", Toast.LENGTH_LONG).show();
    }


    private void updateNoteList() {
        noteAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        saveInventory();
    }

}
