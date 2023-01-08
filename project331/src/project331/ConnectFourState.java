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


import hur.hur;

/**
 *
 * @author
 */
public class ConnectFourState implements Cloneable {
    //number of clumns of the game

    private int cols;
    /**
     * Uses special bit coding. First bit: disk of player 1, second bit: disk of
     * player 2, third bit: win position for player 1, fourth bit: win position
     * for player 2.
     */
    //1D array of byte the board of the game
    private byte[] board;

    //int variable count the number of moves have been done
    private int moveCount;
    /**
     * Indicates the utility of the state. 1: win for player 1, 0: win for
     * player 2, 0.5: draw, -1 for all non-terminal states.
     */
    //-------------replace with eval ---------//
    private double utility;
    private int huristicUtility;
    private hur h = new hur();
    //to count the number of red circle the player one play it
    public int winPositions1;
    //to count the number of red circle the player two play it
    public int winPositions2;

    /**
     * constructor create the board of game for specific number of row and
     * column
     *
     * @param rows
     * @param cols
     */
    public ConnectFourState(int rows, int cols) {
        utility = -1;
        this.cols = cols;
        board = new byte[rows * cols];
    }

    /**
     *
     * @return the board
     */
    public byte[] getBoard() {
        return board;
    }

    /**
     *
     * @return the number of row
     */
    public int getRows() {
        return board.length / cols;
    }

    /**
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     *
     * @return the utility value
     */
    public double getUtility() {
        return utility;
    }

    /**
     *
     * @param row
     * @param col
     * @return who is the player is in this place in the board
     */
    public int getPlayerNum(int row, int col) {
        //  System.out.println("player num is :"+(board[row * cols + col] & 3));
        return board[row * cols + col] & 3; ///problem
    }

    /**
     *
     * @return an integer (turn of whose to play)
     */
    public int getPlayerToMove() {
        return moveCount % 2 + 1;
    }

    /**
     *
     * @return the number of moves have been done
     */
    public int getMoves() {
        return moveCount;
    }

    /**
     * to enter the coin in the board and it call anlayze method
     *
     * @param col
     */
    public void dropDisk(int col) {
        int playerNum = getPlayerToMove();
        int row = getFreeRow(col);//problem
        // System.out.println("the row is "+row);
        if (row != -1) {
            moveCount++;
            if (moveCount == board.length) {
                utility = 0.5;
            }
            if (isWinPositionFor(row, col, 1)) {
                winPositions1--;
                if (playerNum == 1) {
                    utility = 1.0;
                }
            } else if (isWinPositionFor(row, col, 2)) {
                winPositions2--;
                if (playerNum == 2) {
                    utility = 0.0;
                }
            }
            board[row * cols + col] = (byte) playerNum;

            if (utility == -1.0) {
                analyzeWinPositions(row, col);
            }
        }
    }

    /**
     *
     * @param utility
     */
    public void setUtility(double utility) {
        this.utility = utility;
    }

    /**
     * Returns the row of the first empty space in the specified column and -1
     * if the column is full.
     */
    private int getFreeRow(int col) {
        for (int row = getRows() - 1; row >= 0; row--) {
            if (getPlayerNum(row, col) == 0)//problem
            {
                return row;
            }
        }
        return -1;
    }

    public boolean isWinMoveFor(int col, int playerNum) {
        return isWinPositionFor(getFreeRow(col), col, playerNum);
    }

    public boolean isWinPositionFor(int row, int col, int playerNum) {
        return (board[row * cols + col] & playerNum * 4) > 0;
    }

    private void setWinPositionFor(int row, int col, int playerNum) {
        if (playerNum == 1) {
            if (!isWinPositionFor(row, col, 1)) {
                winPositions1++;
            }
        } else if (playerNum == 2) {
            if (!isWinPositionFor(row, col, 2)) {
                winPositions2++;
            }
        } else {
            throw new IllegalArgumentException("Wrong player number.");
        }
        board[row * cols + col] |= playerNum * 4;

    }

    /**
     * Assumes a disk at position <code>moveRow</code> and <code>moveCol</code>
     * and analyzes the vicinity with respect to win positions.
     */
    private void analyzeWinPositions(int moveRow, int moveCol) {
        final int[] rowIncr = new int[]{1, 0, 1, 1};
        final int[] colIncr = new int[]{0, 1, -1, 1};
        int playerNum = getPlayerNum(moveRow, moveCol);
        WinPositionInfo[] wInfo = new WinPositionInfo[]{
            new WinPositionInfo(), new WinPositionInfo()};
        for (int i = 0; i < 4; i++) {
            int rIncr = rowIncr[i];
            int cIncr = colIncr[i];
            int diskCount = 1;

            for (int j = 0; j < 2; j++) {
                WinPositionInfo wInf = wInfo[j];
                wInf.clear();
                int rBound = rIncr > 0 ? getRows() : -1;
                int cBound = cIncr > 0 ? getCols() : -1;

                int row = moveRow + rIncr;
                int col = moveCol + cIncr;
                while (row != rBound && col != cBound) {
                    int plNum = getPlayerNum(row, col);
                    if (plNum == playerNum) {
                        if (wInf.hasData()) {
                            wInf.diskCount++;
                        } else {
                            diskCount++;
                        }
                    } else if (plNum == 0) {
                        if (!wInf.hasData()) {
                            wInf.row = row;
                            wInf.col = col;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                    row += rIncr;
                    col += cIncr;
                }
                rIncr = -rIncr;
                cIncr = -cIncr;
            }
            for (int j = 0; j < 2; j++) {
                WinPositionInfo wInf = wInfo[j];
                if (wInf.hasData() && diskCount + wInf.diskCount >= 3) {
                    setWinPositionFor(wInf.row, wInf.col, playerNum);
                }
            }
        }
    }

    /**
     *
     * @return copy of the state
     */
    public ConnectFourState clone() {
        ConnectFourState result = null;
        try {
            result = (ConnectFourState) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        result.board = board.clone();
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < board.length; i++) {
            result = result * 7 + board[i] + 1;
        }
        return result;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectFourState) {
            ConnectFourState s = (ConnectFourState) obj;
            for (int i = 0; i < board.length; i++) {
                if (board[i] != s.board[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // ////////////////////////////////////////////////////////////////////
    // nested classes
    /**
     *
     */
    static class WinPositionInfo {

        int row = -1;
        int col = -1;
        int diskCount;

        void clear() {
            row = -1;
            col = -1;
            diskCount = 0;
        }

        boolean hasData() {
            return row != -1;
        }
    }

    /**
     *
     * @return Heuristic value
     */
    public int getHuristicUtility() {
        return huristicUtility;
    }

    /**
     * void method to calculate the Heuristic value for specific state
     */
    public void Huristic_function() {
        huristicUtility = h.huresticFunction(board);

    }

}
