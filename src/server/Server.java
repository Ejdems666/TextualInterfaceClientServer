package server;

import textualinterface.TextualInterface;
import textualinterface.TextualInterfaceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(1234);
            Socket connection = waitForConnection(socket);
            createServerIOHandlerThread(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket waitForConnection(ServerSocket socket) throws IOException {
        System.out.println("waiting for a client");
        Socket connection = socket.accept();
        System.out.println("socket started");
        return connection;
    }

    private void createServerIOHandlerThread(Socket connection) throws IOException {
        Scanner clientIn = new Scanner(connection.getInputStream());
        PrintWriter clientOut = new PrintWriter(connection.getOutputStream(), true);
        clientOut.println("connected to the server\n");
        TextualInterface textualInterface = new TextualInterfaceImpl(clientIn, clientOut);
        ServerIOHandler serverIOHandler = new ServerIOHandler(connection, textualInterface, clientOut);
        new Thread(serverIOHandler).start();
    }
}
