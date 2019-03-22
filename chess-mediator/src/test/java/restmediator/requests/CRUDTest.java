package restmediator.requests;

import com.chessapi.jsonpojo.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class CRUDTest {
    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    CRUD crud = new CRUD();

    @Test
    public void getRequest() {
        String response = crud.getRequest(closeableHttpClient,"getSessions");
        assertNotNull(response);
    }

    @Test
    public void postRequest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerName","player1");
        jsonObject.put("isFirstMove",false);
        Response response = crud.postRequest(closeableHttpClient,"newgame",jsonObject);
        assertNotNull(response);
    }
}