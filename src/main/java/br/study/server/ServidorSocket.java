package br.study.server;

import br.study.model.ControladorVoo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {


    private int porta;
    private final ControladorVoo controladorVoo = new ControladorVoo();

    public ServidorSocket(int porta){
        this.porta = porta;
        rodarServidor();
    }

    public int calcularCodigoStatus(String query){
        String[] queryDividida = dividirQuery(query);

        String protocolo = queryDividida[0];
        String vooDesejado = queryDividida[1]; // ex: "A1"
        String assento = queryDividida[2];

        int resposta = -1;
        int assentoInt = Integer.parseInt(assento);

        if(protocolo.equals("C")){
            resposta = controladorVoo.verificarStatus(vooDesejado, assentoInt);
        } else if (protocolo.equals("M")) {
            resposta = controladorVoo.marcarVoo(vooDesejado, assentoInt);
        }
        return resposta;
    }

    private String[] dividirQuery(String query){
        return query.split(";");
    }

    public void rodarServidor() {

        try (ServerSocket server = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);
            System.out.println(" ");

            while (true) {
                Socket client = server.accept();

                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                        String query = bufferedReader.readLine();
                        System.out.println(query);
                        System.out.println(" ");
                        int response = calcularCodigoStatus(query);

                        printWriter.println("0: voo disponível\n1: assento indisponível\n2: assento inexistente\n3: voo inexistente\n4: marcação realizada\n");
                        printWriter.println("Codigo da resposta: " + response);

                        client.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (IOException e) {
            System.out.println("IO Error " + e.getMessage());
        }
    }

}
