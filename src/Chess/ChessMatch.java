package Chess;

import Chess.Pieces.*;

import Chess.Pieces.ChessPosition;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkmate;
    List<Piece> piecesOnTheBoard = new ArrayList<>();
    List<Piece> capturedPieces = new ArrayList<>();

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckmate() {
        return checkmate;
    }


    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.White;
        initialsetup();
    }

    public ChessPiece[][] getpieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }


        }
        return mat;
    }

    private void placenewpiece(char column, int row, ChessPiece piece) {
        board.placepiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    public boolean[][] possibleMoves(ChessPosition sourceposition) {
        Position position = sourceposition.toPosition();
        validateSourceposition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessmove(ChessPosition srcPosition, ChessPosition targPosition) {
        Position source = srcPosition.toPosition();
        Position target = targPosition.toPosition();
        validateSourceposition(source);
        validateTargetposition(source, target);
        Piece capturedPiece = makemove(source, target);
        if (testcheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        check = (testcheck(opponent(currentPlayer))) ? true : false;
        if (testCheckmate(opponent(currentPlayer))) {
            checkmate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private void validateSourceposition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMoves()) {
            throw new ChessException("There is no possibles moves for the chosen piece");
        }
    }

    private void validateTargetposition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private Piece makemove(Position source, Position target) {

        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placepiece(p, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedpiece) {
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placepiece(p, source);

        if (capturedpiece != null) {
            capturedPieces.remove(capturedpiece);
            piecesOnTheBoard.add(capturedpiece);
        }

    }

    private Color opponent(Color color) {
        return (color == Color.White) ? Color.Black : Color.White;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + "King on the board");
    }

    private boolean testcheck(Color color) {
        Position kingposition = king(color).getChessPosition().toPosition();
        List<Piece> opponentpieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentpieces) {
            boolean mat[][] = p.possibleMoves();
            if (mat[kingposition.getRow()][kingposition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckmate(Color color) {
        if (!testcheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == (color)).collect(Collectors.toList());
        for (Piece p : list) {
            boolean mat[][] = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {

                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedpiece = makemove(source, target);
                        boolean testcheck = testcheck(color);
                        undoMove(source, target, capturedpiece);
                        if (!testcheck) {
                            return false;
                        }
                    }

                }
            }

        }
        return true;

    }

    private void nextTurn() {
        turn++;
        if (currentPlayer == Color.White) {
            currentPlayer = Color.Black;
        } else {
            currentPlayer = Color.White;
        }
    }

    private void initialsetup() {
        placenewpiece('a', 1, new Rook(board, Color.White));
        placenewpiece('e', 1, new King(board, Color.White));
        placenewpiece('h', 1, new Rook(board, Color.White));
        placenewpiece('a', 2, new Pawn(board, Color.White));
        placenewpiece('b', 2, new Pawn(board, Color.White));
        placenewpiece('c', 2, new Pawn(board, Color.White));
        placenewpiece('d', 2, new Pawn(board, Color.White));
        placenewpiece('e', 2, new Pawn(board, Color.White));
        placenewpiece('f', 2, new Pawn(board, Color.White));
        placenewpiece('g', 2, new Pawn(board, Color.White));
        placenewpiece('h', 2, new Pawn(board, Color.White));
        placenewpiece('c', 1, new Bishop(board, Color.White));
        placenewpiece('f', 1, new Bishop(board, Color.White));
        placenewpiece('b', 1, new Knight(board, Color.White));
        placenewpiece('g', 1, new Knight(board, Color.White));
        placenewpiece('d', 1, new Queen(board, Color.White));

        placenewpiece('a', 8, new Rook(board, Color.Black));
        placenewpiece('e', 8, new King(board, Color.Black));
        placenewpiece('h', 8, new Rook(board, Color.Black));
        placenewpiece('a', 7, new Pawn(board, Color.Black));
        placenewpiece('b', 7, new Pawn(board, Color.Black));
        placenewpiece('c', 7, new Pawn(board, Color.Black));
        placenewpiece('d', 7, new Pawn(board, Color.Black));
        placenewpiece('e', 7, new Pawn(board, Color.Black));
        placenewpiece('f', 7, new Pawn(board, Color.Black));
        placenewpiece('g', 7, new Pawn(board, Color.Black));
        placenewpiece('h', 7, new Pawn(board, Color.Black));
        placenewpiece('c', 8, new Bishop(board, Color.Black));
        placenewpiece('f', 8, new Bishop(board, Color.Black));
        placenewpiece('b', 8, new Knight(board, Color.Black));
        placenewpiece('g', 8, new Knight(board, Color.Black));
        placenewpiece('d', 8, new Queen(board, Color.Black));

    }

}
