import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class ClienteUDP {

    private final Usuario usuario;
    private final String ipServidor;
    private final int portaServidor;

    public ClienteUDP(
            Usuario usuario,
            String ipServidor,
            int portaServidor
    ) {
        this.usuario = usuario;
        this.ipServidor = ipServidor;
        this.portaServidor = portaServidor;
    }

    public String cadastrar(Usuario usuario) {
        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);

            String mensagem =
                    "CADASTRO;" +
                            usuario.getNome() + ";" +
                            usuario.getEmail();

            DatagramPacket pacote =
                    ComunicadorUDP.montaMensagem(
                            mensagem,
                            ipServidor,
                            portaServidor
                    );

            if (pacote == null) {
                return "Erro: não foi possível localizar o servidor.";
            }

            ComunicadorUDP.enviaMensagem(socket, pacote);

            DatagramPacket resposta =
                    ComunicadorUDP.recebeMensagem(socket);

            if (resposta == null) {
                return "Erro: nenhuma resposta foi recebida.";
            }

            return new String(
                    resposta.getData(),
                    resposta.getOffset(),
                    resposta.getLength(),
                    StandardCharsets.UTF_8
            );

        } catch (SocketTimeoutException e) {
            return "Erro: o servidor demorou para responder.";

        } catch (Exception e) {
            return "Erro na comunicação: " + e.getMessage();
        }
    }
}