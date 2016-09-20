package main.db;

import java.io.Serializable;
import java.util.Date;
import org.prevayler.Transaction;

/**
 *
 * @author Nicholai
 */
public class UpdateEntryTransaction implements Serializable, Transaction<root>{

    private String resourceId;
    private int price;
    private long UnixTime;
    
    public UpdateEntryTransaction(long UnixTime, int price, String id){
        this.UnixTime = UnixTime;
        this.price = price;
        this.resourceId = id;
    }
    
    @Override
    public void executeOn(root prevalentSystem, Date executionTime) {
        prevalentSystem.getEntries().get(this.resourceId).addPrice(UnixTime, price);
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getResourceId(){
        return this.resourceId;
    }
    
}
