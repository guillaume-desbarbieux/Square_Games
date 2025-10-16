## Class diagram

```mermaid
classDiagram
    
    TicTacToe.TicTacToe --> TicTacToe.Cell : contains
    TicTacToe.TicTacToe --> TicTacToe.Player : contains
    TicTacToe.Cell --> TicTacToe.Player : ownedBy
    TicTacToe.Main --> TicTacToe.TicTacToe : call
    TicTacToe.TicTacToe --> TicTacToe.GameScanner : use
    TicTacToe.Player --> TicTacToe.GameScanner : use
    
    
    class TicTacToe.Main{
     + main():void
    }
    
    class TicTacToe.TicTacToe{
     - height : int
     - width : int
     - winningLength : int
     - scanner : TicTacToe.GameScanner
     - players : TicTacToe.Player []
     - board : TicTacToe.Board
     
     - initPlayers(int quantity) void
     - clamp(int value, int min, int max) int
     
     + display() void
     + play() void
     
     - getNextPlayer(TicTacToe.Player) TicTacToe.Player
     - isWinning (int col, int row) boolean
     - countInDirections(int row, int col, int dRow, int dCol, int id) int
    }
    
    class TicTacToe.Cell{
        - representation : String
        - owner : TicTacToe.Player
        
        + setOwner(TicTacToe.Player) void
        + getRepresentation() String
        + getOwner() TicTacToe.Player
        + isEmpty() boolean        
    }
    
    class TicTacToe.Player{
        - id int
        - representation : String
        
        + getId() int
        + getRepresentation() String
        + getMove(TicTacToe.GameScanner, TicTacToe.Board) int[]        
    }
    
    class TicTacToe.GameScanner{
        - scanner : Scanner
        + getIntFromUser() int
    }
    

