package org.example.service.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Rawg {
    private static final String API_KEY = "e78a6c56c9e24f7b92c4449a57dba4ac";
    static final String BASE_URL = "https://api.rawg.io/api/games";

    public static String getGameImageUrl(String gameName){
        HttpClient client = HttpClient.newHttpClient();

        // Build search URL
        String searchUrl = null;
        try {
            searchUrl = BASE_URL + "?key=" + API_KEY
                    + "&search=" + java.net.URLEncoder.encode(gameName, "UTF-8")
                    + "&page_size=1";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(searchUrl))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            JSONObject json = new JSONObject(response.body());
            JSONArray results = json.getJSONArray("results");

            if (results.length() > 0) {
                JSONObject game = results.getJSONObject(0);
                // Get the background image URL
                String imageUrl = game.optString("background_image", null);
                return imageUrl;
            }
        }
        return null;
    }
}
