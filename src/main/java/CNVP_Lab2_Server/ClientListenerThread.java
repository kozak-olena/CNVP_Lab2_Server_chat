package CNVP_Lab2_Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientListenerThread extends Thread {
    public Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;


    public ClientListenerThread(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        System.out.println("Listener thread started for " + this.socket.getInetAddress());
        try {
            while (Server.isRunning) {
                if (Server.serverList.size() > 0) {
                    String message = reader.readLine();
                    System.out.println(message);                //TODO: remove to avoid showing sensitive data
                    String operation = JsonParser.deserializeOperation(message);
                    for (ClientListenerThread thread : Server.serverList) {
                        if (thread != this) {
                            thread.send(message);
                        }
                    }
                    if (operation.equals("Disconnect")) {
                        this.shutdown();
                        break;
                    }
                } else break;
            }

        } catch (SocketException exception) {
            if (!Server.isRunning) {
                System.out.println("Listener thread stopped for " + this.socket.getInetAddress());
            } else {
                exception.printStackTrace();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void send(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException exception) {
            System.out.println("failed to send message");
            exception.printStackTrace();
        }
    }

    private void shutdown() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
            reader.close();
            writer.close();
            Server.serverList.remove(this);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

