package com.example.tictactoebackend.models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String from;
    private String text;

    @Override
    public String toString(){
        return "From: "+from+ "   text: "+text;
    }

}

