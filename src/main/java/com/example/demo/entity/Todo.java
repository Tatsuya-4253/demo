package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String style;
    private LocalDateTime createdAt;
    private boolean complete;

    public Todo(){

    }
    public Todo(String name, String style){
        this.name=name;
        this.style=style;
        this.createdAt=LocalDateTime.now(); 
    }

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getStyle(){
        return style;
    }
    public void setStyle(String style){
        this.style = style;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        return createdAt.format(formatter);
    }
    public boolean getComplete(){
        return complete;
    }
    public void setComplete(boolean complete){
        this.complete = complete;
    }
    
}
