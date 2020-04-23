
public class TypePosition extends Type{
    public static final int TOP_LEFT = 0;
    public static final int TOP = 1;
    public static final int TOP_RIGHT = 2;
    public static final int LEFT = 3;
    //skips midde entry because that is the object being operated on, hence no 4
    public static final int RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM = 7;
    public static final int BOTTOM_RIGHT = 8;

    private int position;
   
    // private int priority;

    public TypePosition(int type, int position, boolean not){
        super(type,not);
        this.position = position;

      //  this.priority = priority;
    }


    public int getPosition(){
        return this.position;
    }


    
}