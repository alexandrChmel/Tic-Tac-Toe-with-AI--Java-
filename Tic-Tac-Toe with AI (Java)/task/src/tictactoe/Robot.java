package tictactoe;

import java.util.Random;

public class Robot {
    String mode;
    char symbol;
    char oponentSymbol;

    Robot(String mode, char symbol){
        if (mode.equals("easy") || mode.equals("medium") || mode.equals("hard")) {
            this.mode = mode;
        }
        this.symbol = symbol;
        if (symbol == 'X') {
            oponentSymbol = 'O';
        } else {
            oponentSymbol = 'X';
        }
    }

    public void makeMove(char[][] field) {
        switch (mode) {
            case "easy" -> easyMove(field);
            case "medium" -> mediumMove(field);
            case "hard" -> hardMove(field);
        }
    }

    public void hardMove(char[][] field) {
        int bestScoreX = -9999;
        int bestScoreO = 9999;
        boolean isMaximizing = symbol == 'X';
        int[] bestMove = null;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == ' ') {
                    field[i][j] = symbol;
                    int score = minimax(field, 0, !isMaximizing);
                    field[i][j] = ' ';
                    if ((symbol == 'X' && score > bestScoreX)) {
                        bestScoreX = score;
                        bestMove = new int[]{i, j};
                    }
                    if ((symbol == 'O' && score < bestScoreO)) {
                        bestScoreO = score;
                        bestMove = new int[]{i, j};
                    }

                }
            }
        }
        if (bestMove != null){
            field[bestMove[0]][bestMove[1]] = symbol;
        }
    }

    public int minimax(char[][] field, int depth, boolean isMaximizingPlayer) {
        String boardValue = fieldValue(field);
        if (!boardValue.equals("nothing")){
            switch (boardValue) {
                case "X" -> {
                    return 10;
                }
                case "O" -> {
                    return -10;
                }
                case "tie" -> {
                    return 0;
                }
            }
        }

        if (isMaximizingPlayer) {
            int bestVal = -9999;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] == ' ') {
                        field[i][j] = 'X';
                        int value = minimax(field, depth + 1, false);
                        field[i][j] = ' ';
                        bestVal = Math.max(bestVal, value);
                    }
                }
            }
            return bestVal;
        } else {
            int bestVal = 9999;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] == ' ') {
                        field[i][j] = 'O';
                        int value = minimax(field, depth + 1, true);
                        field[i][j] = ' ';
                        bestVal = Math.min(bestVal, value);
                    }

                }
            }
            return bestVal;
        }
    }

    public void mediumMove(char[][] field) {

        // col
        for (int i = 0; i < field.length; i++) {
            int sumYou = 0;
            int sumOpponent = 0;
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == symbol) {
                    sumYou++;
                } else if (field[i][j] == oponentSymbol) {
                    sumOpponent++;
                }
            }
            if (sumYou == field.length - 1 || sumOpponent == field.length - 1) {
                for (int k = 0; k < field[i].length; k++) {
                    if (field[i][k] == ' ') {
                        field[i][k] = symbol;
                        return;
                    }
                }
            }
        }
        // row
        for (int i = 0; i < field.length; i++) {
            int sumYou = 0;
            int sumOpponent = 0;
            for (int j = 0; j < field[i].length; j++) {
                if (field[j][i] == symbol) {
                    sumYou++;
                } else if (field[j][i] == oponentSymbol) {
                    sumOpponent++;
                }
            }
            if (sumYou == field.length - 1 || sumOpponent == field.length - 1) {
                for (int k = 0; k < field[i].length; k++) {
                    if (field[k][i] == ' ') {
                        field[k][i] = symbol;
                        return;
                    }
                }
            }
        }

        while (true) {
            int sumYou = 0;
            int sumOpponent = 0;
            for (int i = 0; i < field.length; i++) {
                if (field[i][i] == symbol) {
                    sumYou++;
                } else if (field[i][i] == oponentSymbol) {
                    sumOpponent++;
                }
            }
            if (sumYou == field.length - 1 || sumOpponent == field.length - 1) {
                for (int k = 0; k < field.length; k++) {
                    if (field[k][k] == ' ') {
                        field[k][k] = symbol;
                        return;
                    }
                }
            }
            break;
        }

        while (true) {
            int sumYou = 0;
            int sumOpponent = 0;
            for (int i = 0; i < field.length; i++) {
                if (field[field.length - 1 - i][i] == symbol) {
                    sumYou++;
                } else if (field[field.length - 1 - i][i] == oponentSymbol) {
                    sumOpponent++;
                }
            }
            if (sumYou == field.length - 1 || sumOpponent == field.length - 1) {
                for (int k = 0; k < field.length; k++) {
                    if (field[field.length - 1 - k][k] == ' ') {
                        field[field.length - 1 - k][k] = symbol;
                        return;
                    }
                }
            }
            break;
        }
        easyMove(field);
    }

    public void easyMove(char[][] field) {
        Random random = new Random();
        int row = random.nextInt(field.length);
        int col = random.nextInt(field.length);
        while (field[row][col] != ' ') {
            row = random.nextInt(field.length);
            col = random.nextInt(field.length);
        }
        field[row][col] = symbol;
    }

    public static String fieldValue(char[][] field) {
        char winChar = 'n';

        // --- row + col control ----

        for (int i = 0; i < field.length; i++) {
            int count1 = 0;
            int count2 = 0;
            for (int j = 0; j < field[i].length; j++) {
                if (field[0][i] == field[j][i] && field[0][i] != ' ') {
                    count1++;
                }
                if (field[i][0] == field[i][j] && field[i][0] != ' ') {
                    count2++;
                }
            }
            if (count1 == field.length) {
                winChar = field[0][i];
            } else if (count2 == field[i].length) {
                winChar = field[i][0];
            }
        }

        // --- cross control ----

        int count3 = 0;
        for (int j = 0; j < field.length; j++) {
            if (field[0][0] == field[j][j] && field[j][j] != ' ') {
                count3++;
            }
        }
        if (count3 == field.length) {
            winChar = field[0][0];
        }

        // second
        int count4 = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i][field.length - 1 - i] == field[0][field.length - 1] && field[i][field.length - 1 - i] != ' ') {
                count4++;
            }
        }
        if (count4 == field.length) {
            winChar = field[field.length - 1][0];
        }

        int openSpots = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == ' ') {
                    openSpots++;
                }
            }
        }

        // --- who won -----

        // TODO maybe you need to return 10 or - 10 with symbol now by 'X' or 'O'

        // 5
        if (winChar == 'n' && openSpots == 0) {
            return "tie";
        } else if (winChar == 'n') {
            return "nothing";
        }
        return String.valueOf(winChar);
    }
}


