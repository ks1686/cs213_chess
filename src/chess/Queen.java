package chess;

public class Queen extends Piece {

    // check color to set PieceType
    public Queen(boolean isWhite) {
        super(isWhite);
        if (this.isWhite()) {
            this.pieceType = PieceType.WQ;
        } else {
            this.pieceType = PieceType.BQ;
        }
    }

    // check if the move is valid for a queen
    public boolean canMovePiece(int rank, char file, int newRank, char newFile, int rankChange, int fileChange, Piece sourcePiece, Piece destinationPiece) {
        /*
         * 1. Check if the move is even valid for a queen (diagonals, horizontals, etc.) (complete)
         * 2. Loop to check if there are pieces in the way (will need to make the board an import into such a method)
         * 3. Edge cases for if there is/isn't a piece, if it's a friendly or not, etc.
         */

        if (rank == newRank && file == newFile) {
            // can't move in place
            return false;
        } else if (rank == newRank || file == newFile || rankChange == fileChange) {
            // perfect diagonal
            return true;
        } else if (rankChange == 0 && fileChange != 0) {
            // perfect vertical
            return true;
        } else if (fileChange == 0 && rankChange != 0) {
            // perfect horizontal
            return true;
        }
        
        // invalid move not accounting for pieces in the way
        return false;
    }
}
