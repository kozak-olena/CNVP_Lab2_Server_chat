package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Server.port);
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();// новий потік
                Server.serverList.add(new ThreadsRepository(socket));
            }
        } finally {
            server.close();
        }
    }
}
