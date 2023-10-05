package lk.ijse.dep.service;

import java.util.Random;

public class AiPlayer extends Player{

    protected Board board;

    public AiPlayer(Board newBoard) {
        this.board=newBoard;
    }


    @Override
    public void movePiece(int col) {

        Winner winner = board.findWinner();

        Piece winningPiece = winner.getWinningPiece();

        Random r=new Random();

        int num=col;
        do {
            num= r.nextInt(6);
            if (board.isLegalMoves(num)){
                break;
            }

        }while (!board.isLegalMoves(num));


        if (board.isLegalMoves(num)) {
            board.updateMove(num, Piece.GREEN);
            board.getBoardUI().update(num, false);
        }


        if (winningPiece == Piece.GREEN) {
            board.getBoardUI().notifyWinner(winner);
        } else if (board.exitsLegalMoves()) {
            System.out.println("Empty");
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }

    }
}
