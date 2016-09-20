package main.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @see profit >= 20% for Bid Price \r\n profit >= 29.41% for Bid Now sales
 * @author Nicholai
 */
public class ConfigSettings {

    private final File configFile;
    private boolean isLoaded = false;
    private ArrayList<Account> accounts;
    private double purchaseRate = 0.6;
    private double sellBuyRate = 0.85;
    private double sellBidRate = 0.75;

    public ConfigSettings(File config) {
        this.configFile = config;
        this.load();
    }
    
    //<editor-fold defaultstate="collapsed" desc="System Look">
    private void load() {
        isLoaded = false;
        if (!canReadConfig()) {
            log("ConfigSettings","Config File cannot be read");
            return;
        }
        try {
            accounts = new ArrayList<>();
            BufferedReader configReader = new BufferedReader(new FileReader(this.configFile));
            String line = null;
            while((line = configReader.readLine()) != null){
                try{
                    log("Config Property", line);
                    if(!line.contains("=")){
                        continue;
                    }
                    String[] keys = line.split("=");
                    switch(keys[0]){
                        case "purchaseRate" : {
                            this.purchaseRate = Double.parseDouble(keys[1].replaceAll("[^\\d.]", ""));
                            break;
                        }
                        case "sellBuyRate" : {
                            this.sellBuyRate = Double.parseDouble(keys[1].replaceAll("[^\\d.]", ""));
                            break;
                        }
                        case "sellBidRate" : {
                            this.sellBidRate = Double.parseDouble(keys[1].replaceAll("[^\\d.]", ""));
                            break;
                        }
                        case "accountDetails" : {
                            String[] accountArray = keys[1].split(";");
                            accounts.add(new Account(accountArray[0],accountArray[1],accountArray[2]));
                            break;
                        }
                        case "**":{
                            log("Comment:", keys[1]);
                            break;
                        }
                        default :{
                            log(keys[0]);
                            break;
                        }
                    }
                } catch (Exception e){
                    
                }
            }
        } catch (FileNotFoundException ex) {
            isLoaded = false;
        } catch (IOException ex) {
            Logger.getLogger(ConfigSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        isLoaded = true;
    }
    //</editor-fold>
    
    private boolean canReadConfig() {
        if (this.configFile == null) {
            log("ConfigSettings","Config File is Null");
            return false;
        }
        return this.configFile.canRead() && this.configFile.exists();
    }
    
    public boolean isLoaded(){
        return this.isLoaded;
    }
    
    public ArrayList<Account> getAccounts(){
        return isLoaded ? this.accounts : null;
    }
    
    private void log(Object... obj){
        for(Object o: obj){
            System.out.print(o + "\t");
        }
        System.out.println();
    }
}
