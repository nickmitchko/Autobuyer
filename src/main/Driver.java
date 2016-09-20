package main;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.util.ArrayList;
import main.db.EntryManager;
import main.network.Search;
import main.network.Session;
import main.network.login.Login;
import main.settings.Account;
import main.settings.ConfigSettings;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nicholai
 */
public class Driver {
    private ConfigSettings config;
    private final int accountRpm = 1;
    private int numAccounts = 0;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Session<String[], CookieManager>> sessions = new ArrayList<>();
    private final EntryManager em;
    private Login login = new Login();

    /**
     * @deprecated 
     */
    public Driver() {
        this("config.ini", null);
    }

    public Driver(String config, EntryManager em) {
        this.config = new ConfigSettings(new File(config));
        this.accounts = this.config.getAccounts();
        this.em = em;
    }
    
    public void start() throws IOException, MalformedURLException, JSONException{
        // Login
        this.Login();
        // Searcher
        this.searcher();
    }
    
    @SuppressWarnings("static-access")
    private void Login() throws IOException, MalformedURLException, JSONException{
        if(!this.config.isLoaded() || this.accounts == null){
            this.config = new ConfigSettings(new File("config.ini"));
            this.accounts = this.config.getAccounts();
        }
        for(Account acct : this.accounts){
            Session<String[], CookieManager> Login = this.login.Login(acct);
            sessions.add(Login);
            this.numAccounts++;
        }
    }

    private void searcher() {
        ArrayList<Search> searchers = new ArrayList<>();
        for(Session sc: sessions){
            searchers.add(new Search(0,0,5000,sc));
        }
        for(;;){
            for(Search s : searchers){
                ArrayList<JSONObject> search = s.search();
                System.out.println();
            }
        }
    }
    
    private void processCards(ArrayList<JSONObject> jo){
        
    }
    
}
