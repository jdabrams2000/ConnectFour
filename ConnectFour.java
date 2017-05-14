//Made by Ruben Burgos, Jacob Abrams, Helen Luo Pd 1

import java.util.Scanner;
import java.util.ArrayList;

public class ConnectFour {
  public static void main (String[] args) {
    Scanner xy = new Scanner(System.in);
    Board board = new Board();
    board.clearBoard();
    boolean gameplayCondition = false;
    int turnCount = 0;
    while (!gameplayCondition) {
      String color = "R";
      int c = -1;
      while (c < 0 || 6 < c) {
        System.out.print("Enter the column number (From 0 to 6, inclusive)");
        c = xy.nextInt();
        if(!board.check(0,c).equals("O"))
          c = -1;
      }
      board.drop(c, color);
      System.out.print(board.toString());
      if (tiedGame(board)) {
        System.out.print("There is no winner, only losers.");
        gameplayCondition = true;
      }
      if (winGame(board))
        gameplayCondition = true;
      if(gameplayCondition==false){
        turnCount++;
        System.out.println();
        if (turnCount % 2 != 0)
          color = "B";
        int move = moveComputer(board, turnCount);
        board.drop(move, color);
        turnCount++;
        System.out.print(board.toString());
        if (tiedGame(board)) {
          System.out.print("There is no winner, only losers.");
          gameplayCondition = true;
        }
        if (winGame(board))
          gameplayCondition = true;
      }
    }
    xy.close();
  }
  
  public static int moveComputer (Board board, int turn){
    ArrayList<Integer> no = new ArrayList<Integer>();
    if (turn == 1){
      return 3;
    }
    else{
       //Calculates where the computer can win
      for (int c = 0; c < 7; c++){         //verticals
        for (int r = 5; r > 3; r--){
          if (board.check(r,c).equals(board.check(r - 1, c)) &&
              board.check(r - 1, c).equals(board.check(r - 2, c)) &&
              board.check(r - 3, c).equals("O") &&
              board.check(r,c).equals("B")){
            return c;
          }
        }
      }
      for (int r = 5; r>=0; r--){         //horizontals consecutive
        for (int c = 0; c < 4; c++){          //left to right
          if (board.check(r,c).equals(board.check(r,c + 1)) &&
              board.check(r,c + 1).equals(board.check(r,c + 2)) &&
              board.check(r,c + 3).equals("O") &&
              board.check(r,c).equals("B")){
            if(r<5 && !board.check(r+1,c+3).equals("O"))
              return(c+3);
            else if(r==5)
              return(c+3);
            else
              no.add(c+3);
          }
        }
        for (int c = 6; c > 2; c--){         //right to left
          if (board.check(r,c).equals(board.check(r,c - 1)) &&
              board.check(r,c - 1).equals(board.check(r,c - 2)) &&
              board.check(r,c - 3).equals("O") &&
              board.check(r,c).equals("B")){
            if(r<5 && !board.check(r+1,c-3).equals("O"))
              return(c-3);
            else if(r==5)
              return (c-3);
            else
              no.add(c-3);
          }
        }
      }
      for (int r = 5; r>=0; r--){         //horizontals non-consecutive
        for (int c = 0; c < 4; c++){      //R O R R
          if (board.check(r,c).equals(board.check(r,c + 2)) &&
              board.check(r,c + 2).equals(board.check(r,c + 3)) &&
              board.check(r,c + 1).equals("O") &&
              board.check(r,c).equals("B")){
            if(r<5 && !board.check(r+1,c+1).equals("O"))
              return (c+1);
            else if(r==5)
              return (c-3);
            else
              no.add(c+1);
          }
        }
        for (int c = 0; c < 4; c++){     //R R O R
          if (board.check(r,c).equals(board.check(r,c + 1)) &&
              board.check(r,c + 1).equals(board.check(r,c + 3)) &&
              board.check(r,c + 2).equals("O") &&
              board.check(r,c).equals("B")){
            if(r<5 && !board.check(r+1,c+2).equals("O"))
              return(c+2);
            else if(r==5)
              return (c-3);
            else
              no.add(c+2);
          }
        }
      }
      for (int r = 5; r > 2; r--){                   //diagonal consecutive
        for (int c = 0; c < 4; c++){                 //diagonal right-up
          if (board.check(r,c).equals(board.check(r-1,c+1)) &&
              board.check(r-1,c+1).equals(board.check(r-2,c+2)) &&
              board.check(r,c).equals("R")){
            if(board.check(r-3,c+3).equals("O") &&   //block
              !board.check(r-2,c+3).equals("O"))
                return c+3;
            if(board.check(r-2,c+3).equals("O") &&  //avoid losing 
              !board.check(r-1,c+3).equals("O"))
                no.add(toInteger(c+3));
          }
          if (board.check(r,c).equals(board.check(r - 1, c + 1)) &&        //non-consecutive 1
              board.check(r - 1, c + 1).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 2, c + 2).equals("O") &&
              !board.check(r - 1, c + 2).equals("O") &&
              board.check(r,c).equals("R")){
            return (c + 2);
          }
          if (board.check(r,c).equals(board.check(r - 1, c + 1)) &&
              board.check(r - 1, c + 1).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 1, c + 2).equals("O") &&
              board.check(r - 2, c + 2).equals("O") &&
              !board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c + 2);
          }
          if (board.check(r,c).equals(board.check(r - 2, c + 2)) &&          //2
              board.check(r - 2, c + 2).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 1, c + 1).equals("O") &&
              !board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            return (c + 1);
          }
          if (board.check(r,c).equals(board.check(r - 2, c + 2)) &&
              board.check(r - 2, c + 2).equals(board.check(r - 3, c + 3)) &&
              board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c + 1);
          }
        }
      }
      for (int r = 0; r < 3; r++){                   
        for (int c = 0; c < 4; c++){           //consecutive diagonal right-down
          if (board.check(r,c).equals(board.check(r+1,c+1)) &&
              board.check(r+1,c+1).equals(board.check(r+2,c+2)) &&
              board.check(r,c).equals("B")){
            if(board.check(r+3,c+3).equals("O")){
              if(r==0 && !board.check(r+5, c+3).equals("O") &&
                board.check(r+4, c+3).equals("O"))
                no.add(c+3);
              if(r==1 && board.check(r+4, c+3).equals("O"))
                no.add(c+3);
              if(r<2){
                if(!board.check(r+4,c+3).equals("O"))
                    return(c+3);
              }
              if(r==2)
                return (c+3);
            }
          }
        }
      }
      for (int r = 5; r > 2; r--){          //diagonal left-up (consecutive)
        for (int c = 6; c > 2; c--){
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 3, c - 3).equals("O") &&
              !board.check(r - 2, c - 3).equals("O") &&
              board.check(r,c).equals("B")){
            return (c - 3);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 2, c - 3).equals("O") &&
              !board.check(r - 1, c - 3).equals("O") &&
              board.check(r,c).equals("B")){
            no.add(c - 3);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&        //non-consecutive 1
              board.check(r - 1, c - 1).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 2, c - 2).equals("O") &&
              !board.check(r - 1, c - 2).equals("O") &&
              board.check(r,c).equals("B")){
            return (c - 2);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 1, c - 2).equals("O") &&
              !board.check(r, c - 2).equals("O") &&
              board.check(r,c).equals("B")){
            no.add(c - 2);
          }
          if (board.check(r,c).equals(board.check(r - 2, c - 2)) &&          //2
              board.check(r - 2, c - 2).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 1, c - 1).equals("O") &&
              !board.check(r, c - 1).equals("O") &&
              board.check(r,c).equals("B")){
            return (c - 1);
          }
          if (board.check(r,c).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 2, c - 2).equals(board.check(r - 3, c - 3)) &&
              board.check(r, c - 1).equals("O") &&
              board.check(r,c).equals("B")){
            no.add(c - 1);
          }
        }
      }
      for (int r = 0; r < 3; r++){                   
        for (int c = 6; c > 2; c--){           //consecutive diagonal left-down
          if (board.check(r,c).equals(board.check(r+1,c-1)) &&
              board.check(r+1,c-1).equals(board.check(r+2,c-2)) &&
              board.check(r,c).equals("B")){
            if(board.check(r+3,c-3).equals("O")){
              if(r==0 && !board.check(r+5, c-3).equals("O") &&
                board.check(r+4, c-3).equals("O"))
                no.add(c-3);
              if(r==1 && board.check(r+4, c-3).equals("O"))
                no.add(c-3);
              if(r<2){
                if(!board.check(r+4,c-3).equals("O"))
                    return(c-3);
              }
              if(r==2)
                return (c-3);
            }
          }
        }
      }
          //Calculates where the player can win
      for (int c = 0; c < 7; c++){         //verticals
        for (int r = 5; r > 3; r--){
          if (board.check(r,c).equals(board.check(r - 1, c)) &&
              board.check(r - 1, c).equals(board.check(r - 2, c)) &&
              board.check(r - 3, c).equals("O") &&
              board.check(r,c).equals("R")){
            return c;
          }
        }
      }
      for (int r = 5; r>=0; r--){         //horizontals consecutive
        for (int c = 0; c < 4; c++){          //left to right
          if (board.check(r,c).equals(board.check(r,c + 1)) &&
              board.check(r,c + 1).equals(board.check(r,c + 2)) &&
              board.check(r,c + 3).equals("O") &&
              board.check(r,c).equals("R")){
            if(r<5 && !board.check(r+1,c+3).equals("O"))
              return(c+3);
            else if(r==5)
              return(c+3);
            else
              no.add(c+3);
          }
        }
        for (int c = 6; c > 2; c--){         //right to left
          if (board.check(r,c).equals(board.check(r,c - 1)) &&
              board.check(r,c - 1).equals(board.check(r,c - 2)) &&
              board.check(r,c - 3).equals("O") &&
              board.check(r,c).equals("R")){
            if(r<5 && !board.check(r+1,c-3).equals("O"))
              return(c-3);
            else if(r==5)
              return (c-3);
            else
              no.add(c-3);
          }
        }
      }
      for (int r = 5; r>=0; r--){         //horizontals non-consecutive
        for (int c = 0; c < 4; c++){      //R O R R
          if (board.check(r,c).equals(board.check(r,c + 2)) &&
              board.check(r,c + 2).equals(board.check(r,c + 3)) &&
              board.check(r,c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            if(r<5 && !board.check(r+1,c+1).equals("O"))
              return (c+1);
            else if(r==5)
              return (c-3);
            else
              no.add(c+1);
          }
        }
        for (int c = 0; c < 4; c++){     //R R O R
          if (board.check(r,c).equals(board.check(r,c + 1)) &&
              board.check(r,c + 1).equals(board.check(r,c + 3)) &&
              board.check(r,c + 2).equals("O") &&
              board.check(r,c).equals("R")){
            if(r<5 && !board.check(r+1,c+2).equals("O"))
              return(c+2);
            else if(r==5)
              return (c-3);
            else
              no.add(c+2);
          }
        }
      }
      for (int r = 5; r > 2; r--){                   //diagonal consecutive
        for (int c = 0; c < 4; c++){                 //diagonal right-up
          if (board.check(r,c).equals(board.check(r-1,c+1)) &&
              board.check(r-1,c+1).equals(board.check(r-2,c+2)) &&
              board.check(r,c).equals("R")){
            if(board.check(r-3,c+3).equals("O") &&   //block
              !board.check(r-2,c+3).equals("O"))
                return c+3;
            if(board.check(r-2,c+3).equals("O") &&  //avoid losing 
              !board.check(r-1,c+3).equals("O"))
                no.add(toInteger(c+3));
          }
          if (board.check(r,c).equals(board.check(r - 1, c + 1)) &&        //non-consecutive 1
              board.check(r - 1, c + 1).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 2, c + 2).equals("O") &&
              !board.check(r - 1, c + 2).equals("O") &&
              board.check(r,c).equals("R")){
            return (c + 2);
          }
          if (board.check(r,c).equals(board.check(r - 1, c + 1)) &&
              board.check(r - 1, c + 1).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 1, c + 2).equals("O") &&
              board.check(r - 2, c + 2).equals("O") &&
              !board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c + 2);
          }
          if (board.check(r,c).equals(board.check(r - 2, c + 2)) &&          //2
              board.check(r - 2, c + 2).equals(board.check(r - 3, c + 3)) &&
              board.check(r - 1, c + 1).equals("O") &&
              !board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            return (c + 1);
          }
          if (board.check(r,c).equals(board.check(r - 2, c + 2)) &&
              board.check(r - 2, c + 2).equals(board.check(r - 3, c + 3)) &&
              board.check(r, c + 1).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c + 1);
          }
        }
      }
      for (int r = 0; r < 3; r++){                   
        for (int c = 0; c < 4; c++){           //consecutive diagonal right-down
          if (board.check(r,c).equals(board.check(r+1,c+1)) &&
              board.check(r+1,c+1).equals(board.check(r+2,c+2)) &&
              board.check(r,c).equals("R")){
            if(board.check(r+3,c+3).equals("O")){
              if(r==0 && !board.check(r+5, c+3).equals("O") &&
                board.check(r+4, c+3).equals("O"))
                no.add(c+3);
              if(r==1 && board.check(r+4, c+3).equals("O"))
                no.add(c+3);
              if(r<2){
                if(!board.check(r+4,c+3).equals("O"))
                    return(c+3);
              }
              if(r==2)
                return (c+3);
            }
          }
        }
      }
      for (int r = 5; r > 2; r--){          //diagonal left-up (consecutive)
        for (int c = 6; c > 2; c--){
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 3, c - 3).equals("O") &&
              !board.check(r - 2, c - 3).equals("O") &&
              board.check(r,c).equals("R")){
            return (c - 3);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 2, c - 3).equals("O") &&
              !board.check(r - 1, c - 3).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c - 3);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&        //non-consecutive 1
              board.check(r - 1, c - 1).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 2, c - 2).equals("O") &&
              !board.check(r - 1, c - 2).equals("O") &&
              board.check(r,c).equals("R")){
            return (c - 2);
          }
          if (board.check(r,c).equals(board.check(r - 1, c - 1)) &&
              board.check(r - 1, c - 1).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 1, c - 2).equals("O") &&
              !board.check(r, c - 2).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c - 2);
          }
          if (board.check(r,c).equals(board.check(r - 2, c - 2)) &&          //2
              board.check(r - 2, c - 2).equals(board.check(r - 3, c - 3)) &&
              board.check(r - 1, c - 1).equals("O") &&
              !board.check(r, c - 1).equals("O") &&
              board.check(r,c).equals("R")){
            return (c - 1);
          }
          if (board.check(r,c).equals(board.check(r - 2, c - 2)) &&
              board.check(r - 2, c - 2).equals(board.check(r - 3, c - 3)) &&
              board.check(r, c - 1).equals("O") &&
              board.check(r,c).equals("R")){
            no.add(c - 1);
          }
        }
      }
      for (int r = 0; r < 3; r++){                   
        for (int c = 6; c > 2; c--){           //consecutive diagonal left-down
          if (board.check(r,c).equals(board.check(r+1,c-1)) &&
              board.check(r+1,c-1).equals(board.check(r+2,c-2)) &&
              board.check(r,c).equals("R")){
            if(board.check(r+3,c-3).equals("O")){
              if(r==0 && !board.check(r+5, c-3).equals("O") &&
                board.check(r+4, c-3).equals("O"))
                no.add(c-3);
              if(r==1 && board.check(r+4, c-3).equals("O"))
                no.add(c-3);
              if(r<2){
                if(!board.check(r+4,c-3).equals("O"))
                    return(c-3);
              }
              if(r==2)
                return (c-3);
            }
          }
        }
      }
      for (int r = 5; r >= 0; r--){          //preventing the two way horizontal trap
        for (int c = 1; c < 5; c++){
          if (board.check(r,c).equals(board.check(r, c + 1)) &&
              board.check(r, c + 2).equals("O") &&
              board.check(r, c - 1).equals("O") &&
              board.check(r,c).equals("R")){
            if(r==5)
              return (c - 1);
            if(r<5 && !board.check(r+1, c-1).equals("O") &&
               !board.check(r+1, c+2).equals("O"))
              return (c-1);
          }
        }
      }
      for (int q = 6; q > 2; q--){
        for (int w = 5; w > 2; w--){
          if (board.check(w,q).equals(board.check(w - 1, q - 1)) &&
              board.check(w - 1, q - 1).equals(board.check(w - 2, q - 2)) &&
              board.check(w - 3, q - 3).equals("O") &&
              !board.check(w - 2, q - 3).equals("O") &&
              board.check(w,q).equals("R")){
            return (q - 3);
          }
        }
      }
      for (int z = 0; z > 4; z++){
        for (int x = 5; x > 2; x--){
          if (board.check(x,z).equals(board.check(x - 1, z + 1)) &&
              board.check(x - 1, z + 1).equals(board.check(x - 2, z + 2)) &&
              board.check(x - 3, z + 3).equals("O") &&
              board.check(x,z).equals("R")){
            return (z + 3);
          }
        }
      }
      sort(no);
      removeRepeat(no);
      ArrayList<Integer> ok = new ArrayList<Integer>();
      int[] nums = new int[7];
      for (int e = 0; e < nums.length; e++){ //Calculates the ok moves
        nums[e] = e;
      }
      for (int r = 0; r < no.size(); r++){
        for (int t = 0; t < nums.length; t++){
          if (toInteger(nums[t]) == no.get(r)){
            nums[t] = -1;
          }
        }
      }
      for (int b = 0; b < nums.length; b++){
        if (nums[b] != -1){
          ok.add(nums[b]);
        }
      }
      if (ok.size() > 0){
        return (int) (ok.get((int) (Math.random() * (ok.size() - 1))));
      }
    }
    return (int) (Math.random() * 6);
  }
  
  public static ArrayList<Integer> removeRepeat (ArrayList<Integer> arr1){
    int size = arr1.size();
    if(size>0){
      for (int b = 0; b < size-1; b++){
        if (arr1.get(b) == arr1.get(b + 1)){
          arr1.remove(b);
          b = 0;
          size--;
        }
      }
    }
    return arr1;
  }
  
  public static ArrayList<Integer> sort (ArrayList<Integer> arr){
    for (int v = 0; v < arr.size() - 1; v++){
      if (arr.get(v) > arr.get(v + 1)){
        Integer temp = arr.get(v);
        arr.set(v, arr.get(v + 1));
        arr.set(v + 1, temp);
      }
      v = 0;
    }
    return arr;
  }
  
  public static Integer toInteger(int n){
    Integer r = new Integer(n);
    return r;
  }
  
    public static boolean winGame (Board board) {
    boolean value = false;
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 6; k++) {
        if (board.check(k,i).equals(board.check(k,i+1)) &&
            board.check(k,i+1).equals(board.check(k,i+2)) &&
            board.check(k,i+2).equals(board.check(k,i+3))
           && (board.check(k,i).equals("B") || board.check(k,i).equals("R"))) {
          System.out.print(board.check(k,i) + " is the winner!");
          value = true;
        }}}
    for (int i = 0; i < 7; i++) {
      for (int k = 0; k < 3; k++) {
        if (board.check(k,i).equals(board.check(k+1,i)) &&
            board.check(k+1,i).equals(board.check(k+2,i)) &&
            board.check(k+2,i).equals(board.check(k+3,i))
           && (board.check(k,i).equals("B") || board.check(k,i).equals("R"))) {
          System.out.print(board.check(k,i) + " is the winner!");
          value = true;
      }}}
    for (int i = 0; i < 4; i++) {
      for (int k = 0; k < 3; k++) {
        if (board.check(k,i).equals(board.check(k+1,i+1)) &&
            board.check(k+1,i+1).equals(board.check(k+2,i+2)) &&
            board.check(k+2,i+2).equals(board.check(k+3,i+3
                                                   ))
           && (board.check(k,i).equals("B") || board.check(k,i).equals("R"))) {
          System.out.print(board.check(k,i) + " is the winner!");
          value = true;
      }}}
    for (int i = 6; i > 2; i--) {
      for (int k = 0; k < 3; k++) {
        if (board.check(k,i).equals(board.check(k+1,i-1)) &&
            board.check(k+1,i-1).equals(board.check(k+2,i-2)) &&
            board.check(k+2,i-2).equals(board.check(k+3,i-3))
           && (board.check(k,i).equals("B") || board.check(k,i).equals("R"))) {
          System.out.print(board.check(k,i) + " is the winner!");
          value = true;
      }}}
    return value;
  }

  public static boolean tiedGame (Board board) {
    boolean value = true;
    for (int i = 0; i < 7; i++) {
      if (board.check(0,i) == "O") {
        value = false;
      }
}
    return value;
  }}
