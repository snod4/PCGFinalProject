
public class Type {

    protected int type;
    protected boolean not;

    public Type(int type, boolean not){
        this.type = type;
        this.not = not;

    }

    public boolean notBooleanIsTrue(){
        return this.not;
    }
    
    public int getType(){
        return this.type;
    }
}