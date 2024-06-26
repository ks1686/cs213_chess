package chess;

import java.util.ArrayList;

public class Square {
  public int rank;
  public ReturnPiece.PieceFile file;

  // constructor
  public Square(int rank, ReturnPiece.PieceFile file) throws IllegalArgumentException {
    this.rank = rank;
    this.file = file;
    // throw exception if rank or file is out of bounds
    if (rank < Chess.MIN_RANK || rank > Chess.MAX_RANK || file.ordinal() > 7) {
      throw new IllegalArgumentException("Invalid square. " + rank + file + " is out of bounds.");
    }
  }

  // toString method
  public String toString() {
    return file + "" + rank;
  }

  // equals method
  public boolean equals(Object other) {
    if (!(other instanceof Square otherSquare)) {
      return false;
    }
    return rank == otherSquare.rank && file == otherSquare.file;
  }

  // method to check if the square is within the list
  public static boolean isSquareInList(ArrayList<Square> squares, Square square) {
    for (Square s : squares) {
      if (s.equals(square)) {
        return true;
      }
    }
    return false;
  }

  // method to check if the square is within the nested list
  public static boolean isSquareInNestedList(
      ArrayList<ArrayList<Square>> squaresList, Square square) {
    for (ArrayList<Square> s : squaresList) {
      if (isSquareInList(s, square)) {
        return true;
      }
    }
    return false;
  }
}
