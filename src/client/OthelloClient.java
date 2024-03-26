package src.client;

import src.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class OthelloClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void registerPlayer(PrintWriter writer, BufferedReader reader) throws IOException {
        // Solicitar ao jogador que forneça os dados de registro
        System.out.println("Nickname: ");
        String nickname = reader.readLine();

        System.out.println("Password: ");
        String password = reader.readLine();

        System.out.println("Nacionalidade: ");
        String nationality = reader.readLine();

        System.out.println("Idade:");
        int age = Integer.parseInt(reader.readLine());

        System.out.println("URL da sua foto: ");
        String photoUrl = reader.readLine();

        Player player = new Player(nickname, password, nationality, age, photoUrl);

        // Enviar mensagem de registro para o servidor
        writer.println("REGISTER;" + nickname + ";" + password + ";" + nationality + ";" + age + ";" + photoUrl);

        // Receber e exibir resposta do servidor
        String response = reader.readLine();
        System.out.println("Server response: " + response);
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            System.out.println("Connected to src.server.");

            // Lógica de comunicação com o servidor (leitura e escrita)



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

