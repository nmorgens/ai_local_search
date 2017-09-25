import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.math.*;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.text.NumberFormatter;

import com.sun.java_cup.internal.runtime.Symbol;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;



public class TabbedPane extends JFrame implements ActionListener
{
  //Create main tabePane object where each of the tabs will be placed
  static final String FILENAME = "./basicHillClimbingPuzzles/";

  JTabbedPane tabPane;

  DataPane dataPane     = new DataPane();
  PuzzlePane puzzlePane = new PuzzlePane();
  MovesPane movesPane   = new MovesPane();
  ButtonGrid bg;
  ButtonGrid puzzleMoves;

  JButton confirm;

  public TabbedPane(String initializer)
  {
    super("Artificial Intelligence");

    tabPane  = new JTabbedPane();

    //Create new jpanels for food, drink, appetizers, desserts, and bill
    JPanel initPane   =  getPuzzleMenu(initializer);

    //Adding the tabs to the JTabbedPane
    tabPane.add("Puzzle Initialization", initPane);
    tabPane.add("Puzzle", puzzlePane);
    tabPane.add("Puzzle Moves", movesPane);
    tabPane.add("Puzzle Data", dataPane);

    //Adding all the components together to create the JFrame
    this.add(tabPane);
    setSize(800,600);
    setVisible(true);
}
//References: http://stackoverflow.com/questions/21636895/how-to-add-a-scroll-bar-to-a-jtabbedpane-basically-i-have-an-admin-panel-which

//This will be used for basic puzzle menu, basic hill climbing,
JComboBox sizeBox = new JComboBox();
JButton   cancel;
//Basic user menu will contain a combobox as well as a generate button
JButton   basicGenerate;
//User generated menu will contain a confirm button as well as a text field
// for inputting a file
JButton    userConfirm;
JTextField userField;
//Basic Hill Climbing needs a JButton for the number of iterations
JComboBox iterBox = new JComboBox();
JButton basicHillGenerate;
JButton simulatedAnnealingGenerate;
JButton populationApproachGenerate;
JButton sa50times;

//Simulated Annealing
//JTextField initTempField = new JTextField();
//JTextField tempDecayRateField = new JTextField();
JComboBox initTempField = new JComboBox();
JComboBox tempDecayRateField = new JComboBox();

//population approach
JComboBox iterEndTimeBox = new JComboBox();
JComboBox populationSizeBox = new JComboBox();
JComboBox mutationProbBox = new JComboBox();
/*
public JPanel PopulationPuzzleMenu(){

}
*/

public JPanel populationApproachPuzzleMenu(){
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);
  JLabel title  = new JLabel("Population Approach Puzzle Menu");
  title.setBounds(150,50,600,100);
  Font font = new Font("Cambria", Font.BOLD, 30);
  title.setFont(font);

  JLabel sizePrompt = new JLabel("Size:");
  JLabel iterEndTimePrompt = new JLabel("Set time limit to iterate over: ");
  JLabel populationSizePrompt = new JLabel("Set the initial population size: ");
  
  sizeBox.addItem(5);
  sizeBox.addItem(7);
  sizeBox.addItem(9);
  sizeBox.addItem(11);
  sizeBox.setPrototypeDisplayValue("Size"); //Setting the default text to display

  for(int i = 20; i <= 5000; i+= 20)
    iterEndTimeBox.addItem(i);
  for(int j = 2; j <= 1000; j+= 2)
    populationSizeBox.addItem(j);
  ImageIcon generate_Icon  = new ImageIcon("icons/generate.png");
  populationApproachGenerate = new JButton(generate_Icon);
  ImageIcon cancel_Icon = new ImageIcon("icons/back.png");
  cancel = new JButton(cancel_Icon);

  populationApproachGenerate.setBounds(600,480,150,44);
  cancel.setBounds(100,480,120,44);
  sizePrompt.setBounds(120, 40, 200, 200);
  sizeBox.setBounds(120, 150, 200, 100);
  iterEndTimePrompt.setBounds(120, 190, 250,200);
  iterEndTimeBox.setBounds(120, 300, 200, 100);
  populationSizePrompt.setBounds(450,40,250,200);
  populationSizeBox.setBounds(450,150,200,100);
  
  JLabel mutationProbPrompt = new JLabel("Set the probability for mutation /100:");
  mutationProbPrompt.setBounds(450,190,250,200);
  mutationProbBox.setBounds(450,300,200,100);
  mutationProbBox.addActionListener(this);
  for( float i =0; i <= 1000; ++i)
	  mutationProbBox.addItem((float)i/1000);

  populationApproachGenerate.addActionListener(this);
  pane.add(populationApproachGenerate);
  cancel.addActionListener(this);
  sizeBox.addActionListener(this);
  iterEndTimeBox.addActionListener(this);
  populationSizeBox.addActionListener(this);
  
  //System.out.println("I'm here at least");

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(sizePrompt);
  pane.add(sizeBox);
  pane.add(iterBox);
  pane.add(iterEndTimePrompt);
  pane.add(iterEndTimeBox);
  pane.add(cancel);
  pane.add(populationSizePrompt);
  pane.add(populationSizeBox);
  pane.add(mutationProbPrompt);
  pane.add(mutationProbBox);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel SimulatedAnnealingPuzzleMenu() {
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);

  //Setting the Title and location
  JLabel title  = new JLabel("Simulated Annealing Puzzle Menu");
  title.setBounds(150,50,600,100);
  Font font = new Font("Cambria", Font.BOLD, 30);
  title.setFont(font);

  JLabel iterPrompt = new JLabel("Please select the number of iterations:");
  JLabel initTempPrompt = new JLabel("Please select the initial temperature: ");
  JLabel tempDecayRatePrompt = new JLabel("Please select the decay rate: ");
  JLabel sizePrompt = new JLabel("Size:");
  sizeBox.setPrototypeDisplayValue("Size"); //Setting the default text to display
  //Populate the sizebox with the possible puzzle sizes
  sizeBox.addItem(5);
  sizeBox.addItem(7);
  sizeBox.addItem(9);
  sizeBox.addItem(11);

  for(int i = 250; i <= 100000; i+= 250)
    iterBox.addItem(i);

  for (float i = 0; i <= 1000; i=i+1){
	initTempField.addItem(i);
    tempDecayRateField.addItem((Float) (i/1000));
  }

  //Add generate icon as a button on the gui
  ImageIcon generate_Icon  = new ImageIcon("icons/generate.png");
  simulatedAnnealingGenerate = new JButton(generate_Icon);
  sa50times = new JButton(generate_Icon);
  ImageIcon cancel_Icon = new ImageIcon("icons/back.png");
  cancel = new JButton(cancel_Icon);

  simulatedAnnealingGenerate.setBounds(600,480,150,44);
  cancel.setBounds(100,480,120,44);
  sizePrompt.setBounds(120, 40, 200, 200);
  sizeBox.setBounds(120, 150, 200, 100);
  iterPrompt.setBounds(120, 190, 250,200);
  iterBox.setBounds(120, 300, 200, 100);
  initTempPrompt.setBounds(450,40,250,200);
  initTempField.setBounds(450,150,200,100);
  tempDecayRatePrompt.setBounds(450,190,250,200);
  tempDecayRateField.setBounds(450,300,200,100);

  simulatedAnnealingGenerate.addActionListener(this);
  cancel.addActionListener(this);
  sizeBox.addActionListener(this);
  iterBox.addActionListener(this);
  initTempField.addActionListener(this);
  tempDecayRateField.addActionListener(this);
  
  sa50times.setBounds(10,10,150,44);
  sa50times.addActionListener(this);
  pane.add(sa50times);
  //System.out.println("I'm here at least");

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(sizePrompt);
  pane.add(sizeBox);
  pane.add(iterBox);
  pane.add(iterPrompt);
  pane.add(iterBox);
  pane.add(initTempPrompt);
  pane.add(initTempField);
  pane.add(tempDecayRatePrompt);
  pane.add(tempDecayRateField);
  pane.add(simulatedAnnealingGenerate);
  pane.add(cancel);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel BasicPuzzleMenu() {
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);

  //Setting the Title and location
  JLabel title = new JLabel("Basic Puzzle Generator Main Menu");
  JLabel prompt = new JLabel("Please select the size of n:");
  title.setBounds(110,50,600,100);

  Font font = new Font("Cambria", Font.BOLD, 30);
  title.setFont(font);
  Font font1 = new Font("Cambria", Font.BOLD, 20);
  prompt.setFont(font1);

  //Setting the default text to display
  sizeBox.setPrototypeDisplayValue("Size");
  //Populate the sizebox with the possible puzzle sizes
  sizeBox.addItem(5);
  sizeBox.addItem(7);
  sizeBox.addItem(9);
  sizeBox.addItem(11);

  //Add generate icon as a button on the gui
  ImageIcon generate_Icon  = new ImageIcon("icons/generate.png");
  basicGenerate = new JButton(generate_Icon);
  ImageIcon cancel_Icon = new ImageIcon("icons/back.png");
  cancel = new JButton(cancel_Icon);

  basicGenerate.setBounds(520,400,150,44);
  cancel.setBounds(150,395,150,44);
  sizeBox.setBounds(190, 95, 150, 200);
  basicGenerate.addActionListener(this);
  cancel.addActionListener(this);

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(prompt);
  pane.add(sizeBox);
  pane.add(basicGenerate);
  pane.add(cancel);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel UserPuzzleMenu() {
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);

  //Setting the Title and location
  JLabel title = new JLabel("User Generated Puzzle Main Menu");
  title.setBounds(110,50,600,100);
  Font font = new Font("Cambria", Font.BOLD, 30);
  title.setFont(font);

  //Add generate icon as a button on the gui
  ImageIcon confirm_Icon  = new ImageIcon("icons/confirm.png");
  userConfirm = new JButton(confirm_Icon);
  ImageIcon cancel_Icon = new ImageIcon("icons/back.png");
  cancel = new JButton(cancel_Icon);
  userField = new JTextField("./userPuzzles/assignmentPuzzle.txt");

  userConfirm.setBounds(520,400,150,44);
  cancel.setBounds(150,395,150,44);
  userField.setBounds(270, 150, 250, 50);
  userConfirm.addActionListener(this);
  cancel.addActionListener(this);
  //userField.addActionListener(this);

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(userField);
  pane.add(userConfirm);
  pane.add(cancel);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel BasicHillClimbingPuzzleMenu() {
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);

  //Setting the Title and location
  JLabel title  = new JLabel("Basic Hill Climbing Main Menu");
  title.setBounds(150,50,600,100);
  Font font = new Font("Cambria", Font.BOLD, 30);
  title.setFont(font);

  JLabel sizePrompt = new JLabel("Please select a size n");
  JLabel iterPrompt = new JLabel("Please select the number of iterations:");

  //Setting the default text to display
  sizeBox.setPrototypeDisplayValue("Size");
  //Populate the sizebox with the possible puzzle sizes
  sizeBox.addItem(5);
  sizeBox.addItem(7);
  sizeBox.addItem(9);
  sizeBox.addItem(11);

  for(int i = 250; i <= 100000; i+= 250)
    iterBox.addItem(i);

  //Add generate icon as a button on the gui
  ImageIcon generate_Icon  = new ImageIcon("icons/generate.png");
  basicHillGenerate = new JButton(generate_Icon);
  ImageIcon cancel_Icon = new ImageIcon("icons/back.png");
  cancel = new JButton(cancel_Icon);

  basicHillGenerate.setBounds(520,400,150,44);
  cancel.setBounds(150,390,150,44);
  sizePrompt.setBounds(300, 70, 200, 200);
  sizeBox.setBounds(270, 150, 200, 100);

  iterPrompt.setBounds(260, 190, 250,200);
  iterBox.setBounds(280, 300, 200, 100);
  basicHillGenerate.addActionListener(this);
  cancel.addActionListener(this);
  sizeBox.addActionListener(this);
  iterBox.addActionListener(this);
  //System.out.println("I'm here at least");

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(sizePrompt);
  pane.add(sizeBox);
  pane.add(iterBox);
  pane.add(iterPrompt);
  pane.add(basicHillGenerate);
  pane.add(cancel);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel getPuzzleMenu(String selected) {
  JPanel puzzleMenuPanel = new JPanel();
  //Basic Puzzle Evaluation is for generating random puzzles, generally
  //puzzles are not very "difficult"
  if(selected == "Basic Puzzle Evaluation"){
    puzzleMenuPanel = BasicPuzzleMenu();
    return puzzleMenuPanel;
  }
  //User Generated puzzles are those pre made by the user that have been
  //saved in a text file
  else if(selected == "User Generated Puzzle Evaluation"){
    puzzleMenuPanel = UserPuzzleMenu();
    return puzzleMenuPanel;
  }
  
  else if(selected == "Basic Hill Climbing"){
    puzzleMenuPanel = BasicHillClimbingPuzzleMenu();
    return puzzleMenuPanel;
  }
  //TODO: IMPLEMENT!
  else if(selected == "Hill Climbing with Random Restarts"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
  }
  //TODO: IMPLEMENT!
  else if(selected == "Hill Climbing with Random Walk"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
  }

  else if(selected == "Simulated Annealing"){
    puzzleMenuPanel = SimulatedAnnealingPuzzleMenu();
    return puzzleMenuPanel;
  }
  else if(selected == "Population Based Approach"){
    puzzleMenuPanel = populationApproachPuzzleMenu();
    return puzzleMenuPanel;
  }
  return puzzleMenuPanel;
}

public void basicApproach(){
	  int n = (int)sizeBox.getSelectedItem();

    bg = new ButtonGrid(n,n);
    tabPane.setComponentAt(1, bg.getContentPane());
    //Getting the corresponding graph to create movesPane
    Graph g = bg.getGraph();
    int sqr = n*n;
    int[] visited = new int[sqr];
    visited = g.bfs(0);
    //Call ButtonGrid constructor to create the puzzle moves pane
    puzzleMoves = new ButtonGrid(visited, n);
    tabPane.setComponentAt(2,puzzleMoves.getContentPane());

    dataPane = new DataPane(puzzleMoves.getEvaluationOutput());
    tabPane.setComponentAt(3,dataPane);
}

public void userGeneratedApproach(){
	String fileName = (String)userField.getText();
    if(fileName != null)
    {
      //System.out.println(fileName);
      FileToMatrix ftm = new FileToMatrix(fileName);
      //Get the length of the puzzle
      int puzzleArrLen = ftm.getPuzzleArr().length;
      bg = new ButtonGrid(ftm.getPuzzleArr());
      tabPane.setComponentAt(1, bg.getContentPane());

      //Getting the corresponding graph to create movesPane
      Graph g = bg.getGraph();
      int[] visited = new int[puzzleArrLen];
      visited = g.bfs(0);

      int sqrRoot = (int)Math.sqrt(puzzleArrLen);
      //Call ButtonGrid constructor to create the puzzle moves pane
      puzzleMoves = new ButtonGrid(visited, sqrRoot);
      tabPane.setComponentAt(2,puzzleMoves.getContentPane());

      dataPane = new DataPane(puzzleMoves.getEvaluationOutput());
      tabPane.setComponentAt(3,dataPane);
    }
}


public void basicHillApproach(){
  /*
    PrintWriter maxEvalFile = null;
    try{
      maxEvalFile = new PrintWriter(new FileWriter("./basicHillClimbingPuzzles/maxEval.txt", true));
    }catch (IOException i) {
    // TODO Auto-generated catch block
    i.printStackTrace();
  }*/
    long startTime = System.currentTimeMillis();

    int n    = (int)sizeBox.getSelectedItem();
    int iter = (int)iterBox.getSelectedItem();

    int sqr = n*n;
    int[] visited = new int[sqr];
    int[][] currBestPuzzle = new int[n][n];
    int[][] finalPuzzle     = new int[n][n];
    bg = new ButtonGrid(n,n);
    currBestPuzzle = bg.getPuzzleArr();
    Graph g = bg.getGraph();
    visited = g.bfs(0);

    puzzleMoves = new ButtonGrid(visited, n); //Call ButtonGrid constructor to create the puzzle moves pane
    //get the evalOutput for the first puzzle
    int maxEvalOutput = puzzleMoves.getEvaluationOutput();
    //create a currEvalOutput variable to compare to the maxEvalOutput
    int currEvalOutput = 0;

    HillClimbing hClimb = new HillClimbing(currBestPuzzle, n);
    hClimb.setVisited(visited);

    for(int iterations = 0; iterations < iter; iterations++)
    {
      //Method hillClimb chooses a random non goal cell and then replaces
      //it with a new legal random move
      hClimb.hillClimb();
      //Generate the new directed graph and perform bfs
      bg.generateDigraph(hClimb.getNewPuzzle(), n);
      g = bg.getGraph();
      //set visited to the set of puzzle moves and finally compare
      //the evaluation output
      visited = g.bfs(0);
      //We need to get the new evaluation function output now, we can use
      //the method avaliable in the ButtonGrid class
      bg.evaluationFunction(visited,n);
      currEvalOutput = bg.getEvaluationOutput();

      /*
          FOR DEBUGGING CAN BE REMOVED TO IMPROVE PERFORMANCE
      */

      //System.out.println("maxEvalOutput: " + maxEvalOutput);
      bg.printArr(hClimb.getbestPuzzle());

      //System.out.println("currEvalOutput: " + currEvalOutput);
      bg.printArr(hClimb.getNewPuzzle());
      //System.out.println("Curr")

      if(currEvalOutput >= maxEvalOutput)
      {
        //Set the new evaluation output
        maxEvalOutput = currEvalOutput;
        hClimb.setbestPuzzleToNew(hClimb.getNewPuzzle());
        currBestPuzzle = hClimb.getNewPuzzle();
        hClimb.setVisited(visited);
        //bestPuzzleFile.createNewFile();
      }
      //maxEvalFile.println(maxEvalOutput);
    }//end for loop

    //Calculate the end time and the total time by subtracting end from start
    long endTime = System.currentTimeMillis();
    long evaluationTime = endTime - startTime;

    bg = new ButtonGrid(hClimb.getbestPuzzle(), n);
    tabPane.setComponentAt(1, bg.getContentPane());

    visited = hClimb.getVisited();
    puzzleMoves = new ButtonGrid(visited,n);
    tabPane.setComponentAt(2,puzzleMoves.getContentPane());

    dataPane = new DataPane(puzzleMoves.getEvaluationOutput(), evaluationTime);
    tabPane.setComponentAt(3,dataPane);
    //maxEvalFile.close();

}

public void simulatedAnnealingApproach(){
        LinkedList<Integer> evalValueArray = new LinkedList<Integer>();
        int n = (int)sizeBox.getSelectedItem();
        int iter = (int)iterBox.getSelectedItem();
        float initTemp = (Float) initTempField.getSelectedItem();
        float decayRate = (Float) tempDecayRateField.getSelectedItem();
        float temp = initTemp;
        
        //tabPane.setComponentAt(1, bg.getContentPane());
        long startTime = System.currentTimeMillis();

        Random randy = new Random();
        
        int val;
        int prevVal;
        Puzzle finalPuzzle;
        
        finalPuzzle = new Puzzle(n,n);
        finalPuzzle.evaluationFunction(finalPuzzle.getGraph().bfs(0), n);
        prevVal = finalPuzzle.getEvaluationOutput();
        Puzzle changedPuz;
        //iteration loop
        evalValueArray.add(prevVal);
        for (int i = 0; i < iter; ++i){
          temp = temp * decayRate;
          
          //System.out.println("temp:" + temp);
          changedPuz = new Puzzle(finalPuzzle.puzzleArr, n);
          changedPuz.randCellChange();
          changedPuz.evaluationFunction(changedPuz.getGraph().bfs(0), n);
          val = changedPuz.getEvaluationOutput(); // val is evaluation value
          if ( val > prevVal){ // if evaluation value of current puzzle config is greater than ev. value of previous puzzle config
           prevVal = val;
           finalPuzzle = changedPuz;
          }
          else if ( val <= prevVal){ // if evaluation value of current puzzle config is lte than ev. value of previous puzzle config
        	  //System.out.println("prevVal" + prevVal);
        	  //System.out.println("Val" + val);
        	  //System.out.println( Math.exp((double)(val - prevVal )/temp) );
        	  if (randy.nextFloat() < Math.exp((double)(val - prevVal )/temp)){ //  probability condition met
            	
	              prevVal = val;
	              finalPuzzle = changedPuz;
        	  }
        	  else {// probability failed // taking higher value
        		  prevVal = prevVal;
        	  }
          }
          temp = temp*decayRate; //apply decay to temp
          evalValueArray.add(prevVal);
          //System.out.println(temp);
          //System.out.println("prevVal:" + prevVal);
       }
        finalPuzzle.evaluationFunction(finalPuzzle.getGraph().bfs(0),n);
        //System.out.println("eval output:" + finalPuzzle.getEvaluationOutput());

        long endTime = System.currentTimeMillis();
        long evaluationTime = endTime - startTime;
        bg = new ButtonGrid(finalPuzzle.puzzleArr,finalPuzzle.n);
        tabPane.setComponentAt(1, bg.getContentPane());
        puzzleMoves = new ButtonGrid(finalPuzzle.getGraph().bfs(0), n);
        tabPane.setComponentAt(2,puzzleMoves.getContentPane());
        int[] k = bg.getGraph().bfs(0);
        bg.evaluationFunction(k, n);
        dataPane = new DataPane(bg.getEvaluationOutput(), evaluationTime);
        tabPane.setComponentAt(3,dataPane);
        //write to file
        writeEvaluationArrayToFile(FILENAME + "maxEvalSA9.txt", evalValueArray);

}

public void writeEvaluationArrayToFile(String filename, LinkedList<Integer> evalValueArray){
        PrintWriter maxEvalFile = null;
        try{
          maxEvalFile = new PrintWriter(new FileWriter(filename, true));
        }catch (IOException i) {
          // TODO Auto-generated catch block
          i.printStackTrace();
        }
        int arrayMaxSize = evalValueArray.size();
        for(int i =0; i < arrayMaxSize; i++){
        	maxEvalFile.write(Integer.toString(evalValueArray.removeFirst() ) + " "  );
        }
        maxEvalFile.flush();
        maxEvalFile.close();
}	

public void populationApproach(){
	/**
	 * puzzVal refers to the evaluation function value
	 */
        int n = (int)sizeBox.getSelectedItem();
        long iterEndTime = (int)iterEndTimeBox.getSelectedItem();
        int initialPop = (int)populationSizeBox.getSelectedItem();
        float mutationProb = (float) mutationProbBox.getSelectedItem();
        
        //tabPane.setComponentAt(1, bg.getContentPane());
        long startTime, popGenStartTime;
        long elapsedTime = 0;

        Random randy = new Random();
        Puzzle puzz;
        //iteration loop
        
        popGenStartTime = System.currentTimeMillis();
        Puzzle p1, p2, c1, c2; // parent 1 parent 2 child 1 child 2
        PuzzleOppositeComparator comp = new PuzzleOppositeComparator();
        PriorityQueue<Puzzle> q = new PriorityQueue<Puzzle>(11,comp);
        PriorityQueue<Puzzle> qTemp = new PriorityQueue<Puzzle>(11,comp); 
        Random rand = new Random();
        String a1, a2, b1, b2;

        int numMutations;
        int index;
        char randChar;
        int tempMove;
        Puzzle puzzTemp;
        Puzzle finalPuzzle;
        int puzzTempEval;
        
        puzz = new Puzzle(n,true);
        puzz.evaluationFunction(puzz.getGraph().bfs(0),puzz.n);
        q.add(puzz);
        for (int i = 1; i < initialPop; ++i){ //initialize population
          puzz = new Puzzle(n,true);
          puzz.evaluationFunction(puzz.getGraph().bfs(0),n);
          q.add(puzz);
        }
        elapsedTime = System.currentTimeMillis() - popGenStartTime;
        int iter = 0;
        int startSize;
        do{
        	startTime = System.currentTimeMillis();
	        numMutations = puzz.candidate.length();
	        //crossover and mutation
	        startSize = q.size();
	        for (int i = 0; i < startSize; i=i+1){
	          //crossover
		        p1 = q.poll();
		        p2 = q.poll();
	        	if ((p1!= null && p2!= null)){
		          int crossOverPoint = rand.nextInt(p1.candidate.length());
		          a1 = p1.candidate.substring(0,crossOverPoint);
		          a2 = p1.candidate.substring(crossOverPoint);
		          b1 = p2.candidate.substring(0,crossOverPoint);
		          b2 = p2.candidate.substring(crossOverPoint);
		          c1 = new Puzzle(a1+b2);
		          c2 = new Puzzle(b1+a2);
	        	}
	        	else
	        		break;
	          //mutation
	          for (int j = 0; j < 1; ++j){
				if(j==0)
				  puzzTemp = c1;
				else
				  puzzTemp = c2;
				for(int k =0; k < numMutations; ++k){
				  if (rand.nextFloat() <= mutationProb){
				     index = rand.nextInt(puzzTemp.candidate.length());
				     tempMove = puzzTemp.getMaxLegalJump(index/n, index%n, n);
				     randChar = (char) (96 + tempMove);
				     if (index == puzzTemp.candidate.length())
				       puzzTemp.candidate = puzzTemp.candidate.substring(0, index) + randChar;
				     else{
				       puzzTemp.candidate = puzzTemp.candidate.substring(0, index) + randChar + puzzTemp.candidate.substring(index+1);
				     }
				  }
				}
	          }
	          c1.stringToPuzzle(n);
	          c2.stringToPuzzle(n);
	          c1.evaluationFunction(c1.getGraph().bfs(0),c1.n);
	          c2.evaluationFunction(c2.getGraph().bfs(0),c2.n);
	          qTemp.add(p1);
	          qTemp.add(p2);
	          qTemp.add(c1);
	          qTemp.add(c2);
	        }
	        //get the highest eval value
			q = qTemp;
			puzzTemp = q.peek();
			puzzTempEval = puzzTemp.getEvaluationOutput(); // check these over time
			//System.out.println(puzzTempEval);
			finalPuzzle = puzzTemp;
			elapsedTime += System.currentTimeMillis() - startTime;
			if (elapsedTime >= iterEndTime){
				break;
			}
			++iter;
        }while(iter < 10000000);
        for (int k = 0; k < q.size(); ++k){
        	System.out.println(q.poll().getEvaluationOutput());
        }
        ButtonGrid finalPuzzleBG = new ButtonGrid(finalPuzzle.puzzleArr, n);
        tabPane.setComponentAt(1, finalPuzzleBG.getContentPane());
        puzzleMoves = new ButtonGrid(finalPuzzleBG.getGraph().bfs(0), n);
        tabPane.setComponentAt(2,puzzleMoves.getContentPane());
        dataPane = new DataPane(finalPuzzle.getEvaluationOutput(), iter);
        tabPane.setComponentAt(3,dataPane);

}

/*
*       OVERRIDING THE ACTION LISTENER
*       TO ACCOUNT FOR ALL OF THE POSSIBLE
*       BUTTON CLICKS ACCORDINGLY
*/
  @Override
  public void actionPerformed(ActionEvent e)
  {
    Object source = e.getSource();

    if(source == basicGenerate){
        basicApproach();
    }
    if(source == userConfirm){
      userGeneratedApproach();
    }
    if(source == basicHillGenerate){ //Handles the Basic Hill Climbing evaluation methods
    	basicHillApproach();
    }
    if(source == simulatedAnnealingGenerate){
      simulatedAnnealingApproach();    
    }
    if(source == sa50times){
    	for(int j = 0; j < 50; ++j){
    		simulatedAnnealingApproach();
    	}
    }
    if(source == populationApproachGenerate){
      populationApproach();   
    }
    if(source == cancel){
        setVisible(false);
        MainMenu mainMenu = new MainMenu();
    }

  }//end action listener here

}
