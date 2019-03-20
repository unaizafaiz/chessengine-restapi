package com.chessapi.jsonpojo;

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

}
