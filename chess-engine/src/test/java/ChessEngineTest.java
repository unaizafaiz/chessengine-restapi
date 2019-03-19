import org.junit.Test;

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
       assertNotNull(chessEngine.getLastMove());
   }

    @Test
    public void testMoveIfPlayerBlack(){
        chessEngine = new ChessEngine("Player1", false);
        assertTrue(chessEngine.move(new MyMove("b7","b6")));
    }

    @Test
    public void testMoveIfPlayerBlack_MovesWhitePiece(){
        chessEngine = new ChessEngine("Player1", false);
        assertFalse(chessEngine.move(new MyMove("a2","a4")));
    }

    @Test
    public void testMoveIfPlayerWhite_MovesBlackPiece(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move(new MyMove("g8","h6")));
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsPawn(){
        chessEngine = new ChessEngine("Player1", true);

        assertTrue(chessEngine.move(new MyMove("a2","a4")));
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsPawnWrongMove(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move(new MyMove("a2","a5")));
        assertFalse(chessEngine.move(new MyMove("a2","a1")));
    }

    @Test
    public void testMove_IfPlayerBlack_PieceIsPawn_WrongMove(){
        chessEngine = new ChessEngine("Player1", false);
        chessEngine.move(new MyMove("b7","b6"));//Move forward
        assertFalse(chessEngine.move(new MyMove("b6","b7"))); //Move back again
    }

    @Test
    public void testMove_IfPlayerBlack_PieceIsKnight_RightMove(){
        chessEngine = new ChessEngine("Player1", false);
        chessEngine.move(new MyMove("b8","a6"));//Move forward
        System.out.println(chessEngine.getLastMove());
        assertTrue(chessEngine.move(new MyMove("a6","b8"))); //Move back again
    }

    @Test
    public void testMoveIfPlayerWhitePieceNoPieceInSquare(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move(new MyMove("a3","a5")));
        //assertEquals("a4",chessEngine.getLastMove().getTo());
    }

    @Test
    public void testMoveIfPlayerWhitePieceIsKnight(){
        chessEngine = new ChessEngine("Player1", true);
        assertTrue(chessEngine.move(new MyMove("b1","c3")));
       // assertEquals("c3",chessEngine.getLastMove().getTo());

    }

    @Test
    public void testMoveIfPlayerWhitePieceIsKnightWrongMove(){
        chessEngine = new ChessEngine("Player1", true);
        assertFalse(chessEngine.move(new MyMove("b1","b2")));
        assertFalse(chessEngine.move(new MyMove("b1","d2")));
    }

    @Test
    public void endGame() {
       chessEngine = new ChessEngine("Player1", true);
       assertEquals("Checkmate!",chessEngine.endGame("Checkmate!"));
    }
}