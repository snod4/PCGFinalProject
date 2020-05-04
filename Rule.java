import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class that contains rule information
 */
public class Rule {
    public static final int RULE_FAILED = -1;

    @SuppressWarnings("unchecked")
    ArrayList<Type>[] andSet; //assumes highest priority rules have key value of 1, with lower priority having higher values such as 2, 3, etc
    @SuppressWarnings("unchecked")
    ArrayList<Type>[] orSet; //assumes highest priority rules have key value of 1, with lower priority having higher values such as 2, 3, etc

    private int result; //result to be applied if rule is true
    private int requiredType; //required type of cell to apply rule

    //initalizes rule with  no required type
    public Rule(ArrayList<Type>[] andSet,  ArrayList<Type>[] orSet, int result){
        this.andSet = andSet;
        this.orSet = orSet;
        this.result = result;
        this.requiredType = Cell.NO_TYPE;
        if(this.andSet.length != this.orSet.length){
            throw new Error("Initialized Arrays need to be the same length");
        }
    }
    //initalizes rule with required type
    public Rule(ArrayList<Type>[] andSet,  ArrayList<Type>[] orSet, int result, int requiredType){
        this.andSet = andSet;
        this.orSet = orSet;
        this.result = result;
        this.requiredType = requiredType;
        if(this.andSet.length != this.orSet.length){
            throw new Error("Initialized Arrays need to be the same length");
        }
    }

        /**
         * Applies rule to cell. 
         * @param relativeCells - the surrounding cells, with index 4 being the cell being checked
         * @return this.result if all rules are passed otherswise RULE_FAILED
         */
    public int applyRule(int[] relativeCells){

        //checks to see if rule has required type and if cell matches type
        if(requiredType != Cell.NO_TYPE && requiredType != relativeCells[4]){
            return RULE_FAILED;
        }

        Type currentType;
        //counts the types surrouding the checked cell
        int solid = 0;
        int floor = 0;
        int empty = 0;
        for(int g = 0; g < relativeCells.length; g++){
            if(g != 4){
                switch(relativeCells[g]){
                    case Cell.SOLID:
                    solid++;
                    break;
                    case Cell.EMPTY:
                        empty++;
                        break;
                    case Cell.FLOOR:
                    floor++;
                        break;
                    default:
                        break;
                }
            }
            
        }
   
        //applies position rules and count rules according to and/or logic
        //goes through varying priority levels of and/or
        for(int priority = 0; priority < andSet.length; priority++){
            //Working through 'and' rules first - need all to be true
            if(andSet[priority] != null){
                for(int i = 0; i < andSet[priority].size(); i++){

                    currentType = andSet[priority].get(i);
                    //Count rule application
                    if(currentType instanceof TypeCount){
                        switch(((TypeCount) currentType).getType()){
                            case Cell.SOLID:
                                if(!((TypeCount) currentType).compare(solid)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.FLOOR:
                                if(!((TypeCount) currentType).compare(floor)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.EMPTY:
                                if(!((TypeCount) currentType).compare(empty)){
                                    return RULE_FAILED;
                                }
                                
                            break;
                        }
                    }
                    
                    //Position rule application
                    else{
                        if((relativeCells[((TypePosition) currentType).getPosition()] == currentType.getType() && currentType.notBooleanIsTrue())
                            || (relativeCells[((TypePosition) currentType).getPosition()] != currentType.getType() && !currentType.notBooleanIsTrue())){
                            return RULE_FAILED;
                        }
                    }
                }
            }
            //Working though 'or' rules -- only need one to evaluate to true
            if(orSet[priority] != null){
                Outerloop:
                for(int i = 0; i < orSet[priority].size(); i++){

                    currentType = orSet[priority].get(i);
                    //Count rule application
                    if(currentType instanceof TypeCount){
                        switch(((TypeCount) currentType).getType()){
                            case Cell.SOLID:
                                if(!((TypeCount) currentType).compare(solid)){
                                    break Outerloop;
                                }
                            break;
                            case Cell.FLOOR:
                                if(!((TypeCount) currentType).compare(floor)){
                                    break Outerloop;
                                }
                            break;
                            case Cell.EMPTY:
                                if(!((TypeCount) currentType).compare(empty)){
                                    break Outerloop;
                                }   
                            break;
                        }   
                    }
                
                    //Position rule application
                    else if((relativeCells[((TypePosition) currentType).getPosition()] == currentType.getType() && !currentType.notBooleanIsTrue()) 
                        || (relativeCells[((TypePosition) currentType).getPosition()] != currentType.getType() && currentType.notBooleanIsTrue())){
                        break Outerloop;
                    }
                    //Statement evaluates to true if no 'or' rule evaluates to true
                    if(i == orSet[priority].size() - 1){
                        return RULE_FAILED;
                    }
                }
            }   
        }
        return result;

    }



}