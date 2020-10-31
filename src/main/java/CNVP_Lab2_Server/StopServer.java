package CNVP_Lab2_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StopServer extends Thread {
    public static boolean status = true;

    @Override
    public void run() {
        try {
            String inputMessage;

            while (true) {
                System.out.println("To stop server press 'stop'");
                inputMessage = Server.clientInput.readLine();
                status = getStatus(inputMessage);
                break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean getStatus(String input) {
        if (input.equals("stop")) {
            return false;
        } else {
            return true;
        }
    }
}
