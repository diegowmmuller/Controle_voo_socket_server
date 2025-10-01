package br.study.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorVoo {

    private static final Integer NUMERO_DE_VOO = 10;
    private final ArrayList<Voo> voos;

    public ControladorVoo() {
        voos = new ArrayList<>();
        preencherVoos(voos);
    }

    public Voo procurarVoo(String codigoVoo) {
        if(!validarCodigoVoo(codigoVoo)){
            return null;
        }

        for (Voo voo : voos) {
            if (Objects.equals(voo.getCodigoVoo(), codigoVoo)) {
                return voo;
            }
        }
        return null;
    }

    public Integer verificarStatus(String codigoVoo, int numeroAssento){
        Voo vooBuscado = procurarVoo(codigoVoo);
        if(vooBuscado == null){
            return 3;
        }

        if(vooBuscado.buscarAssento(numeroAssento) == null){
            return 2;
        }

        if(!vooBuscado.buscarAssento(numeroAssento).getDisponivel()){
            return 1;
        }

        return 0;
    }

    public Integer marcarVoo(String codigoVoo, int numeroAssento){

        int disponibilidade = verificarStatus(codigoVoo, numeroAssento);
        if(disponibilidade == 0){
            int intCodigoVoo = separarCodigoVoo(codigoVoo);
            Voo voo =voos.get(intCodigoVoo);
            voo.buscarAssento(numeroAssento).setDisponivel(false);
            return 4;
        }

        return disponibilidade;
    }

    private void preencherVoos(List<Voo> voos){
        for(int i = 1; i <= NUMERO_DE_VOO; i++){
            voos.add(new Voo("A"+i));
        }
    }

    private boolean validarCodigoVoo(String codigoVoo){
        String[] partes = codigoVoo.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        int num = Integer.parseInt(partes[1]);
        return num >= 0 && num <= NUMERO_DE_VOO;
    }

    public Integer separarCodigoVoo(String codigoVoo){
        String[] partes = codigoVoo.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        return Integer.parseInt(partes[1]) - 1;
    }
}
