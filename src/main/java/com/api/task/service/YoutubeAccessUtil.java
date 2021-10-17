package com.api.task.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class YoutubeAccessUtil {
    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtubeService;

    /**
     * Initialize a YouTube object to fetch videos detail from a YouTube channel and return a list of PlaylistItem
     *
     * @param chennelId Youtube Channel id.
     * @return List of PlaylistItem
     */
    public static List<PlaylistItem> GetDateFromYoutubeChennel(String chennelId) throws IOException {
        // Define a list to store items in the list of uploaded videos.
        List<PlaylistItem> playlistItemList = new ArrayList<>();
        // Read the developer key from the properties file.
        Properties properties = new Properties();
        try {
            InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        // This object is used to make YouTube Data API requests. The last
        // argument is required, but since we don't need anything
        // initialized when the HttpRequest is initialized, we override
        // the interface and provide a no-op function.
        youtubeService = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("youtube-chennel-access-data").build();

        YouTube.Channels.List request = youtubeService.channels()
                .list("snippet,contentDetails,statistics");

        // Set your developer key from the {{ Google Cloud Console }} for
        // non-authenticated requests. See:
        // {{ https://cloud.google.com/console }}
        String apiKey = properties.getProperty("youtube.apikey");
        request.setKey(apiKey);
        ChannelListResponse channelResult = request.setId("UCOO4KwlBt52Yk_6OURJUCHQ").execute();
        List<Channel> channelsList = channelResult.getItems();
        if (channelsList != null) {
            // The user's default channel is the first item in the list.
            // Extract the playlist ID for the channel's videos from the
            // API response.
            String uploadPlaylistId =
                    channelsList.get(0).getContentDetails().getRelatedPlaylists().getUploads();

            // Retrieve the playlist of the channel's uploaded videos.
            YouTube.PlaylistItems.List playlistItemRequest =
                    youtubeService.playlistItems().list("id,contentDetails,snippet");
            playlistItemRequest.setPlaylistId(uploadPlaylistId);

            // Only retrieve data used in this application, thereby making
            // the application more efficient. See:
            // https://developers.google.com/youtube/v3/getting-started#partial
            playlistItemRequest.setFields(
                    "items(contentDetails/videoId,snippet/description),nextPageToken,pageInfo");

            String nextToken = "";

            // Call the API one or more times to retrieve all items in the
            // list. As long as the API response returns a nextPageToken,
            // there are still more items to retrieve.
            do {
                playlistItemRequest.setPageToken(nextToken);
                playlistItemRequest.setKey(apiKey);
                PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                playlistItemList.addAll(playlistItemResult.getItems());

                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);
        }
        return playlistItemList;
    }
}
