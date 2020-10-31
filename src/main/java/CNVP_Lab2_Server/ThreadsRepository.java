package CNVP_Lab2_Server;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ThreadsRepository extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;


    public ThreadsRepository(Socket socket) throws IOException {
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
            while (true) {
                if (Server.serverList.size() > 0) {
                    message = reader.readLine();
                    System.out.println(message);
                    String operation = JsonParser.deserializeOperation(message);
                    for (ThreadsRepository thread : Server.serverList) {
                        thread.send(message);
                        //TODO: send to client System.out.println("Your message was successfully accepted and send to other users");
                        // }
                        if (operation.equals("Disconnect")) {
                            this.downService();
                            break;
                        }
                    }
                } else break;
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
                for (ThreadsRepository repository : Server.serverList) {
                    if (repository.equals(this)) repository.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}

