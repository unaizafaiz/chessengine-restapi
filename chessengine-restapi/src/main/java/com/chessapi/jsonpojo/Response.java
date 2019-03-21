package com.chessapi.jsonpojo;

public class Response {
    int status;
    NextMove nextmove = new NextMove();

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

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", nextmove=" + nextmove +
                ", message='" + message + '\'' +
                '}';
    }
}
