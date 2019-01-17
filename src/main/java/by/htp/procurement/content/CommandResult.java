package by.htp.procurement.content;

public class CommandResult {

    public static enum ResponseType {
        FORWARD, REDIRECT;
    }

    private String page;
    private ResponseType responseType;

    public CommandResult() {
    }

    public CommandResult(String page) {
        this.page = page;
    }

    public CommandResult(String page, ResponseType type) {
        this.page = page;
        this.responseType = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
