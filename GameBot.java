/**
This is a recursive AI module so the human player can play TicTacToe with a computer.
The module takes in the game board, examine it, and returns optimal row and col values
for a counter move against the human player.
*/
public class GameBot
{
  //fields
  private int[] optimalMove = new int[2];
  private final char ai = 'O', human = 'X';
  private final int win = 10, lose = -10;

  //create a new 2D array to mirror incoming 2D array
  private char[][] game = new char[3][3];

  //create a value map for each move
  private String[][] values = new String[3][3];

  //keep track of number of calculations made for one move
  private int counter;
  //keep track of maximum depth
  private int maxDepth;

  //win condition flags
  // private boolean maximizerWin = true, minimizerWin = true, noOneWin = true;

  //determine optimal move against human player
  public int[] getOptimalMove(char[][] board)
  {
    //copy every single value from board 2D array onto game 2D array
    //so original game board is not affected by operations here
    //also do the same thing for value map
    for (int row = 0; row < board.length; row++)
      for (int col = 0; col < board[0].length; col++)
      {
        game[row][col] = board[row][col];
        values[row][col] = Character.toString(board[row][col]);
      }

    //variables
    int optimal = Integer.MIN_VALUE;
    int val;

    //reset counters
    counter = 0;
    maxDepth = 0;

    //examine each cell on board
    for (int row = 0; row < game.length; row++)
      for (int col = 0; col < game[0].length; col++)
        if (game[row][col] == ' ') //locate empty cell
        {
          //make move
          game[row][col] = ai;
          //evaluate move
          val = minimax(game, 0, false);
          //undo move
          game[row][col] = ' ';
          //find optimal move so far and store it
          if (val > optimal)
          {
            optimal = val;
            optimalMove[0] = row;
            optimalMove[1] = col;
          }
          //record value onto value map
          values[row][col] = Integer.toString(val);
          //update counter
          counter++;
        }

    //return
    return optimalMove;
  }

  //return counter value
  public int getCounter()
  {
    return counter;
  }

  //return max depth
  public int getMaxDepth()
  {
    return maxDepth + 1;
  }

  //get value map
  public String[][] getValueMap()
  {
    return values;
  }

  //check for tie condition
  private boolean tie(char[][] game)
  {
    //variables
    boolean gameover = true;

    for (int row = 0; row < game.length; row++)
      for (int col = 0; col < game[0].length; col++)
        if (game[row][col] == ' ')
        {
          gameover = false;
          break;
        }

    if (gameover && evaluate(game) == 0)
      return true;
    else
      return false;
  }

  //evaluation function
  private int evaluate(char[][] game)
  {
    //horizontal lines
    for (int row = 0; row < game.length; row++)
      if (game[row][0] == game[row][1] && game[row][1] == game[row][2])
      {
        if (game[row][0] == ai)
          return win;
        else if (game[row][0] == human)
          return lose;
      }
    //vertical lines
    for (int col = 0; col < game[0].length; col++)
      if (game[0][col] == game[1][col] && game[1][col] == game[2][col])
      {
        if (game[0][col] == ai)
          return win;
        else if (game[0][col] == human)
          return lose;
      }
    //diagonal lines
    if (game[0][0] == game[1][1] && game[1][1] == game[2][2])
    {
      if (game[0][0] == ai)
        return win;
      else if (game[0][0] == human)
        return lose;
    }
    if (game[2][0] == game[1][1] && game[1][1] == game[0][2])
    {
      if (game[2][0] == ai)
        return win;
      else if (game[2][0] == human)
        return lose;
    }

    return 0; //game ongoing, no win yet
  }

  //minimax algorithm
  private int minimax(char[][] game, int depth, boolean maximizer)
  {
    //variables
    int score, best;

    //evaluate score via evaluation function
    score = evaluate(game);

    //base cases -------------------------------------------
    //maximizer wins (AI), maximizer penalized for delays
    if (score == win)
    {
      //update counter
      counter++;
      System.out.println("End state reached at depth " + depth + ": maximizer wins");
      if (depth > maxDepth)
        maxDepth = depth;
      return score - depth;
    }
    //minimizer wins (human), maximizer rewarded for delays
    else if (score == lose)
    {
      //update counter
      counter++;
      if (depth > maxDepth)
        maxDepth = depth;
      return score + depth;
    }
    //tie condition
    else if (tie(game))
    {
      //update counter
      counter++;
      System.out.println("End state reached at depth " + depth + ": a tie");
      return 0;
    }

    //recursive cases --------------------------------------
    if (maximizer) //if it's maximizer's move
    {
      best = Integer.MIN_VALUE;
      //examine each cell on board
      for (int row = 0; row < game.length; row++)
        for (int col = 0; col < game[0].length; col++)
          if (game[row][col] == ' ') //locate empty cell
          {
            //make move
            game[row][col] = ai;
            //call minimax recursively
            best = Math.max(best, minimax(game, depth + 1, !maximizer));
            //undo move
            game[row][col] = ' ';
            //update counter
            counter++;
          }
      return best;
    }
    else //if it's minimizer's move
    {
      best = Integer.MAX_VALUE;
      //examine each cell on board
      for (int row = 0; row < game.length; row++)
        for (int col = 0; col < game[0].length; col++)
          if (game[row][col] == ' ') //locate empty cell
          {
            //make move
            game[row][col] = human;
            //call minimax recursively
            best = Math.min(best, minimax(game, depth + 1, maximizer));
            //undo move
            game[row][col] = ' ';
            //update counter
            counter++;
          }
      return best;
    }
  }
}
