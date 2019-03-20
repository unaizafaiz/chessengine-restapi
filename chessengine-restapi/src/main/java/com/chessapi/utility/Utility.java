package com.chessapi.utility;

import com.chessapi.jsonpojo.NextMove;
import com.javaopenchess.engine.ChessEngine;

import java.util.ArrayList;

public class Utility {
    public ArrayList<Session> removeSession(Sessions currentsession, long sessionid){
        ArrayList<Session> all = currentsession.getSessions();
        Session removeSession = new Session();
        for(Session session: all){
            if(session.getSessionid()==sessionid) {
                removeSession = session;
            }
        }
        all.remove(removeSession);
        return all;
    }

    public ArrayList<Session> addSession(Sessions currentsessions, Session session) {
        ArrayList<Session> tempSession = new ArrayList<>();
        if(currentsessions.getSessions().size()>0) {
            tempSession = currentsessions.getSessions();
            tempSession.add(session);
        }else{
            tempSession.add(session);
        }
        return tempSession;
    }

    public NextMove getNextMove(ChessEngine chessGame) {
        String[] moveMade = chessGame.waslastMove().split("-");
        NextMove nextMove = new NextMove();
        nextMove.setSquareFrom(moveMade[0]);
        nextMove.setSquareTo(moveMade[1]);
        return nextMove;
    }

    public Session getSession(Sessions currentsessions, long sessionid) {
        ArrayList<Session> sessionArrayList = currentsessions.getSessions();
        Session thissession = null;
        for(Session session: sessionArrayList){
            long thisessionid = session.getSessionid();
            if((long) thisessionid == (long) sessionid) {
                System.out.println(sessionid+" found!!! ");
                thissession = session;
            }
        }
        return thissession;
    }
}
