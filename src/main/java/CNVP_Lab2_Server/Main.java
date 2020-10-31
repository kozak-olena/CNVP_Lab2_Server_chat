package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket server = Server.createServer();
        System.out.println("Server is running");
        StopServer stopServer = new StopServer();
        boolean status = true;
        try {
            stopServer.start();
            while (StopServer.status) {
                Socket socket = server.accept();
                System.out.println("socket is connected");
                try {
                    ThreadsRepository threadsRepository = new ThreadsRepository(socket);
                    Server.serverList.add(threadsRepository);
                    try {
                        threadsRepository.join();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                } catch (IOException e) {
                    socket.close();
                }
            }
            stopServer.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            server.close();
        }
    }
}
