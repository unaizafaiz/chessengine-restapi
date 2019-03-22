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
    public Sessions getMoves(){
        logger.debug("Getting all sessions");
        return currentsessions;
    }

    @GetMapping("/getallmoves/{sessionid}")
    public Response getMoves(@PathVariable long sessionid){
        logger.debug("Request received to get moves for session id "+sessionid);
        Session session = utility.getSession(currentsessions,sessionid);
        ChessEngine chessEngine = null;
        Response response = new Response();
        //If session exists
        if(session!=null) {
             chessEngine = session.getChessEngine();
             response.setStatus(0);
             response.setMessage(sessionid+" "+chessEngine.printAllMoves().toString());
        }else{//Session does not exist
            response.setStatus(-1);
            response.setMessage("Session "+sessionid+" not available");
        }

        return response;
    }

    @DeleteMapping("/endgame")
    public Response endsession(@RequestParam long sessionid){
        logger.debug("Request received to end session "+sessionid);
        Response response = new Response();
        Session session = utility.getSession(currentsessions,sessionid);
        //If session exits
        if(session!=null){
            currentsessions.setSessions(utility.removeSession(currentsessions,sessionid));
            counter.decrementAndGet();
            response.setStatus(0);
            response.setMessage("Session "+sessionid+" ended successfully");
        }else{ //Session does not exits
            response.setStatus(-1);//id not found
            response.setMessage("Session "+sessionid+" not found");
        }
        return response;
    }


    @PostMapping("/newgame")
    public Response createnewgame(@RequestBody Game game) {
        logger.debug("Request received to create a new game with properties "+game);
        Response response = new Response();
        logger.debug("Game created",game.getPlayerName());

        //New session instance
        Session session = new Session();

        //Game object defined currectly then process request
        if(game!=null) {
            //If playername or isfirstmove does not exist
            if(game.getPlayerName()==null || game.getisFirstMove()==null){
                response.setStatus(1);
                response.setMessage("game values incorrect");
                return response;
            }
            //Create a new chess engine instance
            ChessEngine chessEngine = new ChessEngine(game.getPlayerName(), game.getisFirstMove());
            long sessionid = counter.getAndIncrement(); //getsessionid

            //adding session to Sessions
            session.setSessionid(sessionid);
            session.setChessEngine(chessEngine);
            currentsessions.setSessions(utility.addSession(currentsessions, session));

            //creating the response
            response.setStatus(0);
            response.setMessage("New Game ready to be played. Session id =" + sessionid);
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
        logger.debug("Making a new move for "+makeMove);

        //Getting session id for the request
        long sessionid = makeMove.getSessionid();
        Session session = utility.getSession(currentsessions,sessionid);
        Response response = new Response();

        //If session id exists
        if(session!=null) {
        ChessEngine chessEngine = session.getChessEngine();

            //Calling move() in chessEngine
            Boolean isMoveMade = chessEngine.move(makeMove.getSquareone(), makeMove.getSquaretwo());

            //Checking if end of game reached then return response code =100
            if(!isMoveMade && chessEngine.isEndOfGame()) {
                response.setStatus(100);//game over
                response.setMessage(chessEngine.gameEndMessage());
               // gameList.remove(makeMove.getSessionid());
                currentsessions.setSessions(utility.removeSession(currentsessions,makeMove.getSessionid()));
                return response;
            }

            //Move made returned true, then set NextMove and return response
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
        //If session id not found
        response.setMessage("Session "+sessionid+"not found");
        response.setStatus(-1); //no session status
        return response;
    }


}
