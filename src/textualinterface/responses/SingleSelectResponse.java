package textualinterface.responses;

public class SingleSelectResponse implements Response {
    private int choice;
    private String[] options;

    public SingleSelectResponse(int choice, String[] options) {
        this.choice = choice;
        this.options = options;
    }

    @Override
    public String toString() {
        return (choice + 1) + ": " + options[choice];
    }
}
