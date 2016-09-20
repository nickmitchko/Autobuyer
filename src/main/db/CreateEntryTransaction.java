package main.db;

import java.io.Serializable;
import java.util.Date;
import org.prevayler.Transaction;

/**
 *
 * @author Nicholai
 */
public class CreateEntryTransaction implements Transaction<root>, Serializable {

    private long UnixTime;
    private int price;
    private String resourceId;

    public CreateEntryTransaction(long UnixTime, int price, String resourceId){
        this.UnixTime = UnixTime;
        this.price = price;
        this.resourceId = resourceId;
    }

    @Override
    public void executeOn(root prevalentSystem, Date executionTime) {
        Statistics thisEntry = new Statistics();
        thisEntry.addPrice(UnixTime, price);
        prevalentSystem.getEntries().put(this.resourceId, thisEntry);
    }
    
    public long getUnixTime(){
        return this.UnixTime;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getResourceId(){
        return this.resourceId;
    }

}