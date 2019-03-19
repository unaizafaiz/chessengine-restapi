import pl.art.lach.mateusz.javaopenchess.core.Chessboard;
import pl.art.lach.mateusz.javaopenchess.core.Colors;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.Square;
import pl.art.lach.mateusz.javaopenchess.core.ai.AI;
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

public class ChessEngine{

    private String playerName;
    private Boolean isFirstMove;
    private Game game = new Game();
    private Chessboard chessboard;
    private Player player;


    public ChessEngine(String playerName, boolean isFirstMove) {
        this.playerName = playerName;
        this.isFirstMove = isFirstMove;
        if(isFirstMove) {
            player = PlayerFactory.getInstance(playerName, Colors.WHITE, PlayerType.LOCAL_USER);
            Player playerComp = PlayerFactory.getInstance("comp", Colors.BLACK, PlayerType.COMPUTER);
            Settings sett = game.getSettings();
            sett.setPlayerWhite(player);
            sett.setPlayerBlack(playerComp);
            sett.setGameMode(GameModes.NEW_GAME);
            sett.setGameType(GameTypes.LOCAL);

            /*game = new GameBuilder()
                    .setBlackPlayerName("comp")
                    .setWhitePlayerName(playerName)
                    .setWhitePlayerType(PlayerType.LOCAL_USER)
                    .setBlackPlayerType(PlayerType.COMPUTER)
                    .setGameMode(GameModes.NEW_GAME)
                    .setGameType(GameTypes.LOCAL)
                    .build();*/
            game.setSettings(sett);

        }else{
            player = PlayerFactory.getInstance(playerName, Colors.BLACK, PlayerType.LOCAL_USER);
            Player playerComp = PlayerFactory.getInstance("comp", Colors.WHITE, PlayerType.COMPUTER);
            Settings sett = game.getSettings();
            sett.setPlayerWhite(playerComp);
            sett.setPlayerBlack(player);
            sett.setGameMode(GameModes.NEW_GAME);
            sett.setGameType(GameTypes.LOCAL);

            /*game = new GameBuilder()
                    .setBlackPlayerName(playerName)
                    .setWhitePlayerName("comp")
                    .setWhitePlayerType(PlayerType.COMPUTER)
                    .setBlackPlayerType(PlayerType.LOCAL_USER)
                    .setGameMode(GameModes.NEW_GAME)
                    .setGameType(GameTypes.LOCAL)
                    .build();*/
             game.setSettings(sett);
            }

        game.getChessboard().setPieces4NewGame(game.getSettings().getPlayerWhite(), game.getSettings().getPlayerBlack());
        game.setActivePlayer(game.getSettings().getPlayerWhite());
        if (game.getActivePlayer().getPlayerType() != PlayerType.LOCAL_USER)
        {
            game.setBlockedChessboard(true);
        }
        chessboard = game.getChessboard();
       // MoveHistory moveHistory = game.getMoves();
        if(!isFirstMove){
            game.setAi(AIFactory.getAI(1));
            game.doComputerMove();

        }
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

    public void setIsFirstMove(Boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public MyMove getLastMove() {
        Move lastMove = game.getMoves().getLastMoveFromHistory();
        String fromSquare = lastMove.getFrom().getAlgebraicNotation();
        String toSquare = lastMove.getTo().getAlgebraicNotation();
        return new MyMove(fromSquare,toSquare);
    }

    public Boolean move(MyMove myMove) {
        String from = myMove.getFrom();
        String to = myMove.getTo();
        int beginX = from.charAt(0)-'a';
        int beginY = 8-Character.getNumericValue(from.charAt(1));
        System.out.println("beginx = "+beginX);
        System.out.println("beginY = "+beginY);
        int endX = to.charAt(0)-'a';
        int endY = 8-Character.getNumericValue(to.charAt(1));
        System.out.println("endX = "+endX);
        System.out.println("endY = "+endY);
        Square sq1 = chessboard.getSquare(beginX,beginY);
        Piece piece = sq1.getPiece();
        //Square sq2 = new Square(endX,endY,piece);
        Square sq2 = chessboard.getSquare(endX,endY);
        if(piece==null || piece.getPlayer()!=game.getActivePlayer()) //Square does not contain a piece
            return false;

        if(piece.getAllMoves().contains(sq2)) {
            chessboard.move(sq1, sq2);
            // switch player
            game.nextMove();

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

            //Make the opponent do the next move and return
            AI ai = AIFactory.getAI(1);
            game.setAi(ai);
            game.doComputerMove();
            return true;
        }else
            System.out.println("Cannot make the move");
        return false;

    }

    public String endGame(String message){
        game.setBlockedChessboard(true);
        game.setIsEndOfGame(true);
        return message;
    }

}