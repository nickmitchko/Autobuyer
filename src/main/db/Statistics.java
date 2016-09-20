package main.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nicholai
 */
public class Statistics implements Serializable{
    private double thrityDayAverage;
    private double sevenDayAverage;
    private double threeDayAverage;
    private static final long thirty = 2592000L;
    private static final long seven  = 604800L;
    private static final long three  = 259200L;
    private Map<Long, Integer> entries = new HashMap<>();
    
    public Statistics(){
        thrityDayAverage = 0.0;
        sevenDayAverage = 0.0;
        threeDayAverage = 0.0;
    }
    
    /**
     * @see This automatically calculates averages
     * @param timeStamp Unix time of price find
     * @param n price find
     */
    public void addPrice(long timeStamp, int n){
        entries.put(timeStamp, n);
        calculate();
    }
    
    private void calculate(){
        //calculates the average to last thousand
        long currentTime = System.currentTimeMillis() / 1000L;
        double thirtyDayNum = 0.0,
                sevenDayNum = 0.0,
                threeDayNum = 0.0,
                thirtyDayTot = 0.0,
                sevenDayTot = 0.0,
                threeDayTot = 0.0;
        
        for(long timeStamp : this.entries.keySet()){
            long difference = timeStamp - currentTime;
            if(difference > thirty){
                entries.remove(timeStamp);
                continue;
            }
            thirtyDayNum += 1.0;
            thirtyDayTot += this.entries.get(timeStamp);
            if(difference <= seven){
                sevenDayNum += 1.0;
                sevenDayTot += this.entries.get(timeStamp);
            }
            if(difference <= three){
                threeDayNum += 1.0;
                threeDayTot += this.entries.get(timeStamp);
            }
        }
        this.thrityDayAverage = thirtyDayTot / thirtyDayNum;
        this.threeDayAverage = sevenDayTot / sevenDayNum;
        this.sevenDayAverage = threeDayTot / threeDayNum;
    }
    
    public int getThrityDayAverage(){
        return (int) equalize(this.thrityDayAverage);
    }
    
    public int getSevenDayAverage(){
        return (int) equalize(this.sevenDayAverage);
    }
    
    public int getThreeDayAverage(){
        return (int) equalize(this.threeDayAverage);
    }
    
    private double equalize(double in){
        return (in % 1000) * 1000;
    }
}
