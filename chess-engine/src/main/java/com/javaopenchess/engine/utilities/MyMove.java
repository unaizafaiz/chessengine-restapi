package com.javaopenchess.engine.utilities;

public class MyMove {
    String from;
    String to;

    public MyMove(String from, String to){
        this.from=from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "com.javaopenchess.engine.utilities.MyMove{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
