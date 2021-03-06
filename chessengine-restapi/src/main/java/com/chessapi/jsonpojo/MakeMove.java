package com.chessapi.jsonpojo;

/**
 * JSONObject POJO for making a move on a sessionid
 */
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

    @Override
    public String toString() {
        return "MakeMove{" +
                "sessionid=" + sessionid +
                ", squareone='" + squareone + '\'' +
                ", squaretwo='" + squaretwo + '\'' +
                '}';
    }
}
