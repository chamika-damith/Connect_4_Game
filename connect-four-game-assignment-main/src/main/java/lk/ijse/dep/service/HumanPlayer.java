package lk.ijse.dep.service;

public class HumanPlayer extends Player{

    protected Board board;

    public HumanPlayer(Board newBoard) {
        this.board=newBoard;
    }

    public void movePiece(int col){
        boolean isTrue=board.isLegalMoves(col);
        if (board.isLegalMoves(col)) {
            board.updateMove(col, Piece.BLUE);
            board.getBoardUI().update(col, isTrue);

            Winner winner = board.findWinner();

            Piece winningPiece = winner.getWinningPiece();

            if (winningPiece != Piece.EMPTY) {
                board.getBoardUI().notifyWinner(winner);
            } else if (!board.exitsLegalMoves()) {
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }
}
