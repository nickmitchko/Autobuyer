/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.settings;

/**
 *
 * @author Nicholai
 */
public class Account {

    protected String username;
    protected String password;
    protected String secuhash;

    public Account(String username, String password, String secuhash) {
        this.username = username;
        this.password = password;
        this.secuhash = makeHash(secuhash);
        System.out.println(this.secuhash);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSecurityHash() {
        return this.secuhash;
    }
    
    private String makeHash(String q){
        return EAHash.makeHash(q);
    }

}
