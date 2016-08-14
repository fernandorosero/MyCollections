package com.thelastmonkey.mycollections.bdmycollections;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelastmonkey.mycollections.DBAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tatof on 13/08/2016.
 */
public class DatabaseMyCollections extends SQLiteOpenHelper {

   // DatabaseMyCollections(Context context){
      //  super(context, BBDD_NOMBRE, null, BBDD_VERSION);
 //   }
    public DatabaseMyCollections(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String cadenaSQL;

        List<String> listadoTablas = new ArrayList<String>();
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_Collection);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_Figura);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_CollectionFigura);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_Imagen);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_FiguraImagen);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_CollectionImagen);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_Clase);
        listadoTablas.add(DBAdapter.BBDD_Crear_Tabla_FiguraClase);

        for(String ejecutarSentencias : listadoTablas){
            sqLiteDatabase.execSQL(ejecutarSentencias.toString());
        }
/*
        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_Collection;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_Figura;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_CollectionFigura;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_Imagen;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_FiguraImagen;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_CollectionImagen;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_Clase;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_FiguraClase;
        sqLiteDatabase.execSQL(cadenaSQL);
*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
