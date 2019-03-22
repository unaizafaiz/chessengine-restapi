package restmediator;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TestChessGame {

    public static void main(String[] args){
        NewGame newGame = new NewGame("Unaiza", "Manaswi");
        newGame.run();
    }



}
