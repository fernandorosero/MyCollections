package com.thelastmonkey.mycollections;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thelastmonkey.mycollections.dto.FiguraDTO;
import com.thelastmonkey.mycollections.dto.FiguraImagenDTO;
import com.thelastmonkey.mycollections.dto.ImagenDTO;
import com.thelastmonkey.mycollections.util.MyCollectionConstant;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AgregarFigura extends AppCompatActivity {

    private static final String CERO = "0";
    TextView txtNombreCollectionAgregarFiguraTitulo;
    EditText editTextNombreAgregarFigura;
    EditText editTextFechaCompra;
    EditText editTextPrecioCompra;
    EditText editTextPrecioVenta;
    CheckBox checkBoxFiguraVenta;
    ImageView imageViewUno;
    ImageView imageViewDos;
    ImageView imageViewTres;
    Button btnAgregarFigura;

    private final int CRO = 0;
    private final int UNO = 1;
    private final int DOS = 2;
    private final int TRES = 3;
    private final String EDITAR = "Editar: ";

    int imagenSeleccionadaGlobal;

    private LinearLayout mRlView;
    private String mPath;

    String editar;

    List<String> pathImagenesAgregadas = new ArrayList<String>();
    //Recojo los datos recibidos a la vista con try y catch si no hay datos
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_figura);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNombreCollectionAgregarFiguraTitulo = (TextView)findViewById(R.id.txtNombreCollectionAgregarFiguraTitulo);
        editTextNombreAgregarFigura = (EditText)findViewById(R.id.editTextNombreAgregarFigura);
        editTextFechaCompra = (EditText)findViewById(R.id.editTextFechaCompraAgregarFigura);
        editTextPrecioCompra = (EditText)findViewById(R.id.editTextPrecioCompra);
        editTextPrecioVenta = (EditText)findViewById(R.id.editTextPrecioVenta);
        checkBoxFiguraVenta = (CheckBox)findViewById(R.id.checkBoxConfirmaVenta);
        imageViewUno = (ImageView)findViewById(R.id.imageViewUno);
        imageViewDos = (ImageView)findViewById(R.id.imageViewDos);
        imageViewTres = (ImageView)findViewById(R.id.imageViewTres);
        btnAgregarFigura = (Button)findViewById(R.id.btnAgregarFigura);


        //Dimenciono el List
        pathImagenesAgregadas.clear();
        pathImagenesAgregadas.add(0,"");
        pathImagenesAgregadas.add(1,"");
        pathImagenesAgregadas.add(2,"");


        //Recojo los datos
        bundle = getIntent().getExtras();
        String idFigura = null;
        try {

            idFigura = bundle.getString(MyCollectionConstant.PARAMETRO_ID_FIGURA);
            String nombreFigura = bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_FIGURA);

            if(idFigura != null){
                Log.i("Editosss", idFigura);
            }

        }catch (Exception e){}

        if(idFigura != null){//Editar
            setTitle(EDITAR + bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_FIGURA));
            //editTextNombreAgregarFigura.setText(bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_FIGURA));

            FiguraDTO figuraDTO = MyCollectionUtil.getFiguraDTO(AgregarFigura.this,bundle.getString(MyCollectionConstant.PARAMETRO_ID_FIGURA));
            editTextNombreAgregarFigura.setText(figuraDTO.getNombre());
            editTextFechaCompra.setText(figuraDTO.getFechaCompra());
            editTextPrecioCompra.setText(figuraDTO.getPrecioCompra());
            editTextPrecioVenta.setText(figuraDTO.getPrecioVenta());

            //PAra las figuras de la imagen
            List<FiguraImagenDTO> listFiguraImagenDTO = MyCollectionUtil.listadoFiguraImagenDTO(AgregarFigura.this, bundle.getString(MyCollectionConstant.PARAMETRO_ID_FIGURA));
            Log.i("Numero de elementos: " , String.valueOf(listFiguraImagenDTO.size()));
            List<ImagenDTO> listImagenDTO = MyCollectionUtil.listadoImagenDTO(AgregarFigura.this,listFiguraImagenDTO);

            Log.i("numero de imagenes", String.valueOf(listImagenDTO.size()));
            if(listImagenDTO.size()>0) {
                imageViewUno.setImageURI(Uri.parse(listImagenDTO.get(CRO).getImgPath()));
                imageViewDos.setImageURI(Uri.parse(listImagenDTO.get(UNO).getImgPath()));
                imageViewTres.setImageURI(Uri.parse(listImagenDTO.get(DOS).getImgPath()));
            }
            else
            {
                Toast.makeText(AgregarFigura.this, "No tiene elemntos", Toast.LENGTH_SHORT).show();
            }
            if(figuraDTO.getVenta().equals("1")){
                checkBoxFiguraVenta.setChecked(true);
            }
            else if(figuraDTO.getVenta().equals("0")){
                checkBoxFiguraVenta.setChecked(false);
            }

        }else{  //Nuevo
            editTextFechaCompra.setText(MyCollectionUtil.mostrarFecha());
            editTextPrecioCompra.setText(CERO);
            editTextPrecioVenta.setText(CERO);
        }


        txtNombreCollectionAgregarFiguraTitulo.setText("Colección: " + bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION));




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageViewUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mayRequestStoragePermission()) {
                    Log.i("*****", "uno");
                    showOptions(UNO);
                }
            }
        });
        imageViewDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mayRequestStoragePermission())
                {
                    Log.i("********","dos");
                    Log.i("SI", "Si tiene permiso goooo");
                    showOptions(DOS);
                }
            }
        });
        imageViewTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mayRequestStoragePermission()) {
                    Log.i("+++++", "tres");
                    showOptions(TRES);
                }
            }
        });
        btnAgregarFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MyCollectionUtil.TAG_MY_COLLECTION,"AGREGO FIGURA");
                int codigoVenta = 0;
                if (checkBoxFiguraVenta.isChecked()){
                    codigoVenta = 1;
                }

                String idFigura =
                MyCollectionUtil.createFiguraCollection(AgregarFigura.this,
                                                bundle.getString(MyCollectionConstant.PARAMETRO_ID_COLLECTION),
                                                editTextNombreAgregarFigura.getText().toString(),
                                                editTextFechaCompra.getText().toString(),
                                                Integer.parseInt(editTextPrecioCompra.getText().toString()),
                                                Integer.parseInt(editTextPrecioVenta.getText().toString()),
                                                codigoVenta);

                MyCollectionUtil.createImagenDeFiguras(AgregarFigura.this,pathImagenesAgregadas,idFigura);

                onBackPressed();

            }
        });
    }

    /************INICIO CODIGO PARA AGREGAR IMAGENES A FIGURA ****************/
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
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MyCollectionConstant.MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MyCollectionConstant.MY_PERMISSIONS);
        }

        return false;
    }
    private void showOptions(int imagenSleccionada) {

        imagenSeleccionadaGlobal = imagenSleccionada;

        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AgregarFigura.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), MyCollectionConstant.SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MyCollectionConstant.MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MyCollectionConstant.MEDIA_DIRECTORY
                    + File.separator + imageName;
            Log.i(" Es el mPath:",mPath);
            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, MyCollectionConstant.PHOTO_CODE);
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
                case MyCollectionConstant.PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    if(imagenSeleccionadaGlobal == UNO){
                                        pathImagenesAgregadas.add(CRO, path);
                                    }else if(imagenSeleccionadaGlobal == DOS){
                                        pathImagenesAgregadas.add(UNO, path);
                                    }else if(imagenSeleccionadaGlobal == TRES){
                                        pathImagenesAgregadas.add(DOS, path);
                                    }

                                    //imagenPathGuardar = path;
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);

                    if(imagenSeleccionadaGlobal==UNO){
                        imageViewUno.setImageBitmap(bitmap);
                    }else if(imagenSeleccionadaGlobal==DOS){
                        imageViewDos.setImageBitmap(bitmap);
                    }else if(imagenSeleccionadaGlobal==TRES){
                        imageViewTres.setImageBitmap(bitmap);
                    }
                    //imageViewCollectionNuevo.setImageBitmap(bitmap);
                    break;
                case MyCollectionConstant.SELECT_PICTURE:
                    Uri path = data.getData();
                    Log.i("path galeria ok: " , String.valueOf(data.getData()));
                    if(imagenSeleccionadaGlobal == UNO){
                        pathImagenesAgregadas.add(CRO, String.valueOf(data.getData()));
                        imageViewUno.setImageURI(path);
                    }else if(imagenSeleccionadaGlobal == DOS){
                        pathImagenesAgregadas.add(UNO, String.valueOf(data.getData()));
                        imageViewDos.setImageURI(path);
                    }else if(imagenSeleccionadaGlobal == TRES){
                        pathImagenesAgregadas.add(DOS, String.valueOf(data.getData()));
                        imageViewTres.setImageURI(path);
                    }

                   // imagenPathGuardar = String.valueOf(data.getData());
                   // imageViewCollectionNuevo.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MyCollectionConstant.MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AgregarFigura.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //btnAgregarImagen.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarFigura.this);
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
    /************FIN CODIGO PARA AGREGAR IMAGENES A FIGURA ****************/


}
