package cadastroclient;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.List;

public class ThreadClient extends Thread {

    private ObjectInputStream entrada;
    private JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object response = entrada.readObject();
                if (response instanceof String) {
                    String message = (String) response;
                    SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
                } else if (response instanceof List) {
                    List<?> produtos = (List<?>) response;
                    for (Object produto : produtos) {
                        SwingUtilities.invokeLater(() -> textArea.append(produto.toString() + "\n"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
