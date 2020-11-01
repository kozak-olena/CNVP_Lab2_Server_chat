package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {
    public static LinkedList<ClientListenerThread> serverList = new LinkedList<>();
    public static ServerSocket server;

    static {
        try {
            server = new ServerSocket(5555, 10, InetAddress.getByName("127.0.0.1"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}


