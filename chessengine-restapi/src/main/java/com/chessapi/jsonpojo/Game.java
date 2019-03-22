package com.chessapi.jsonpojo;

/**
 * Class to create a JSON object that contains new game request details
 *PlayerName
 *isFirstMove
 */
public class Game {

    private String playerName;
    private Boolean isFirstMove;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean getisFirstMove() {
        return isFirstMove;
    }

    public void setisFirstMove(Boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playerName='" + playerName + '\'' +
                ", isFirstMove=" + isFirstMove +
                '}';
    }
}
