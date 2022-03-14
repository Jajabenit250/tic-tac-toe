package com.example.tictactoe.controllers;

import com.example.tictactoe.exceptions.InvalidBoardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


    static class Move
{
    int row, col;
};

@RestController
public class ticTacController {



    @GetMapping("/main")
    public ResponseEntity<Object> getStudentList(@RequestParam String board) {
        String[] boardMoves = {" ", " ", " ", " ", " ", " ", " ", " ", " "};
        List<String> movesLst = List.of(board.toUpperCase().split(""));
        if (movesLst.size() > 9) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("provided board has many characters it should exceed 9");
        }

        for (int i = 0; i < movesLst.size(); i++) {
            if (!movesLst.get(i).equals("O") && !movesLst.get(i).equals("X") && !movesLst.get(i).equals(" ")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("invalid character at character " + i);
            }
            boardMoves[i] = movesLst.get(i);
        }
        String resWinner = checkWinner(List.of(boardMoves));

        System.out.println(resWinner);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(boardMoves);
    }

    public String checkWinner(List<String> board) {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board.get(0) + board.get(1) + board.get(2);
                    break;
                case 1:
                    line = board.get(3) + board.get(4) + board.get(5);
                    break;
                case 2:
                    line = board.get(6) + board.get(7) + board.get(8);
                    break;
                case 3:
                    line = board.get(0) + board.get(3) + board.get(6);
                    break;
                case 4:
                    line = board.get(1) + board.get(4) + board.get(7);
                    break;
                case 5:
                    line = board.get(2) + board.get(5) + board.get(8);
                    break;
                case 6:
                    line = board.get(0) + board.get(4) + board.get(8);
                    break;
                case 7:
                    line = board.get(2) + board.get(4) + board.get(6);
                    break;
            }
            if (line.equals("XXX")) {
                return "Player is already a Winner";
            } else if (line.equals("OOO")) {
                return "Computer is already a Winner";
            }
        }

        if(board.stream().filter(Objects::nonNull).collect(Collectors.toList()).size() == 8) {
            return "draw";
        }

        return null;
    }

//    public String nextMove (List<String> board) {
//
//    }
}


// static int minimax(char board[][],
//                     int depth, Boolean isMax)
// {
//     // If this maximizer's move
//     if (isMax)
//     {
//         int best = -1000;
 
//         // Traverse all cells
//         for (int i = 0; i < 3; i++)
//         {
//             for (int j = 0; j < 3; j++)
//             {
//                 // Check if cell is empty
//                 if (board[i][j]=='_')
//                 {
//                     // Make the move
//                     board[i][j] = player;
 
//                     // Call minimax recursively and choose
//                     // the maximum value
//                     best = Math.max(best, minimax(board,
//                                     depth + 1, !isMax));
 
//                     // Undo the move
//                     board[i][j] = '_';
//                 }
//             }
//         }
//         return best;
//     }
 
//     // If this minimizer's move
//     else
//     {
//         int best = 1000;
 
//         // Traverse all cells
//         for (int i = 0; i < 3; i++)
//         {
//             for (int j = 0; j < 3; j++)
//             {
//                 // Check if cell is empty
//                 if (board[i][j] == '_')
//                 {
//                     // Make the move
//                     board[i][j] = opponent;
 
//                     // Call minimax recursively and choose
//                     // the minimum value
//                     best = Math.min(best, minimax(board,
//                                     depth + 1, !isMax));
 
//                     // Undo the move
//                     board[i][j] = '_';
//                 }
//             }
//         }
//         return best;
//     }
// }
 
// // This will return the best possible
// // move for the player
// static Move findBestMove(char board[][])
// {
//     int bestVal = -1000;
//     Move bestMove = new Move();
//     bestMove.row = -1;
//     bestMove.col = -1;
 
//     // Traverse all cells, evaluate minimax function
//     // for all empty cells. And return the cell
//     // with optimal value.
//     for (int i = 0; i < 3; i++)
//     {
//         for (int j = 0; j < 3; j++)
//         {
//             // Check if cell is empty
//             if (board[i][j] == '_')
//             {
//                 // Make the move
//                 board[i][j] = player;
 
//                 // compute evaluation function for this
//                 // move.
//                 int moveVal = minimax(board, 0, false);
 
//                 // Undo the move
//                 board[i][j] = '_';
 
//                 // If the value of the current move is
//                 // more than the best value, then update
//                 // best/
//                 if (moveVal > bestVal)
//                 {
//                     bestMove.row = i;
//                     bestMove.col = j;
//                     bestVal = moveVal;
//                 }
//             }
//         }
//     }
 
//     System.out.printf("The value of the best Move " +
//                              "is : %d\n\n", bestVal);
 
//     return bestMove;
// }
 