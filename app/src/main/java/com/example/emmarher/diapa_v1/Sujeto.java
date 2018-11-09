package com.example.emmarher.diapa_v1;

public class Sujeto {
    //Fields
    private int sujetoID;
    private String sujetoName;

    //constructors
    public Sujeto(){}
    public Sujeto(int id, String sujetoname){
        this.sujetoID = id;
        this.sujetoName = sujetoname;
    }
    //properties
    public void setID(int id){
        this.sujetoID = id;
    }
    public int getID(){
        return this.sujetoID;
    }

    public void setSujetoName (String sujetoname){
        this.sujetoName = sujetoname;
    }
    public String getSujetoName(){
        return this.sujetoName;
    }
}

/*
public class Student {
    // fields
    private int studentID;
    private String studentName;
    // constructors
    public Student() {}
    public Student(int id, String studentname) {
        this.studentID = id;
        this.studentName = studentname;
    }
    // properties
    public void setID(int id) {
        this.studentID = id;
    }
    public int getID() {
        return this.studentID;
    }
    public void setStudentName(String studentname) {
        this.studentName = studentname;
    }
    public String getStudentName() {
        return this.studentName;
    }
}*/
