package com.example.bdsqllite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bdsqllite.utilidades.Utilidades;

public class ConsultarUsuarioActivity extends AppCompatActivity {
    EditText campoID, campoNombre, campoTelefono;
    conexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_usuario_activity);

        conn = new conexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null, 1);

        campoID = findViewById(R.id.idUsuario);
        campoNombre = findViewById(R.id.nombreUsuario);
        campoTelefono = findViewById(R.id.telefonoUsuario);
    }

    public void consultarUsuario(View view) {
        switch (view.getId()){
            case R.id.btnConsultar:
                //consultar();
                consultarSQL();
                break;
            case R.id.actualizar:
                actualizarUsuario();
                break;
            case R.id.eliminar:
                eliminarUsuario();
                break;
        }
    }
    private void consultar(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] param = {campoID.getText().toString()};
        String[] campos = {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_TELEFONO};

        try {
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_ID+"=?", param, null,null,null);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            clear();
        }
    }

    private void clear(){
        campoNombre.setText("");
        campoTelefono.setText("");
    }

    private void consultarSQL(){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] param = {campoID.getText().toString()};

        try {
            Cursor cursor = db.rawQuery("SELECT "+ Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_TELEFONO+
                    " FROM "+ Utilidades.TABLA_USUARIO + " WHERE " + Utilidades.CAMPO_ID + "=?", param);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            clear();
        }
    }

    private void actualizarUsuario(){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] param = {campoID.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

        db.update(Utilidades.TABLA_USUARIO, values, Utilidades.CAMPO_ID+"=?", param);
        Toast.makeText(getApplicationContext(),"Datos actualizados",Toast.LENGTH_LONG).show();
        db.close();
    }

    private void eliminarUsuario(){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] param = {campoID.getText().toString()};

        db.delete(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID+"=?",param);
        Toast.makeText(getApplicationContext(),"Usuario Eliminado",Toast.LENGTH_LONG).show();
        campoID.setText("");
        clear();
        db.close();
    }
}
