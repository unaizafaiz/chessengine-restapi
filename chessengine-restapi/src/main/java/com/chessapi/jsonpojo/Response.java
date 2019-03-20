package com.chessapi.jsonpojo;

public class Response {
    int status;
    NextMove nextmove;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NextMove getNextMove() {
        return nextmove;
    }

    public void setNextMove(NextMove nextMove) {
        this.nextmove = nextMove;
    }
}
