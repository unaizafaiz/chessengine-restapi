package com.chessapi.utility;

import com.javaopenchess.engine.ChessEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilityTest {

    private Utility utility = new Utility();

    @Test
    public void removeSession() {

        Sessions currentSession = new Sessions();
        Session session1 = new Session();
        session1.setSessionid(1);
        Session session2 = new Session();
        session2.setSessionid(2);

        ArrayList<Session> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);

        currentSession.setSessions(sessionList);
        assertTrue(currentSession.getSessions().size()==2);

        utility.removeSession(currentSession,2);
        assertTrue(currentSession.getSessions().size()==1);
    }

    @Test
    public void addSession() {
        Sessions currentSession = new Sessions();
        Session session1 = new Session();
        session1.setSessionid(1);
        Session session2 = new Session();
        session2.setSessionid(2);

        ArrayList<Session> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);

        currentSession.setSessions(sessionList);
        assertTrue(currentSession.getSessions().size()==2);
        Session newSession = new Session();
        utility.addSession(currentSession,newSession);
        assertTrue(currentSession.getSessions().size()==3);



    }

    @Test
    public void getNextMove() {
        ChessEngine chessEngine = new ChessEngine("Unaiza", false);
        assertNotNull(utility.getNextMove(chessEngine));
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