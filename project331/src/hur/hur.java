/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hur;

/**
 *
 * @author Safiah
 */
import project331.ConnectFourState;


/**
 * We will apply Heuristic function on minimax with cut off algorithm and alpha
 * Beta with cut off algorithm Sammmmmmmmmmmeraaaaaaaaaaa write the Explanation
 * of the Heuristic function
 *
 * @author
 */
public class hur {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        String[] board = new String[]{
//            "", "", "", "", "", "", "",
//            "", "", "", "", "", "", "",
//            "", "", "", "", "", "", "",
//            "", "", "", "", "B", "", "",
//            "", "", "", "", "B", "", "",
//            "R", "B", "R", "R", "R", "", ""
//        };
//
//        int hurestic = huresticFunction(board);
//
//        System.out.println(hurestic);
//
//    }
   
   /**
    * 
    * @param board
    * @return an integer witch is the heuristic
    */
    public int huresticFunction(byte[] board) {
        int[] result = new int[]{45, 45};
        //vertical
        result[1] = vertical(board, result[1], 1);
        result[0] = vertical(board, result[0], 2);
       // System.out.println("ver" + result[0] + " " + result[1]);// true answer

        //Horizontal
        result[1] = horisuntel(board, result[1], 1);
        result[0] = horisuntel(board, result[0], 2);
       // System.out.println("hur " + result[0] + " " + result[1]);// true answer

        //diagonal of B
        result[1] = upDiagonal(board, result[1], 1);
        result[1] = downDiagonal(board, result[1], 1);
        //diagonal of R
        result[0] = upDiagonal(board, result[0], 2);
        result[0] = downDiagonal(board, result[0], 2);
      //  System.out.println("hur " + result[0] + " " + result[1]);
        
        return result[0] - result[1];
    }
    /**
     * 
     * @param board
     * @param R
     * @param s
     * @return  ------complete it ------
     */
    private int upDiagonal(byte[] board, int R, int s) {
        int col = 7;//bourd[0].length;
        int row = 6;// bourd.length;
        int count;
        for (int r = row - 1; r > 2; r--) {
            for (int c = 0; c < 4; c++) {
                count = 0;
                for (int c1 = c, r1 = r; c1 <= c + 3; c1++, r1--) {
                    if (!(board[r1 * col + c1] == (byte)s)) {

                        count++;
                    } else {
                        break;
                    }
                }
                if (count == 4) {
                    R = R + 1;
                }
            }

        }

        return R;
    }
    /**
     * 
     * @param board
     * @param R
     * @param s
     * @return ------complete it ------
     */
    private int downDiagonal(byte[] board, int R, int s) {
        int col = 7;//bourd[0].length;
        int row = 6;// bourd.length;
        int count;
        for (int r = row - 1; r > 2; r--) {
            for (int c = col - 1; c > 2; c--) {
                count = 0;
                for (int c1 = c, r1 = r; c1 >= c - 3; c1--, r1--) {
                    if (!(board[r1 * col + c1]== (byte)s)) {

                        count++;
                    } else {
                        break;
                    }
                }
                if (count == 4) {
                    R = R + 1;
                }
            }

        }

        return R;
    }
    /**
     * 
     * @param board
     * @param R
     * @param s
     * @return ------complete it ------
     */
    private int vertical(byte[] board, int R, int s) {

        int col = 7;//bourd[0].length;  //we can use get row and col methods in state class
        int row =6;// bourd.length;
        boolean enter ;
        for (int c = 0; c < col; c++) {
            enter = false;
            for (int r = 0; r < 4; r++) {
                if (board[r*col+c]== (byte)s) {
                     R -= 3;
                     enter = true;
                     break;
                }
            }
            if(!enter){
               if (board[4*col+c]== (byte)s) {
                  R -= 2;
               } else if (board[5*col+c]== (byte)s) {
                  R -= 1;
               }
            }
        }
        return R;
    }
    /**
     * 
     * @param board
     * @param R
     * @param s
     * @return ------complete it ------
     */
    private int horisuntel(byte[] board, int R, int s) {
        int col = 7;//bourd[0].length;
        int row = 6;//bourd.length;
        int result ;
        boolean enterC1 , enterC2;
        for (int r = row - 1; r >= 0; r--) {
            result = 0;
            if (board[r*col+((col / 2))]== (byte)s) {
                result = 4;
            } else {
                    enterC1 = false; enterC2=false;
                for (int c1 = (col / 2)-1 , c2 = (col / 2) + 1; c2 != col ; c1--, c2++) {
                    if (board[r*col+c1]== (byte)s&&!enterC1&&result!=4) {
                        enterC1 = true;
                        if(result+(c1 + 1)>=4){ result = 4;}
                        else{result+= (c1 + 1);}
                    }  if (board[r*col+c2]== (byte)s&&!enterC2&&result!=4) {
                        enterC2=true;
                        if(result+(c1 + 1)>=4){ result = 4;}
                        else{result+=(c1 + 1);}
                    }
                }

            }
            R-=result;
           
        }
        return R;
    }
}
