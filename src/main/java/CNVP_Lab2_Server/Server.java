package CNVP_Lab2_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {
    public static LinkedList<ThreadsRepository> serverList = new LinkedList<>();
    public static BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
    public static ServerSocket createServer() throws IOException {
        final int port = 5542;
        ServerSocket server = new ServerSocket(5542, 10, InetAddress.getByName("127.0.0.1"));
        return server;
    }
}


