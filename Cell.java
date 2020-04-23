import java.util.ArrayList;

public class Cell {

    private static ArrayList<Rule> rules;
    public static final int SOLID = 0;
    public static final int FLOOR = 1;
    public static final int EMPTY = 2;

    private int type;
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

    public void evaluateSurroundingCells(Cell[][] cells, Cell[][] newCells, int row, int col){
        int[] relativeCells = new int[9];
        int index = TypePosition.TOP_LEFT;
        for(int r = row - 1; r < row + 2; r++){
            for(int c = col - 1; c < col + 2; c++){
                if(r < 0 || r >= cells.length || c < 0 || c >= cells[0].length){
                    relativeCells[index] = -1;
                }
                else{
                relativeCells[index] = cells[r][c].type;
                }
                index++;
            }
        }
        
        for(int i = 0; i < rules.size(); i++){
            int temp;
            if((temp = rules.get(i).applyRule(relativeCells)) != Rule.RULE_FAILED){
                System.out.println("Applying Result (" + row + ", " + col + ") with value: " + temp);
                newCells[row][col].type = temp;
                newCells[row][col].modified = true;
                this.modified = true;
            }
        }
    }

    public boolean isModified(){
        return modified;
    }

    public int getType(){
        return this.type;
    }

}