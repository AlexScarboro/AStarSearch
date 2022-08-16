import java.util.*;
import java.lang.*;

/**
 * @author alexs
 */
public class AStarDriver {
   
    //method to display the grid of Nodes
    public void displayNode(Node[][] display) {
        System.out.println("     0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 ");
        System.out.print("    ____________________________________________");
        
        //loop thru the length of the display Node array 
        for(int i = 0; i < display.length; i++) {
            System.out.print("\n"); //prints a new line
            
            //checks the spacing and adds an extra space if it's a single digit
            if (i < 10){
                System.out.print(i + "  | ");
            } else{
                System.out.print(i + " | ");
            }

            //traverses the display Node array and gets the value of type for the node
            for(int j = 0; j < display[0].length; j++) {
                System.out.print(display[i][j].getType()+ " ");
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    //method to make 10% of the Nodes unpathable
    public void set10percent(int[][] blocked) {
        Random rand = new Random();
        for(int i = 0; i < blocked.length; i++){ //looping thru the 2d array of blocked nodes
            for(int j = 0; j < blocked[0].length; j++){
                float chance = rand.nextFloat(); //chance var to hold value of rand value
                if (chance <= 0.10){ // <=0.10f
                   //System.out.println("One added");
                   blocked[i][j]= 1; /*setting that particular node to be blocked. 
                   Comment from Node class that says type 0 is
                   traverseable, 1 is not */
                }
            }
        }
    }
    
    //method to transfer the array into the Blocked Nodes
    public void writeToNode(int[][] arr, Node[][] blocked){
        for (int i = 0; i < arr.length; i++) {
            
            // System.out.print("\n");
            for (int j = 0; j < arr[0].length; j++) {
                blocked[i][j] = new Node(i, j, arr[i][j]); //row i, column j, type a
            }
        }
    }    
    
    //method that gets the Start Node of the grid to begin
    public Node getStart(Node[][] blocked){
        Scanner input = new Scanner(System.in);
        System.out.println("\nUsing the row and column numbers,\n" +
                "Please enter the Starting Node\n" +
                "separated by a space. For example: \"2 3\" for Row: 2 Column: 3\n" +
                "________________________________________________________\n");
        int startRow = input.nextInt();
        int startCol = input.nextInt();
        blocked[startRow][startCol].setType(2); 
        //sets the type of the starting node to the value 2

        System.out.println("Your starting Node is: (" + startRow + ", " + startCol + ")\n");

        Node startNode = new Node(startRow, startCol, 2); //puts the Start Node into Start
        startNode.setG(0);
        startNode.setF();
        return startNode;
    }
    
    //method to get the Goal Node
    public Node getGoal(Node[][] blocked){
        Scanner input = new Scanner(System.in);
        System.out.println("\nUsing the row and column numbers,\n" +
        "Please enter the Goal Node\n" +
        "separated by a space. For example: \"2 3\" for Row: 2 Column: 3\n" +
        "________________________________________________________\n");

        int row = input.nextInt();
        int col = input.nextInt();
        blocked[row][col].setType(3); //sets the type of the clocked node to the value 3

        System.out.println("\nUsing \"2\" as our Start Node and \"3\" as our Goal Node \n" +
                "\"0\" represents the Pathable areas and \"1\' represents the Blocked areas");
        System.out.println("Your Goal Node is: (" + row + ", " + col + ")\n");

        Node g = new Node(row, col, 3); //puts the End node into the Goal
        g.setF();
        return g;
    }
    
    //method to get the heuristic using the blocked Node and the moving cost (G)
    public static void getHeuristic(Node[][] blocked, Node g){
        for (int i = 0; i < blocked.length; i++){
            for(int j = 0; j < blocked[0].length; j++){
                blocked[i][j].setH((10 *(Math.abs(i-g.getRow()))) + (10 *(Math.abs(j-g.getCol()))));
                /* set the Heurisitic to (10 * the abs value of the index - row value) 
                + (10 * the abs value of the index - col value) */
            }
        }
    }
    
    //method to display the heuristic of the display Node array
    public void displayHeuristic(Node[][] display) {
        System.out.println("\n Below is a grid of the heuristics: ");
        for(int i = 0; i < display.length; i++) {
            System.out.print("\n");
            
            //checking the spacing and adding an extra space if the space is a single digit
            if (i < 10){
                System.out.print(i + "  | ");
            } else
                System.out.print(i + " | ");
            
            //traverses the display Node array and gets the value of the type for the Node
            for(int j = 0; j < display[0].length; j++) {    
                if ((display[i][j].getH() >= 10)&&(display[i][j].getH() <= 90)){
                System.out.print(display[i][j].getH()+ "  ");}

                else if (display[i][j].getH()<10){
                    System.out.print(display[i][j].getH()+ "   ");
                } else
                    System.out.print(display[i][j].getH()+ " ");

                System.out.print(" ");
            }
        }
        System.out.println("\n");
    }
    
    //method to see if the Node is in the Node ArrayList
    public static Node checkIfInList(Node node, ArrayList<Node> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals(node)){
                return list.get(i);
            }
        }
        return null;
    }
    
    //method to sort the list for the Node with the lowest F value
    public static void sort(ArrayList<Node> sortList){
        int lowestF;
        Node tempNode;
        
        for(int i = 0; i < sortList.size(); i++){
            lowestF = i;//sets the lowest value posible to the index
            //loops through the list
            int b = sortList.size() - 1;
            for(int id = i; id < b; id++) { //starting from the top and moving towards the bottom
                if (sortList.get(id + 1).getF() < sortList.get(lowestF).getF()) {//searches for lowest F
                    lowestF = id + 1;
                }
            }
            tempNode = sortList.get(i); //the tempNode = the index of the SortedList
            sortList.set(i, sortList.get(lowestF)); /*set the index equal to the
            lowest F score of the sortList */
            sortList.set(lowestF, tempNode);
        }
    }
}