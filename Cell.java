import java.util.ArrayList;

/**
 * Cell Object containing cell info
 */
public class Cell {

    private static ArrayList<Rule> rules;
    //types of cells
    public static final int SOLID = 0;
    public static final int FLOOR = 1;
    public static final int EMPTY = 2;
    public static final int NO_TYPE = 4;

    private int type; //instance type
    private boolean modified = false;

    public Cell(int type){
        this.type = type;
    }


    public static void setRules(ArrayList<Rule> rules){
        Cell.rules = rules;
    }

    public static void addRule(Rule rule){
        rules.add(rule);
    }

    public boolean isType(int type){
       
        return this.type == type;
    }

    /**
     * Evaluates the surrounding cells of this cell
     * @param cells - previous cell matrix
     * @param newCells - cell matrix to be modified
     * @param row - row of this cell
     * @param col - column of this cell
     */
    public void evaluateSurroundingCells(Cell[][] cells, Cell[][] newCells, int row, int col){
        //gets the surrounding cells starting from top left
        int[] relativeCells = new int[9];
        int index = TypePosition.TOP_LEFT;
        for(int r = row - 1; r < row + 2; r++){
            for(int c = col - 1; c < col + 2; c++){
                //ensures only cells that are real are taken
                if(r < 0 || r >= cells.length || c < 0 || c >= cells[0].length){
                    relativeCells[index] = -1;
                }
                else{
                relativeCells[index] = cells[r][c].type;
                }
                index++;
            }
        }
        //applies rules to cell and its surroudings
        for(int i = 0; i < rules.size(); i++){
            int temp;
            if((temp = rules.get(i).applyRule(relativeCells)) != Rule.RULE_FAILED){
             //   System.out.println("Applying Result (" + row + ", " + col + ") with value: " + temp);
                newCells[row][col].type = temp;
                newCells[row][col].modified = true;
                this.modified = true;
            }
        }
    }

    public boolean isModified(){
        return modified;
    }

    /**
     * gets this.type
     * @return this.type
     */
    public int getType(){
        return this.type;
    }

    public void setType(int type){
        if(type < 0 || type >= 4){
            throw new Error("type:" + type + " not valid");
        }
        this.type = type;
    }

}