package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutosJpaController;
import model.Usuarios;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class cadastroserver {
    private static final int PORT = 4321;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado na porta " + PORT);

            ProdutosJpaController ctrlProd = new ProdutosJpaController();
            PessoaJpaController ctrlPessoa = new PessoaJpaController();
            MovimentoJpaController ctrlMov = new MovimentoJpaController();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Usuarios usuarioLogado =;
                new CadastroThread(clientSocket, ctrlProd, ctrlPessoa, ctrlMov, usuarioLogado).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}