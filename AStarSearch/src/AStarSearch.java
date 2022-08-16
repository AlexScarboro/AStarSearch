import java.util.*;
import java.lang.*;

/**
 *
 * @author alexs
 */
public class AStarSearch { 

    public static int row = 15; //number of rows
    public static int col = 15; //number of columns
    public static int[][] mainBoard = new int [row][col]; //this holds the main board
    public static Node[][] node = new Node[row][col];
    public static Node start;
    public static Node goal;
    
    public static void main(String[] args) {
        AStarDriver asd = new AStarDriver();
        asd.set10percent(mainBoard); //makes the main board 10% unpathable
        asd.writeToNode(mainBoard, node); //transfers the main board into Nodes
        
        start = asd.getStart(node);//gets the start node
        goal = asd.getGoal(node);//gets the goal node
        
        asd.getHeuristic(node, goal);
        asd.displayNode(node);
        
        asd.displayHeuristic(node);
        //System.out.println(node[start.getRow()][start.getCol()].getH());
        
        start.setG(0); //set the cost of moving to the starting Node 0
        start.setH(node[start.getRow()][start.getCol()].getH()); /*set the starting heuristic
        to the value of the start Node's row and col */
        start.setF(); //set the starting Node's total score
        
        //make a copy of the board of Nodes. Later it can be used to display the path.
        Node[][] copyBoard = new Node[row][col];

        for (int i = 0; i < node.length; i++){ //loop thru the Node's length
            for (int j = 0; j < node[0].length; j++) { //loop thru the 1st Node's length 
                copyBoard[i][j] = node[i][j]; //copy the Nodes to the copyBoard
            }
        }
        
        boolean continueSearching = true;

        //create an openList and a closedList array
        ArrayList<Node> openList = new ArrayList<>(); //used to hold Nodes to be visited
        ArrayList<Node> closedList = new ArrayList<>(); //used to hold Nodes already visited
        
        openList.add(start); //add the start node to the openList
        //System.out.println("Adding Node: " + start.toString() + " to the openList");
        
        while(continueSearching){
            //remove the Node from openList
            Node n = openList.remove(0); //remove the first element
            //System.out.println("\nRemoving node " + n.toString()+ " from openList\n");
        
            //check if the Goal has been reached
            if(n.equals(goal)){
                System.out.println("Goal reached! A path has been found.");
                System.out.println("This is the path: ");
                continueSearching = false; /* if the Goal is reached, then the boolean is set to
                false, which exits the loop and stops the searching */

                while(!n.equals(start)){
                    System.out.print(n.getParent().toStringFinal()); 
                    n = n.getParent(); //set the parent to n

                    copyBoard[n.getRow()][n.getCol()].setType(8);
                }
            }else{ //look for neighbors around node
                int nRow = n.getRow(); //nRow = Node row
                int nCol = n.getCol(); //nCol = Node col
        
                for(int i = nRow - 1; i <= nRow + 1; i++){
                    for(int j = nCol - 1; j <= nCol + 1; j++){
                        //traverses to look for neighbors
                        if((i >= 0 && i < row) && (j >= 0 && j < row) && (i != nRow || j != nCol) && (node[i][j].getType() != 1)){
                            /* 1. if index i is greater than 0 AND less than row
                               2. if j index is greater than 0 AND less than row
                               3. if i index is NOT equal to Node row OR j index is NOT equal to Node col
                               4. if the type of the Node at position i,j is NOT equal to 1
                            
                            The above conditions are for searching for neighbors of the Node
                            ... these are here because of these reasons (explain a little
                            about why you should use all of these as your checks)
                            */
                            
                            Node newNode = new Node(i, j, 0); /* this is the current node
                            to get neighbors of */
                            //System.out.println("Getting neighbors: " + newNode.toString());
                            newNode.setParent(n); //set the newNode's parent Node to n
                            int newG = 10; //if moving vertically or horizontally, cost to move is 10 
                            if(Math.abs(i - nRow) + Math.abs(j - nCol) == 2){
                                newG = 14; //if moving diagonally, cost to move is 14 
                            }
                            
                            newNode.setG(n.getG() + newG); //sets the new G value
                            newNode.setH(node[start.getRow()][start.getCol()].getH());
                            //assigns the heuristic value to the Node
                            newNode.setF(); //gets the new F value (total score)
                            
                            if(asd.checkIfInList(newNode, closedList) == null){ 
                            //checks to see if the newNode is in the closed list
                                Node checkParent = asd.checkIfInList(newNode, openList);
                                //checks the newNode against the openList to see if it's present
                                if(checkParent == null){
                                    openList.add(newNode); 
                                    /*if checkParent isn't null, newNode gets added to the
                                    openList */
                                    /*System.out.println("\nAdding Node: " + newNode.toString() 
                                        + " to List\n"); */
                                }else{
                                    if(newNode.getG() < checkParent.getG()){ /*check the G value
                                        and if it is smaller */
                                        checkParent.setG(newNode.getG());//it means that path is shorter
                                        checkParent.setParent(n);//so it repaths to that node and sets it as its new parent
                                    }
                                }
                            }
                        }
                    }
                }
                asd.sort(openList);
                closedList.add(n);
            }
        }
        System.out.println("\nThis is the grid layout. The \"8\" is the path. \n");
        asd.displayNode(copyBoard); //displays the grid with the path.
    }
    
}
