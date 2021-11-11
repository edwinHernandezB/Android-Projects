package com.example.bdsqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexionSQLiteHelper conn = new conexionSQLiteHelper(this, "bd_usuarios", null, 1);
    }

    public void onClick(View view) {
        Intent miIntent = null;

        switch (view.getId()){
            case R.id.REGISTRAR:
                miIntent = new Intent(MainActivity.this, RegistroUsuariosActivity.class);
                break;
            case R.id.CONSULTAR_USER:
                miIntent = new Intent(MainActivity.this, ConsultarUsuarioActivity.class);
                break;
            case R.id.CONSULTAR_SPINNER:
                miIntent = new Intent(MainActivity.this, ConsultarUsuariosSpinner.class);
                break;
            case R.id.CONSULTAR_LISTVIEW:
                miIntent = new Intent(MainActivity.this, ConsultarUsuariosListView.class);
                break;
        }
       startActivity(miIntent);

    }

}