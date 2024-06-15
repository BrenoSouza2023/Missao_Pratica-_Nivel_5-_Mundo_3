package cadastroserver;

import controller.MovimentoJpaController;
import controller.PessoaJpaController;
import controller.ProdutosJpaController;
import model.Movimento;
import model.Produtos;
import model.Usuarios;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class CadastroThread extends Thread {
    private Socket clientSocket;
    private ProdutosJpaController ctrlProd;
    private PessoaJpaController ctrlPessoa;
    private MovimentoJpaController ctrlMov;
    private Usuarios usuarioLogado;

    public CadastroThread(Socket socket, ProdutosJpaController ctrlProd, PessoaJpaController ctrlPessoa,
                         MovimentoJpaController ctrlMov, Usuarios usuarioLogado) {
        this.clientSocket = socket;
        this.ctrlProd = ctrlProd;
        this.ctrlPessoa = ctrlPessoa;
        this.ctrlMov = ctrlMov;
        this.usuarioLogado = usuarioLogado;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            // Lê login e senha
            String login = (String) in.readObject();
            String senha = (String) in.readObject();

            // Valida credenciais
            if (validarCredenciais(login, senha)) {
                out.writeObject("Credenciais válidas. Bem-vindo!");

                // Ciclo de resposta
                String command;
                while ((command = (String) in.readObject()) != null) {
                    if (command.equalsIgnoreCase("L")) {
                        List<Produtos> produtos = ctrlProd.findProdutoEntities();
                        out.writeObject(produtos);
                    } else if (command.equalsIgnoreCase("E") || command.equalsIgnoreCase("S")) {
                        try {
                            // Receber Id da pessoa e do produto
                            int idPessoa = (int) in.readObject();
                            int idProduto = (int) in.readObject();
                            int quantidade = (int) in.readObject();
                            double valorUnitario = (double) in.readObject();

                            // Gerar o objeto Movimento
                            Movimento movimento = new Movimento();
                            movimento.setUsuarios(usuarioLogado);
                            movimento.setTipoMovimento(command.equalsIgnoreCase("E") ? "E" : "S");
                            movimento.setIdPessoa(idPessoa);
                            movimento.setIdProduto(idProduto);
                            movimento.setQuantidade(quantidade);
                            movimento.setValorUnitario(valorUnitario);

                            // Persistir o movimento
                            ctrlMov.create(movimento);

                            // Atualizar a quantidade de produtos
                            Produtos produto = ctrlProd.findProduto(idProduto);
                            if (command.equalsIgnoreCase("E")) {
                                produto.setQuantidade(produto.getQuantidade() + quantidade);
                            } else {
                                produto.setQuantidade(produto.getQuantidade() - quantidade);
                            }
                            ctrlProd.edit(produto);

                            out.writeObject("Movimento registrado com sucesso.");
                        } catch (Exception e) {
                            out.writeObject("Erro ao processar movimento: " + e.getMessage());
                        }
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

    private boolean validarCredenciais(String login, String senha) {
        // Implementar validação de credenciais contra o banco de dados
        // Exemplo simplificado aqui para fins educacionais
        return "admin".equals(login) && "admin".equals(senha);
    }
}