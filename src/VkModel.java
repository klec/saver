import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by klec on 7/4/14.
 */
public class VkModel {
    //https://oauth.vk.com/authorize?client_id=4445948&scope=audio&%20redirect_uri=https://oauth.vk.com/blank.html&display=page&v=5.0&response_type=token
    public static final String ACCESS_TOKEN = "e017d973913d992d89d43f7cdfc9973d3664521ab49a349ccd2a54401ff271a2113d2703c83b8d97b8366";

    public static void getListOfMusic(){
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(
                    "https://api.vk.com/method/audio.get?access_token=" + ACCESS_TOKEN +
                            "&owner_id=5496709" +
                            "&needuser=0" +
                            "&count=2" +
                            "&v=5.21");
            getRequest.addHeader("accept", "application/json");

        try {
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer result = new StringBuffer();

            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            //Object o = new Gson().fromJson(result.toString(), Object.class);
            //System.out.println(o);
            //String url = ((JSONObject)(JSONArray)((JSONObject)((JSONObject)o.get("response")).get("items")).get(0)).get("url").toString();
            //PlayFrom("http://cs5013.vk.me/u886852/audios/f53e3ba65ae1.mp3");
            PlayFrom("http://cs5013.vk.me/u886852/audios/2fff48d034a8.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//{"response":{"count":60,"items":[{"id":290095402,"owner_id":5496709,"artist":"Apocalyptica","title":"Path Vol.2 (feat. Sandra Nasic)","duration":203,"url":"http:\/\/cs5013.vk.me\/u886852\/audios\/2fff48d034a8.mp3?extra=q4OXK5ML-kr9sVwVqjuGFKDsdlcrLKU2Zr90uCIqpI1vS91cIiC8Jqi9FldQbAFHbrtkF0v9SC2ff0uMRHVb18FHG7plpaY","lyrics_id":7581817,"genre_id":21},{"id":284848833,"owner_id":5496709,"artist":"Mario Basanov & Vidis Ft. Jazzu","title":"I'll Be Gone","duration":238,"url":"http:\/\/cs4272.vk.me\/u2498783\/audios\/5c72419eb53b.mp3?extra=KpxZQVvnAz5HqQQI9pjY5TBZUOaIZuoMhZiBp_kOgnopi4M6ApdcGF4uIhKYBiVLQCK17Lc9Esj03DLsxKx1viKwjb9IMrU","lyrics_id":1721267,"genre_id":18}]}}
    public void PlayList(){

    }

    public static void PlayFrom(String url){
        try {
            InputStream input = new URL(url).openStream();
            //FileInputStream FIS = new FileInputStream("/home/klec/IdeaProjects/saver/resources/apoc.mp3");
            BufferedInputStream BIS = new BufferedInputStream(input);

            Player player = new Player(BIS);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Media hit = new Media(url);
        //MediaPlayer mediaPlayer = new MediaPlayer(hit);
        //mediaPlayer.play();
    }


    public static void main( String[] args ){
        getListOfMusic();
    }
}
