import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Rule {
    public static final int RULE_FAILED = -1;

    @SuppressWarnings("unchecked")
    ArrayList<Type>[] andSet; //assumes highest priority rules have key value of 1, with lower priority having higher values such as 2, 3, etc
    @SuppressWarnings("unchecked")
    ArrayList<Type>[] orSet;
    private int result;
    private int requiredType;
    public Rule(ArrayList<Type>[] andSet,  ArrayList<Type>[] orSet, int result){
        this.andSet = andSet;
        this.orSet = orSet;
        this.result = result;
        this.requiredType = Cell.NO_TYPE;
        if(this.andSet.length != this.orSet.length){
            throw new Error("Initialized Arrays need to be the same length");
        }
    }

    public Rule(ArrayList<Type>[] andSet,  ArrayList<Type>[] orSet, int result, int requiredType){
        this.andSet = andSet;
        this.orSet = orSet;
        this.result = result;
        this.requiredType = requiredType;
        if(this.andSet.length != this.orSet.length){
            throw new Error("Initialized Arrays need to be the same length");
        }
    }

    public int applyRule(int[] relativeCells){
        System.out.println("Applying Rule");
        if(requiredType != Cell.NO_TYPE && requiredType != relativeCells[4]){
            return RULE_FAILED;
        }
        Type currentType;
        int solid = 0;
        int floor = 0;
        int empty = 0;
        for(int g = 0; g < relativeCells.length; g++){
            if(g == 4){}
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
        System.out.println("Empty:" + empty);
        for(int priority = 0; priority < andSet.length; priority++){
            if(andSet[priority] != null){
                for(int i = 0; i < andSet[priority].size(); i++){

                    currentType = andSet[priority].get(i);
                    if(currentType instanceof TypeCount){
                        switch(((TypeCount) currentType).getType()){
                            case Cell.SOLID:
                                System.out.println("CASE SOLID");
                                if(!((TypeCount) currentType).compare(solid)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.FLOOR:
                                System.out.println("CASE FLOOR");
                                if(!((TypeCount) currentType).compare(floor)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.EMPTY:
                                System.out.println("CASE EMPTY");
                                if(!((TypeCount) currentType).compare(empty)){
                                    return RULE_FAILED;
                                }
                                
                            break;
                        }
                    }
                    
                
                    else{
                        if((relativeCells[((TypePosition) currentType).getPosition()] == currentType.getType() && currentType.notBooleanIsTrue())
                            || (relativeCells[((TypePosition) currentType).getPosition()] != currentType.getType() && !currentType.notBooleanIsTrue())){
                            return RULE_FAILED;
                        }
                    }
                }
            }
            if(orSet[priority] != null){
                Outerloop:
                for(int i = 0; i < orSet[priority].size(); i++){

                    currentType = orSet[priority].get(i);
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
                
                
                    else if((relativeCells[((TypePosition) currentType).getPosition()] == currentType.getType() && !currentType.notBooleanIsTrue()) 
                        || (relativeCells[((TypePosition) currentType).getPosition()] != currentType.getType() && currentType.notBooleanIsTrue())){
                        break Outerloop;
                    }
                    if(i == orSet[priority].size() - 1){
                        return RULE_FAILED;
                    }
                }
            }   
        }
        System.out.println("Success - Returning Result: " + result);
        return result;

    }



}