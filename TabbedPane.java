import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import javax.swing.table.*;
import javax.swing.JTable;

public class TabbedPane extends JFrame implements ActionListener
{
  //Create main tabePane object where each of the tabs will be placed
  JTabbedPane tabPane;

  StatsPane statsPane   = new StatsPane();
  PuzzlePane puzzlePane = new PuzzlePane();
  MovesPane movesPane   = new MovesPane();
  ButtonGrid bg;
  ButtonGrid puzzleMoves;

  JButton cancel;
  JButton confirm;

  public TabbedPane(String initializer)
  {
    super("Artificial Intelligence");

    tabPane  = new JTabbedPane();

    //Create new jpanels for food, drink, appetizers, desserts, and bill
    JPanel initPane   =  getPuzzleMenu(initializer);
    //JPanel puzzle =
    //JPanel moves  =
    //JPanel stats  =

    //Adding the tabs to the JTabbedPane
    tabPane.add("Puzzle Initialization", initPane);
    tabPane.add("Puzzle", puzzlePane);
    tabPane.add("Puzzle Moves", movesPane);
    tabPane.add("Statistics", statsPane);


    //Adding all the components together to create the JFrame
    this.add(tabPane);
    setSize(800,600);
    setVisible(true);
}
//References: http://stackoverflow.com/questions/21636895/how-to-add-a-scroll-bar-to-a-jtabbedpane-basically-i-have-an-admin-panel-which

JComboBox sizeBox = new JComboBox();
JButton   generate;

public JPanel BasicPuzzleMenu() {
  //super("Basic Puzzle Generator");
  JPanel pane = new JPanel();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  pane.setLayout(null);

  //Setting the Title and location
  JLabel title = new JLabel("Basic Puzzle Generator Main Menu");
  JLabel prompt = new JLabel("Please select the size of n:");
  title.setBounds(150,50,600,100);

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
  generate = new JButton(generate_Icon);

  generate.setBounds(520,400,150,44);
  sizeBox.setBounds(500, 180, 50, 30);
  //prompt.setBounds(180, 95, 350, 200);
  sizeBox.setBounds(180, 95, 350, 200);
  generate.addActionListener(this);

  //Add the title, prompt, sizebox, and generate icon to the interface
  pane.add(title);
  pane.add(prompt);
  pane.add(sizeBox);
  pane.add(generate);

  getContentPane().add(pane);
  setSize(800,600);
  setVisible(true);

  return pane;
}

public JPanel getPuzzleMenu(String selected)
{
  JPanel puzzleMenuPanel = new JPanel();
  //TODO: IMPLEMENT!
  if(selected == "Basic Puzzle Evaluation"){
    puzzleMenuPanel = BasicPuzzleMenu();
    return puzzleMenuPanel;
  }
  //TODO: IMPLEMENT!
  else if(selected == "User Generated Puzzle Evaluation"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
  }
  //TODO: IMPLEMENT!
  else if(selected == "Basic Hill Climbing"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
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

  //TODO: IMPLEMENT!
  else if(selected == "Simulated Annealing"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
  }
  //TODO: IMPLEMENT!
  else if(selected == "Population Based Approach"){
    //puzzleMenuPanel = BasicPuzzleMenu();
    //return puzzleMenuPanel;
  }
  return puzzleMenuPanel;
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

    if(source == generate){
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
    }
  }

}