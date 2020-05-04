/**
 * Contains basic type information
 */
public class Type {

    protected int type;
    protected boolean not; // same as !

    public Type(int type, boolean not){
        this.type = type;
        this.not = not;

    }
    //checks to see if ! is enabled or not
    public boolean notBooleanIsTrue(){
        return this.not;
    }
    
    public int getType(){
        return this.type;
    }
}