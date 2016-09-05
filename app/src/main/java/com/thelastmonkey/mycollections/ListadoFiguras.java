package com.thelastmonkey.mycollections;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.thelastmonkey.mycollections.util.MyCollectionConstant;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

import java.util.List;

public class ListadoFiguras extends AppCompatActivity {

    TextView txtNombreColeccionFiguras;
    ListView listViewFiguras;
    Button btnAgregarFigura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_figuras);

        txtNombreColeccionFiguras = (TextView)findViewById(R.id.txtNombreCollectionFiguras);
        listViewFiguras = (ListView)findViewById(R.id.listViewFiguras);
        btnAgregarFigura = (Button)findViewById(R.id.btnAgregarFigura);

        //Recojo los datos recibidos al layout
        final Bundle bundle = getIntent().getExtras();
        txtNombreColeccionFiguras.setText("Colección: " + bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION));

        cargarListViewFiguras(bundle.getString(MyCollectionConstant.PARAMETRO_ID_COLLECTION));

        btnAgregarFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAgregarFigura = new Intent(ListadoFiguras.this, AgregarFigura.class);

                //Datos a enviar al layout AgregarFigura
                Bundle bundleAgregarFigura = new Bundle();

                //Cargo los parámetros a enviar
                bundleAgregarFigura.putString(MyCollectionConstant.PARAMETRO_ID_COLLECTION, bundle.getString(MyCollectionConstant.PARAMETRO_ID_COLLECTION));
                bundleAgregarFigura.putString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION, bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION));

                //Cargo al intent los parámetros
                intentAgregarFigura.putExtras(bundleAgregarFigura);

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

    public void cargarListViewFiguras(String idCollection){
        //Listado de las figuras de la coleccion
        List<String>listadoIdFiguras =  MyCollectionUtil.listadoCollectionFigura(ListadoFiguras.this, idCollection);
        List<String>listadoNombreFiguras = MyCollectionUtil.returnNombresFiguras(ListadoFiguras.this,listadoIdFiguras);
        ArrayAdapter<String> adaptadorIdFiguras = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listadoNombreFiguras);
        listViewFiguras.setAdapter(adaptadorIdFiguras);

    }


    @Override
    protected void onRestart() {
        //Recojo los datos recibidos al layout
        final Bundle bundle = getIntent().getExtras();
        cargarListViewFiguras(bundle.getString(MyCollectionConstant.PARAMETRO_ID_COLLECTION));
        super.onRestart();
    }

}
