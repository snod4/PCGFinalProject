
public class TypeCount extends Type {

    private int count;
    String booleanOp;

    public TypeCount(int type, int count, String booleanOp) {
        super(type, false);
        this.count = count;
        this.booleanOp = booleanOp;

    }

    public boolean compare(int val){
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

    public int getCount(){
        return this.count;
    }

}