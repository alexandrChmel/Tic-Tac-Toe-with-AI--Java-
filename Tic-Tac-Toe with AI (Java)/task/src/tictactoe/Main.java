package tictactoe;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // --- get input ----
        Scanner scanner = new Scanner(System.in);
        // --- set and print field ----
        char[][] field = createField();
        // --- starting input ----
        String input;
        String[] inputArr;
        while(true){
            System.out.println("Input command: ");
            input = scanner.nextLine();
            inputArr = input.split(" ");

            if (inputArr[0].equals("exit")){
                return;
            }

            if (inputArr.length == 3){
                if (inputArr[0].equals("start")){
                    if (inputArr[1].equals("easy") || inputArr[1].equals("medium") || inputArr[1].equals("hard") || inputArr[1].equals("user")){
                        if (inputArr[2].equals("easy") || inputArr[2].equals("medium") || inputArr[2].equals("hard") || inputArr[2].equals("user")){
                            break;
                        }
                    }
                }
            }
            System.out.println("Bad parameters!");
        }
        printField(field);

        // --- start the game!!! -----

        String first;
        String second;
        if (inputArr[1].equals("user")){
            first = "user";
        } else{
            first = "bot";
        }
        if (inputArr[2].equals("user")){
            second = "user";
        } else{
            second = "bot";
        }

        Robot robot1 = new Robot(inputArr[1], 'X');
        Robot robot2 = new Robot(inputArr[2], 'O');


        while(!checkWin(field)){
            if (first.equals("user")){
                set(field, scanner);
            } else{
                System.out.printf("Making move level \"%s\"\n", robot1.mode);
                robot1.makeMove(field);
            }
            printField(field);
            if (checkWin(field)){
                break;
            }

            if (second.equals("user")){
                set(field, scanner);
            } else{
                System.out.printf("Making move level \"%s\"\n", robot2.mode);
                robot2.makeMove(field);
            }
            printField(field);
        }
    }

    public static char getCurrent(char[][] field){
        int xs = 0;
        int os = 0;
        for (char[] chars : field) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == 'X') {
                    xs++;
                }
                if (chars[j] == 'O') {
                    os++;
                }
            }
        }
        if (xs == os){
            return 'X';
        } else{
            return 'O';
        }
    }
    public static char[][] createField(){
        char[][] field = new char[3][3];
        for (int i = 0; i < field.length; i++){
            Arrays.fill(field[i], ' ');
        }
        return field;
    }
    public static boolean checkWin(char[][] field){
        char winChar = 'd';

        // --- game not finished control ---

        int counSpace = 0;
        for (char[] chars : field) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == ' ') {
                    counSpace++;
                    break;
                }
            }
        }
        if (counSpace == 0){
            System.out.println("Draw");
            return true;
        }
        // --- normal control ----

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


            int count1 = 0;
            for (int j = 0; j < field.length; j++){
                if (field[0][0] == field[j][j] && field[j][j] != ' '){
                    count1++;
                }
            }
            if (count1 == field.length){
                winChar = field[0][0];
            }


        // second
        // TODO HERE IS ERROR
        /*
        _ _ X
        _ X _
        X _ _
         */
        // TODO paste this to robot
        int count2 = 0;
        for (int i = 0; i < field.length; i++){
            if (field[i][field.length - 1 - i] == field[0][field.length - 1] && field[i][field.length - 1 - i] != ' ') {
                count2++;
            }
        }
        if (count2 == field.length){
            winChar = field[field.length - 1][0];
        }

        // --- who won -----
        if (winChar != 'd'){
            System.out.println(winChar + " wins");
            return true;
        }
        return false;
    }

    public static void printField(char[][] field){
        System.out.println("---------");
        for (char[] chars : field) {
            System.out.print("| ");
            for (int j = 0; j < chars.length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
    public static void set(char[][] field, Scanner scanner){
        // --- isValid check ---
        boolean again;
        int row = 0;
        int col = 0;
        while(true){
            again = false;
            System.out.print("Enter the coordinates: ");
            try {
                row = scanner.nextInt() - 1;
                col = scanner.nextInt() - 1;

                if (row >= 3 || col >= 3 || row < 0 || col < 0){
                    System.out.println("Coordinates should be from 1 to 3!");
                    again = true;
                } else if (field[row][col] != ' '){
                    System.out.println("This cell is occupied! Choose another one!");
                    again = true;
                }
            } catch (Exception e){
                System.out.println("You should enter numbers!");
                scanner.nextLine();
                again = true;
            }

            if (!again){
                break;
            }
        }
        field[row][col] = getCurrent(field);
    }

}