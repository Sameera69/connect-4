/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adversarial;

public class MinimaxSearch<STATE, ACTION, PLAYER> implements
        AdversarialSearch<STATE, ACTION> {

    //object of the game interface
    private Game<STATE, ACTION, PLAYER> game;
    //counter ot the expanded node in the game 
    private int expandedNodes; // chnge to public sameeera
    private int playLimit;// when the play finish

    /**
     * Creates a new search object for a given game.
     */
    public static <STATE, ACTION, PLAYER> MinimaxSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game) {
        return new MinimaxSearch<STATE, ACTION, PLAYER>(game);
    }

    /**
     * Constructor
     *
     * @param game
     */
    public MinimaxSearch(Game<STATE, ACTION, PLAYER> game) {
        this.game = game;
    }

    /**
     * Override method of the one exist in AdversarialSearch interface
     *
     * @param state (the current state of the game)
     * @return an action
     */
    @Override
    public ACTION makeDecision(STATE state, int playlimit) {
        expandedNodes = 0;
        playLimit = playlimit;
        ACTION result;
        result = null;
        //double resultValue to compare it with the utility 
        //initilize with - infinity
        double resultValue;
        //return the player that his turn to play
        PLAYER player = game.getPlayer(state);
        //for each action get the utility and compare
        double value = 0.0;
        //make dec. for red

        resultValue = Double.NEGATIVE_INFINITY;
        if (player.equals("R")) {
            for (ACTION action : game.getActions(state)) {

                value = minValue(game.getResult(state, action), player);

                if (value >= resultValue) { //if was greater swap because we play for max
                    result = action;
                    resultValue = value;
                }
            }

        } else {
            resultValue = Double.POSITIVE_INFINITY;
            for (ACTION action : game.getActions(state)) {

                value = maxValue(game.getResult(state, action), player);

                if (value <= resultValue) { //if was greater swap because we play for max
                    result = action;
                    resultValue = value;
                }

                //make des. for blue
            }

        }

        return result;
    }

    /**
     *
     * @param state
     * @param player
     * @return maximum utility
     */
    public double maxValue(STATE state, PLAYER player) { // returns an utility
        // value
        expandedNodes++;
        if (game.isTerminal(state)) {
            return Double.NEGATIVE_INFINITY;
        } else if (game.isCutoff_test(state, playLimit)) {
            return game.eval(state, player);
        }

        double value = Double.NEGATIVE_INFINITY;
        for (ACTION action : game.getActions(state)) {
            double minVa = minValue(game.getResult(state, action), player);
            value = Math.max(value, minVa);
        }
        return value;
    }

    /**
     *
     * @param state
     * @param player
     * @return minimum value
     */
    public double minValue(STATE state, PLAYER player) {
        // value
        expandedNodes++;

        if (game.isTerminal(state)) {
            return Double.POSITIVE_INFINITY;

        } else if (game.isCutoff_test(state, playLimit)) {
            return game.eval(state, player);
        }

        double value = Double.POSITIVE_INFINITY;

        for (ACTION action : game.getActions(state)) //get minimum value 
        {
            double maxVa = maxValue(game.getResult(state, action), player);

            value = Math.min(value, maxVa);
        }
        return value;
    }
}
