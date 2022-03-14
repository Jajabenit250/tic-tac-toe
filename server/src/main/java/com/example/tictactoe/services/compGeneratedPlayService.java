package com.example.tictactoe.services;

import com.example.tictactoe.exceptions.InvalidBoardException;

import java.util.Map;

public class compGeneratedPlayService {
    public Map<String, String> board;

    public void updateState(String position ) throws InvalidBoardException {
        if (board.get(position) != null) {
            throw new InvalidBoardException("The position has already been taken");
        }
        board.put(position, null);
    }
}
