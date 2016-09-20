package main.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import org.prevayler.TransactionWithQuery;

/**
 *
 * @author Nicholai
 */
public class FindEntry implements TransactionWithQuery<root, Statistics>, Serializable {

    private String resourceId;

    public FindEntry(String rID) {
        this.resourceId = rID;
    }

    @Override
    public Statistics executeAndQuery(root prevalentSystem, Date executionTime) throws Exception {
        Map<String, Statistics> mp = prevalentSystem.getEntries();
        return mp.containsKey(this.resourceId) ? mp.get(this.resourceId) : null;
    }

    public String getPhoneNumber() {
        return this.resourceId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.resourceId = phoneNumber;
    }

}
