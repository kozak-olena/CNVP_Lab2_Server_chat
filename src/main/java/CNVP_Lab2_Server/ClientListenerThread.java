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
        String message;
        try {
            while (StopServer.status) {
                if (Server.serverList.size() > 0) {
                    message = reader.readLine();
                    System.out.println(message);
                    String operation = JsonParser.deserializeOperation(message);
                    for (ClientListenerThread thread : Server.serverList) {
                        if (thread != this) {
                            thread.send(message);
                        } else {
                            if (operation.equals("UserConnection")) {
                                Story.printStory(writer);
                            }
                        }
                        if (operation.equals("Disconnect")) {
                            this.downService();
                            break;
                        }
                    }
                } else break;
            }

        } catch (SocketException exception) {
            if (!StopServer.status) {
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
        } catch (IOException ignored) {
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                reader.close();
                writer.close();
                for (ClientListenerThread repository : Server.serverList) {
                    if (repository.equals(this)) repository.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}

