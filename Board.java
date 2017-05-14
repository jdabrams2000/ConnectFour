public class Board{
  private int cols;
  private int rows;
  private String[][] chips; //Red chips are "R", black chips are "B", empty is "O"
  
  public Board(){
    cols = 7;
    rows = 6;
    chips = new String[rows][cols];
    for(int i = 0;i<rows;i++){
      for(int p = 0;p<cols;p++){
        chips[i][p]="O";
      }
    }
  }
  
  public String toString(){                  //print out board
    String result="";
    for( int i=0;i<rows;i++){
      for( int j = 0;j<cols;j++){
        result+=chips[i][j]+" ";
      }
      result+="\n";
    }
    return result;
  }
  
  public void drop(int col, String color){      //drop chip in selected column
    if(color == "R"||color == "B"){
      for(int i = rows-1;i>=0;i--){
        if(chips[i][col]=="O"){
          chips[i][col]=color;
          break;
        }
      }
    }
  }
  
  public void clearBoard(){      //clear board
    chips = new String[rows][cols];
    for(int i = 0;i<rows;i++){
      for(int p = 0;p<cols;p++){
        chips[i][p]="O";
      }
    }
  }
  
  public String check(int row, int col){  //return chip color at row, column 
    return chips[row][col];
  }
}