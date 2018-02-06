package textualinterface.responses;

public class IntegerResponse implements Response {
    private int response;

    public IntegerResponse(int response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "number: " + response;
    }
}
