package com.thelastmonkey.mycollections;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;

/**
 * Created by tatof on 14/08/2016.
 */
public class DBAdapter {

    //Definicion de BBDD
    private static final String BBDD_Nombre = "DBMyCollections";

    //Definicion de Tablas para la BDD
    private static final String BBDD_Tabla_MyCollection = "MyCollection";
    private static final String BBDD_Tabla_Figuras = "Figuras";
    private static final String BBDD_Tabla_MyCollection_Figura = "MyCollectionFigura";
    private static final String BBDD_Tabla_Imagen = "Imagen";
    private static final String BBDD_Tabla_Figura_Imagen = "FiguraImagen";
    private static final String BBDD_Tabla_MyCollection_Imagen = "MyCollectionImagen";
    private static final String BBDD_Tabla_Clase = "Clase";
    private static final String BBDD_Tabla_Figura_Clase = "FiguraClase";

    private static final int BBDD_VERSION = 1;

    //Declaracion para campos de tabla MiCollection
    public static final String idCollection = "idCollection";
    public static final String name = "name";
    public static final String date = "date";
    public static final String imgPAth = "imgPath";



    private static final String TAG = "DBAdapter";

    //Creacion de tablas para la BBDD
    public static final String BBDD_Crear_Tabla_MyCollection = "create table MyCollection (idCollection integer primary key autoincrement, " +
            "nombre text not null, fecha text not null, imgPath text);";
    public static final String BBDD_Crear_Tabla_Figura = "create table Figura (idFigura integer primary key autoincrement, " +
            "nombre text not null, fechaCompra text not null, precioCompra integer not null, precioVenta integer," +
            "venta integer not null, imgPath text);";

    private final Context context;

    private DatabaseMyCollections databaseMyCollections;
    private SQLiteDatabase sqLiteDatabase;

    public DBAdapter(Context context) {
        this.context = context;
        //databaseMyCollections = new DatabaseMyCollections(context);
    }

}
