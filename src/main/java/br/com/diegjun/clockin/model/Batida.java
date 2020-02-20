package br.com.diegjun.clockin.model;

public enum Batida {

    ENTRADA("E"), SAIDA("S");

    private Batida(String tipo) {
        this.tipo = tipo;
    }

    private String tipo;

    public String getTipo() {
        return this.tipo;
    }

}
