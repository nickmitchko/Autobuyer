package main.network.login;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import main.network.Session;
import main.settings.Account;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @version 1.0
 * @author Nicholai
 */
public class Login {
    
    private static final String encoding = "UTF-8";
    
    public Login(){
        
    }
    
    public static Session<String[], CookieManager> Login(Account acct) throws MalformedURLException, IOException, JSONException{
        String sid = "";
        String validatePost = ("answer=" + acct.getSecurityHash() + "&deviceId=0");
        //<editor-fold defaultstate="collapsed" desc="URL Declarations">
        URL fifa_ultimate_team = new URL("http://www.ea.com/soccer/fifa-ultimate-team");
        URL fut_web = new URL("http://www.easports.com/iframe/fut/?locale=en_US&baseShowoffUrl=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team%2Fshow-off&guest_app_uri=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team");
        //</editor-fold>
        
        CookieManager cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieManager.setDefault(cm);
        
        //<editor-fold defaultstate="collapsed" desc="1st URL">
        HttpURLConnection firstURL = (HttpURLConnection) fifa_ultimate_team.openConnection();
        firstURL.setInstanceFollowRedirects(true);
        firstURL.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        firstURL.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        firstURL.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        firstURL.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        firstURL.setRequestProperty("Host", "www.ea.com");
        firstURL.setRequestProperty("Connection", "keep-alive");
        firstURL.setRequestProperty("DNT", "1");
        firstURL.getInputStream();
        URL https = new URL(firstURL.getHeaderField("Location"));
        HttpURLConnection redirect = null;
        if(!fifa_ultimate_team.getProtocol().equals(https.getProtocol())){
            redirect = (HttpURLConnection) https.openConnection();
            redirect.setInstanceFollowRedirects(true);
            redirect.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            redirect.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            redirect.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            redirect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
            redirect.setRequestProperty("Host", "www.ea.com");
            redirect.setRequestProperty("Connection", "keep-alive");
            redirect.setRequestProperty("DNT", "1");
            redirect.getInputStream();
        }
        //</editor-fold>
        
        log("1/7");
        
        //<editor-fold defaultstate="collapsed" desc="Login URL">
        byte[] loginPost = getLoginPost(acct);
        HttpURLConnection secondURL = (HttpURLConnection) redirect.getURL().openConnection();
        secondURL.setInstanceFollowRedirects(true);
        secondURL.setDoOutput(true);
        secondURL.setRequestMethod("POST");
        secondURL.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        secondURL.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        secondURL.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        secondURL.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        secondURL.setRequestProperty("Host", "www.ea.com");
        secondURL.setRequestProperty("Connection", "keep-alive");
        secondURL.setRequestProperty("DNT", "1");
        secondURL.setRequestProperty("Origin", "https://signin.ea.com");
        secondURL.setRequestProperty("Content-Length", loginPost.length + "");
        secondURL.setRequestProperty("Referer", redirect.getURL().toString());
        secondURL.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStream loginOutput = null;
            try{
                loginOutput = secondURL.getOutputStream();
                loginOutput.write(loginPost);
            } finally {
                if(loginOutput != null){
                    try{
                        loginOutput.close();
                    } catch (IOException e){
                    }
                }
            }
        Scanner sc = new Scanner(secondURL.getInputStream()).useDelimiter("//A");
        String loginReturn = sc.hasNext() ? sc.next() : "";
        https = new URL(secondURL.getHeaderField("Location"));
        redirect = null;
        //follows the redirect from https signin to http web app
        String body = "";
        if(!secondURL.getURL().getProtocol().equals(https.getProtocol())){
            redirect = (HttpURLConnection) https.openConnection();
            redirect.setInstanceFollowRedirects(true);
            redirect.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            redirect.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            redirect.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            redirect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
            redirect.setRequestProperty("Host", "www.ea.com");
            redirect.setRequestProperty("Connection", "keep-alive");
            redirect.setRequestProperty("DNT", "1");
            Scanner scan;
            try{
                scan = new Scanner(new GZIPInputStream(redirect.getInputStream())).useDelimiter("//A");
            } catch (IOException e){
                scan = new Scanner(redirect.getInputStream()).useDelimiter("//A");
            }
            body = scan.hasNext() ? scan.next() : "";
            sid = body.substring(body.indexOf("userid : \"") + 10, body.indexOf("\"", body.indexOf("userid : \"") + 10));

        }
        //TODO: do login....
        //</editor-fold>
        
        log("2/7");
        
        //<editor-fold defaultstate="collapsed" desc="4 Proticol Change FUT-WEB">
        HttpURLConnection futweb = (HttpURLConnection) fut_web.openConnection();
        futweb.setInstanceFollowRedirects(true);
        futweb.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        futweb.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        futweb.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        futweb.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        futweb.setRequestProperty("DNT", "1");
        futweb.setRequestProperty("Referer", "http://www.easports.com/fifa/football-club/ultimate-team");
        futweb.setRequestProperty("Connection", "keep-alive");
        futweb.setRequestProperty("Host", "www.easports.com");
        futweb.getInputStream();
        URL next = new URL(futweb.getHeaderField("Location"));
        if (!futweb.getURL().getProtocol().equals(next.getProtocol())) {
            futweb = (HttpURLConnection) next.openConnection();
            futweb.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            futweb.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            futweb.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            futweb.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
            futweb.setRequestProperty("DNT", "1");
            futweb.setRequestProperty("Referer", "http://www.easports.com/fifa/football-club/ultimate-team");
            futweb.setRequestProperty("Connection", "keep-alive");
            futweb.setRequestProperty("Host", "www.easports.com");
            futweb.getInputStream();
            next = new URL(futweb.getHeaderField("Location"));
            if (!futweb.getURL().getProtocol().equals(next.getProtocol())) {
                futweb = (HttpURLConnection) next.openConnection();
                futweb.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                futweb.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
                futweb.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
                futweb.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
                futweb.setRequestProperty("DNT", "1");
                futweb.setRequestProperty("Referer", "http://www.easports.com/fifa/football-club/ultimate-team");
                futweb.setRequestProperty("Connection", "keep-alive");
                futweb.setRequestProperty("Host", "www.easports.com");
                futweb.getInputStream();
            }
        }
        futweb = (HttpURLConnection) fut_web.openConnection();
        futweb.setInstanceFollowRedirects(true);
        futweb.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        futweb.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        futweb.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        futweb.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        futweb.setRequestProperty("DNT", "1");
        futweb.setRequestProperty("Referer", "http://www.easports.com/fifa/football-club/ultimate-team");
        futweb.setRequestProperty("Connection", "keep-alive");
        futweb.setRequestProperty("Host", "www.easports.com");
        futweb.getInputStream();
        //</editor-fold>
        
        log("3/7");
        
        //<editor-fold defaultstate="collapsed" desc="shards URL">
        URL shard = new URL("http://www.easports.com/iframe/fut/p/ut/shards?_=" + (System.currentTimeMillis()));
        HttpURLConnection shards = (HttpURLConnection) shard.openConnection();
        shards.setRequestMethod("GET");
        shards.setRequestProperty("Accept", "application/json, text/javascript;");
        shards.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        shards.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        shards.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        shards.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        shards.setRequestProperty("DNT", "1");
        shards.setRequestProperty("Content-Type", "application/json");
        shards.setRequestProperty("Easw-Session-Data-Nucleus-Id", sid);
        shards.setRequestProperty("Referer", "http://www.easports.com/iframe/fut/?baseShowoffUrl=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team%2Fshow-off&guest_app_uri=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team&locale=en_US");
        shards.setRequestProperty("X-UT-Embed-Error", "true");
        shards.setRequestProperty("X-UT-Route", "https://utas.fut.ea.com");
        shards.setRequestProperty("Connection", "keep-alive");
        shards.setRequestProperty("Host", "www.easports.com");
        shards.getInputStream();
        //</editor-fold>
        
        log("4/7");
        
        //<editor-fold defaultstate="collapsed" desc="Account Info URL">
        URL acctinfo = new URL("http://www.easports.com/iframe/fut/p/ut/game/fifa14/user/accountinfo?_=" + (System.currentTimeMillis()));
        HttpURLConnection acctInfo = (HttpURLConnection) acctinfo.openConnection();
        acctInfo.setRequestMethod("GET");
        acctInfo.setRequestProperty("Accept", "text/javascript;");
        acctInfo.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        acctInfo.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        acctInfo.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
        acctInfo.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        acctInfo.setRequestProperty("DNT", "1");
        acctInfo.setRequestProperty("Easw-Session-Data-Nucleus-Id", sid);
        acctInfo.setRequestProperty("Referer", "http://www.easports.com/iframe/fut/?baseShowoffUrl=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team%2Fshow-off&guest_app_uri=http%3A%2F%2Fwww.easports.com%2Ffifa%2Ffootball-club%2Fultimate-team&locale=en_US");
        acctInfo.setRequestProperty("X-UT-Embed-Error", "true");
        acctInfo.setRequestProperty("X-UT-Route", "https://utas.fut.ea.com:443");
        acctInfo.setRequestProperty("Connection", "keep-alive");
        acctInfo.setRequestProperty("Host", "www.easports.com");
        Scanner scan;
        try{
            scan = new Scanner(new GZIPInputStream(acctInfo.getInputStream())).useDelimiter("//A");
        } catch (IOException e){
            scan = new Scanner(acctInfo.getInputStream()).useDelimiter("//A");
        }
        String jsonBody = scan.hasNext() ? scan.next() : "";
        JSONObject AccountInfo = new JSONObject(jsonBody);
        //</editor-fold>
        
        log("5/7");
        
        //<editor-fold defaultstate="collapsed" desc="Auth URL">
        HttpURLConnection auth = null;
        Scanner authScan = null;
        String authPost = getAuthPost(AccountInfo, sid);
//        log(authPost);
        auth = (HttpURLConnection) new URL("http://www.easports.com/iframe/fut/p/ut/auth").openConnection();
        auth.setDoOutput(true);
        auth.setRequestMethod("POST");
        auth.setRequestProperty("Content-Length", "" + authPost.length());
        auth.setRequestProperty("Content-Type", "application/json");
        auth.setRequestProperty("Easw-Session-Data-Nucleus-Id", sid);
        auth.setRequestProperty("X-UT-Route", "https://utas.fut.ea.com:443");
        OutputStream authO = null;
        try {
            authO = auth.getOutputStream();
            authO.write(authPost.getBytes());
        } finally {
            if (authO != null) {
                try {
                    authO.close();
                } catch (IOException e) {
                }
            }
        }

        try {
            authScan = new Scanner(new GZIPInputStream(auth.getInputStream())).useDelimiter("//A");
        } catch (IOException e) {
            authScan = new Scanner(auth.getInputStream()).useDelimiter("//A");
        }
        String response =  (authScan.hasNext() ? authScan.next() : "");
        if(!response.substring(0,2).equals("{\"")){
            response = "{\"" + response;
        }
        JSONObject xsid = new JSONObject(response);
        String X_UT_SID = xsid.getString("sid");
        String[] tokens = new String[2];
        tokens[0] = X_UT_SID;
        //</editor-fold>
        
        log("6/7");
        
        //<editor-fold defaultstate="collapsed" desc="Phishing-Question">
        
        HttpURLConnection fish = (HttpURLConnection) new URL("http://www.easports.com/iframe/fut/p/ut/game/fifa14/phishing/validate").openConnection();
        fish.setRequestMethod("POST");
        fish.setDoOutput(true);
        fish.setRequestProperty("Content-Length", "" + validatePost.length());
        fish.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        fish.setRequestProperty("Easw-Session-Data-Nucleus-Id", sid);
        fish.setRequestProperty("X-UT-Embed-Error", "true");
        fish.setRequestProperty("X-UT-Route", "https://utas.fut.ea.com:443");
        fish.setRequestProperty("X-UT-SID", tokens[0]);
        OutputStream fishOut = null;
        try {
            fishOut = fish.getOutputStream();
            fishOut.write(validatePost.getBytes());
        } finally {
            if (fishOut != null) {
                try {
                    fishOut.close();
                } catch (IOException e) {
                }
            }
        }
        Scanner fishScan = null;
        try {
            fishScan = new Scanner(new GZIPInputStream(fish.getInputStream())).useDelimiter("//A");
        } catch (IOException e) {
            fishScan = new Scanner(fish.getInputStream()).useDelimiter("//A");
        }
        String fishResponse = fishScan.hasNext() ? fishScan.next() : "";
        if(!fishResponse.substring(0,2).equals("{\"")){
            fishResponse = "{\"" + fishResponse;
        }
        JSONObject fishJson = new JSONObject(fishResponse);
        tokens[1] = fishJson.getString("token");
        //</editor-fold>
        
        log("7/7");
        Session<String[], CookieManager> sm = new Session(tokens, cm);
        return sm;
    }
    
    private static byte[] getLoginPost(Account acct) throws UnsupportedEncodingException{
        return ("email="+ URLEncoder.encode(acct.getUsername(), encoding) +"&password="+ URLEncoder.encode(acct.getPassword(), encoding) +"&_rememberMe=on&rememberMe=on&_eventId=submit&facebookAuth=").getBytes();
    }
    
    //(int) (((JSONArray)(((JSONObject) AccountInfo.get("userAccountInfo")).get("personas"))).getJSONObject(0)).get("personaId")
    private static String getAuthPost(JSONObject AccountInfo, String sid) throws UnsupportedEncodingException, JSONException{
        JSONObject info = (((JSONArray)(((JSONObject) AccountInfo.get("userAccountInfo")).get("personas"))).getJSONObject(0));
//        System.out.println("{ \"isReadOnly\": false, \"sku\": \"FUT14WEB\", \"clientVersion\": 1, \"nuc\": "+ sid +", \"nucleusPersonaId\": " + info.get("personaId") + ", \"nucleusPersonaDisplayName\": \""+info.get("personaName")+"\", \"nucleusPersonaPlatform\": \"360\", \"locale\": \"en-GB\", \"method\": \"authcode\", \"priorityLevel\":4, \"identification\": { \"authCode\": \"\" } }");
        return ("{ \"isReadOnly\": false, \"sku\": \"FFA14CAP\", \"clientVersion\": 1, \"nuc\": "+ sid +", \"nucleusPersonaId\": " + info.get("personaId") + ", \"nucleusPersonaDisplayName\": \""+info.get("personaName")+"\", \"nucleusPersonaPlatform\": \"360\", \"locale\": \"en-GB\", \"method\": \"authcode\", \"priorityLevel\": 4, \"identification\": { \"authCode\": \"\" } }");
    }
    
    private static void log(Object... o){
        for(Object obj : o){
            System.out.print(obj + "\t");
        }
        System.out.println();
    }
}
