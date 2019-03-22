package com.chessapi.utility;

import com.chessapi.controller.ChessGame;
import com.chessapi.jsonpojo.NextMove;
import com.javaopenchess.engine.ChessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Utility {

    private final Logger logger = LoggerFactory.getLogger(Utility.class);

    public ArrayList<Session> removeSession(Sessions currentsession, long sessionid){
        logger.debug("Remove session requested for session id "+sessionid);
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
        logger.debug("add session requested for new session "+session);
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
        logger.debug("Get the next move to be made");
        String[] moveMade = chessGame.waslastMove().split("-");
        NextMove nextMove = new NextMove();
        nextMove.setSquareFrom(moveMade[0]);
        nextMove.setSquareTo(moveMade[1]);
        return nextMove;
    }

    public Session getSession(Sessions currentsessions, long sessionid) {
        logger.debug("Request to retrieve session with id "+sessionid);
        ArrayList<Session> sessionArrayList = currentsessions.getSessions();
        Session thissession = null;
        for(Session session: sessionArrayList){
            long thisessionid = session.getSessionid();
            if((long) thisessionid == (long) sessionid) {
                logger.debug("Session id "+sessionid+"found!");
                thissession = session;
            }
        }
        return thissession;
    }
}
