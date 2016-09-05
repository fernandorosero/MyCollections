package com.thelastmonkey.mycollections;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelastmonkey.mycollections.util.MyCollectionConstant;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

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


        //Recojo los datos recibidos a la vista
        final Bundle bundle = getIntent().getExtras();
        txtNombreCollectionAgregarFiguraTitulo.setText("Colección: " + bundle.getString(MyCollectionConstant.PARAMETRO_NOMBRE_COLECTION));

        editTextFechaCompra.setText(MyCollectionUtil.mostrarFecha());
        editTextPrecioCompra.setText(CERO);
        editTextPrecioVenta.setText(CERO);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnAgregarFigura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MyCollectionUtil.TAG_MY_COLLECTION,"AGREGO FIGURA");

                MyCollectionUtil.createFiguraCollection(AgregarFigura.this,
                                                bundle.getString(MyCollectionConstant.PARAMETRO_ID_COLLECTION),
                                                editTextNombreAgregarFigura.getText().toString(),
                                                editTextFechaCompra.getText().toString(),
                                                Integer.parseInt(editTextPrecioCompra.getText().toString()),
                                                Integer.parseInt(editTextPrecioVenta.getText().toString()),
                                                1);

                onBackPressed();

            }
        });
    }

}
