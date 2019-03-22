package com.chessapi.utility;

import com.javaopenchess.engine.ChessEngine;

public class Session {
    private long sessionid;
    private ChessEngine chessEngine;

    public long getSessionid() {
        return sessionid;
    }

    public void setSessionid(long sessionid) {
        this.sessionid = sessionid;
    }

    public ChessEngine getChessEngine() {
        return chessEngine;
    }

    public void setChessEngine(ChessEngine chessEngine) {
        this.chessEngine = chessEngine;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionid=" + sessionid +
                ", chessEngine=" + chessEngine +
                '}';
    }
}
