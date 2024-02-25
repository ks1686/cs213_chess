package chess;

import chess.Chess.Player;
import java.util.ArrayList;

public abstract class Piece extends ReturnPiece {
  public boolean isWhite;
  public boolean hasMoved = false; // default, nothing moved

  // Constructor; sets the color of the piece
  public Piece(boolean isWhite) {
    this.isWhite = isWhite;
  }

  public abstract ArrayList<ArrayList<Square>> getVisibleSquaresFromLocation(
      int rank, ReturnPiece.PieceFile file);

  public abstract boolean canMoveSpecific(
      int rank, ReturnPiece.PieceFile file, int newRank, ReturnPiece.PieceFile newFile);

  // TODO: canMove method not playing nicely with check since we cannot move opposite color pieces,
  // need to find workaround
  public boolean canMove(int newRank, ReturnPiece.PieceFile newFile) {
    int rank = this.pieceRank;
    ReturnPiece.PieceFile file = this.pieceFile;
    char fileChar = file.name().charAt(0);
    char newFileChar = newFile.name().charAt(0);

    if ((Chess.currentPlayer == Player.white && pieceType.name().charAt(0) == 'B')
        || (Chess.currentPlayer == Player.black && pieceType.name().charAt(0) == 'W')) {
      return false; // can't move a piece of the opposite color
    }

    // check if move is to same square or out of bounds
    if (file == newFile && rank == newRank) {
      return false; // can't move to the same square
    } else if (fileChar < Chess.MIN_FILE
        || fileChar > Chess.MAX_FILE
        || newFileChar < Chess.MIN_FILE
        || newFileChar > Chess.MAX_FILE
        || rank < Chess.MIN_RANK
        || rank > Chess.MAX_RANK
        || newRank < Chess.MIN_RANK
        || newRank > Chess.MAX_RANK) {
      return false; // check if square is within bounds of the board
    }

    Piece otherPiece = Chess.getPiece(newRank, newFile);

    // check if the new square is occupied by a piece of the same team
    if ((this.pieceType != ReturnPiece.PieceType.WK && this.pieceType != ReturnPiece.PieceType.BK)
        && otherPiece != null) {
      if (!this.isEnemy(otherPiece)) {
        return false; // can't move to a square occupied by a piece of the same team
      }
    }

    // test moving the piece to the new square, then see if currentPlayer's king is in check
    // if the king is in check, the move is invalid
    Piece king = Chess.getKing(Chess.currentPlayer);

    // save the piece's rank and file
    int oldRank = this.pieceRank;
    ReturnPiece.PieceFile oldFile = this.pieceFile;

    // move the piece to the new square
    this.pieceRank = newRank;
    this.pieceFile = newFile;

    // check if the king is in check
    if (Piece.inCheck(king)) {
      // move the piece back to its original square
      this.pieceRank = oldRank;
      this.pieceFile = oldFile;
      return false; // can't move the piece if it puts the king in check
    }

    this.pieceRank = oldRank;
    this.pieceFile = oldFile;

    // check if the piece can move to the new square after checking for obstacles
    return canMoveSpecific(rank, file, newRank, newFile);
  }

  // TODO: static method to check if a piece is in check
  public static boolean inCheck(Piece king) {
    // set the checkFilter to true to bypass color check

    // retrieve the king's rank and file
    int kingRank = king.pieceRank;
    ReturnPiece.PieceFile kingFile = king.pieceFile;

    // iterate through all the pieces on the board
    for (ReturnPiece piece : Chess.getPiecesOnBoard()) {
      // cast piece into a Piece object
      Piece otherPiece = (Piece) piece;
      // check if the piece is an enemy and can move to the king's square
      if (otherPiece.isEnemy(king) && otherPiece.canMove(kingRank, kingFile)) {
        return true; // king is in check
      }
    }
    return false; // king is not in check
  }

  // move the piece to a new spot
  public void movePiece(int newRank, Piece.PieceFile newFile) {
    boolean isNewSpotEmpty;
    int oldRank = this.pieceRank;
    Piece.PieceFile oldFile = this.pieceFile;

    // piece can move, check if the new spot is empty
    Piece otherPiece = Chess.getPiece(newRank, newFile);
    isNewSpotEmpty = otherPiece == null;
    if (!isNewSpotEmpty) {
      Chess.capturePiece(otherPiece);
    }

    // move the piece to the new spot
    this.pieceRank = newRank;
    this.pieceFile = newFile;

    // TODO: check for possible check
    if (Piece.inCheck(Chess.getKing(Chess.currentPlayer))) {
      // move the piece back to its original spot
      this.pieceRank = oldRank;
      this.pieceFile = oldFile;
      // add the chess class variable capturedPiece back to the board
      Chess.getPiecesOnBoard().add(Chess.capturedPiece);
      return;
    }

    // set hasMoved to true
    this.hasMoved = true;
  }

  // return pieceType
  public ReturnPiece.PieceType getPieceType() {
    return this.pieceType;
  }

  // override toString
  public String toString() {
    return "Piece: " + this.getPieceType().name() + " " + this.pieceFile.name() + this.pieceRank;
  }

  // method that returns a char for the piece's file
  public static char enumFileToChar(ReturnPiece.PieceFile file) {
    return file.name().charAt(0); // return the first character of the enum constant
  }

  // method that returns a PieceFile for the piece's file
  public static ReturnPiece.PieceFile charToEnumFile(char file) {
    for (ReturnPiece.PieceFile fileEnum : ReturnPiece.PieceFile.values()) {
      if (Character.toLowerCase(fileEnum.name().charAt(0)) == Character.toLowerCase(file)) {
        return fileEnum;
      }
    }

    throw new IllegalArgumentException(file + " is not a valid enum constant.");
  }

  // method that returns a PieceFile for the piece's file
  public boolean isEnemy(Piece other) {
    // returns true if they are opposite teams
    return (other != null) && (this.isWhite ^ other.isWhite);
  }
}
