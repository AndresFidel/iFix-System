package com.example.conexionprueba.Constructores;

public class tipoDetalle {

    /*
    Todos los servicios
    Nombre, fecha que se hizo y el costo.
     */

    private  String noServicio, servicio, fechaEntrada, fechaSalida, fechaTerminacion, estado, desc, costo;

    public tipoDetalle(String noServicio, String servicio, String fechaEntrada, String fechaSalida, String fechaTerminacion, String estado, String desc, String costo) {
        this.noServicio = noServicio;
        this.servicio = servicio;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.fechaTerminacion = fechaTerminacion;
        this.estado = estado;
        this.desc = desc;
        this.costo = costo;
    }

    public String getNoServicio() {
        return noServicio;
    }

    public void setNoServicio(String noServicio) {
        this.noServicio = noServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(String fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    /* private String nombreDetalle, fechaDetalle, costoDetalle;

    public tipoDetalle(String nombreDetalle, String fechaDetalle, String costoDetalle){
        this.nombreDetalle=nombreDetalle;
        this.fechaDetalle=fechaDetalle;
        this.costoDetalle=costoDetalle;
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
    }*/
}
