import javax.swing.*;

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new CadastroUsuario().setVisible(true);
    });
}