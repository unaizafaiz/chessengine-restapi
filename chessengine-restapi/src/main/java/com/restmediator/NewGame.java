package com.restmediator;

import com.chessapi.jsonpojo.NextMove;
import com.chessapi.jsonpojo.Response;
import com.restmediator.requests.CRUD;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.sql.SQLOutput;

public class NewGame implements Runnable{
    String playerWhite;
    String playerBlack;
    CRUD crud = new CRUD();
    static String GET_SESSIONS = "getsessions";
    static String GET_ALLMOVES = "getallmoves";
    static String CREATE_GAME = "newgame";
    static String MOVE = "move";

    public NewGame(String playerWhite, String playerBlack){
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }
    @Override
    public void run() {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        crud.getRequest(closeableHttpClient,GET_SESSIONS);

        //Creating JSONOBJECT for player
        JSONObject jsonPlayerWhite = createPlayerJSON(playerWhite,true);
        JSONObject jsonPlayerBlack = createPlayerJSON(playerBlack,false);

        //Create player instances
        Response player1Respone = crud.postRequest(closeableHttpClient,CREATE_GAME,jsonPlayerWhite);
        Response player2Response = crud.postRequest(closeableHttpClient, CREATE_GAME, jsonPlayerBlack);


        System.out.println("Player1 respon:"+player1Respone);
        System.out.println("Player2 respon:"+player2Response);

        //Playing the game
        if(player1Respone.getStatus()==0 && player2Response.getStatus()==0) {
            String[] message = player1Respone.getMessage().split("=");

            //Getting session id
            long p1Sessionid = Long.parseLong(message[1]);
            System.out.println("Player1 Session ID: "+p1Sessionid);

            //
            String[] message2 = player2Response.getMessage().split("=");
            long p2Sessionid = Long.parseLong(message2[1]);
            System.out.println("Player2 Session ID: "+p2Sessionid);



            System.out.println("P1"+player1Respone);
            System.out.println("P2"+player2Response);


            Response responsecode =  player2Response;
            Boolean isWhiteTurn = true;
            long sessionid = p1Sessionid;

            //Running the game until a move cannot be made
            while (responsecode.getStatus()==0) {
                NextMove nextMove = responsecode.getNextMove();
                System.out.println(nextMove);
                JSONObject moveRequest = createMoveJSON(sessionid, nextMove);
                responsecode = crud.postRequest(closeableHttpClient, MOVE, moveRequest);
                System.out.println("New response code = "+responsecode.getStatus());
                if(isWhiteTurn){
                    sessionid = p2Sessionid;
                    isWhiteTurn = false;
                }else{
                    sessionid = p1Sessionid;
                    isWhiteTurn = true;
                }
            }

            System.out.println(responsecode.getMessage());

            crud.getRequest(closeableHttpClient,GET_ALLMOVES+"/"+p1Sessionid);

        }else{
            System.out.println("Unable to process game request");
        }

        //crud.getRequest(httpclient,GET_SESSIONS);

    }

    private JSONObject createMoveJSON(long sessionid, NextMove nextMove) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionid",sessionid);
        jsonObject.put("squareone",nextMove.getSquareFrom());
        jsonObject.put("squaretwo",nextMove.getSquareTo());
        return jsonObject;
    }

    private JSONObject createPlayerJSON(String playerWhite, boolean b) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerName",playerWhite);
        jsonObject.put("isFirstMove",b);
        return jsonObject;
    }

}
