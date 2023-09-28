package lk.ijse.dep.service;

public class BoardImpl implements Board{
    private Piece piece[][];
    BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        return 0;
    }

    @Override
    public boolean isLegalMoves(int col) {
        return false;
    }

    @Override
    public boolean exitsLegalMoves() {
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {

    }

    @Override
    public Winner findWinner() {
        return null;
    }

    public void setBoardUI(BoardUI boardUI) {
        this.boardUI = boardUI;
    }
}
