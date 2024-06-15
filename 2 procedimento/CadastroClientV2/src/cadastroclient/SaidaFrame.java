package cadastroclient;

import javax.swing.*;

public class SaidaFrame extends JDialog {

    public JTextArea texto;

    public SaidaFrame() {
        setBounds(100, 100, 400, 300);
        setModal(false);

        texto = new JTextArea();
        getContentPane().add(new JScrollPane(texto));

        setVisible(true);
    }

    public JTextArea getTexto() {
        return texto;
    }

    public void setTexto(JTextArea texto) {
        this.texto = texto;
    }
}
