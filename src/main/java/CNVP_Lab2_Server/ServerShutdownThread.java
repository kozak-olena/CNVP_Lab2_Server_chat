package CNVP_Lab2_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerShutdownThread extends Thread {
    public static BufferedReader clientInput;

    @Override
    public void run() {
        try {
            String inputMessage;
            clientInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("To stop server enter \"stop\"");
                inputMessage = clientInput.readLine();
                if (inputMessage.equals("stop")) {
                    Server.isRunning = false;
                    System.out.println("server is closed");
                    Server.getServer().close();
                    for (ClientListenerThread clientListenerThread : Server.serverList) {
                        clientListenerThread.socket.close();
                    }
                    break;
                } else {
                    Server.isRunning = true;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
