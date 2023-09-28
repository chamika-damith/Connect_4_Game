package lk.ijse.dep.service;

public class BoardImpl {
    private Piece piece[][];
    BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public void setBoardUI(BoardUI boardUI) {
        this.boardUI = boardUI;
    }
}
