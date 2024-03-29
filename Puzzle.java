import java.util.*;
import java.io.*;

public class Puzzle{
/**
* This class is very similar to buttongrid. It removes the display portions of buttongrid.
* This allows for fast generation of random puzzles
* If one wants to display a puzzle object, they can simply call the ButtonGrid constructor ( ButtonGrid(puzzleArr, length) )
*/

    int[][] puzzleArr;
    private Graph g;
    private int evaluationOutput;
    int n ;
    String candidate; // remember candidate length = n^2 - 1. We don't include the goal box

  public Puzzle(String cand){
	  this.candidate = cand;
  }
  
  public Puzzle(int[][] newPuzzleArray, int n ){
    /**
    * Create a puzzle object from a 2d int array
    */
    puzzleArr = new int[n][n];
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            puzzleArr[i][j] = newPuzzleArray[i][j];
        }
    }
    this.n = n;
    generateDigraph(puzzleArr, n);
  }
  public Puzzle(int width, int length){
    /**
    * Create a new random puzzle object from 2 dimensions
    */
      Random randy = new Random();
      int max = width - 1;
      int min = 1;
      int holder = 0;
      int tempMax = 0;
      int vert;
      n = length;

      puzzleArr = new int[width][length];

      for(int x = 0; x < length; x++)
      {
          for(int y = 0; y < width; y++)
          {
              //Find the max legal jump
              tempMax = getMaxLegalJump(x, y, max);
              //get a random move that is valid in at least one direction
              holder = randy.nextInt(tempMax - min + 1) + min;

              //Check for the "Goal" condition
              if(x == width - 1 && y == length - 1)
              {
                  puzzleArr[x][y] = 0;
              }
              //Else add the random number to the grid
              else
              {
                vert = x*width + y;
                //grid[x][y] = new JButton(holder + "{" + vert + "}");
                puzzleArr[x][y] = holder;              //Add move to puzzle
              }
          }
      }
      //Create the digraph where the goal is located at n^2 - 1
      generateDigraph(puzzleArr, width);
    }
  	public Puzzle (int n, boolean stringRepresentation){
	    this(n,n);
	    if (stringRepresentation){
	        char chromosome;
	        candidate = "";
	        for (int x = 0; x < n; ++x){
	            for (int y = 0; y < n; ++y){
	              chromosome = (char) (96 + puzzleArr[x][y]); // in ascii, a = 97, b = 98, etc
	              if(x== n-1 && y == n-1){
	            	  break;
	              }
	              else
	            	  candidate = candidate.concat(""+chromosome);
	            }
	        }
	    }
      	//System.out.println("candidate: "+ candidate);
	  }
  	public void stringToPuzzle(int n){
  		puzzleArr = new int[n][n];
  		for (int i = 0; i < candidate.length(); ++i){
  			puzzleArr[i/n][i%n] = ((int)candidate.charAt(i)) - 96 ;	
  		}
  		puzzleArr[n-1][n-1] = 0;
  		generateDigraph(puzzleArr, n);
  	}
	public Puzzle(int[] puzzleArr){

      Random randy = new Random();
      int width = (int)Math.sqrt(puzzleArr.length);
      int length = width;
      int max = width - 1;
      int min = 1;
      int holder = 0;
      int tempMax = 0;
      int vert;

      int compatabilityArr[][];//consequence of not generalizing things

      compatabilityArr = new int[width][length]; //consequence of not generalizing things

      for(int x = 0; x < length; x++)
      {
          for(int y = 0; y < width; y++)
          {
              //Find the max legal jump
              tempMax = getMaxLegalJump(x, y, max);
              //get a random move that is valid in at least one direction
              holder = randy.nextInt(tempMax - min + 1) + min;

              //Check for the "Goal" condition
              if(x == width - 1 && y == length - 1)
              {
                  compatabilityArr[x][y] = 0;//consequence of not generalizing things
              }
              //Else add the random number to the grid
              else
              {
                vert = x*width + y;
                compatabilityArr[x][y] = puzzleArr[vert];      //consequence of not generalizing things
              }
            }
      }
      generateDigraph(compatabilityArr, width);
    }


	   
	   
    public void randCellChange(){
      /** Edit a random position in the puzzle to include a new jump value
      */
      Random randy = new Random();
      int row; int col;
      row = randy.nextInt(n-1)+1;
      col = randy.nextInt(n-1)+1;
      while ((row == n-1 && col == n-1) ){
        row = randy.nextInt(n-1);
        col = randy.nextInt(n-1);
      }
      int jumpVal = randy.nextInt( getMaxLegalJump(row+1,col+1,n) ) + 1;

      puzzleArr[col][row] = jumpVal;
      generateDigraph(puzzleArr, n);
    }

    public Graph getGraph(){
      return g;
    }
    public int[][] getPuzzleArr(){
      return puzzleArr;
    }
    /*
    PRECONDITION: evaluationFunction() is called
    POSTCONDITION: returns the evaluation output  of the most recently
    generated puzzle
    */
    public int getEvaluationOutput(){
      return evaluationOutput;
    }

    public int getMaxLegalJump(int row, int col, int max){
		int a = max - row; // a = max row cells it can jump
		int b = row - 1;
		int c = max - col; // c = max col cells it can jump
		int d = col - 1;

		int[] tempArray = {b, c, d};
		int tempMax = a; 

		for(int i = 0; i < 3; i++){
  		if(tempMax < tempArray[i])
  			tempMax = tempArray[i];
		}
		return tempMax;
	}

	public void evaluationFunction(int[] visited, int n){
		int goalValue = visited[visited.length - 1];

		//The case where the goal is reachable should set the evaluation
		//functions value to the minimum number of moves
		if( goalValue > 0)
		{
		this.evaluationOutput = goalValue;
		return;
		}
		//Set/Reset unreachable to -1 corresponding to the unreachable
		//status of the goal cell
		this.evaluationOutput = -1;
		for(int i = 1; i < visited.length -1; i++)
		{
		if(visited[i] == 0)
		  this.evaluationOutput -= 1;
		}
    }//end evaluationFunction
	public void printArr(int[][] arr){
		for(int i = 0; i < arr[0].length; i++){
			for(int j = 0; j < arr[0].length; j++){
				System.out.print(arr[i][j] + ", ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public void writeArr(PrintWriter file, int[][] arr){
		for(int i = 0; i < arr[0].length; i++){
			for(int j = 0; j < arr[0].length; j++){
		    	file.write(arr[i][j] + " ");
		  	}
		  	file.write("\n");
		}
		file.write("\n");
	}

	public void generateDigraph(int[][] arr, int n){
          g = new Graph(n*n);

          int currVertex;   //curr vertex stores the current vertex x*n + y
          int legalJump;    //integer for storing the legal jump of curr vert
          int rightChecker; //integer for checking the right side bounds
          int leftChecker;  //integer for checking the left side bounds

          //Declarations of integers for holding right,left,up, and down vertices
          int rightVertex;
          int leftVertex;
          int upVertex;
          int downVertex;

          for(int i = 0; i < n; i++)
          {
            for(int j = 0; j < n; j++ )
            {
                //The current vertex location
                currVertex = i * n + j;
                legalJump  = arr[i][j]; //the legal jump for curr vertex

                //Normalizing the the row to check if right/left are in bounds
                rightChecker = currVertex - n*i;
                leftChecker  = currVertex - n*i;

                //Check whether up, down, left, or right are valid jumps
                rightChecker += legalJump;
                leftChecker  -= legalJump;
                //setting rightvertex and left vertex to their positions if they
                //are valid
                rightVertex = currVertex + legalJump;
                leftVertex  = currVertex - legalJump;

                //Should fall within the bounds [0, n - 1] for the move to be valid
                if( (rightChecker >= 0) && (rightChecker < n) )
                {
                  g.addEdge(currVertex, rightVertex, legalJump);
                  //System.out.println("Moving right..." + legalJump);
                  //System.out.println(currVertex + "->" + rightVertex);
                }
                if( (leftChecker >= 0) && (leftChecker < n) )
                {
                  g.addEdge(currVertex, leftVertex, legalJump);
                  //System.out.println("Moving left..." + legalJump);
                  //System.out.println(currVertex + "->" + leftVertex);
                }
                upVertex   = currVertex - n * legalJump;
                downVertex = currVertex + n * legalJump;
                //Checking the conditions the vertices above and below current
                //vertex are valid where the current vertexes can have values
                // [ 0, (n^2 - 1)] ex: n = 5, [0, 24]
                if( (upVertex >= 0) && (upVertex < n*n))
                {
                  g.addEdge(currVertex, upVertex, legalJump);
                  //System.out.println("Moving up..." + legalJump);
                  //System.out.println(currVertex + "->" + upVertex);
                }
                if( (downVertex >= 0) && (downVertex < n*n))
                {
                  g.addEdge(currVertex, downVertex, legalJump);
                  //System.out.println("Moving down..." + legalJump);
                  //System.out.println(currVertex + "->" + downVertex);
                }
          }//end inner for loop
        }//end outer for loop
      }//end generateDigraph()
}