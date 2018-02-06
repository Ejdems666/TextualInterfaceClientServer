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
            Socket clientSocket = waitForConnection(socket);
            createServerToClientThread(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket waitForConnection(ServerSocket socket) throws IOException {
        System.out.println("waiting for a client");
        Socket clientSocket = socket.accept();
        System.out.println("socket started");
        return clientSocket;
    }

    private void createServerToClientThread(Socket clientSocket) throws IOException {
        Scanner in = new Scanner(clientSocket.getInputStream());
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("connected to the server\n");
        TextualInterface textualInterface = new TextualInterfaceImpl(in, out);
        ServerTransmitterRunnable serverTransmitterRunnable = new ServerTransmitterRunnable(clientSocket, textualInterface, out);
        new Thread(serverTransmitterRunnable).start();
    }
}
