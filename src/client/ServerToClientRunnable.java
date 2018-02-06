package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerToClientRunnable implements Runnable {
    private final Socket serverSocket;
    private final Scanner serverIn;
    private final PrintStream clientOut;

    public ServerToClientRunnable(Socket serverSocket, Scanner serverIn, PrintStream clientOut) {
        this.serverSocket = serverSocket;
        this.serverIn = serverIn;
        this.clientOut = clientOut;
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isConnected()) {
                clientOut.println(serverIn.nextLine());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Server closed the connection!");
        }
    }
}
