package kr.co.polycube.backendtest.Exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	USER_NOT_FOUND("USR_001", "User not found");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
    	
        this.code = code;
        this.message = message;
        
    }

}