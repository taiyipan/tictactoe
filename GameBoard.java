import java.util.Scanner;
/**
This keeps track of the current state of game board
*/
public class GameBoard
{
  //fields
  private final int ROW_COUNT = 3, COL_COUNT = 3;
  private char[][] board = new char[ROW_COUNT][COL_COUNT];
  private String[][] values = new String[ROW_COUNT][COL_COUNT];
  private Scanner scan = new Scanner(System.in);
  private final char ai = 'O', human = 'X';

  //create TiBot object
  private GameBot bot = new GameBot();

  //constructor
  public GameBoard()
  {
    //initialize board to empty spaces
    for (int row = 0; row < ROW_COUNT; row++)
      for (int col = 0; col < COL_COUNT; col++)
        board[row][col] = ' ';
  }

  //display game board
  public void displayBoard()
  {
    System.out.println("\n     0     1     2");
    System.out.println("  -------------------");
    System.out.println("0 |  " + board[0][0] + "  |  " + board[0][1] + "  |  " + board[0][2] + "  |");
    System.out.println("  -------------------");
    System.out.println("1 |  " + board[1][0] + "  |  " + board[1][1] + "  |  " + board[1][2] + "  |");
    System.out.println("  -------------------");
    System.out.println("2 |  " + board[2][0] + "  |  " + board[2][1] + "  |  " + board[2][2] + "  |");
    System.out.println("  -------------------");
  }

  //get human player's next move onto the game board
  public void humanMove()
  {
    //variables
    int row, col;
    boolean duplicate;

    do
    {
      //get player move
      System.out.print("\nPlease enter a row: ");
      row = scan.nextInt();
      while (row < 0 || row >= ROW_COUNT) //input validation
      {
        System.out.print("Please enter a row (0-2): ");
        row = scan.nextInt();
      }
      System.out.print("Please enter a column: ");
      col = scan.nextInt();
      while (col < 0 || col >= COL_COUNT) //input validation
      {
        System.out.print("Please enter a column (0-2): ");
        col = scan.nextInt();
      }

      //validate that cell is previously unoccupied
      if (board[row][col] == ' ')
        duplicate = false;
      else
      {
        duplicate = true;
        System.out.println("This cell is already occupied. Try a different cell.");
      }
    } while (duplicate);

    //make player move on the game board
    board[row][col] = human;

    //announce move
    System.out.println("\nYour move: row " + row + " column " + col);
  }

  //make bot move
  public void botMove()
  {
    //variables
    int row, col;
    int[] optimalMove = new int[2];

    //calculate optimal move
    System.out.println("\nCalculating countermove...");
    optimalMove = bot.getOptimalMove(board);
    row = optimalMove[0];
    col = optimalMove[1];

    //number of total calculations made
    System.out.println("Possible moves examined: " + bot.getCounter());
    //maximum depth
    System.out.println("Maximum recursion depth: " + bot.getMaxDepth());

    //get value map
    values = bot.getValueMap();
    //show value map
    printValues();

    //make move
    board[row][col] = ai;

    //announce move
    System.out.println("\nGameBot move: row " + row + " column " + col);
  }

  //show value map
  private void printValues()
  {
    System.out.println("\nHere are the values of GameBot's possible moves:");
    System.out.println("\n     0     1     2");
    System.out.println("  -------------------");
    System.out.printf("0 |%s|%s|%s|\n", centerString(5, values[0][0]), centerString(5, values[0][1]), centerString(5, values[0][2]));
    System.out.println("  -------------------");
    System.out.printf("1 |%s|%s|%s|\n", centerString(5, values[1][0]), centerString(5, values[1][1]), centerString(5, values[1][2]));
    System.out.println("  -------------------");
    System.out.printf("2 |%s|%s|%s|\n", centerString(5, values[2][0]), centerString(5, values[2][1]), centerString(5, values[2][2]));
    System.out.println("  -------------------");
  }

  //center string output
  public static String centerString(int width, String s)
  {
    return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
  }

  //check for tie conditions
  public boolean isDraw()
  {
    //variables
    boolean tie = true;

    //check for empty cells
    for (int row = 0; row < ROW_COUNT; row++)
      for (int col = 0; col < COL_COUNT; col++)
        if (board[row][col] == ' ')
        {
          tie = false;
          break;
        }

    //check if either player has win conditions
    if (isWon('X') == true)
      tie = false;
    else if (isWon('O') == true)
      tie = false;

    return tie;
  }

  //check for win conditions
  public boolean isWon(char ch)
  {
    //variables
    boolean win = false;

    //horizontal lines
    for (int row = 0; row < ROW_COUNT; row++)
      if (board[row][0] == board[row][1] && board[row][1] == board[row][2])
        if (board[row][0] == ch)
        {
          win = true;
          break;
        }
    //vertical lines
    if (win == false)
    {
      for (int col = 0; col < COL_COUNT; col++)
        if (board[0][col] == board[1][col] && board[1][col] == board[2][col])
          if (board[0][col] == ch)
          {
            win = true;
            break;
          }
    }
    //diagonal lines
    if (win == false)
    {
      if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
        if (board[0][0] == ch)
          win = true;
      if (board[2][0] == board[1][1] && board[1][1] == board[0][2])
        if (board[2][0] == ch)
          win = true;
    }

    return win;
  }
}
