package textualinterface.responses;

public class StringResponse implements Response {
    private String response;

    public StringResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return response;
    }
}
