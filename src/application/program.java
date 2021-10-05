package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Pieces.ChessPosition;


public class program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getpieces());
                System.out.println();
                System.out.println("source: ");
                ChessPosition source = UI.readChessPosition(sc);
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getpieces(),possibleMoves);
                System.out.println();
                System.out.println("target ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece captured = chessMatch.performChessmove(source, target);
            } catch (ChessException e) {

                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {

                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}

