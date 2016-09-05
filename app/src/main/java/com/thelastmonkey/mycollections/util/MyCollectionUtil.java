package com.thelastmonkey.mycollections.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thelastmonkey.mycollections.DBAdapter;
import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    }

    public static List<String> listadoCollectionFigura(Context contexto,String idCollection){

        String sentenciaSQL;
        sentenciaSQL = "select * from CollectionFigura where idCollection='"+idCollection+"'";

        Cursor cursorlistFigurasPorColeccion;
        List<String> listadoIdFigura = new ArrayList<String>();

        //Conecto a la BBDD
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre, null, DBAdapter.BBDD_VERSION);
        //Acceso escritura
        db = db_myCollection.getWritableDatabase();

        if(db != null){
            cursorlistFigurasPorColeccion = db.rawQuery(sentenciaSQL, null);
            cursorlistFigurasPorColeccion.moveToFirst();

            Log.i("numero de elementos:",String.valueOf(cursorlistFigurasPorColeccion.getCount()));

            for(int i=0; i<cursorlistFigurasPorColeccion.getCount(); i++){
                listadoIdFigura.add(cursorlistFigurasPorColeccion.getString(cursorlistFigurasPorColeccion.getColumnIndex("idFigura")));
                cursorlistFigurasPorColeccion.moveToNext();
            }

            cursorlistFigurasPorColeccion.close();
        }

        return listadoIdFigura;
    }

    public static List<String> returnNombresFiguras(Context contexto, List<String> idFiguras){
        List<String> listaNombresFiguras = new ArrayList<>();
        Cursor cursorNombreFigura = null;
        String sentenciaSQL;
        String idfigura="";
        sentenciaSQL = "select * from Figura where idFigura=";
        //Conecto a la BBDD
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre, null, DBAdapter.BBDD_VERSION);
        //Acceso escritura
        db = db_myCollection.getWritableDatabase();
        if(db != null){
            for(int i=0; i<idFiguras.size(); i++) {
                idfigura = "'"+idFiguras.get(i)+"'";
                cursorNombreFigura = db.rawQuery(sentenciaSQL + idfigura, null);
                cursorNombreFigura.moveToFirst();
                listaNombresFiguras.add(cursorNombreFigura.getString(cursorNombreFigura.getColumnIndex("nombre")));
            }

            //cursorNombreFigura.close();
        }
        return listaNombresFiguras;
    }
    public static  void createFiguraCollection(Context contexto,String idCollection,String nombre, String fechaCompra, int precioCompra, int precioVenta, int venta){
        Log.i("VEnta++++++", String.valueOf(venta));
        String sentenciaSQL;
        sentenciaSQL = "insert into Figura(nombre, fechaCompra, precioCompra, precioVenta, venta) values('"+nombre+"', '"+fechaCompra+"', '"+precioCompra+"', '"+precioVenta+"', + '"+venta+"')";
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        Cursor cursorAgregarFigura;
        String idFiguraCreado;
        //Compruebo si existe la db
        if(db != null){
            Log.i(TAG_MY_COLLECTION, "++++++va todo ok++++++");
            db.execSQL(sentenciaSQL);
            sentenciaSQL = "select * from Figura where idFigura = (select max(idFigura) FROM Figura);";
            cursorAgregarFigura = db.rawQuery(sentenciaSQL,null);
            cursorAgregarFigura.moveToFirst();
            idFiguraCreado = cursorAgregarFigura.getString(cursorAgregarFigura.getColumnIndex("idFigura"));
            cursorAgregarFigura.close();
            createCollectionFigura(contexto, idCollection,idFiguraCreado);
        }
        db.close();
        db_myCollection.close();
    }

    public static void createCollectionFigura(Context contexto,String idCollection, String idFigura) {
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre, null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        //Compruebo si existe la db
        if (db != null) {
            String sentenciaSQL;
            sentenciaSQL = "insert into CollectionFigura(idCollection, idFigura, fecha) values('" + idCollection + "', '" + idFigura + "', '" + mostrarFecha() + "')";
            db.execSQL(sentenciaSQL);
        }
        db.close();
        db_myCollection.close();
    }

}
