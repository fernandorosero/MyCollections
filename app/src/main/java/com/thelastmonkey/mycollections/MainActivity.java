package com.thelastmonkey.mycollections;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;
import com.thelastmonkey.mycollections.dto.CollectionDTO;
import com.thelastmonkey.mycollections.util.MyCollectionConstant;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerCollections;
    Button btnAgregarColeccion;
    Button btnEditar;
    ImageView imageViewCollection;
    TextView txtIdCollection;
    TextView txtNombreCollection;
    TextView txtPathImagen;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAgregarColeccion = (Button)findViewById(R.id.btnAgregarColeccion);
        btnEditar = (Button)findViewById(R.id.btnEditar);
        imageViewCollection = (ImageView)findViewById(R.id.imageViewCollection);
        txtIdCollection = (TextView)findViewById(R.id.txtIdCollection);
        txtNombreCollection = (TextView)findViewById(R.id.txtNombreCollection);
        txtPathImagen = (TextView)findViewById(R.id.txtPathImagen);
        //File newFile = new File("content://media/external/images/media/63911");

        //imageViewCollection.setImageURI(Uri.parse("storage/emulated/0/MyPictureAppColecction/PictureApp/1471396234.jpg"));
        //imageViewCollection.setImageURI(Uri.fromFile(newFile));
        //imageViewCollection.setImageURI(uri);
        //storage/emulated/0/MyPictureAppColecction/PictureApp/1471648225.jpg
        //content://media/external/images/media/63910
        //List<String> listaColecciones = new ArrayList<String>();



        consultaListadoBBDD();
/*
        List<CollectionDTO> listaColeccionesDTO = new ArrayList<CollectionDTO>();
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
        imageViewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListadoFiguras = new Intent(MainActivity.this, ListadoFiguras.class);
                //Información para enviar a Listado de Figuras
                Bundle bundleListadoFiguras = new Bundle();
                bundleListadoFiguras.putString(MyCollectionConstant.PARAMETRO_ID_COLLECTION, txtIdCollection.getText().toString());
                bundleListadoFiguras.putString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION,txtNombreCollection.getText().toString());

                intentListadoFiguras.putExtras(bundleListadoFiguras);

                startActivity(intentListadoFiguras);
            }
        });

        btnAgregarColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLayoutNewCollection = new Intent(MainActivity.this,Collection_main.class);
                startActivity(intentLayoutNewCollection);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MyCollectionUtil.TAG_MY_COLLECTION,"Press edit");
                Intent intent = new Intent(MainActivity.this, Collection_main.class);

                //Creo la informacion para enviar para editar
                Bundle bundleEditar = new Bundle();

                bundleEditar.putString(MyCollectionConstant.PARAMETRO_ID_COLLECTION,txtIdCollection.getText().toString());
                bundleEditar.putString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION, txtNombreCollection.getText().toString());
                bundleEditar.putString(MyCollectionConstant.PARAMETRO_PATH_IMAGEN, txtPathImagen.getText().toString());

                //Añado la información al intent
                intent.putExtras(bundleEditar);

                //Inicio la nueva actividad
                startActivity(intent);
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
        consultaListadoBBDD();
        super.onRestart();
    }

    public void consultaListadoBBDD(){

        spinnerCollections = (Spinner)findViewById(R.id.spinnerCollections);
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(this, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);

        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();

        //Compruebo si existe la db
        if(db != null){
            //Toast.makeText(MainActivity.this, "Conectado con éxito a la BBDD!", Toast.LENGTH_SHORT).show();

            Cursor resultado;
            String consultaSql;
            //db.rawQuery(consultaSql, null);
            consultaSql = DBAdapter.BBDD_Conculta_Collection;

            resultado = db.rawQuery(consultaSql, null);


            //db = db_myCollection.;
            Log.i("MyCollection", String.valueOf(resultado.getCount()));
            final List<String> listadoNombresCollection = new ArrayList<String>();
            final List<Integer> listadoIdCollection = new ArrayList<Integer>();
            resultado.moveToFirst();
            //int columnIndex=resultado.getColumnIndex("nombre");
            try {
                for (int i = 0; i < resultado.getCount(); i++) {
                    Log.i("", resultado.getString(resultado.getColumnIndex("nombre")));

                    // Log.i("", resultado.getString(resultado.getColumnIndex("fecha")));
                    listadoIdCollection.add(Integer.parseInt(resultado.getString(resultado.getColumnIndex("idCollection"))));
                    listadoNombresCollection.add(resultado.getString(resultado.getColumnIndex("nombre")));
                    //Log.i("***", listadoNombresCollection.get(i));

                    resultado.moveToNext();
                }
            }
            catch(Exception e){}

            //Cargo el id de la Colleccion la primera vez si existe
            if (listadoIdCollection.size()>0){
                txtIdCollection.setText(listadoIdCollection.get(0).toString());
                txtNombreCollection.setText(listadoNombresCollection.get(0).toString());
            }

            ArrayAdapter<String> dataAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                            listadoNombresCollection);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCollections.setAdapter(dataAdapter);

            spinnerCollections.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent,
                                                   android.view.View v, int position, long id) {
                            //lblMensaje.setText("Seleccionado: " +parent.getItemAtPosition(position) + " posición: " + position);
                            //      Toast.makeText(MainActivity.this, "Seleccionado: " + parent.getItemAtPosition(position) +
                            //              " posición: " + position, Toast.LENGTH_SHORT).show();
                            //AQi la accion cuando se selecciona un elemento
                            //Log.i("idCollection:",Integer.toString(listadoIdCollection.get(position)));
                            //Log.i("nombre:",listadoNombresCollection.get(position));
                            consultaPathCollectionImagen(listadoIdCollection.get(position));
                            //Paso el idCollection seleccionado
                            txtIdCollection.setText(listadoIdCollection.get(position).toString());
                            txtNombreCollection.setText(listadoNombresCollection.get(position).toString());
                        }



                        public void onNothingSelected(AdapterView<?> parent) {
                            //    lblMensaje.setText("");
                            Toast.makeText(MainActivity.this, "Accion cuando no se selecciona naa", Toast.LENGTH_SHORT).show();
                        }
                    });

            //db.close();

            resultado.close();


        }else
        {
            Toast.makeText(MainActivity.this, "No se ha podido completar ", Toast.LENGTH_SHORT).show();
        }

    }

    public void consultaPathCollectionImagen(int idCollection){
        Cursor resultado = null;
        String sql;

        //Log.i("Esto llega:",Integer.toString(idCollection));
        try {
            sql = "select * from CollectionImagen where idCollection = '" + idCollection + "';";

            resultado = db.rawQuery(sql, null);
            resultado.moveToFirst();
            String idImagen=resultado.getString(resultado.getColumnIndex("idImagen"));
            resultado.close();
            //Log.i("idImagen", resultado.getString(resultado.getColumnIndex("idImagen")));

            Cursor resultado2=null;
            sql = "select * from Imagen where idImagen = '"+idImagen+"';";

            resultado2 = db.rawQuery(sql, null);
            resultado2.moveToFirst();
            imageViewCollection.setImageURI(Uri.parse(resultado2.getString(resultado2.getColumnIndex("imgPath"))));
            txtPathImagen.setText(resultado2.getString(resultado2.getColumnIndex("imgPath")).toString());
            resultado2.close();
        }
        catch (Exception e){
            System.out.println("ERROR:"+e);
        }
        //resultado.close();
    }

}
