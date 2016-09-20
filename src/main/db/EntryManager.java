package main.db;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

/**
 *
 * @author Nicholai
 */
public class EntryManager {
    private final String name = "Player-Database";
    Prevayler<root> prevayler;
    
    public EntryManager() throws Exception{
        prevayler = PrevaylerFactory.createPrevayler(new root(), name);
    }
    
    /**
     * Adds an entry for the specified resource Id and provides self-adjusting price metrics
     * For use for Adding a new entry, will create one if it doesn't exist
     * This should be called After the decision to buy
     * @param resourceId Resource Id from the search transaction
     * @param price Price as Listed
     * @throws Exception
     */
    public void addEntry(String resourceId, int price) throws Exception{
        long UnixTime = System.currentTimeMillis() / 1000L;
        if(!entryExists(resourceId)){
            prevayler.execute(new CreateEntryTransaction(UnixTime, price, resourceId));
        } else {
            prevayler.execute(new UpdateEntryTransaction(UnixTime, price, resourceId));
        }
    }
    
    public boolean entryExists(String resourceId) throws Exception{
        Statistics execute = prevayler.execute(new FindEntry(resourceId));
        return (execute != null);
    }
    
    
}
