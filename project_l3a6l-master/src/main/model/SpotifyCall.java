package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SpotifyCall {

    public static void stopPlaying() {
        String uri = "https://api.spotify.com/v1/me/player/pause";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(uri))
                .timeout(Duration.ofMinutes(1))
                .header("Authorization", "Bearer " + Constants.auth_token)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playTrack(String track) {
        String uri = "https://api.spotify.com/v1/me/player/play";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString("{ \"uris\": [\"" + track + "\"]}"))
                .uri(URI.create(uri))
                .timeout(Duration.ofMinutes(1))
                .header("Authorization", "Bearer " + Constants.auth_token)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String trackSearch(String q) throws MalformedURLException {
        String uri = "https://api.spotify.com/v1/search?q=" + q + "&type=track";
        String decodedURL = URLDecoder.decode(uri, UTF_8);
        URL url = new URL(decodedURL);
        try {
            URI uri2 = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            uri = uri2.toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String results = "";


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .timeout(Duration.ofMinutes(1))
                .header("Authorization", "Bearer " + Constants.auth_token)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            results = response.body();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }


    public static void parse(String responseBody) {
        responseBody = "[" + responseBody + "]";
        JSONArray tracks = new JSONArray(responseBody);
        JSONObject track = tracks.getJSONObject(0).getJSONObject("tracks");
        JSONArray items = track.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONArray artists = item.getJSONArray("artists");
            String artistName = "";
            for (int x = 0; x < artists.length(); x++) {
                JSONObject artist = artists.getJSONObject(x);
                artistName += ", " + artist.getString("name");
            }
            artistName = artistName.substring(2);
            String trackName = item.getString("name");
            String trackuri = item.getString("uri");

            System.out.println(i + 1 + ". Name: " + trackName + " Artist(s): " + artistName + " uri: " + trackuri);
        }
    }

    public static String getTrackURI(int index, String responseBody) {
        responseBody = "[" + responseBody + "]";
        JSONArray tracks = new JSONArray(responseBody);
        JSONObject track = tracks.getJSONObject(0).getJSONObject("tracks");
        JSONArray items = track.getJSONArray("items");

        JSONObject item = items.getJSONObject(index - 1);

        return  item.getString("uri");
    }
}
