package com.example.bdsqllite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bdsqllite.utilidades.Utilidades;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoID, campoNombre, campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        campoID = findViewById(R.id.id);
        campoNombre = findViewById(R.id.nombre);
        campoTelefono = findViewById(R.id.telefono);
    }


    public void registrarUsuario(View view) {
        insertarUsuario();
        //insertarUsuarioExecSQL();
    }

    private void insertarUsuario(){
        conexionSQLiteHelper conn = new conexionSQLiteHelper(this, "bd_usuarios", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, campoID.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID, values);

        Toast.makeText(getApplicationContext(), "Id de registro: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void insertarUsuarioExecSQL(){
        conexionSQLiteHelper conn = new conexionSQLiteHelper(this, "bd_usuarios", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        String sentencia = "INSERT INTO " + Utilidades.TABLA_USUARIO + " ( " + Utilidades.CAMPO_ID +
                "," + Utilidades.CAMPO_NOMBRE + "," + Utilidades.CAMPO_TELEFONO + ") VALUES ("
                + campoID.getText().toString() + ",'" + campoNombre.getText().toString() + "','" +
                campoTelefono.getText().toString() + "')";

        db.execSQL(sentencia);
        db.close();

    }
}
