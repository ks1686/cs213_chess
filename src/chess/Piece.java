package chess;

/*Class to represent a generic chess piece and commonly shared methods for inheritance
* Extends ReturnPiece class to use the PieceType and PieceFile enums
*/
public abstract class Piece extends ReturnPiece {

    private boolean isWhite;
    private boolean hasMoved = false; // default, nothing moved

    public Piece(boolean isWhite){
        this.isWhite = isWhite;
    }

    // Setter; sets the color of the piece
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    // Getter; gets the color of the piece
    public boolean isWhite() {
        return this.isWhite;
    }

    // Setter; sets the hasMoved boolean
    public void hasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // Getter; gets the hasMoved boolean
    public boolean hasMoved() {
        return this.hasMoved;
    }

    // Abstract method to be implemented by the subclasses

    public abstract boolean canMove(int rank, int file, int newRank, int newFile, boolean isNewSpotEmpty);

    // set hasMoved to true when moved
    public void movePiece() {
        this.hasMoved = true;
    }

    // return the piece type
    public PieceType getPieceType() {
        return this.pieceType;
    }
}