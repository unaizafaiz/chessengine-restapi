package com.javaopenchess.engine;

import com.javaopenchess.engine.utilities.MyMove;
import org.junit.Test;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.ai.AI;
import pl.art.lach.mateusz.javaopenchess.core.ai.AIFactory;
import pl.art.lach.mateusz.javaopenchess.core.moves.Move;

import static org.junit.Assert.*;

public class ChessEngineTest {

    private ChessEngine chessEngine;

   @Test
    public void createNewChessGame(){
       chessEngine = new ChessEngine("abc",true);
       assertTrue(chessEngine.getIsFirstMove());
       assertEquals("abc",chessEngine.getPlayerName());
   }

   @Test
    public void testGetPrevMove(){
       chessEngine = new ChessEngine("Player1", false);
       assertNotNull(chessEngine.waslastMove());
   }

    @Test
    public void testMoveIfPlayerBlack(){
        chessEngine = new ChessEngine("Player1", false);
        assertTrue(chessEngine.move("b7","b6"));
    }

    @Test
    public void testMoveIfPlayerBlack_MovesWhitePiece(){
        chessEngine = new ChessEngine("Player1", false);
        assertFalse(chessEngine.move("a2","a4"));
    }

    @Test
    public void testMoveIfPlayerWhite_MovesBlackPiece(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move("g8","h6"));
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsPawn(){
        chessEngine = new ChessEngine("Player1", true);

        assertTrue(chessEngine.move("a2","a4"));
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsPawnWrongMove(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move("a2","a5"));
        assertFalse(chessEngine.move("a2","a1"));
    }

    @Test
    public void testMove_IfPlayerBlack_PieceIsPawn_WrongMove(){
        chessEngine = new ChessEngine("Player1", false);
        chessEngine.move("b7","b6");//Move forward
        assertFalse(chessEngine.move("b6","b7")); //Move back again
    }

    @Test
    public void testMove_IfPlayerBlack_PieceIsKnight_RightMove(){
        chessEngine = new ChessEngine("Player1", false);
        chessEngine.move("b8","a6");//Move forward
       // System.out.println(chessEngine.lastMove());
        assertTrue(chessEngine.move("a6","b8")); //Move back again
    }

    @Test
    public void testMoveIfPlayerWhitePieceNoPieceInSquare(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move("a3","a5"));
        //assertEquals("a4",chessEngine.lastMove().getTo());
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsKnight(){
        chessEngine = new ChessEngine("Player1", true);
        assertTrue(chessEngine.move("b1","c3"));
       // assertEquals("c3",chessEngine.lastMove().getTo());

    }

    @Test
    public void testMoveIfPlayerWhitePieceIsKnightWrongMove(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move("b1","b2"));
        assertFalse(chessEngine.move("b1","d2"));
    }

    @Test
    public void endGame() {
       chessEngine = new ChessEngine("Player1", true);
       assertEquals("Checkmate!",chessEngine.endGame("Checkmate!"));
    }

    @Test
    public void testTwoGame_playingEachOther(){
       ChessEngine chessEngine_playerWhite = new ChessEngine("Unaiza", true);
        ChessEngine chessEngine_playerBlack = new ChessEngine("Mana", false);

        int count = 0;
        while ((!chessEngine_playerWhite.isEndOfGame() && !chessEngine_playerBlack.isEndOfGame())&& count<100) {
           String[] whiteMove = chessEngine_playerBlack.waslastMove().split("-");
            if(chessEngine_playerBlack.isEndOfGame())
                break;
            Boolean moveMade = chessEngine_playerWhite.move(whiteMove[0],whiteMove[1]);
            if(!moveMade || chessEngine_playerWhite.isEndOfGame())
                break;
            String[] blackMove = chessEngine_playerWhite.waslastMove().split("-");
            if(chessEngine_playerWhite.isEndOfGame())
                break;
            Boolean blackMoveMade = chessEngine_playerBlack.move(blackMove[0],blackMove[1]);
            if(!blackMoveMade || chessEngine_playerBlack.isEndOfGame())
                break;
            count++;
            System.out.println(chessEngine_playerWhite.printAllMoves());
        }
      //  System.out.println(chessEngine_playerWhite.isEndOfGame());
      //  System.out.println(chessEngine_playerBlack.isEndOfGame());
      //  System.out.println(chessEngine_playerWhite.gameEndMessage());
      //  System.out.println(chessEngine_playerBlack.gameEndMessage());



    }
}