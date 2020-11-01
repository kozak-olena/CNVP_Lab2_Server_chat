package CNVP_Lab2_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StopServer extends Thread {
    public static boolean status = true;
    public static BufferedReader clientInput;

    @Override
    public void run() {
        try {
            String inputMessage;
            clientInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("To stop server press 'stop'");
                inputMessage = clientInput.readLine();
                status = getStatus(inputMessage);
                for (ClientListenerThread clientListenerThread : Server.serverList) {
                    clientListenerThread.socket.close();
                }
                break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean getStatus(String input) throws IOException {
        if (input.equals("stop")) {
            System.out.println("server is closed");
            Server.server.close();
            return false;
        } else {
            return true;
        }
    }
}
