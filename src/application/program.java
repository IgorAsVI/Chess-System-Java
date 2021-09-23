package application;
import java.util.Scanner;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Pieces.ChessPosition;
import boardgame.Board;

public class program {
    public static void main(String[] args) {
Scanner sc =new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while(true) {
            UI.printBoard(chessMatch.getpieces());
            System.out.println();
            System.out.println("source: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.println("target ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece captured = chessMatch.performChessmove(source,target);
        }
    }
}
