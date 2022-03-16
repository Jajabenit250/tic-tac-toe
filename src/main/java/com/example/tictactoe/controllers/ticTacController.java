package com.example.tictactoe.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;




@RestController
public class ticTacController {

    class Move {
        int row, col;
    }

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

        if (resWinner == "Player" || resWinner == "Computer" ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Board Provided a winner: " + resWinner);
        } else if (resWinner == "Draw" ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Looks Like Board Provided has a: " + resWinner);
        } 

        char sugestedMove[][]  = {{boardMoves[0].charAt(0),boardMoves[1].charAt(0), boardMoves[2].charAt(0) },
                {boardMoves[3].charAt(0),boardMoves[4].charAt(0), boardMoves[5].charAt(0) },
                {boardMoves[6].charAt(0),boardMoves[7].charAt(0), boardMoves[8].charAt(0) }};
 
    Move bestMove = findBestMove(sugestedMove);

    sugestedMove [bestMove.row][bestMove.col] = 'O';
        String finalPlay = "";
        for (int i=0; i < sugestedMove.length; i++) {
            for (int j=0; j < sugestedMove[i].length; j++) {
                finalPlay += sugestedMove[i][j];
            }
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(finalPlay);
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
                return "Player";
            } else if (line.equals("OOO")) {
                return "Computer";
            }
        }

        if (board.stream().filter(remainingMove -> " ".equals(remainingMove)).count() <= 1) {
            return "Draw";
        }

        return null;
    }

    static Boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return true;
        return false;
    }
 
    static int evaluate(char b[][])
    {
    for (int row = 0; row < 3; row++)
    {
        if (b[row][0] == b[row][1] &&
            b[row][1] == b[row][2])
        {
            if (b[row][0] == 'X')
                return +10;
            else if (b[row][0] == 'O')
                return -10;
        }
    }

    for (int col = 0; col < 3; col++)
    {
        if (b[0][col] == b[1][col] &&
            b[1][col] == b[2][col])
        {
            if (b[0][col] == 'X')
                return +10;
 
            else if (b[0][col] == 'O')
                return -10;
        }
    }
    if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
    {
        if (b[0][0] == 'X')
            return +10;
        else if (b[0][0] == 'O')
            return -10;
    }
 
    if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
    {
        if (b[0][2] == 'X')
            return +10;
        else if (b[0][2] == 'O')
            return -10;
    }

    return 0;
}

    public int maxValue(char board[][],
                       int depth, Boolean isMax)
    {
        int score = evaluate(board);

    if (score == 10)
        return score;
    if (score == -10)
        return score;

    if (isMovesLeft(board) == false)
        return 0;
        if (isMax)
        {
            int best = -1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j]==' ')
                    {
                        board[i][j] = 'X';
                        best = Math.max(best, maxValue(board,
                                depth + 1, !isMax));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
        else
        {
            int best = 1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (board[i][j] == ' ')
                    {
                        board[i][j] = 'O';
                        best = Math.min(best, maxValue(board,
                                depth + 1, !isMax));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }
    public Move findBestMove(char board[][])
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == ' ')
                {
                    board[i][j] = 'X';
                    int moveVal = maxValue(board, 0, false);

                    board[i][j] = ' ';
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

}
 