package bots.nooby;

import bots.Util.Move;
import bots.Util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Nooby {
    private static final byte EMPTY = 0;

    public static byte[][] playNewMove(byte[][] board, boolean isWhiteTurn) {
        // Initialize a list of possible moves
        List<Move> possibleMoves = new ArrayList<>();

        // Loop through all the pieces on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                byte piece = board[i][j];
                if ((piece > 0 && isWhiteTurn) || (piece < 0 && !isWhiteTurn)) {
                    // This is one of our pieces, so check its possible moves
                    List<Move> moves = Util.getPossibleMoves(board, i, j);
                    possibleMoves.addAll(moves);
                }
            }
        }

        // If there are no possible moves, we've either checkmated or stalemated the opponent
        if (possibleMoves.isEmpty()) {
            return board;
        }

        // Sort the list of possible moves in descending order of the value of the captured piece (if any)
        Collections.sort(possibleMoves, (m1, m2) -> Integer.compare(Math.abs(m2.getCapturedPiece()), Math.abs(m1.getCapturedPiece())));

        // Select the move with the highest value capture (if any), or else just the first move in the list
        Move selectedMove = possibleMoves.get(0);

        // Apply the selected move to the board and return the resulting state
        byte[][] newBoard = Util.applyMove(board, selectedMove);
        return newBoard;
    }
}