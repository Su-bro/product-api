package com.musinsa.common.response;

import java.util.List;

public class ErrorBody {

    private String message;

    public ErrorBody() {
    }

    public ErrorBody(String message) {
        this.message = message;
    }

    public ErrorBody(List<String> messages) {
        this.message = String.join("\n", messages);
    }

    public String getMessage() {
        return message;
    }

}
