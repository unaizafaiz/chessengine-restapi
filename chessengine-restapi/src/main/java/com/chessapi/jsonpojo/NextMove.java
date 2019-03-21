package com.chessapi.jsonpojo;

public class NextMove {

    String squareFrom;
    String squareTo;


    public String getSquareFrom() {
        return squareFrom;
    }

    public void setSquareFrom(String squareFrom) {
        this.squareFrom = squareFrom;
    }

    public String getSquareTo() {
        return squareTo;
    }

    public void setSquareTo(String squareTo) {
        this.squareTo = squareTo;
    }

    @Override
    public String toString() {
        return "NextMove{" +
                "squareFrom='" + squareFrom + '\'' +
                ", squareTo='" + squareTo + '\'' +
                '}';
    }
}
