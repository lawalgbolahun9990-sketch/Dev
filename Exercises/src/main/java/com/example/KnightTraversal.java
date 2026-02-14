package com.example;

import java.util.ArrayList;

public class KnightTraversal {
    private final int VISITED = 1;
    private int visitedCount = 0;
    private int[] startCoordinates = {0,0};
    private int[][] board = new int[8][8];
    ArrayList<int[]> coordinates = new ArrayList<>();

    KnightTraversal(int[][] board) {
        if (board == null)
            return;

        this.board = board;
    }

    public ArrayList<int[]> getTraversedCoordinates(int startRow, int startCol) {
        clear();
        startCoordinates[0] = startRow;
        startCoordinates[1] = startCol;
        System.out.println(" ( " + startCoordinates[0] + " , " + startCoordinates[1] + ") ");
        setTraversedCoordinates(startRow, startCol);
        coordinates.addLast(startCoordinates);
        return coordinates;
    }
    public void clear(){
        visitedCount = 0;
        coordinates.clear();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    private boolean setTraversedCoordinates(int startRow, int startCol) {
        board[startRow][startCol] = VISITED;
        visitedCount++;
        if (hasVisitedAll()) {
            int[] dimension = { startRow, startCol };
            coordinates.add(dimension);
            return true;
        }

        int[][] possibleMoves = sortByAccessibility(getPossibleMoves(startRow, startCol));

        for (int i = 0; i < possibleMoves.length; i++) {
            int[] move = possibleMoves[i];
            if (!isValidCell(move)) continue;

            if (setTraversedCoordinates(possibleMoves[i][0], possibleMoves[i][1])) {
                int[] dimension = { startRow, startCol };
                coordinates.add(dimension);
                return true;
            }
        }
        board[startRow][startCol] = 0;
        visitedCount--;
        return false;
    }

    private boolean isValidCell(int[] cell) {
        if (cell[0] < 0 || cell[0] > board.length - 1)
            return false;
        if (cell[1] < 0 || cell[1] > board[0].length - 1)
            return false;
        if (board[cell[0]][cell[1]] == VISITED)
            return false;

        return true;
    }

    private boolean hasVisitedAll() {
        return visitedCount == board.length * board[0].length;
    }

    private int[][] swap(int[][] arr, int i1, int i2) {
        int[] temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;

        return arr;
    }

    private int[][] getPossibleMoves(int row, int col) {
        int[][] possibleMoves = {
                { row + 2, col - 1 },
                { row + 2, col + 1 },
                { row - 2, col - 1 },
                { row - 2, col + 1 },
                { row + 1, col - 2 },
                { row + 1, col + 2 },
                { row - 1, col - 2 },
                { row - 1, col + 2 }
        };
        return possibleMoves;
    }

    private int[][] sortByAccessibility(int[][] possibleMoves) {
        int[] leastMovesArr = new int[possibleMoves.length];

        for (int i = 0; i < possibleMoves.length; i++) {
            int numValidMoves = possibleMoves.length;
            if (!isValidCell(possibleMoves[i])) {
                leastMovesArr[i] = Integer.MAX_VALUE;
            } else {
                int[][] childPossibleMoves = getPossibleMoves(possibleMoves[i][0], possibleMoves[i][1]);

                for (int[] move : childPossibleMoves) {
                    if (!isValidCell(move))
                        numValidMoves--;
                }
            }

            leastMovesArr[i] = numValidMoves;

            boolean swapped = false;
            for (int j = 0; j <= i; j++) {
                if (j == 0) continue;
                if (swapped) j = 1;

                swapped = false;

                if (leastMovesArr[j] < leastMovesArr[j - 1]) {
                    possibleMoves = swap(possibleMoves, j, j - 1);
                    int temp = leastMovesArr[j - 1];
                    leastMovesArr[j - 1] = leastMovesArr[j];
                    leastMovesArr[j] = temp;
                    swapped = true;
                }
            }
        }
        return possibleMoves;
    }
}