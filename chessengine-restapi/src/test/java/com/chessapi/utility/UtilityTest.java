package com.chessapi.utility;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilityTest {

    private Utility utility = new Utility();

    @Test
    public void removeSession() {
    }

    @Test
    public void addSession() {
    }

    @Test
    public void getNextMove() {
    }

    @Test
    public void getSession() {
        Sessions currentSession = new Sessions();
        Session session1 = new Session();
        session1.setSessionid(1);
        Session session2 = new Session();
        session2.setSessionid(2);

        ArrayList<Session> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);

        currentSession.setSessions(sessionList);

        assertNotNull(utility.getSession(currentSession,1));

    }
}