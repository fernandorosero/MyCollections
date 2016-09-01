package com.thelastmonkey.mycollections;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListadoFiguras extends AppCompatActivity {

    TextView txtNombreColeccionFiguras;
    ListView listViewFiguras;
    Button btnAgregarFigura;
    Button btnEditarFigura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_figuras);

        txtNombreColeccionFiguras = (TextView)findViewById(R.id.txtNombreCollectionFiguras);
        listViewFiguras = (ListView)findViewById(R.id.listViewFiguras);
        btnAgregarFigura = (Button)findViewById(R.id.btnAgregarFigura);
        btnEditarFigura = (Button)findViewById(R.id.btnEditar);


        btnAgregarFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAgregarFigura = new Intent(ListadoFiguras.this, AgregarFigura.class);
                startActivity(intentAgregarFigura);
            }
        });









        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
