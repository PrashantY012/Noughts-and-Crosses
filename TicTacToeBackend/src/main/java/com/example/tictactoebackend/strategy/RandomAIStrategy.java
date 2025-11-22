package com.example.tictactoebackend.strategy;

import com.example.tictactoebackend.models.Board;
import com.example.tictactoebackend.models.Player;
import com.example.tictactoebackend.models.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class RandomAIStrategy implements  AIStrategy{

    @Override
    public Position getNextMove(Board board, Player aiPlayer) {
        //find all filled position and set a random value
        int gridSize = board.getGridSize();
        List<List<Integer>>emptyPositions = new ArrayList<>();
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(board.isValidMove(i,j)){
                    emptyPositions.add(new ArrayList<>(Arrays.asList(i,j)));
                }
            }
        }
       //TODO: handle when no space is left
        if(emptyPositions.isEmpty())return null;
        Random rand = new Random();
        int randomIndex = rand.nextInt(emptyPositions.size());
        return new Position(emptyPositions.get(randomIndex).get(0), emptyPositions.get(randomIndex).get(1));
    }

}
