import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroUsuario extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JButton btnEnviar;

    public CadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        btnEnviar = new JButton("Cadastrar");
        painel.add(new JLabel());
        painel.add(btnEnviar);

        add(painel);

        btnEnviar.addActionListener((ActionEvent e) -> {
            Usuario usuario = new Usuario(
                    txtNome.getText(),
                    txtEmail.getText()
            );
            ClienteUDP cliente = new ClienteUDP();
            String resposta = cliente.cadastrar(usuario);
            JOptionPane.showMessageDialog(
                    this,
                    "Resposta do servidor: " + resposta
            );
        });
    }

}