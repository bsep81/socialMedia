package pl.sda.socialmedia.model;

import lombok.Getter;

@Getter
public class Error {

    private final int code;
    private final String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
