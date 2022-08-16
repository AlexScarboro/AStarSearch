
/**
 *
 * @author alexs
 */
public class Node {

    private int row, col, f, g, h, type;
    private Node parent;

    public Node(int r, int c, int t){
            row = r;
            col = c;
            type = t;
            parent = null;
            //type 0 is traverseable, 1 is not
    }

    /* REFRESHER:
    F = Total score. Lower is better. F = G + H.
    G = Cost to move to current Node from starting Node. Up, down, left, and right = 10.
        Diag = 14.
    H = Heurisitic (estimatation of how close current Node is to goal Node. Estimated with
        Manhattan Method (no diags)).
    */
    
    //mutator methods to set values
    public void setF(){
            f = g + h;
    }
    
    public void setG(int value){
            g = value;
    }
    
    public void setH(int value){
            h = value;
    }
    
    public void setParent(Node n){
            parent = n;
    }
    
    public void setType(int value){
        type = value;
    }

    //accessor methods to get values
    public int getF(){
            return f;
    }
    
    public int getG(){
            return g;
    }
    
    public int getH(){
            return h;
    }
    
    public Node getParent(){
            return parent;
    }
    
    public int getRow(){
            return row;
    }
    
    public int getCol(){
            return col;
    }

    public int getType(){
            return type;
    }

    public boolean equals(Object in){ 
            //typecast to Node
            Node n = (Node) in;

            return row == n.getRow() && col == n.getCol();
    }

    public String toString(){
            return "Node: " + row + "_" + col + " ";
    }

    public String toStringFinal(){ 
            return "Node: " + row + "_" + col + " -> ";
    }

}
