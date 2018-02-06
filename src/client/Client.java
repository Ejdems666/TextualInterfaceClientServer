package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        try {
            Socket connection = new Socket("localhost", 1234);
            startClientOutputThread(connection);
            startClientInputThread(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startClientOutputThread(Socket connection) throws IOException {
        PrintWriter serverOut = new PrintWriter(connection.getOutputStream(), true);
        Scanner consoleIn = new Scanner(System.in);
        ClientOutputRunnable clientOutputRunnable = new ClientOutputRunnable(connection, consoleIn, serverOut);
        new Thread(clientOutputRunnable).start();
    }

    private void startClientInputThread(Socket connection) throws IOException {
        Scanner serverIn = new Scanner(connection.getInputStream());
        ClientInputRunnable clientInputRunnable = new ClientInputRunnable(connection, serverIn, System.out);
        new Thread(clientInputRunnable).start();
    }
}
