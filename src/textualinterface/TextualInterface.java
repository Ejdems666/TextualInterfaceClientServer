package textualinterface;

import textualinterface.responses.Response;

public interface TextualInterface {
    Response sendMessage(String message);

    Response askForString(String question);

    Response askForPassword(String question);

    Response askForEmail(String question);

    Response askForInteger(String question);

    Response askForInteger(String question, int min, int max);

    Response selectSingleOption(String question, String... options);

    Response selectMultipleOptions(String question, String... options);
}
