package restmediator.requests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import restmediator.pojo.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CRUD {


    /**
     * Method that executes a GET HTTP request from specified path
     * @param closeableHttpClient
     * @param pathVariable
     * @return
     */
    public String getRequest(CloseableHttpClient closeableHttpClient, String pathVariable){
        HttpGet httpGet = new HttpGet("http://localhost:8080/" + pathVariable);
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            //
            closeableHttpResponse = closeableHttpClient.execute(httpGet);
            //System.out.println(httpResponse.getStatusLine());

            //Reading the response entity as string
            String response = getResponseString(closeableHttpResponse);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Exception occured";
        } finally {
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public Response postRequest(CloseableHttpClient httpclient, String pathvariable, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost("http://localhost:8080/" + pathvariable);
        httpPost.setHeader("Accept", "application/json");
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            //Getting the post parameters
            StringEntity postParams = new StringEntity(jsonObject.toString());
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(postParams);

            //Execute the http POST request
            closeableHttpResponse = httpclient.execute(httpPost);

            //Converting the response entity to string
            String httpresp = getResponseString(closeableHttpResponse);

            Response response = new ObjectMapper().readValue(httpresp, Response.class);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getResponseString(CloseableHttpResponse closeableHttpResponse) throws IOException {
        //get the response entity
        HttpEntity httpEntity = closeableHttpResponse.getEntity();

        //Read into a buffer String
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        StringBuffer response = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        System.out.println(response);

        //Close the entity
        EntityUtils.consume(httpEntity);
        return response.toString();
    }



}
