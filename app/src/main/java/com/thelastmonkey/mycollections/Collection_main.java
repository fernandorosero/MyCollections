package com.thelastmonkey.mycollections;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

//Importante
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

import java.io.File;

public class Collection_main extends AppCompatActivity {

    //DECLARO CONSTANTES PARA GUARDAR LAS IMAGENES
    private static  String APP_DIRECTORY = "MyPictureAppColecction/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;


    EditText editTextNombreCollection;
    EditText editTextFechaCollection;
    Button btnGuardarColeccion;
    Button btnAgregarImagen;
    ImageView imageViewCollectionNuevo;
    private LinearLayout mRlView;

    private String mPath;

    private String imagenPathGuardar;

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
        btnAgregarImagen = (Button)findViewById(R.id.btnAgregarImagen);
        imageViewCollectionNuevo = (ImageView)findViewById(R.id.imageViewCollectionNuevo);
        mRlView = (LinearLayout)findViewById(R.id.linearLAyout);

        if(mayRequestStoragePermission())
            btnAgregarImagen.setEnabled(true);
        else
            btnAgregarImagen.setEnabled(false);

        //Flecha en el menú para ir hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOptions();
            }
        });

        //Conecto a la bbdd
        DatabaseMyCollections baseDatos = new DatabaseMyCollections(this, DBAdapter.BBDD_Nombre,null,DBAdapter.BBDD_VERSION);

        //Acceso de escritura
        db = baseDatos.getWritableDatabase();

        btnGuardarColeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db != null){
                    if(editTextNombreCollection.getText().toString().equals("")){
                        editTextNombreCollection.requestFocus();
                        Toast.makeText(Collection_main.this, "Rellene el campo Nombre!!", Toast.LENGTH_SHORT).show();
                    }else if (editTextFechaCollection.getText().toString().equals("")){
                        editTextFechaCollection.requestFocus();
                        Toast.makeText(Collection_main.this, "Rellene el campo Fecha!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Inserto en la db
                        String sql = "Insert into Collection(" + DBAdapter.Collection_nombre +","+DBAdapter.Collection_fecha+") values";
                        sql += "('" + editTextNombreCollection.getText().toString();
                        sql += "','" + editTextFechaCollection.getText().toString() + "');";

                        Log.i("My Collection", sql);

                        //inserto
                        db.execSQL(sql);
                        Cursor resultado;
                        sql = "select * from Collection where idCollection = (select max(idCollection) FROM Collection);";
                        resultado = db.rawQuery(sql, null);
                        resultado.moveToFirst();
                        int cant = resultado.getCount();
                        Log.i("Numero de elementos" , String.valueOf(cant));
                        Log.i("MyCollection", resultado.getString(resultado.getColumnIndex("idCollection")));
                        Log.i("MyCollection", resultado.getString(resultado.getColumnIndex("nombre")));
                        Log.i(MyCollectionUtil.TAG_MY_COLLECTION, resultado.getString(resultado.getColumnIndex("fecha")));


                        Cursor imagenCursor = null;
                       // imageViewCollectionNuevo.
                        if(imagenPathGuardar.length()>0){
                            sql = "insert into Imagen(imgPath) values('" + imagenPathGuardar + "');";
                            db.execSQL(sql);
                            sql = "select * from Imagen where idImagen = (select max(idImagen) from Imagen);";
                            imagenCursor = db.rawQuery(sql, null);
                            imagenCursor.moveToFirst();
                            Log.i(MyCollectionUtil.TAG_MY_COLLECTION, imagenCursor.getString(imagenCursor.getColumnIndex("idImagen")));
                            Log.i(MyCollectionUtil.TAG_MY_COLLECTION, imagenCursor.getString(imagenCursor.getColumnIndex("imgPath")));

                            sql = "insert into CollectionImagen(idCollection, idImagen, fecha) values('"+ resultado.getString(resultado.getColumnIndex("idCollection"))
                                    +"','"+imagenCursor.getString(imagenCursor.getColumnIndex("idImagen"))+"','"+resultado.getString(resultado.getColumnIndex("fecha"))+"');";


                        }



                        resultado.close();
                        imagenCursor.close();
                        Toast.makeText(Collection_main.this, "Registro insertado. . .", Toast.LENGTH_SHORT).show();




                        //Borro los campos
                        editTextNombreCollection.setText("");
                        editTextFechaCollection.setText("");
                        Intent intentVolverMain = new Intent(Collection_main.this, MainActivity.class);
                        startActivity(intentVolverMain);
                    }
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



    //Sobreescribiendo este método regreso al menú principal
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Collection_main.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Collection_main.this);
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;
            Log.i(" Es el mPath:",mPath);
            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
        Log.i(" Es el mPath:",mPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    imagenPathGuardar = path;
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    imageViewCollectionNuevo.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    Log.i("path galeria ok: " , String.valueOf(data.getData()));
                    imagenPathGuardar = String.valueOf(data.getData());
                    imageViewCollectionNuevo.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Collection_main.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                btnAgregarImagen.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

}
