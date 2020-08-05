package com.example.matyena.tresenrayafinal.entidades;

public class Jugador {

    private Integer id;
    private byte[] imagen;
    private String jugador;
    private String victorias;
    private String empates;
    private String partidas;

    public Jugador(Integer id, String jugador, String partidas, String victorias, String empates, byte[] imagen) {
        this.id = id;
        this.imagen = imagen;
        this.jugador = jugador;
        this.partidas = partidas;
        this.victorias = victorias;
        this.empates = empates;
    }

    public Jugador() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getPartidas() {
        return partidas;
    }

    public void setPartidas(String partidas) {
        this.partidas = partidas;
    }

    public String getVictorias() {
        return victorias;
    }

    public void setVictorias(String victorias) {
        this.victorias = victorias;
    }

    public String getEmpates() {
        return empates;
    }

    public void setEmpates(String empates) {
        this.empates = empates;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
