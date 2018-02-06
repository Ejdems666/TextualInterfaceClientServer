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
            Socket serverSocket = new Socket("localhost", 1234);
            startClientToServerThread(serverSocket);
            startServerToClientThread(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startClientToServerThread(Socket serverSocket) throws IOException {
        PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
        Scanner consoleIn = new Scanner(System.in);
        ClientTransmitterRunnable clientTransmitterRunnable = new ClientTransmitterRunnable(serverSocket, consoleIn, serverOut);
        new Thread(clientTransmitterRunnable).start();
    }

    private void startServerToClientThread(Socket serverSocket) throws IOException {
        Scanner serverIn = new Scanner(serverSocket.getInputStream());
        ClientReceiverRunnable clientReceiverRunnable = new ClientReceiverRunnable(serverSocket, serverIn, System.out);
        new Thread(clientReceiverRunnable).start();
    }
}
