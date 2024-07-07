package kr.co.polycube.backendtest.Exception;

public class UserNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public UserNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
