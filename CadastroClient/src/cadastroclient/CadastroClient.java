package cadastroclient;

import model.Produtos;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class CadastroClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 4321;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar login e senha
            out.writeObject("op1");
            out.writeObject("op1");

            // Receber resposta do servidor
            String response = (String) in.readObject();
            System.out.println(response);

            if ("Credenciais válidas. Bem-vindo!".equals(response)) {
                // Enviar comando "L"
                out.writeObject("L");

                // Receber lista de produtos
                List<Produtos> produtos = (List<Produtos>) in.readObject();
                for (Produtos produto : produtos) {
                    System.out.println(produto.getNome());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}