package com.chessapi.jsonpojo;

public class MakeMove {
    private long sessionid;
    private String squareone;
    private String squaretwo;

    public long getSessionid() {
        return sessionid;
    }

    public void setSessionid(long sessionid) {
        this.sessionid = sessionid;
    }

    public String getSquareone() {
        return squareone;
    }

    public void setSquareone(String squareone) {
        this.squareone = squareone;
    }

    public String getSquaretwo() {
        return squaretwo;
    }

    public void setSquaretwo(String squaretwo) {
        this.squaretwo = squaretwo;
    }
}
