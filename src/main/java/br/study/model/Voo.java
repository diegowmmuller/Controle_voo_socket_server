package br.study.model;

import java.util.ArrayList;
import java.util.List;

public class Voo {

    private static final Integer NUMERO_DE_ASSENTOS = 50;
    private String codigoVoo;
    private final List<Assento> assentos;

    public Voo(String codigoVoo) {
        this.codigoVoo = codigoVoo;
        assentos = new ArrayList<>();
        fillAssentos(assentos);
    }

    public String getCodigoVoo() {
        return codigoVoo;
    }

    public List<Assento> getAssentos() {
        return assentos;
    }

    public Assento findAssento(int numeroDoAssento){
        if (validarNumeroAssento(numeroDoAssento)){
            return null;
        }
        return assentos.get(numeroDoAssento - 1);
    }

    private void fillAssentos(List<Assento> seats){
        for(int i = 1; i<= NUMERO_DE_ASSENTOS; i++){
            assentos.add(new Assento(i));
        }
    }

    private boolean validarNumeroAssento(int numeroDoAssento){
        return numeroDoAssento < 1 || numeroDoAssento > NUMERO_DE_ASSENTOS;
    }}
