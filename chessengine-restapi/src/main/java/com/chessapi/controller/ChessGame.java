package com.chessapi.controller;

import com.chessapi.jsonpojo.*;
import com.chessapi.utility.Session;
import com.chessapi.utility.Sessions;
import com.chessapi.utility.Utility;
import com.javaopenchess.engine.ChessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ChessGame {
    Sessions currentsessions = new Sessions();
    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(ChessGame.class);
    Utility utility = new Utility();


    @GetMapping("/getsessions")
    public Sessions getAllSessions(){
        return currentsessions;
    }

    @GetMapping("/getallmoves/{sessionid}")
    public Response getAllSessions(@PathVariable long sessionid){
        Session session = utility.getSession(currentsessions,sessionid);
        ChessEngine chessEngine = null;
        Response response = new Response();
        if(session!=null) {
             chessEngine = session.getChessEngine();
             response.setStatus(0);
             response.setMessage(sessionid+" "+chessEngine.printAllMoves().toString());
        }else{
            response.setStatus(-1);
            response.setMessage("Session "+sessionid+" not available");
        }

        return response;
    }

    @DeleteMapping("/endgame")
    public Response endsession(@RequestParam long sessionid){
        Response response = new Response();
        Session session = utility.getSession(currentsessions,sessionid);

        if(session!=null){
            currentsessions.setSessions(utility.removeSession(currentsessions,sessionid));
            counter.decrementAndGet();
            response.setStatus(0);
            response.setMessage("Session "+sessionid+" ended successfully");
        }else{
            response.setStatus(-1);//id not found
            response.setMessage("Session "+sessionid+" not found");
        }
        return response;
    }


    @PostMapping("/newgame")
    public Response createnewgame(@RequestBody Game game) {
        Response response = new Response();
        logger.debug("Game created",game.getPlayerName());


        Session session = new Session();

        if(game!=null) {
            if(game.getPlayerName()==null || game.getisFirstMove()==null){
                response.setStatus(1);
                response.setMessage("game values incorrect");
                return response;
            }
            ChessEngine chessEngine = new ChessEngine(game.getPlayerName(), game.getisFirstMove());
            long sessionid = counter.getAndIncrement(); //getsessionid

            //adding session to Sessions
            session.setSessionid(sessionid);
            session.setChessEngine(chessEngine);
            currentsessions.setSessions(utility.addSession(currentsessions, session));

            //creating the response
            response.setStatus(0);
            response.setMessage("New Game ready to be played. Session id = " + sessionid);
            if (!game.getisFirstMove()) {
                NextMove nextMove = utility.getNextMove(chessEngine);
                response.setNextMove(nextMove);
            }
        }else{
            response.setStatus(-1);
            response.setMessage("Unable to process your request");
        }

       // System.out.println(chessGame.getSessionId());
        return response;
    }



    @PostMapping("/move")
    //@ResponseBody
    public Response makemove(@RequestBody MakeMove makeMove) {
        //ChessEngine game = gameList.get(makeMove.getSessionid());
        long sessionid = makeMove.getSessionid();
        Session session = utility.getSession(currentsessions,sessionid);
       // Session session = utility.getSession(currentsessions,makeMove.getSessionid());
        Response response = new Response();
        if(session!=null) {
        ChessEngine chessEngine = session.getChessEngine();

            //MyMove myMove = new MyMove();
            //myMove.setFrom(from);
            System.out.println(makeMove.getSessionid());
            System.out.println(makeMove.getSquareone());
            System.out.println(makeMove.getSquaretwo());

            Boolean isMoveMade = chessEngine.move(makeMove.getSquareone(), makeMove.getSquaretwo());
            if(chessEngine.isEndOfGame()) {
                response.setStatus(100);//game over
                response.setMessage(chessEngine.gameEndMessage());
               // gameList.remove(makeMove.getSessionid());
                currentsessions.setSessions(utility.removeSession(currentsessions,makeMove.getSessionid()));
                return response;
            }
            if (isMoveMade) {
                NextMove nextMove = utility.getNextMove(chessEngine);
                response.setStatus(0); //success status
                response.setMessage("Move successfull");
                response.setNextMove(nextMove);
            }else {
                response.setMessage("Invalid move");
                response.setStatus(1); //invalid move status
            }
            return response;
        }
        response.setMessage("Session "+sessionid+"not found");
        response.setStatus(-1); //no session status
        return response;
    }


}
