package main.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nicholai
 */
public class Search {
    
    private int start = 100,
            num = 1000,
            minb = 4000;
    private Session session;
    private String TRANSFER_URL = "https://utas.fut.ea.com/ut/game/fifa14/transfermarket?type=%s&lev=%s"+
                   "&num=%s&minb=%s" +
                   "&start=%s";
    
    public Search(int start, int num, int minb, Session s){
        if(start != 0){
            this.start = start;
        }
        if(num != 0){
            this.num = num;
        }
        if(minb != 0){
            this.minb = minb;
        }
        this.session = s;
    }
        
    public ArrayList<JSONObject> search(){
        String[] tokens = (String[]) session.getTokens();
        String emptyPost = " ";
        try {
            URL search = getSearchURL();
            HttpURLConnection s7 = (HttpURLConnection) search.openConnection();
            s7.setDoOutput(true);
            s7.setRequestMethod("POST");
            s7.setInstanceFollowRedirects(true);
//            s7.setRequestProperty("Accept", "application/json");
//            s7.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
//            s7.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
//            s7.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
//            s7.setRequestProperty("Content-Type", "application/json");
//            s7.setRequestProperty("Content-Length", "1");
//            s7.setRequestProperty("Origin", "http://www.easports.com");
//            s7.setRequestProperty("Referer", "http://www.easports.com/iframe/fut/bundles/futweb/web/flash/FifaUltimateTeam.swf");
            s7.setRequestProperty("X-UT-Embed-Error", "true");
            s7.setRequestProperty("X-HTTP-Method-Override", "GET");
            s7.setRequestProperty("X-UT-PHISHING-TOKEN", tokens[1]);
            s7.setRequestProperty("X-UT-SID", tokens[0]);
//            s7.setRequestProperty("Connection", "keep-alive");
//            s7.setRequestProperty("Host", "utas.fut.ea.com");
            OutputStream output = null;
            try{
                output = s7.getOutputStream();
                output.write(emptyPost.getBytes());
            } finally {
                if(output != null){
                    output.close();
                }
            }
            InputStream temp = s7.getInputStream();
            Scanner sc = new Scanner(temp).useDelimiter("//A");
            String response = sc.hasNext() ? sc.next() : "";
            ArrayList<JSONObject> jo = new ArrayList<>();
            for(String a: response.split(",(?=\\{)")){
                jo.add(new JSONObject(a));
            }
            start += num;
            return jo;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return null;
    }
    
    private URL getSearchURL() throws MalformedURLException {
        String url = String.format(TRANSFER_URL, "player", "gold", num, minb, start);
        return new URL(url);
    }
}
