## Class diagram

```mermaid
classDiagram
    
    TicTacToe --> Cell : contains
    TicTacToe --> Player : contains
    TicTacToe --> InteractionUser : use
    TicTacToe --> View : use
    
    Cell --> Player : ownedBy
    Main --> TicTacToe : call
    
    HumanPlayer --> Player : extends
    ArtificialPlayer --> Player : extends
    Player --> InteractionUser : interact
    
    View --> Player : display
    View <-- InteractionUser : use
    
    
    
    class Main{
     + main():void
    }
    
    class TicTacToe{
     - view View
     - interact : InteractionUser
     
     - height : int
     - width : int
     - winningLength : int
     - players : Player []
     - board : Board
     
     - menu()
     - initGame(int height, int width, int winningLength, int nbHumanPlayers, int nbArtificialsPlayers)
     - initPlayers(int quantity) void
     - clamp(int value, int min, int max) int
     
     - play() void
     
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
        + getMove(InteractionUser, View, Board) int[]        
    }
    
    class HumanPlayer{
    }
    
    class ArtificialPlayer{
    }
    
    class InteractionUser{
        - scanner : Scanner
        - view : View
        + getIntFromUser() int
    }
    
    class View{
        + displayTitle(String) void
        + displayMessage(String) void
        + displayBoard(Board) void
    }
        

