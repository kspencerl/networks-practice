import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class ServidorTCP {
    public static void main(String[] args) throws IOException {
        int porta = 5000;
        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("[TCP] Servidor aguardando conexões na porta " + porta + "...");
            try (Socket cliente = servidor.accept();
                 BufferedReader entrada = new BufferedReader(
                         new InputStreamReader(cliente.getInputStream()));
                 PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)) {

                System.out.println("[TCP] Cliente conectado: " + cliente.getRemoteSocketAddress());
                String mensagem;
                while ((mensagem = entrada.readLine()) != null) {
                    if(mensagem.equalsIgnoreCase("hora")) {
                        String horaAtual = LocalTime.now().toString().substring(0, 8);
                        saida.println("Hora atual: " + horaAtual);
                        System.out.println("[TCP] Recebido: " + horaAtual);
                        continue;
                    }
                    System.out.println("[TCP] Recebido: " + mensagem);
                    if (mensagem.equalsIgnoreCase("sair")) {
                        saida.println("Encerrando conexão. Até mais!");
                        break;
                    }
                    saida.println("Monitor responde: recebi sua mensagem -> \"" + mensagem + "\"");
                }
            }
        }
        System.out.println("[TCP] Servidor encerrado.");
    }
}