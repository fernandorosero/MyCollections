package com.thelastmonkey.mycollections.bdmycollections;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thelastmonkey.mycollections.DBAdapter;

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

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_MyCollection;
        sqLiteDatabase.execSQL(cadenaSQL);

        cadenaSQL = DBAdapter.BBDD_Crear_Tabla_Figura;
        sqLiteDatabase.execSQL(cadenaSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
