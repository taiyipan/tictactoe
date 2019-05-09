/**
The driver program for human vs computer TicTacToe
*/
public class TicTacToe
{
  private static final char ai = 'O', human = 'X';

  public static void main(String[] args)
  {
    //variables
    boolean keepPlaying = true;
    //create GameBoard object
    GameBoard game = new GameBoard();

    //intro
    System.out.println("\nWelcome to TicTacToe ver 2.0: Human vs GameBot!");

    while (keepPlaying)
    {
      //display game board
      game.displayBoard();
      //player X's turn
      game.humanMove();
      if (game.isWon(human))
      {
        game.displayBoard();
        System.out.println("\nYou won!");
        keepPlaying = false;
      }
      else if (game.isDraw())
      {
        game.displayBoard();
        System.out.println("\nYou tied with GameBot!");
        keepPlaying = false;
      }
      //player O's turn
      if (keepPlaying)
      {
        game.displayBoard();
        game.botMove();
        if (game.isWon(ai))
        {
          game.displayBoard();
          System.out.println("\nGameBot won!");
          keepPlaying = false;
        }
        else if (game.isDraw())
        {
          game.displayBoard();
          System.out.println("\nGameBot tied with you!");
          keepPlaying = false;
        }
      }
    }
  }
}
