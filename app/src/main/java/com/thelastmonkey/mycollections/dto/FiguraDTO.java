package com.thelastmonkey.mycollections.dto;

/**
 * Created by FERNANDO on 10/09/2016.
 */
public class FiguraDTO {

    String idFigura;
    String nombre;
    String fechaCompra;
    String precioCompra;

    public String getIdFigura() {
        return idFigura;
    }

    public void setIdFigura(String idFigura) {
        this.idFigura = idFigura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    String precioVenta;
    String venta;

}
