package server;

import textualinterface.TextualInterface;
import textualinterface.responses.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerToClientRunnable implements Runnable {
    private final Socket clientSocket;
    private final TextualInterface textualInterface;
    private final PrintWriter out;

    public ServerToClientRunnable(Socket clientSocket, TextualInterface textualInterface, PrintWriter out) {
        this.clientSocket = clientSocket;
        this.textualInterface = textualInterface;
        this.out = out;
    }


    @Override
    public void run() {
        textualInterface.sendMessage("Hello");

        Response response = textualInterface.askForString("Give me a string please:");
        printResponse(response);

        response = textualInterface.askForEmail("Give me an email please:");
        printResponse(response);

        response = textualInterface.askForPassword("Give me a password (string) please:");
        printResponse(response);

        response = textualInterface.askForInteger("Give me an integer please:");
        printResponse(response);

        response = textualInterface.askForInteger("Give me an integer between 10 and 20 please:", 10, 20);
        printResponse(response);

        String[] options = new String[]{"option1", "option2", "option3", "option4"};
        response = textualInterface.selectSingleOption("Select an option please: ", options);
        printResponse(response);


        response = textualInterface.selectMultipleOptions("Select some options please: ", options);
        printResponse(response);

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printResponse(Response response) {
        out.println("Your response: " + response);
        out.println("\n-------------------------------------------------------------------------\n");
    }
}
