/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.project.library.authentication;

/**
 *
 * @author bryan
 */
public class Profileobj {
    private int id;
    private String fname;
    private String lname;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private String tablename;
    private String username;
    
    public Profileobj(){
        this.id = 0;
        this.fname = "";
        this.lname = "";
        this.address = "";
        this.gender = "";
        this.email = "";
        this.phone = "";
        this.tablename = "";
        this.username = "";
    }
    public Profileobj(String fn, String ln, String gn, 
            String ad, String em,String ph, String tb){
        this.fname = fn;
        this.lname = ln;
        this.address = ad;
        this.gender = gn;
        this.email = em;
        this.phone = ph;
        this.tablename = tb;
    }
    
    //set methods
    public void setID(int id){this.id = id;}
    public void setFname(String fn){this.fname = fn;}
    public void setLname(String ln){ this.lname = ln;}
    public void setGender(String gn){ this.gender = gn;}
    public void setAddress(String ad){ this.address = ad;}
    public void setEmail(String em){ this.email = em;}
    public void setPhone(String ph){ this.phone = ph;}
    public void setTable(String tb){this.tablename = tb;}
    public void setUsername(String us){this.username = us;}
    
    //get methods
    public int getID(){return this.id;}
    public String getFname(){return this.fname;}
    public String getLname(){return this.lname;}
    public String getGender(){ return this.gender;}
    public String getAddress(){ return this.address;}
    public String getEmail(){ return this.email; }
    public String getPhone(){ return this.phone;}
    public String getTable(){ return this.tablename;}
    public String getUsername(){return this.username;}
}
