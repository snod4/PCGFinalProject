import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Rule {
    public static final int RULE_FAILED = -1;

    @SuppressWarnings("unchecked")
    ArrayList<Type>[] andSet = new ArrayList[5]; //assumes highest priority rules have key value of 1, with lower priority having higher values such as 2, 3, etc
    @SuppressWarnings("unchecked")
    ArrayList<Type>[] orSet = new ArrayList[5];
    private int result;
    public Rule(ArrayList<Type>[] andSet,  ArrayList<Type>[] orSet, int result){
        this.andSet = andSet;
        this.orSet = orSet;
        this.result = result;
    }

    public int applyRule(int[] relativeCells){
        System.out.println("Applying Rule");
        Type currentCell;
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
        System.out.println("Solid:" + solid);
        for(int priority = 0; priority < 5; priority++){
            if(andSet[priority] != null){
                for(int i = 0; i < andSet[priority].size(); i++){

                    currentCell = andSet[priority].get(i);
                    if(currentCell instanceof TypeCount){
                        switch(((TypeCount) currentCell).getType()){
                            case Cell.SOLID:
                                if(!((TypeCount) currentCell).compare(solid)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.FLOOR:
                                if(!((TypeCount) currentCell).compare(floor)){
                                    return RULE_FAILED;
                                }
                            break;
                            case Cell.EMPTY:
                                if(!((TypeCount) currentCell).compare(empty)){
                                    return RULE_FAILED;
                                }
                            break;
                        }
                    }
                    
                
                    else{
                        if((relativeCells[((TypePosition) currentCell).getPosition()] == currentCell.getType() && currentCell.notBooleanIsTrue())
                            || (relativeCells[((TypePosition) currentCell).getPosition()] != currentCell.getType() && !currentCell.notBooleanIsTrue())){
                            return RULE_FAILED;
                        }
                    }
                }
            }
            if(orSet[priority] != null){
                Outerloop:
                for(int i = 0; i < orSet[priority].size(); i++){

                    currentCell = orSet[priority].get(i);
                    if(currentCell instanceof TypeCount){
                        switch(((TypeCount) currentCell).getType()){
                            case Cell.SOLID:
                                if(!((TypeCount) currentCell).compare(solid)){
                                    break Outerloop;
                                }
                            break;
                            case Cell.FLOOR:
                                if(!((TypeCount) currentCell).compare(floor)){
                                    break Outerloop;
                                }
                            break;
                            case Cell.EMPTY:
                                if(!((TypeCount) currentCell).compare(empty)){
                                    break Outerloop;
                                }   
                            break;
                        }   
                    }
                
                
                    else if((relativeCells[((TypePosition) currentCell).getPosition()] == currentCell.getType() && !currentCell.notBooleanIsTrue()) 
                        || (relativeCells[((TypePosition) currentCell).getPosition()] != currentCell.getType() && currentCell.notBooleanIsTrue())){
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