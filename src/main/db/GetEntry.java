package main.db;

import java.util.Date;
import org.prevayler.Query;

/**
 *
 * @author Nicholai
 */
public class GetEntry implements Query<root, Statistics> {

    private String resourceId;

    public GetEntry(String RID) {
        this.resourceId = RID;
    }

    @Override
    public Statistics query(root prevalentSystem, Date executionTime) throws Exception {
        return prevalentSystem.getEntries().get(resourceId);
    }

}
