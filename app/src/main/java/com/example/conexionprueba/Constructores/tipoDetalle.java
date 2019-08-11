package com.example.conexionprueba.Constructores;

public class tipoDetalle {

    /*
    Todos los servicios
    Nombre, fecha que se hizo y el costo.
     */
    private String nombreDetalle, fechaDetalle, costoDetalle, estadoDetalle;

    public tipoDetalle(String nombreDetalle, String fechaDetalle, String costoDetalle, String estadoDetalle) {
        this.nombreDetalle = nombreDetalle;
        this.fechaDetalle = fechaDetalle;
        this.costoDetalle = costoDetalle;
        this.estadoDetalle = estadoDetalle;
    }

    public String getNombreDetalle() {
        return nombreDetalle;
    }

    public void setNombreDetalle(String nombreDetalle) {
        this.nombreDetalle = nombreDetalle;
    }

    public String getFechaDetalle() {
        return fechaDetalle;
    }

    public void setFechaDetalle(String fechaDetalle) {
        this.fechaDetalle = fechaDetalle;
    }

    public String getCostoDetalle() {
        return costoDetalle;
    }

    public void setCostoDetalle(String costoDetalle) {
        this.costoDetalle = costoDetalle;
    }

    public String getEstadoDetalle() {
        return estadoDetalle;
    }

    public void setEstadoDetalle(String estadoDetalle) {
        this.estadoDetalle = estadoDetalle;
    }
}
