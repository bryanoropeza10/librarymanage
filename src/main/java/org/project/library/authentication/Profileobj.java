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
    private String fname;
    private String lname;
    private String gender;
    private String address;
    private String email;
    private String phone;
    
    public Profileobj(){
        this.fname = "";
        this.lname = "";
        this.address = "";
        this.gender = "";
        this.email = "";
        this.phone = "";
    }
    public Profileobj(String fn, String ln, String gn, String ad, String em, String ph){
        this.fname = fn;
        this.lname = ln;
        this.address = ad;
        this.gender = gn;
        this.email = em;
        this.phone = ph;
    }
    
    //set methods
    public void setFname(String fn){this.fname = fn;}
    public void setLname(String ln){ this.lname = ln;}
    public void setGender(String gn){ this.gender = gn;}
    public void setAddress(String ad){ this.address = ad;}
    public void setEmail(String em){ this.email = em;}
    public void seetPhone(String ph){ this.phone = ph;}
    
    //get methods
    public String getFname(){return this.fname;}
    public String getLname(){return this.lname;}
    public String getGender(){ return this.gender;}
    public String getAddress(){ return this.address;}
    public String getEmail(){ return this.email; }
    public String getPhone(){ return this.phone;}
}
