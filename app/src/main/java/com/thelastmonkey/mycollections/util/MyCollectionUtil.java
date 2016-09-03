package com.thelastmonkey.mycollections.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tatof on 16/08/2016.
 */
public class MyCollectionUtil {
    public static final String TAG_MY_COLLECTION = "My Colecction";

    public static String mostrarFecha(){
        String fechaFormateada;
        SimpleDateFormat formatea = new SimpleDateFormat("dd/MM/yyyy");
        fechaFormateada = formatea.format(new Date());
        return fechaFormateada;
        //editTextFechaCollection.setText(formatea.format(new Date()));
    }
}
