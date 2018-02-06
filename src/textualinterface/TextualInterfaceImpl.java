package textualinterface;

import textualinterface.responses.*;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextualInterfaceImpl implements TextualInterface {
    private Scanner in;
    private PrintWriter out;

    public TextualInterfaceImpl(Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public Response sendMessage(String message) {
        out.println(message);
        return new EmptyResponse();
    }

    @Override
    public Response askForString(String question) {
        out.println(question);
        String string = in.nextLine();
        return new StringResponse(string);
    }

    @Override
    public Response askForPassword(String question) {
        out.println(question);
        String password = in.nextLine();
        // TODO: encrypt
        return new StringResponse(password);
    }

    @Override
    public Response askForEmail(String question) {
        while (true) {
            try {
                out.println(question);
                String email = in.nextLine();
                validateEmail(email);
                return new StringResponse(email);
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }

    }

    private void validateEmail(String email) throws Exception {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new Exception("Email does not match an email format!\n");
        }
    }

    @Override
    public Response askForInteger(String question) {
        int number = getNumberFromConsole(question);
        return new IntegerResponse(number);
    }

    private int getNumberFromConsole(String question) {
        while (true) {
            out.println(question);
            try {
                String rawInteger = in.nextLine();
                int number = Integer.parseInt(rawInteger);
                return number;
            } catch (NumberFormatException e) {
                out.println("This is not a number!\n");
            }
        }
    }

    @Override
    public Response askForInteger(String question, int min, int max) {
        while (true) {
            int number = getNumberFromConsole(question);
            if (number >= min && number <= max) {
                return new IntegerResponse(number);
            }
            out.println("The number is not between " + min + " and " + max+"!\n");
        }
    }

    @Override
    public Response selectSingleOption(String question, String... options) {
        while (true) {
            try {
                printOptions(options);
                out.println("(one option only)");
                int choice = getNumberFromConsole(question);
                choice--;
                checkIfChoiceIsValid(options, choice);
                return new SingleSelectResponse(choice, options);
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }

    private void checkIfChoiceIsValid(String[] options, int optionIndex) throws Exception {
        if (optionIndex < 0 || optionIndex >= options.length) {
            throw new Exception("Option number: "+(optionIndex+1)+" is not one of the possible options!\n");
        }
    }

    private void printOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            out.println((i + 1) + ": " + options[i]);
        }
    }

    @Override
    public Response selectMultipleOptions(String question, String... options) {
        while (true) {
            try {
                printOptions(options);
                out.println("(one or more options separated by commas e.g. 1,2.)");
                out.println(question);
                String rawChoices = in.nextLine();
                String[] splitRawChoices = rawChoices.split(",");
                int[] choices = new int[splitRawChoices.length];
                for (int i = 0; i < splitRawChoices.length; i++) {
                    int choice = Integer.parseInt(splitRawChoices[i]);
                    choice--;
                    checkIfChoiceIsValid(options, choice);
                    choices[i] = choice;
                }
                return new MultiSelectResponse(choices, options);
            } catch (NumberFormatException e) {
                out.println("This is not a number!\n");
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
    }
}
