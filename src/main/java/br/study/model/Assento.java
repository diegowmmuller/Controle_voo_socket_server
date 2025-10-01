package br.study.model;

public class Assento {

    private final Integer numero;
    private Boolean disponivel;

    public Assento(Integer numero) {
        this.numero = numero;
        disponivel = true;
    }

    public Integer getNumero() {
        return numero;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Assento: " +
                "numero = " + numero +
                ", disponivel = " + disponivel +
                '.';
    }
}
