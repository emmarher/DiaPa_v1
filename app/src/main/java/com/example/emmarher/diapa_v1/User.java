package com.example.emmarher.diapa_v1;

public class User {
    public String dni;
    public String nombre;
    public String mail;
    public String edad;
    public String genero;
    public String etapa;
    public String acx;
    public String acy;
    public String acz;
    public String gyx;
    public String gyy;
    public String gyz;
    public String pulso;
    public String temp;

    public User(){
        //Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String dni, String nombre, String mail, String edad, String genero, String etapa, String acx, String acy, String acz, String gyx, String gyy, String gyz, String pulso, String temp){
        this.dni = dni;
        this.mail = mail;
        this.nombre=nombre;
        this.edad=edad;
        this.genero=genero;
        this.etapa=etapa;
        this.acx=acx;
        this.acy=acy;
        this.acz=acz;
        this.gyx=gyx;
        this.gyy=gyy;
        this.gyz=gyz;
        this.pulso=pulso;
        this.temp=temp;
    }

    public String getDni() { return dni; }

    public void setDni(String dni) {this.dni = dni;}

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {this.nombre = nombre; }

    public String getMail() { return mail; }

    public void setMail(String mail) {this.mail = mail; }

    public String getEdad() { return edad; }

    public void setEdad(String edad) {this.edad = edad; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) {this.genero = genero;}

    public String getEtapa() { return etapa; }

    public void setEtapa(String etapa) { this.etapa = etapa; }

    public String getAcx() { return acx; }

    public void setAcx(String acx) {this.acx = acx; }

    public String getAcy() { return acy; }

    public void setAcy(String acy) {this.acy = acy;}

    public String getAcz() { return acz; }

    public void setAcz(String acz) {this.acz = acz; }

    public String getGyx() { return gyx; }

    public void setGyx(String gyx) {this.gyx = gyx; }

    public String getGyy() { return gyy; }

    public void setGyy(String gyy) {this.gyy = gyy;}

    public String getGyz() { return gyz; }

    public void setGyz(String gyz) {this.gyz = gyz; }

    public String getPulso() { return pulso; }

    public void setPulso(String pulso) {this.pulso = pulso; }

    public String getTemp() { return temp; }

    public void setTemp(String temp) {this.temp = temp; }
}
