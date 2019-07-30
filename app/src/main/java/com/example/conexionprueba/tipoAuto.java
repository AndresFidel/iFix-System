package com.example.conexionprueba;

public class tipoAuto {
    private String modelo,marca,ano,fecha;
    private int imgAuto;

    public tipoAuto(String modelo, String marca, String ano, String fecha, int imgAuto) {
        this.modelo = modelo;
        this.marca = marca;
        this.imgAuto = imgAuto;
        this.ano=ano;
        this.fecha=fecha;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    //Constructor de año
    public String getAno(){
        return ano;
    }

    public void setAno(String ano){
        this.ano=ano;
    }

    //Constructor de fecha
    public String getFecha(){
        return fecha;
    }

    public void setFecha(String fecha){
        this.fecha=fecha;
    }

    public int getImgAuto() {
        return imgAuto;
    }

    public void setImgAuto(int imgAuto) {
        this.imgAuto = imgAuto;
    }

}
