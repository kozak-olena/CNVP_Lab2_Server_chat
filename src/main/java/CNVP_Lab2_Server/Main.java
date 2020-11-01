package CNVP_Lab2_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = Server.server;
        System.out.println("Server is running");
        StopServer stopServer = new StopServer();
        boolean status = StopServer.status;
        try {
            stopServer.start();
            //Semaphore semaphore = new Semaphore(10);    TODO: ??????
            while (status) {
                if (stopServer.isAlive()) {
                    //semaphore.acquire();
                    Socket socket = server.accept();
                    System.out.println("socket is connected");
                    try {
                        ClientListenerThread threadsRepository = new ClientListenerThread(socket);
                        Server.serverList.add(threadsRepository);
                        // try {  TODO:JOIN
                        //   threadsRepository.join();
                        // } catch (InterruptedException exception) {
                        //     exception.printStackTrace();
                        // }
                    } catch (IOException e) {
                        socket.close();
                    } //finally {
                    //semaphore.release();
                    //}
                }
            }
            stopServer.join();
        } catch (IOException ioException) {
            if (!StopServer.status) {
                System.out.println("server closed");
            } else {
                throw ioException;
            }
        } finally {
            server.close();
        }

    }
}
