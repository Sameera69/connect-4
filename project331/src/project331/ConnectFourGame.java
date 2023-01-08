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

import java.util.ArrayList;
import java.util.List;

import adversarial.Game;

/**
 * Provides an implementation of the ConnectFour game which can be used for
 * experiments with the Minimax algorithm.
 *
 * @author Ruediger Lunde
 *
 */
public class ConnectFourGame implements Game<ConnectFourState, Integer, String> {

    //1D array of type String to save the players name
    String[] players = new String[]{"R", "B"};
    //initilize new object of ConnectFourState and send the #row and #cloumn
    ConnectFourState initialState = new ConnectFourState(6, 7);

    /**
     * Override method of the one exist in Game interface
     *
     * @return initialState of the game
     */
    @Override
    public ConnectFourState getInitialState() {
        return initialState;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @return players array String of players name
     */
    @Override
    public String[] getPlayers() {
        return players;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state (the current state of the game)
     * @return the name of the player is turn to play (red) turn player one to
     * play ,(blue) turn player two to play
     */
    @Override
    public String getPlayer(ConnectFourState state) {
        return getPlayer(state.getPlayerToMove());
    }

    /**
     * Returns the player corresponding to the specified player number. For
     * efficiency reasons, <code>ConnectFourState</code>s use numbers instead of
     * strings to identify players.
     */
    public String getPlayer(int playerNum) {
        switch (playerNum) {
            case 1:
                return players[0];
            case 2:
                return players[1];
        }
        return null;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state (the current state of the game)
     * @return list of integer contain the possible actions the player can do
     */
    @Override
    public List<Integer> getActions(ConnectFourState state) {
        //list of integer to put the actions in it
        List<Integer> result = new ArrayList<Integer>();
        //for each column check if the place is empty mean no one is have been played in 
        //befor if it is empty then enter that place in result list 
        for (int i = 0; i < state.getCols(); i++) {
            if (state.getPlayerNum(0, i) == 0) {
                result.add(i);
            }
        }

        return result;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state object of ConnectFourState (the current state of the game)
     * @param action object of Integer (the action the player is done)
     * @return result which is the new state after doing specific action
     */
    @Override
    public ConnectFourState getResult(ConnectFourState state, Integer action) {
        //clone is method to make a refrence of the state object
        ConnectFourState result = state.clone();
        //call method drop Disk and send the action
        result.dropDisk(action);//problem
        return result;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state object of ConnectFourState (the current state of the game)
     * @return ture if the state is terminals false if it is not
     */
    @Override
    public boolean isTerminal(ConnectFourState state) {
        return state.getUtility() != -1;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state object of ConnectFourState (the current state of the game)
     * @param player String the name of the player
     * @return
     */
    @Override
    public double getUtility(ConnectFourState state, String player) {
        double result = state.getUtility();
        if (result != -1) {//if ths state is terminal 
            if (player == players[1]) //the player two is should play last one so check if the player was him
            {
                result = 1 - result;//return the result
            }
        } else {
            throw new IllegalArgumentException("State is not terminal.");
        }
        return result;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state
     * @param limit
     * @return true if it is cutoff false otherwise
     */
    @Override
    public boolean isCutoff_test(ConnectFourState state, int limit) {

        return state.getMoves() == limit;
    }

    /**
     * Override method of the one exist in Game interface
     *
     * @param state
     * @param player
     * @return the Heuristic value
     */
    @Override
    public double eval(ConnectFourState state, String player) {
        state.Huristic_function();

        return state.getHuristicUtility();
    }
}
