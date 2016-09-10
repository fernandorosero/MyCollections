package com.thelastmonkey.mycollections.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thelastmonkey.mycollections.DBAdapter;
import com.thelastmonkey.mycollections.bdmycollections.DatabaseMyCollections;
import com.thelastmonkey.mycollections.dto.FiguraDTO;
import com.thelastmonkey.mycollections.dto.FiguraImagenDTO;
import com.thelastmonkey.mycollections.dto.ImagenDTO;

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
    public static  String createFiguraCollection(Context contexto,String idCollection,String nombre, String fechaCompra, int precioCompra, int precioVenta, int venta){
        String sentenciaSQL;
        String idFigura;
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
            idFigura = idFiguraCreado;
            cursorAgregarFigura.close();
            createCollectionFigura(contexto, idCollection,idFiguraCreado);
            return idFiguraCreado;
        }
        db.close();
        db_myCollection.close();
        return null;
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


    public static void createImagenDeFiguras(Context contexto, List<String> pathFiguras, String idFigura){

        String sentenciaSQL;
        sentenciaSQL = "";
        //Conecto a la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre, null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        //Compruebo si existe
        if (db != null) {

            Log.i("Aqui empieza todo", String.valueOf(pathFiguras.size()));
            for (int i = 0; i < pathFiguras.size(); i++) {
                sentenciaSQL = "insert into FiguraImagen(idFigura,idImagen,fecha) values('"+idFigura+"','"+createImagen(pathFiguras.get(i).toString(),db)+"','"+MyCollectionUtil.mostrarFecha()+"');";
                db.execSQL(sentenciaSQL);
            }
        }

    }

    public static String createImagen(String pathImagen, SQLiteDatabase db){
        String idImagen="";
        String sentenciaSQL;
        Cursor cursorAgregarImagen;
        sentenciaSQL = "insert into Imagen(imgPath) values('"+pathImagen+"');";
        db.execSQL(sentenciaSQL);
        sentenciaSQL = "select * from Imagen where idImagen = (select max(idImagen) FROM Imagen);";
        cursorAgregarImagen = db.rawQuery(sentenciaSQL,null);
        cursorAgregarImagen.moveToFirst();
        idImagen = cursorAgregarImagen.getString(cursorAgregarImagen.getColumnIndex("idImagen"));
        cursorAgregarImagen.close();
        return idImagen;
    }

    public static FiguraDTO getFiguraDTO(Context contexto, String idFigura){
        FiguraDTO figuraDTO = new FiguraDTO();
        String sentenciaSQL;
        sentenciaSQL = "select * from Figura where idFigura = '"+idFigura+"'";
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        Cursor cursorGetFigura;
        //Compruebo si existe la db
        if(db != null){
            cursorGetFigura = db.rawQuery(sentenciaSQL,null);
            cursorGetFigura.moveToFirst();
            figuraDTO.setNombre(cursorGetFigura.getString(cursorGetFigura.getColumnIndex("nombre")));
            figuraDTO.setFechaCompra(cursorGetFigura.getString(cursorGetFigura.getColumnIndex("fechaCompra")));
            figuraDTO.setPrecioCompra(cursorGetFigura.getString(cursorGetFigura.getColumnIndex("precioCompra")));
            figuraDTO.setPrecioVenta(cursorGetFigura.getString(cursorGetFigura.getColumnIndex("precioVenta")));
            figuraDTO.setVenta(cursorGetFigura.getString(cursorGetFigura.getColumnIndex("venta")));
        }
        return  figuraDTO;
    }

    public static List<FiguraImagenDTO> listadoFiguraImagenDTO(Context contexto, String idFigura){
        List<FiguraImagenDTO> figuraImagenDTO =  new ArrayList<FiguraImagenDTO>();
        Log.i("idFigura",idFigura);
        String sentenciaSQL;
        sentenciaSQL = "select * from FiguraImagen where idFigura='"+idFigura+"';";
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        Cursor cursorGetListFiguraImagen;
        //Compruebo si existe la db
        if(db != null) {
            cursorGetListFiguraImagen = db.rawQuery(sentenciaSQL, null);
            cursorGetListFiguraImagen.moveToFirst();
            Log.i("cursosssss",String.valueOf(cursorGetListFiguraImagen.getCount()));
            for(int i=0; i<cursorGetListFiguraImagen.getCount(); i++){
                FiguraImagenDTO figImDTO = new FiguraImagenDTO();
                figImDTO.setIdFiguraImagen(cursorGetListFiguraImagen.getString(cursorGetListFiguraImagen.getColumnIndex("idFiguraImagen")));
                figImDTO.setIdFigura(cursorGetListFiguraImagen.getString(cursorGetListFiguraImagen.getColumnIndex("idFigura")));
                figImDTO.setIdImagen(cursorGetListFiguraImagen.getString(cursorGetListFiguraImagen.getColumnIndex("idImagen")));
                figImDTO.setFecha(cursorGetListFiguraImagen.getString(cursorGetListFiguraImagen.getColumnIndex("fecha")));
                figuraImagenDTO.add(figImDTO);
                cursorGetListFiguraImagen.moveToNext();
            }
        }

            return  figuraImagenDTO;
    }

    public static List<ImagenDTO> listadoImagenDTO(Context contexto, List<FiguraImagenDTO> listFiguraImagenDTO){
        List<ImagenDTO> listImagenDTO = new ArrayList<ImagenDTO>();
        String sentenciaSQL;
        sentenciaSQL = "select * from imagen where idImagen='";
        //Conecto con la bbdd
        DatabaseMyCollections db_myCollection = new DatabaseMyCollections(contexto, DBAdapter.BBDD_Nombre,null, DBAdapter.BBDD_VERSION);
        //Acceso de escritura
        db = db_myCollection.getWritableDatabase();
        Cursor cursorGetListImagen;
        //Compruebo si existe la db

        if(db != null) {
            for(int i = 0; i<listFiguraImagenDTO.size(); i++) {

                cursorGetListImagen = db.rawQuery(sentenciaSQL + listFiguraImagenDTO.get(i).getIdImagen()+ "';", null);
                cursorGetListImagen.moveToFirst();
                ImagenDTO imgDTO = new ImagenDTO();
                imgDTO.setIdImagen(cursorGetListImagen.getString(cursorGetListImagen.getColumnIndex("idImagen")));
                imgDTO.setImgPath(cursorGetListImagen.getString(cursorGetListImagen.getColumnIndex("imgPath")));
                listImagenDTO.add(imgDTO);
                cursorGetListImagen.close();
            }
        }

        return listImagenDTO;
    }

}
