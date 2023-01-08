/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project331;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import adversarial.AdversarialSearch;
import adversarial.AlphaBetaSearch;
import adversarial.Game;
import adversarial.MinimaxSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * * * @author
 */
public class Main {

    public static void main(String[] args) {
       
       
       
        int input = 1;
        Scanner in = new Scanner(System.in);

        do {// loop until the user choose correct answer
            System.out.println("");
            //System.out.println(");
            //prompt user to enter type of process 
            input =Integer.valueOf(JOptionPane.showInputDialog("---------WLCOME TO CONNECT FOUR GAME--------- \n" +
"--------- CHOOSE THE GAME TYPE FROM LIST ---------\n" +"1-play with two AI(smart) egent by applay minmax with cutoff algorithm"
                    + "\n2-play with two AI(smart) egent by applay AlphaBeta with cutoff algorithm \n3-minimax play with user and AI agent\n4-alpha beta play with user and AI agent "
                    + "\n5-Exit\n" +"\nEnter process type :"));

            if (input == 1) {
                minmax();
            } else if (input == 2) {
                alphaBeta();
            } else if (input == 3) {
                JOptionPane.showMessageDialog(null,"You will be the Blue Player");
                withUser1();
            } // exit 
            else if (input == 4) {
                JOptionPane.showMessageDialog(null,"You will be the Blue Player");
                withUser2();
            } else if (input != 5) {
                System.out.println("Wrong choice");
            }
        } while (input != 5);
       JOptionPane.showMessageDialog(null,"I Hope You Enjoyed ..HAVE A NICE DAY :");

    }

    /**
     * to evaluate the minimax with cut off game Two Agent play
     */
    public static void minmax() {
        Game g = new ConnectFourGame();
        AdversarialSearch a = new MinimaxSearch(g);
        boolean finish;
        int limit = 2;
        ConnectFourState state = (ConnectFourState) g.getInitialState();
        finish = false;
        int action;
        do {
            if (state.getPlayerToMove() == 2) {

                action = (int) a.makeDecision(state, limit);
            } else {
                action = (int) a.makeDecision(state, limit);

                limit += 2;
            }
            
            state = (ConnectFourState) g.getResult(state, action);
           JOptionPane.showMessageDialog(null, printState(state));
            

            if (state.getUtility() != -1) {
                finish = true;
            }
        } while (!finish);
       JOptionPane.showMessageDialog(null,"The final state of the board : \n"+printState(state)+
               "\nThe utility " + state.getUtility()+theWinner((double) state.getUtility()));
        
        

    }

    /**
     * to evaluate the alphaBetat with cut off game Two agent play
     */
    public static void alphaBeta() {
        Game g = new ConnectFourGame();
        AdversarialSearch a = new AlphaBetaSearch(g);
        boolean finish;
        int limit = 2;
        List<Integer> actions = new ArrayList<Integer>();
        List<String> player = new ArrayList<String>();
        ConnectFourState state = (ConnectFourState) g.getInitialState();
        finish = false;
        int action;
        do {
            if (state.getPlayerToMove() == 2) {
                action = (int) a.makeDecision(state, limit);
            } else {
                action = (int) a.makeDecision(state, limit);

                limit += 2;
            }
           
            state = (ConnectFourState) g.getResult(state, action);
          JOptionPane.showMessageDialog(null, printState(state));
            if (state.getUtility() != -1) {
                finish = true;
            }
        } while (!finish);
        System.out.println("The final state of the board : ");
         JOptionPane.showMessageDialog(null,"The final state of the board : \n"+printState(state)+
               "\nThe utility " + state.getUtility()+theWinner((double) state.getUtility()));
        
    }

    /**
     * To evaluate minimax with cutoff Agent with the user
     */
    public static void withUser1() {
        Game g = new ConnectFourGame();
        AdversarialSearch a = new MinimaxSearch(g);
        boolean finish;
        int limit = 2;
        ConnectFourState state = (ConnectFourState) g.getInitialState();
        finish = false;
        int action;
        Scanner input = new Scanner(System.in);
        do {
            if (state.getPlayerToMove() == 2) {
               
                action = Integer.valueOf(JOptionPane.showInputDialog( printState(state)+"\nEnter the number of col "));
            } else {
                action = (int) a.makeDecision(state, limit);

                limit += 2;
            }
           
            state = (ConnectFourState) g.getResult(state, action);
                
            if (state.getUtility() != -1) {
                finish = true;
            }
        } while (!finish);
         JOptionPane.showMessageDialog(null,"The final state of the board : \n"+printState(state)+
               "\nThe utility " + state.getUtility()+theWinner((double) state.getUtility()));
        
    }

    /**
     * To evaluate alpha beta with cutoff Agent with the user
     */
    public static void withUser2() {
        Game g = new ConnectFourGame();
        AdversarialSearch a = new AlphaBetaSearch(g);
        boolean finish;
        int limit = 2;
        ConnectFourState state = (ConnectFourState) g.getInitialState();
        finish = false;
        int action;
        Scanner input = new Scanner(System.in);
        do {
            if (state.getPlayerToMove() == 2) {

                               action = Integer.valueOf(JOptionPane.showInputDialog( printState(state)+"\nEnter the number of col "));

            } else {
                action = (int) a.makeDecision(state, limit);

                limit += 2;
            }
           
            state = (ConnectFourState) g.getResult(state, action);
                    
            if (state.getUtility() != -1) {
                finish = true;
            }
        } while (!finish);
         JOptionPane.showMessageDialog(null,"The final state of the board : \n"+printState(state)+
               "\nThe utility " + state.getUtility()+theWinner((double) state.getUtility()));
         
    }

    /**
     * To print the Transaction model
     *
     * @param state
     */
    public static String printState(ConnectFourState state) {
        ConnectFourGame g = new ConnectFourGame();
        byte[] board = state.getBoard();
         String output = "";
          if (state.getMoves()== state.getBoard().length)
               output +="\n";
          else if (state.getPlayerToMove()==1)
            output +="Player 1: \n";
        else if (state.getPlayerToMove()==2)
            output+="Player 2: \n";
       
          
       
        for (int i = 0; i < board.length; i++) {

            if (i % 7 == 0 && i != 0) {
               output +="\n";
            }
            if (board[i] % 4 == 0 || board[i] == 0) {
                output+=("0 ");
            } else {
                output+=g.getPlayer(board[i])+" ";
            }
        }
        return output;
    }

    /**
     * Show is the winner
     *
     * @param utility
     */
    public static String theWinner(double utility) {
        if (utility == 0.5) {
            return("\nTie NO one is won !!");
        } else if (utility == 1.00) {
            return("\nThe Red player is won CONGRATULATION");
        } else {
            System.out.println("\nThe Blue player is won CONGRATULATION");
        }
        return 
                ("");
    }

}
