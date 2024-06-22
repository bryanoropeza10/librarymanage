/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.project.library.patronview;

/**
 *
 * @author bryan
 */
public class Bookobj {
    private String isbn;
    private String author;
    private String title;
    private String madedate;
    private String duedate;
    private String bookformat;
    private String status;
    
    public Bookobj(){
        this.author = "";
        this.title = "";
        this.madedate = "";
        this.duedate = "";
        this.bookformat = "";
        this.status = "";
    }
    public Bookobj(String isbn, String auth, String tit, String made, String due, String format, String sta){
        this.isbn = isbn;
        this.author = auth;
        this.title = tit;
        this.madedate = made;
        this.duedate = due;
        this.bookformat = format;
        this.status = sta;
    }
    
    //get methods
    public String getIsbn(){return this.isbn;}
    public String getAuthor(){ return this.author;}
    public String getTitle(){return this.title;}
    public String getMadedate(){return this.madedate;}
    public String getDuedate(){return this.duedate;}
    public String getBookformat(){return this.bookformat;}
    public String getStatus(){return this.status;}
    
    //set methods
    public void setIsbn(String isbn){this.isbn = isbn;}
    public void setAuthor(String auth){this.author = auth;}
    public void setTitle(String tit){this.title = tit;}
    public void setMadedate(String date){this.madedate = date;}
    public void setDuedate(String date){this.duedate = date;}
    public void setBookformat(String format){this.bookformat = format;}
    public void setStatus(String stat){this.status = stat;}
}
