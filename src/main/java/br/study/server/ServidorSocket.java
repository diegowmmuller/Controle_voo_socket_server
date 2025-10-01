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
    }

    public int calcularCodigoStatus(String query){
        String[] queryDividida = dividirQuery(query);

        String protocolo = queryDividida[0];
        System.out.println(protocolo);
        String vooDesejado = queryDividida[1];
        System.out.println(vooDesejado);
        String assento = queryDividida[2];
        System.out.println(assento);

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
            System.out.println();

            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente aceito: " + client.getInetAddress().getHostAddress());
                System.out.println();

                // Cria uma thread para cada cliente
                new Thread(() -> {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                         PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true)) {

                        String query;
                        while ((query = bufferedReader.readLine()) != null) { // Loop para múltiplas mensagens
                            System.out.println("Recebido: " + query);
                            int response = calcularCodigoStatus(query);
                            printWriter.println("Codigo da resposta: " + response);
                        }

                        System.out.println("Cliente desconectou: " + client.getInetAddress().getHostAddress());

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            client.close();
                        } catch (IOException ignored) {}
                    }
                }).start();
            }

        } catch (IOException e) {
            System.out.println("IO Error " + e.getMessage());
        }
    }

}
