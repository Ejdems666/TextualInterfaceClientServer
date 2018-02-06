package textualinterface.responses;

public class MultiSelectResponse implements Response {
    private int[] choices;
    private String[] options;

    public MultiSelectResponse(int[] choices, String[] options) {
        this.choices = choices;
        this.options = options;
    }

    @Override
    public String toString() {
        String response = "";
        for (int i = 0; i < choices.length; i++) {
            response += "\n" + (i + 1) + ": " + options[i];
        }
        return response;
    }
}
