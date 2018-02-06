package client;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientToServerRunnable implements Runnable {
    private final Socket serverSocket;
    private final Scanner clientIn;
    private final PrintWriter serverOut;

    public ClientToServerRunnable(Socket serverSocket, Scanner clientIn, PrintWriter serverOut) {
        this.serverSocket = serverSocket;
        this.clientIn = clientIn;
        this.serverOut = serverOut;
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isConnected()) {
                String message = clientIn.nextLine();
                serverOut.println(message);
            }
        } catch (NoSuchElementException e) {
            System.out.println("--------------------------------\nServer closed the connection!");
        }
    }
}
