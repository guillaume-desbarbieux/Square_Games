## Class diagram

```mermaid
classDiagram
    
    TicTacToe --> Cell : contains
    TicTacToe --> Player : contains
    Cell --> Player : ownedBy
    Main --> TicTacToe : call
    TicTacToe --> GameScanner : use
    Player --> GameScanner : use
    
    
    class Main{
     + main():void
    }
    
    class TicTacToe{
     - height : int
     - width : int
     - winningLength : int
     - scanner : GameScanner
     - players : Player []
     - board : Board
     
     - initPlayers(int quantity) void
     - clamp(int value, int min, int max) int
     
     + display() void
     + play() void
     
     - getNextPlayer(Player) Player
     - isWinning (int col, int row) boolean
     - countInDirections(int row, int col, int dRow, int dCol, int id) int
    }
    
    class Cell{
        - representation : String
        - owner : Player
        
        + setOwner(Player) void
        + getRepresentation() String
        + getOwner() Player
        + isEmpty() boolean        
    }
    
    class Player{
        - id int
        - representation : String
        
        + getId() int
        + getRepresentation() String
        + getMove(GameScanner, Board) int[]        
    }
    
    class GameScanner{
        - scanner : Scanner
        + getIntFromUser() int
    }
    

