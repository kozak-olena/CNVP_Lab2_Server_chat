package CNVP_Lab2_Server;

import java.io.*;
import java.net.Socket;

public class ThreadsRepository extends Thread {
    private Socket socket;

    private BufferedReader in;
    private BufferedWriter out;

    public ThreadsRepository(Socket socket) throws IOException {
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();   //java.lang.Thread.start() method causes this thread to begin execution, the Java Virtual Machine calls the
        // run method of this thread.The result is that two threads
        // are running concurrently: the current thread
        // (which returns from the call to the start method)
        // and the other thread (which executes its run method)
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                if (message.equals("stop")) {
                    break;
                }
                for (ThreadsRepository thread : Server.serverList) {
                    if (this != thread) {                        //will not send msg to client who send it
                        thread.send(message);
                    } else {
                        System.out.println("Your message was successfully accepted and send to other users");
                    }
                }
            }

        } catch (IOException e) {
        }
    }

    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}

