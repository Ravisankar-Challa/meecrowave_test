package meecrowave.model;

public class ErrorMessage {
    private int errorCode;
    private String errorDescription;
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorDescription() {
        return errorDescription;
    }
    public void setErrorDescription(String errorMessage) {
        this.errorDescription = errorMessage;
    }
    
}
