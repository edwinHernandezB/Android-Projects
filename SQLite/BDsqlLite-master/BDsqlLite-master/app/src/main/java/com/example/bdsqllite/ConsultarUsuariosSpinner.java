package com.example.bdsqllite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bdsqllite.entidades.usuario;
import com.example.bdsqllite.utilidades.Utilidades;

import java.util.ArrayList;
//Show people lists
public class ConsultarUsuariosSpinner extends AppCompatActivity {
    Spinner spinnerPersonas;
    TextView txtNombre, txtID, txtTelefono;
    ArrayList<String> listaPersonas;
    ArrayList<usuario> personasList;
    conexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_usuarios_spinner_activity);

        conn = new conexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null, 1);

        spinnerPersonas = findViewById(R.id.spinnerlistadoPersonas);
        txtID = findViewById(R.id.txtID);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);

        consultarListaPersonas();

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaPersonas);
        spinnerPersonas.setAdapter(adaptador);

        spinnerPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position != 0){
                    txtID.setText(personasList.get(position - 1).getId().toString());
                    txtNombre.setText(personasList.get(position - 1).getNombre());
                    txtTelefono.setText(personasList.get(position - 1).getTelefono());
                }else{
                    txtID.setText("");
                    txtNombre.setText("");
                    txtTelefono.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void consultarListaPersonas(){
        SQLiteDatabase db = conn.getReadableDatabase();

        usuario persona = null;
        personasList = new ArrayList<usuario>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO, null);

        while (cursor.moveToNext()){
            persona = new usuario();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));

            Log.i("id", persona.getId().toString());
            Log.i("Nombre", persona.getNombre().toString());
            Log.i("Telefono", persona.getTelefono().toString());

            personasList.add(persona);
        }
        obtenerLista();

    }

    private void obtenerLista(){
        listaPersonas = new ArrayList<String>();
        listaPersonas.add("Seleccione");

        for (int i = 0; i < personasList.size(); i++) {
            listaPersonas.add(personasList.get(i).getId()+ " - " + personasList.get(i).getNombre());
        }
    }
}
