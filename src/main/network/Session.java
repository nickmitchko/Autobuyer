package main.network;

/**
 *
 * @author Nicholai
 */
public class Session<T, V> {
    
    private final T[] tokens;
    private final V cookies;
    
    public Session(T[] tok, V cook){
        this.tokens = tok;
        this.cookies = cook;
    }
    
    public T[] getTokens(){
        return this.tokens;
    }
    
    public V getCookies(){
        return this.cookies;
    }
    
}
