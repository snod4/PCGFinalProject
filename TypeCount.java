/**
 * Contains information of a counting rule
 */
public class TypeCount extends Type {

    private int count; 
    String booleanOp; //comparator for count

    public TypeCount(int type, int count, String booleanOp) {
        super(type, false);
        this.count = count;
        this.booleanOp = booleanOp;

    }
    
    /**
     * compares val to this.count
     * @param val, an integer
     * @return true if the comparison is true, otherwise false
     */
    public boolean compare(int val){

       //System.out.println("Is " + val + " " + booleanOp + " " + this.count + "?");
        if (booleanOp.equals("==")) {
            return val == this.count;
        } else if (booleanOp.equals("<")) {
            return val < this.count;
        } 
        else if (booleanOp.equals("<=")) {
            return val <= this.count;
        } 
        else if (booleanOp.equals(">")) {
            return val > this.count;
        } 
        else if (booleanOp.equals(">=")) {
            return val >= this.count;
        }
         else if (booleanOp.equals("!=")) {
            return val != this.count;
        } 
        else {
            throw new Error("This boolean operator is not supported");
        }
    }

    /**
     * gets count
     * @return count, an integer
     */
    public int getCount(){
        return this.count;
    }

}