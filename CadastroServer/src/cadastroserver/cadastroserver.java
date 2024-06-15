package cadastroserver;

import controller.ProdutosJpaController;
import controller.UsuariosJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.ServerSocket;
import java.net.Socket;

public class cadastroserver {
    private static final int PORT = 4321;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            // Criação da EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");

            // Criação dos controladores JPA
            ProdutosJpaController ctrl = new ProdutosJpaController(emf);
            UsuariosJpaController ctrlUsu = new UsuariosJpaController(emf);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new CadastroThread(clientSocket, ctrl, ctrlUsu).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}