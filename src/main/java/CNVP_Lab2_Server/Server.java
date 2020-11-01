package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.Scanner;

public class Server {
    public static LinkedList<ClientListenerThread> serverList = new LinkedList<>();
    private static ServerSocket server;
    public static boolean isRunning = true;

    public static ServerSocket getServer() {
        if (server == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("enter port");
            int port = scanner.nextInt();
            try {
                server = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1")); //TODO:IP from console
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return server;
    }

}


