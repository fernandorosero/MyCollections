package com.thelastmonkey.mycollections;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;
import com.thelastmonkey.mycollections.dto.CollectionDTO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerCollections;
    TextView lblMensaje;
    Button btnAgregarColeccion;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerCollections = (Spinner)findViewById(R.id.spinnerCollections);
        lblMensaje = (TextView)findViewById(R.id.lblMensaje);
        btnAgregarColeccion = (Button)findViewById(R.id.btnAgregarColeccion);

        List<String> listaColecciones = new ArrayList<String>();

        List<CollectionDTO> listaColeccionesDTO = new ArrayList<CollectionDTO>();

        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(this, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);

        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        
        //Compruebo si existe la db
        if(db != null){
            Toast.makeText(MainActivity.this, "Se ha creado con éxito!", Toast.LENGTH_SHORT).show();
            System.out.println("Se ha creado la BBDD");

        }else
        {
            Toast.makeText(MainActivity.this, "No se ha podido completar ", Toast.LENGTH_SHORT).show();
        }

        List<String> resultadoConsulta = new ArrayList<String>();

        Cursor resultado;
        String consultaSql;
        //db.rawQuery(consultaSql, null);
        consultaSql = DBAdapter.BBDD_Conculta_Collection;

        resultado = db.rawQuery(consultaSql, null);


        //db = db_myCollection.;
        Log.i("MyCollection", String.valueOf(resultado.getCount()));
        List<String> listadoNombresCollection = new ArrayList<String>();
        resultado.moveToFirst();
       //int columnIndex=resultado.getColumnIndex("nombre");
        try {
            for (int i = 0; i < resultado.getCount(); i++) {
                Log.i("", resultado.getString(resultado.getColumnIndex("nombre")));
                Log.i("", resultado.getString(resultado.getColumnIndex("fecha")));
                listadoNombresCollection.add(resultado.getString(resultado.getColumnIndex("nombre")));
                Log.i("***", listadoNombresCollection.get(i));

                resultado.moveToNext();
            }
        }
        catch(Exception e){}


/*
        int i=0;
        CollectionDTO coleDTO = new CollectionDTO();
        coleDTO.setIdColecction("1");
        coleDTO.setName("Transformers");
        coleDTO.setDate("13/08/2016");
        listaColeccionesDTO.add(coleDTO);

        CollectionDTO coleDTO1 = new CollectionDTO();
        coleDTO1.setIdColecction("1");
        coleDTO1.setName("Micro Machines");
        coleDTO1.setDate("13/08/2016");
        listaColeccionesDTO.add(coleDTO1);

        CollectionDTO coleDTO2 = new CollectionDTO();
        coleDTO2.setIdColecction("1");
        coleDTO2.setName("Barriguitas");
        coleDTO2.setDate("13/08/2016");
        listaColeccionesDTO.add(coleDTO2);

        listaColecciones.clear();
        for(CollectionDTO colection : listaColeccionesDTO){
            listaColecciones.add(colection.getName());
        }
*/

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                        listadoNombresCollection);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCollections.setAdapter(dataAdapter);

        spinnerCollections.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        lblMensaje.setText("Seleccionado: " +
                                parent.getItemAtPosition(position) + " posición: " + position);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        lblMensaje.setText("");
                    }
                });

        db.close();
        btnAgregarColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLayoutNewCollection = new Intent(MainActivity.this,Collection_main.class);
                startActivity(intentLayoutNewCollection);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        Log.i("Vuelve al main","vuelveeeeeeee");
        super.onRestart();
    }

    public void consultaListadoBBDD(){

    }
}
