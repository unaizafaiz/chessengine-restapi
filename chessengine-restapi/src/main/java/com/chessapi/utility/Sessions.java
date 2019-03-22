package com.chessapi.utility;

import java.util.ArrayList;

/**
 * Maintains a list of all the current sessions
 */
public class Sessions {
    private ArrayList<Session> sessions = new ArrayList<>();

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }



}
