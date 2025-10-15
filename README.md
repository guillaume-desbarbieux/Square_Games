## Class diagram

```mermaid
classDiagram
    
    TicTacToe --> Cell : contains
    TicTacToe --> Player : contains
    Cell --> Player : ownedBy
    Main --> TicTacToe : call
    
    
    class TicTacToe{
     - size : int
     - cells : Cell [][]
     - player1 : Player
     - player2 : Player
     - initBoard() void
     - display() void
     - getMoveFromPlayer() void
     - setOwner(int x, int y, Player player) void
     + play() void
     - isOver() boolean

    }
    
    class Cell{
        - representation : String
        - owner : Player
        + getRepresentation() String
        + getOwner() Player
        
    }
    
    class Player{
        - representation : String
        + getRepresentation() String
    }
    
    class Main{
    + main():void
    }
