package com.devteam5.fun5helloworld.events;

/**
 * Created by paikhantko on 6/16/18.
 */

public class ApiErrorEvent {

    private String message;

    public ApiErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
