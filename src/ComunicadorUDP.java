import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ComunicadorUDP {

    private static final int TAMANHO_BUFFER = 1024;

    public static DatagramPacket montaMensagem(
            String mensagem,
            String ip,
            int porta
    ) {
        try {
            byte[] buffer =
                    mensagem.getBytes(StandardCharsets.UTF_8);

            InetAddress endereco =
                    InetAddress.getByName(ip);

            return new DatagramPacket(
                    buffer,
                    buffer.length,
                    endereco,
                    porta
            );

        } catch (UnknownHostException e) {
            System.err.println(
                    "Erro: endereço IP ou nome do servidor inválido."
            );

            return null;
        }
    }

    public static boolean enviaMensagem(
            DatagramSocket socket,
            DatagramPacket pacote
    ) {
        if (socket == null) {
            System.err.println("Erro: socket não informado.");
            return false;
        }

        if (pacote == null) {
            System.err.println("Erro: pacote não informado.");
            return false;
        }

        try {
            socket.send(pacote);
            return true;

        } catch (IOException e) {
            System.err.println(
                    "Erro ao enviar mensagem: " + e.getMessage()
            );

            return false;
        }
    }

    public static DatagramPacket recebeMensagem(
            DatagramSocket socket
    ) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException(
                    "O socket não pode ser nulo."
            );
        }

        byte[] buffer = new byte[TAMANHO_BUFFER];

        DatagramPacket pacote =
                new DatagramPacket(
                        buffer,
                        buffer.length
                );

        socket.receive(pacote);

        return pacote;
    }

    public static String extraiMensagem(
            DatagramPacket pacote
    ) {
        if (pacote == null) {
            return null;
        }

        return new String(
                pacote.getData(),
                pacote.getOffset(),
                pacote.getLength(),
                StandardCharsets.UTF_8
        );
    }
}