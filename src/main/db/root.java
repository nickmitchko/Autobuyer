package main.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nicholai
 */
public class root implements Serializable {

    private Map<String, Statistics> entries = new HashMap<>();

    public Map<String, Statistics> getEntries() {
        return this.entries;
    }

    public void setEntries(Map<String, Statistics> newEntries) {
        this.entries = newEntries;
    }
}
