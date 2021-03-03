package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = Server.getServer();
        System.out.println("Server is running");
        ServerShutdownThread serverShutdownThread = new ServerShutdownThread();
        try {
            serverShutdownThread.start();
            while (Server.isRunning) {
                Socket socket = server.accept();
                System.out.println("socket is connected");
                ClientListenerThread clientListenerThread = new ClientListenerThread(socket);
                Server.serverList.add(clientListenerThread);
            }
            serverShutdownThread.join();
        } catch (IOException ioException) {
            if (!Server.isRunning) {
                System.out.println("server closed");
            } else {
                throw ioException;
            }
        } finally {
            server.close();
        }
    }
}
