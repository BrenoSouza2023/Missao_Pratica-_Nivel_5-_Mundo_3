package cadastroclient;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class CadastroClientV2 {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 4321;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Instancia a janela de saída e a thread para preenchimento assíncrono
            SaidaFrame saidaFrame = new SaidaFrame();
            new ThreadClient(in, saidaFrame.getTexto()).start();

            // Escrever login e senha
            System.out.println("Digite o login:");
            String login = reader.readLine();
            out.writeObject(login);

            System.out.println("Digite a senha:");
            String senha = reader.readLine();
            out.writeObject(senha);

            // Receber resposta do servidor
            String resposta = (String) in.readObject();
            saidaFrame.getTexto().append(resposta + "\n");

            // Ciclo de interação com o servidor
            String command;
            do {
                System.out.println("Menu:");
                System.out.println("L - Listar");
                System.out.println("E - Entrada de produtos");
                System.out.println("S - Saída de produtos");
                System.out.println("X - Finalizar");

                command = reader.readLine();
                out.writeObject(command);

                if (command.equalsIgnoreCase("L")) {
                    // Resposta recebida e tratada na ThreadClient
                } else if (command.equalsIgnoreCase("E") || command.equalsIgnoreCase("S")) {
                    // Entrada ou saída de produtos
                    System.out.println("Digite o Id da pessoa:");
                    int idPessoa = Integer.parseInt(reader.readLine());
                    out.writeObject(idPessoa);

                    System.out.println("Digite o Id do produto:");
                    int idProduto = Integer.parseInt(reader.readLine());
                    out.writeObject(idProduto);

                    System.out.println("Digite a quantidade:");
                    int quantidade = Integer.parseInt(reader.readLine());
                    out.writeObject(quantidade);

                    System.out.println("Digite o valor unitário:");
                    double valorUnitario = Double.parseDouble(reader.readLine());
                    out.writeObject(valorUnitario);

                    // Resposta recebida e tratada na ThreadClient
                }

            } while (!command.equalsIgnoreCase("X"));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
