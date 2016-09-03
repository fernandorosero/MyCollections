package com.thelastmonkey.mycollections.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thelastmonkey.mycollections.DBAdapter;
import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tatof on 16/08/2016.
 */
public class MyCollectionUtil {
    public static final String TAG_MY_COLLECTION = "My Colecction";
    static SQLiteDatabase db;

    public static String mostrarFecha(){
        String fechaFormateada;
        SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");
        fechaFormateada = formatea.format(new Date());
        return fechaFormateada;
        //editTextFechaCollection.setText(formatea.format(new Date()));
    }

    public static  void createFiguraCollection(Context contexto,String nombre, String fechaCompra, int precioCompra, int precioVenta, int venta){

        Log.i(MyCollectionUtil.TAG_MY_COLLECTION, "nombre:" + nombre + " FechaCompra:" + fechaCompra + " precio compra: " + precioCompra +  " precio venta: " + precioVenta + " venta: " + venta);

        String sentenciaSQL;

        sentenciaSQL = "insert into Figura(nombre, fechaCompra, precioCompra, precioVenta, venta) values('"+nombre+"', '"+fechaCompra+"', '"+precioCompra+"', '"+precioVenta+"', + '"+venta+"')";

        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);

        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();

        //Compruebo si existe la db
        if(db != null){
            Log.i(TAG_MY_COLLECTION, "++++++va todo ok++++++");
            db.execSQL(sentenciaSQL);
        }


    }

}
