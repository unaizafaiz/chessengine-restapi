package com.javaopenchess.engine;

import com.javaopenchess.engine.utilities.MyMove;
import pl.art.lach.mateusz.javaopenchess.core.Chessboard;
import pl.art.lach.mateusz.javaopenchess.core.Colors;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.Square;
import pl.art.lach.mateusz.javaopenchess.core.ai.AIFactory;
import pl.art.lach.mateusz.javaopenchess.core.moves.Move;
import pl.art.lach.mateusz.javaopenchess.core.pieces.Piece;
import pl.art.lach.mateusz.javaopenchess.core.pieces.implementation.King;
import pl.art.lach.mateusz.javaopenchess.core.players.Player;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerFactory;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerType;
import pl.art.lach.mateusz.javaopenchess.utils.GameModes;
import pl.art.lach.mateusz.javaopenchess.utils.GameTypes;
import pl.art.lach.mateusz.javaopenchess.utils.Settings;

import java.util.ArrayList;

public class ChessEngine{

    private String playerName;
    private Boolean isFirstMove;

    private Game game = new Game();
    private Chessboard chessboard;
    private Player player;
    private Boolean isEndGame = false;


    private String message;


    public ChessEngine(String playerName, boolean isFirstMove) {
        this.playerName = playerName;
        this.isFirstMove = isFirstMove;
        if(isFirstMove) {
            player = PlayerFactory.getInstance(playerName, Colors.WHITE, PlayerType.COMPUTER);
            Player playerComp = PlayerFactory.getInstance("comp", Colors.BLACK, PlayerType.COMPUTER);
            Settings sett = game.getSettings();
            sett.setPlayerWhite(player);
            sett.setPlayerBlack(playerComp);
            sett.setGameMode(GameModes.NEW_GAME);
            sett.setGameType(GameTypes.LOCAL);

            game.setSettings(sett);

        }else{
            player = PlayerFactory.getInstance(playerName, Colors.BLACK, PlayerType.COMPUTER);
            Player playerComp = PlayerFactory.getInstance("comp", Colors.WHITE, PlayerType.COMPUTER);
            Settings sett = game.getSettings();
            sett.setPlayerWhite(playerComp);
            sett.setPlayerBlack(player);
            sett.setGameMode(GameModes.NEW_GAME);
            sett.setGameType(GameTypes.LOCAL);

            game.setSettings(sett);
            }

        game.getChessboard().setPieces4NewGame(game.getSettings().getPlayerWhite(), game.getSettings().getPlayerBlack());
        game.setActivePlayer(game.getSettings().getPlayerWhite());
        if (game.getActivePlayer().getPlayerType() != PlayerType.LOCAL_USER)
        {
            game.setBlockedChessboard(true);
        }
        chessboard = game.getChessboard();
        game.setAi(AIFactory.getAI(1));
        if(!isFirstMove){
            game.doComputerMove();
        }
    }

    public String gameEndMessage() {
        return message;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean getIsFirstMove() {
        return isFirstMove;
    }

    public Boolean isEndOfGame(){
        return isEndGame;
    }

    public void setIsFirstMove(Boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public Game myGame(){
        return game;
    }


    public String waslastMove() {
        Move lastMove = game.getMoves().getLastMoveFromHistory();
        String fromSquare = lastMove.getFrom().getAlgebraicNotation();
        String toSquare = lastMove.getTo().getAlgebraicNotation();
        return fromSquare+"-"+toSquare;
    }


    public Boolean move(String from, String to) {
        int beginX = from.charAt(0)-'a';
        int beginY = 8-Character.getNumericValue(from.charAt(1));
        int endX = to.charAt(0)-'a';
        int endY = 8-Character.getNumericValue(to.charAt(1));
        Square sq1 = chessboard.getSquare(beginX,beginY);
        Piece piece = sq1.getPiece();
        Square sq2 = chessboard.getSquare(endX,endY);
        if(piece==null || piece.getPlayer()!=game.getActivePlayer()) //Square does not contain a piece
            return false;

        if(!isEndOfGame()) {
            if (piece.getAllMoves().contains(sq2)) {//if the move is valid for the piece
                chessboard.move(sq1, sq2);
                // switch player
                game.nextMove();
                checkKing();
                //Make the opponent do the next move and return
                if (!isEndOfGame()) {
                    game.doComputerMove();
                    checkKing();
                    // checkmate or stalemate
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    private void checkKing() {
        // checkmate or stalemate
        King king;
        if (isFirstMove)
        {
            king = chessboard.getKingWhite();
        } else
        {
            king = chessboard.getKingBlack();
        }

        switch (king.isCheckmatedOrStalemated())
        {
            case 1:
                endGame("Checkmate! " + king.getPlayer().getColor().toString() + " player lose!");
                break;
            case 2:
                endGame("Stalemate! Draw!");
        }
    }

    public String endGame(String message){
        game.setBlockedChessboard(true);
        game.setIsEndOfGame(true);
        isEndGame=game.isIsEndOfGame();
        this.message = message;
        return message;
    }

    public ArrayList<MyMove> printAllMoves() {
        ArrayList<String> lastMove = game.getMoves().getMoves();
        ArrayList<MyMove> allMoves = new ArrayList<>();

        for(String move: lastMove){
            String symbol="-";
            if(move.contains("+"))
                move = move.substring(0,move.length()-1);
            if(move.contains("-"))
                symbol = "-";
            else if(move.contains("x"))
                symbol = "x";
            else if(move.contains("#"))
                symbol = "#";
            else
                symbol = "\\(e.p\\)";
            String[] temp = move.split(symbol);
            if(temp.length!=2)
                System.out.println(move);

            if(temp[0].length()==3)
                temp[0] = temp[0].charAt(1)+""+temp[0].charAt(2);
            allMoves.add(new MyMove(temp[0],temp[1]));
        }

        return allMoves;
    }
}