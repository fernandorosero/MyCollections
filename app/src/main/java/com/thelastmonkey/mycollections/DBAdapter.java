package com.thelastmonkey.mycollections;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;
import com.thelastmonkey.mycollections.util.MyCollectionUtil;

/**
 * Created by tatof on 14/08/2016.
 */
public class DBAdapter {

    //Definicion de BBDD
    public static final String BBDD_Nombre = "DBMyCollections";

    //Definicion de Tablas para la BDD
    private static final String BBDD_Tabla_Collection = "Collection";
    private static final String BBDD_Tabla_Figura = "Figura";
    private static final String BBDD_Tabla_Collection_Figura = "CollectionFigura";
    private static final String BBDD_Tabla_Imagen = "Imagen";
    private static final String BBDD_Tabla_Figura_Imagen = "FiguraImagen";
    private static final String BBDD_Tabla_Collection_Imagen = "CollectionImagen";
    private static final String BBDD_Tabla_Clase = "Clase";
    private static final String BBDD_Tabla_Figura_Clase = "FiguraClase";



    public static final int BBDD_VERSION = 1;

    //Declaracion para campos de tabla Collection
    public static final String Collection_idCollection = "idCollection";
    public static final String Collection_nombre = "nombre";
    public static final String Collection_fecha = "fecha";

    private static final String TAG = "DBAdapter";

    //Creacion de tablas para la BBDD
    public static final String BBDD_Crear_Tabla_Collection = "create table " + BBDD_Tabla_Collection + " (idCollection integer primary key autoincrement, " +
            "nombre text not null, fecha text not null);";
    public static final String BBDD_Crear_Tabla_Figura = "create table " + BBDD_Tabla_Figura + " (idFigura integer primary key autoincrement, " +
            "nombre text not null, fechaCompra text not null, precioCompra integer not null, precioVenta integer," +
            "venta integer not null);";
    public static final String BBDD_Crear_Tabla_CollectionFigura = "create table " + BBDD_Tabla_Collection_Figura + " (idCollectionFigura integer primary key autoincrement, " +
            "idCollection integer not null, idFigura integer not null, fecha text not null);";
    public static final String BBDD_Crear_Tabla_Imagen = "create table " + BBDD_Tabla_Imagen + " (idImagen integer primary key autoincrement, " +
            "imgPath text not null);";
    public static final String BBDD_Crear_Tabla_FiguraImagen = "create table " + BBDD_Tabla_Figura_Imagen + " (idFiguraImagen integer primary key autoincrement, " +
            "idFigura integer not null, idImagen integer not null, fecha text not null);";
    public static final String BBDD_Crear_Tabla_CollectionImagen = "create table " + BBDD_Tabla_Collection_Imagen + " (idCollectionImagen integer primary key autoincrement, " +
            "idCollection integer not null, idImagen integer not null, fecha text not null);";
    public static final String BBDD_Crear_Tabla_Clase = "create table " + BBDD_Tabla_Clase + " (idClase integer primary key autoincrement, " +
            "nombreClase text not null);";
    public static final String BBDD_Crear_Tabla_FiguraClase = "create table " + BBDD_Tabla_Figura_Clase + " (idFiguraCLase integer primary key autoincrement, " +
            "idFigura integer not null, idClase integer not null);";


    public static final String BBDD_Conculta_Collection = "select * from Collection;";
    public static final String BBDD_Insertar_Collection = "insert into Collection(nombre, fecha) values('TransFormers'," +
            " '15/08/2016');";

    private final Context context;

    private DatabaseMyCollections databaseMyCollections;
    private  static SQLiteDatabase sqLiteDatabase;

    public DBAdapter(Context context) {
        this.context = context;
        //databaseMyCollections = new DatabaseMyCollections(context);
    }

}
