package Secur.o.Cord.OAuth2;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Token {

    /** Exchanging a authorization code for an access token
     *
     * @param clientId Client Id of the application, not null
     * @param clientSecret Client Secret of the application, not null
     * @param code Authorization code, you got when the user authorized the application
     * @param redirectURI The Redirect URI you set for the application
     * @return a JSON file containing an Access token, the token type, the expiration time in seconds, the scopes and a Refresh token
     * @throws IOException if opening of the URLConnection fails or connecting to the Discord API Endpoint fails
     */
    @RequestMapping(value = "/api/oauth2/token/exchange")
    public static String tokenExchange(@RequestParam("clientId") String clientId,
                                       @RequestParam("clientSecret") String clientSecret,
                                       @RequestParam("code") String code,
                                       @RequestParam("redirectURI") URI redirectURI)
                                       throws IOException {

        String urlString = "https://discord.com/api/oauth2/token";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("redirect_uri", redirectURI.toString());


        conn.setConnectTimeout(5000);

            conn.connect();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        writer.write(getPostDataString(params));
        writer.flush();
        writer.close();
        conn.getOutputStream().close();


        String response = null;

        int connResponse = conn.getResponseCode();
        if (connResponse == HttpsURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = br.readLine();

        }
        return response;
    }

    /** Refreshs an access token using a refresh token
     *
     * @param clientId Client Id of the application, not null
     * @param clientSecret Client Secret of the application, not null
     * @param refreshToken Refresh token you got with the Access token
     * @param redirectURI The Redirect URI you set for the application
     * @return a JSON file containing an Access token, the token type, the expiration time in seconds, the scopes and a Refresh token
     * @throws IOException if opening of the URLConnection fails or connecting to the Discord API Endpoint fails
     */
    @RequestMapping(value = "/api/oauth2/token/refresh")
    public static String refreshToken(@RequestParam("clientId") String clientId,
                                       @RequestParam("clientSecret") String clientSecret,
                                       @RequestParam("refreshToken") String refreshToken,
                                       @RequestParam("redirectURI") URI redirectURI)
                                       throws IOException {

        String urlString = "https://discordapp.com/api/oauth2/token";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        params.put("redirect_uri", redirectURI.toString());


        conn.setConnectTimeout(5000);
        conn.connect();


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        writer.write(getPostDataString(params));
        writer.flush();
        writer.close();
        conn.getOutputStream().close();


        String response = null;

        int connResponse = conn.getResponseCode();
        if (connResponse == HttpsURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = br.readLine();

        }
        return response;
    }

    /** Creates a URL-encoded String containing parameters specified in params
     *
     * @param params the specified parameters, not null
     * @return a URL-encoded String
     */
    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
