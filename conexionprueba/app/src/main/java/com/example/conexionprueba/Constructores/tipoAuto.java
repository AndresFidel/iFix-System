package com.example.conexionprueba.Constructores;

public class tipoAuto {
    private String modelo,marca,ano,noAuto;
    private int imgAuto;

    public tipoAuto(String noAuto, String modelo, String marca, String ano, int imgAuto) {
        this.noAuto=noAuto;
        this.modelo = modelo;
        this.marca = marca;
        this.imgAuto = imgAuto;
        this.ano=ano;
    }

    public String getNoAuto() {
        return noAuto;
    }

    public void setNoAuto(String noAuto) {
        this.noAuto = noAuto;
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


    public int getImgAuto() {
        return imgAuto;
    }

    public void setImgAuto(int imgAuto) {
        this.imgAuto = imgAuto;
    }

}
