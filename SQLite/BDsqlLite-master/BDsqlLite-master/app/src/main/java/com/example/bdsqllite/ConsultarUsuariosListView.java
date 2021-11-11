package com.example.bdsqllite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bdsqllite.entidades.usuario;
import com.example.bdsqllite.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarUsuariosListView extends AppCompatActivity {
    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<usuario> listaUsuarios;
    conexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_usuarios_listview);

        conn = new conexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null, 1);

        listViewPersonas = findViewById(R.id.listViewPersonas);

        consultarListaPersonas();


    }

    private void consultarListaPersonas(){
        SQLiteDatabase db = conn.getReadableDatabase();

        usuario persona = null;
        listaUsuarios = new ArrayList<usuario>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO, null);

        while (cursor.moveToNext()){
            persona = new usuario();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));

            listaUsuarios.add(persona);
        }
        obtenerLista();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPersonas.setAdapter(adaptador);

    }

    private void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i < listaUsuarios.size(); i++) {
            listaInformacion.add(listaUsuarios.get(i).getId() + " - "
                    + listaUsuarios.get(i).getNombre());
        }
    }
}
