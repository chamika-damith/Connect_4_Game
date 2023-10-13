package lk.ijse.dep.service;

import javax.swing.plaf.nimbus.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AiPlayer extends Player {
    Winner winner;
    int randomNum;
    boolean isTrue;
    public AiPlayer(Board newBoard) {
        super(newBoard);
    }


    private class MctsAlgorithm {

        private Node rootNode;

        public MctsAlgorithm(State initialState) {
            rootNode = new Node(initialState, null);
        }

        public Node select() {
            Node currentNode = rootNode;
            return currentNode;
        }

        public void expand(Node node) {

        }

        public State simulate(Node node) {
            State currentState = node.getState();
            return currentState;
        }

        public void backpropagate(Node node, State winner) {
            Node currentNode = node;
            while (currentNode != null) {
                currentNode.visit();
                currentNode = currentNode.getParent();
            }
        }

        public State getBestMove() {
            return rootNode.selectBestChild().getState();
        }
    }



    private class Node {

        private State state;
        private Node parent;
        private List<Node> children;
        private int visitCount;
        private int winCount;

        public Node(State state, Node parent) {
            this.state = state;
            this.parent = parent;
            this.children = new ArrayList<>();
            this.visitCount = 0;
            this.winCount = 0;
        }

        public boolean isTerminal() {
            return false;
        }

        public List<State> getChildrenStates() {
            List<State> childStates = new ArrayList<>();
            return childStates;
        }

        public Node selectBestChild() {
            return children.stream().max(Comparator.comparing(Node::getUCT)).get();
        }

        public State getState() {
            return state;
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }

        public double getUCT() {
            double c = 1.414;
            return (double) winCount / visitCount + c * Math.sqrt(Math.log(parent.getVisitCount()) / visitCount);
        }

        public void visit() {
            visitCount++;
        }

        public void incrementWinCount() {
            winCount++;
        }

        public int getVisitCount() {
            return visitCount;
        }
    }


    @Override
    public void movePiece(int col1) {
        randomNum = bestMove();
        board.updateMove(randomNum, Piece.GREEN);
        board.getBoardUI().update(randomNum, isTrue);
        winner = board.findWinner();
        if (winner.getWinningPiece() != Piece.EMPTY) {
            board.getBoardUI().notifyWinner(winner);
        } else {
            if (!board.exitsLegalMoves()) {
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }
    private int bestMove() {
        boolean isUserWinning = false;
        int winningCol = 0;
        for (int i = 0; i < 6; ++i) {
            if (board.isLegalMoves(i)) {
                int row = board.findNextAvailableSpot(i);
                board.updateMove(i, Piece.GREEN);
                int heuristicVal = minimax(0, false);
                board.updateMove(i, row, Piece.EMPTY);
                if (heuristicVal == 1) {
                    return i;
                } else if (heuristicVal == -1) {
                    isUserWinning = true;
                } else {
                    winningCol = i;
                }
            }
        }
        if (isUserWinning && board.isLegalMoves(winningCol)) {
            return winningCol;
        } else {
            int j;
            do {
                j = (int) ((Math.random() * ((5 - 0) + 1)) + 0);
            } while (!board.isLegalMoves(j));

            return j;
        }
    }
    private int minimax(int depth, boolean maximizingPlayer) {
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1;
        } else if (winner.getWinningPiece() == Piece.BLUE) {//
            return -1;
        } else if (board.exitsLegalMoves() && depth != 5) {
            int heuristicVal;
            if (!maximizingPlayer) {
                int minEva= 1000;
                for (int i = 0; i < 6; ++i) {
                    if (board.isLegalMoves(i)) {
                        int row = board.findNextAvailableSpot(i);
                        board.updateMove(i, Piece.BLUE);
                        heuristicVal = minimax(depth + 1, true);
                        minEva= min(minEva,heuristicVal);
                        board.updateMove(i, row, Piece.EMPTY);
                        if (heuristicVal == -1) {
                            return minEva;
                        }
                    }
                }
            } else {
                int maxEvA = -1000;
                for (int i = 0; i < 6; ++i) {
                    if (board.isLegalMoves(i)) {
                        int row = board.findNextAvailableSpot(i);
                        board.updateMove(i, Piece.GREEN);
                        heuristicVal = minimax(depth + 1, false);
                        maxEvA = max(maxEvA,heuristicVal);
                        board.updateMove(i, row, Piece.EMPTY);
                        if (heuristicVal == 1) {
                            return maxEvA;
                        }
                    }
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

}
