package org.example.tierhub;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;


public class TestAPI {
        private static final String API_KEY = "e78a6c56c9e24f7b92c4449a57dba4ac";
        static final String BASE_URL = "https://api.rawg.io/api/games";

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter game name: ");
            String gameName = scanner.nextLine();

            try {
                String imageUrl = getGameImageUrl(gameName);
                if (imageUrl != null) {
                    System.out.println("Game Image URL: " + imageUrl);
                } else {
                    System.out.println("No image found for: " + gameName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String getGameImageUrl(String gameName) throws Exception {
            HttpClient client = HttpClient.newHttpClient();

            // Build search URL
            String searchUrl = BASE_URL + "?key=" + API_KEY
                    + "&search=" + java.net.URLEncoder.encode(gameName, "UTF-8")
                    + "&page_size=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(searchUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

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