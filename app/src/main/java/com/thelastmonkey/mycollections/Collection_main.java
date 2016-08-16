package com.thelastmonkey.mycollections;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;

public class Collection_main extends AppCompatActivity {

    EditText editTextNombreCollection;
    EditText editTextFechaCollection;
    Button btnGuardarColeccion;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNombreCollection = (EditText)findViewById(R.id.editTextNombreCollection);
        editTextFechaCollection = (EditText)findViewById(R.id.editTextFechaCollection);
        btnGuardarColeccion = (Button)findViewById(R.id.btnGuardarColeccion);

        //Conecto a la bbdd
        DatabaseMyCollections baseDatos = new DatabaseMyCollections(this, DBAdapter.BBDD_Nombre,null,1);

        //Acceso de escritura
        db = baseDatos.getWritableDatabase();

        btnGuardarColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db != null){
                    //Inserto en la db
                    String sql = "Insert into Collection(nombre,fecha) values";
                    sql += "('" + editTextNombreCollection.getText().toString();
                    sql += "','" + editTextFechaCollection.getText().toString() + "');";

                    Log.i("My Collection", sql.toString());

                    //inserto
                    db.execSQL(sql);
                    Toast.makeText(Collection_main.this, "Registro insertado. . .", Toast.LENGTH_SHORT).show();

                    //Borro los campos
                    editTextNombreCollection.setText("");
                    editTextFechaCollection.setText("");
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
