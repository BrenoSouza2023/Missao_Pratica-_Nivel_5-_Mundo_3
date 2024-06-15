package cadastroserver;

import controller.ProdutosJpaController;
import controller.UsuariosJpaController;
import model.Produtos;
import model.Usuarios;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class CadastroThread extends Thread {
    private Socket clientSocket;
    private ProdutosJpaController ctrl;
    private UsuariosJpaController ctrlUsu;

    public CadastroThread(Socket socket, ProdutosJpaController ctrl, UsuariosJpaController ctrlUsu) {
        this.clientSocket = socket;
        this.ctrl = ctrl;
        this.ctrlUsu = ctrlUsu;
    }

    CadastroThread(ProdutosJpaController ctrl, UsuariosJpaController ctrlUsu, Socket clientSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            // Lê login e senha
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            // Valida credenciais
            Usuarios usuario = ctrlUsu.findUsuario(login, senha);
            if (usuario != null) {
                out.writeObject("Credenciais válidas. Bem-vindo!");

                // Ciclo de resposta
                String command;
                while ((command = (String) in.readObject()) != null) {
                    if (command.equalsIgnoreCase("L")) {
                        List<Produtos> produtos = ctrl.findProdutoEntities();
                        out.writeObject(produtos);
                    } else {
                        out.writeObject("Comando não reconhecido.");
                    }
                }
            } else {
                out.writeObject("Credenciais inválidas. Conexão encerrada.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
