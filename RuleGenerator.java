import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Creates text prompt for making rules for cellular automata
 */
public class RuleGenerator {
   
    public RuleGenerator(){

    }
    /**
     * Gets  rules from text input
     * @return ArrayList of Rule objects
     */
    public static ArrayList<Rule> getStringInput(){
        
        ArrayList<Rule> temp = getStringInputHelper();
        return temp;

    }
    /**
     * Gets rules from terminal input
     * @return ArrayList of Rule objects
     */
    private static ArrayList<Rule> getStringInputHelper(){
        //Opens terminal scanner and preps text scanner -- called scan
        Scanner scanTerminal = new Scanner(System.in);
        String input;
        Scanner scan = null;
        
        
        ArrayList<Rule> rules = new ArrayList<Rule>();
        boolean continueAddingRules = true;

        //loop that lets multiple rules be added
        while(continueAddingRules){
            try{
                //generates andSet and orSet data structures for parts of rules
                ArrayList<Type>[] andSet = new ArrayList[5];
                ArrayList<Type>[] orSet = new ArrayList[5];

                for(int i = 0; i < andSet.length; i++){
                    andSet[i] = new ArrayList<Type>();
                    orSet[i] = new ArrayList<Type>();
                }

                //starts the terminal input
                System.out.println("[Input rule parts in order of priority staring with the highest priority, press enter when done with a priority level]\n");
                //Loop for differing priority of rule parts 
                //Example (SOLID == 2 && EMPTY > 3) || FLOOR > 2 -- FLOOR > 2 is highest priority
                Outerloop:
                for(int i = 0; i < andSet.length; i++){
                    //Gets termnial input
                    System.out.println("Input rule as either SOLID, FLOOR, EMPTY  ==, <=, <, >=, >, !=  NUMBER to make a couting rule\n\nInput SOLID, FLOOR, EMPTY  ==, !=  TOP_RIGHT, TOP, TOP_LEFT, LEFT, RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT to make a postion rule\n\nCan combine rule parts using and/or\n");
                    input = getInput(scanTerminal);
                    String andOr = null;

                    //parses terminal input
                    scan = new Scanner(input);
                    //parses terminal input by splitting statements with and/or
                    do{
                        String blockType = scan.next();
                        int blockTypeNum;
                        //Checks block type of statement
                        if(blockType.equalsIgnoreCase("solid")){
                            blockTypeNum = Cell.SOLID;
                        }
                        else if(blockType.equalsIgnoreCase("floor")){
                            blockTypeNum = Cell.FLOOR;
                        }
                        else if(blockType.equalsIgnoreCase("empty")){
                            blockTypeNum = Cell.EMPTY;
                        }
                        else{
                            throw new Exception("BLOCK TYPE NOT CORRECT");
                        }

                        String comparator = scan.next();
                        String element = scan.next();
                        boolean typeCount = true;
                        int numElement = Integer.MIN_VALUE;
                        //checks if element of statement is number or not to determine if it is a counting or position rule
                        try{
                        numElement = Integer.parseInt(element);
                        }
                        catch(NumberFormatException e){
                            typeCount = false;
                        }
                        if(typeCount){
                            //TypeCount
                            //checks to ensure comparator given is supported
                            if (comparator.equals("==") 
                            || comparator.equals("<") 
                            || comparator.equals("<=") 
                            || comparator.equals(">") 
                            ||comparator.equals(">=") 
                            || comparator.equals("!=")) {
                                
                                //checks for and/or
                                if(scan.hasNext()){
                                    //if no and/or found previously, categorize rule part by the one found, otherwise use the previous one
                                    if(andOr == null){
                                        andOr = scan.next();
                                        
                                        if(andOr.equalsIgnoreCase("and")){
                                        andSet[i].add(new TypeCount(blockTypeNum, numElement, comparator));
                                        }
                                        else if(andOr.equalsIgnoreCase("or")){
                                            orSet[i].add(new TypeCount(blockTypeNum, numElement, comparator));
                                        }
                                        else{
                                            throw new Exception("String conjunction not and/or");
                                        }
                                    }
                                    else{
                                        if(andOr.equalsIgnoreCase("and")){
                                            andSet[i].add(new TypeCount(blockTypeNum, numElement, comparator));
                                            }
                                        else if(andOr.equalsIgnoreCase("or")){
                                            orSet[i].add(new TypeCount(blockTypeNum, numElement, comparator));
                                        }
                                        else{
                                            throw new Exception("String conjunction not and/or");
                                        }
                                        //gets the and/or to know category of next rule part
                                        andOr = scan.next();
                                    }
                                }
                                else{
                                    andSet[i].add(new TypeCount(blockTypeNum, numElement, comparator));
                                    System.out.println("\n[TypeCount Rule Generated]\n");
                                }

                            } 
                            else {
                                throw new Exception("This boolean operator is not supported");
                            }
                    
                        }
                        else{
                            //TypePosition
                            int positionVal;
                            boolean not;
                            if(comparator.equalsIgnoreCase("==")){
                                not = false;
                            }
                            else if(comparator.equalsIgnoreCase("!=")){
                                not = true;
                            }
                            else{
                                throw new Exception("This boolean operator is not supported");
                            }

                            //Converts text position to computational representation
                            if(element.equalsIgnoreCase("TOP_LEFT")){
                                positionVal = TypePosition.TOP_LEFT;
                            }
                            else if(element.equalsIgnoreCase("TOP")){
                                positionVal = TypePosition.TOP;
                            }
                            else if(element.equalsIgnoreCase("TOP_RIGHT")){
                                positionVal = TypePosition.TOP_RIGHT;
                            }
                            else if(element.equalsIgnoreCase("LEFT")){
                                positionVal = TypePosition.LEFT;
                            }
                            else if(element.equalsIgnoreCase("RIGHT")){
                                positionVal = TypePosition.RIGHT;
                            }
                            else if(element.equalsIgnoreCase("BOTTOM_LEFT")){
                                positionVal = TypePosition.BOTTOM_LEFT;
                            }
                            else if(element.equalsIgnoreCase("BOTTOM")){
                                positionVal = TypePosition.BOTTOM;
                            }
                            else if(element.equalsIgnoreCase("BOTTOM_RIGHT")){
                                positionVal = TypePosition.BOTTOM_RIGHT;
                            }
                            else{
                                throw new Exception("Positon type not supported");
                            }
                            //checks and/or
                            if(scan.hasNext()){
                                //if no and/or found previously, categorize rule part by the one found, otherwise use the previous one
                                if(andOr == null){
                                andOr = scan.next();
                                
                                    if(andOr.equalsIgnoreCase("and")){
                                        andSet[i].add(new TypePosition(blockTypeNum, positionVal, not));
                                    }
                                    else if(andOr.equalsIgnoreCase("or")){
                                        orSet[i].add(new TypePosition(blockTypeNum, positionVal, not));
                                    }
                                    else{
                                        throw new Exception("String conjunction not and/or");
                                    }
                                }
                                else{
                                    if(andOr.equalsIgnoreCase("and")){
                                        andSet[i].add(new TypePosition(blockTypeNum, positionVal, not));
                                        }
                                    else if(andOr.equalsIgnoreCase("or")){
                                        orSet[i].add(new TypePosition(blockTypeNum, positionVal, not));
                                    }
                                    else{
                                        throw new Exception("String conjunction not and/or");
                                    }
                                    //gets the and/or to know category of next rule part
                                    andOr = scan.next();
                                }
                            }
                            else{
                                andSet[i].add(new TypePosition(blockTypeNum, positionVal, not));
                                System.out.println("\n[TypePostion Rule Generated]\n");
                            }

                        }
                    
                    } while(scan.hasNext());
                    
                    //Lets user add more rule parts or lets them continue to finish the rule
                    if(i < andSet.length - 1){
                    System.out.println("To input another part with lesser priority, type add, otherwise type continue or c to finish the rule \n");
                    String s;
                    if((s = getInput(scanTerminal)).equalsIgnoreCase("continue") || s.equalsIgnoreCase("c")) {
                        break Outerloop;
                    }
                    
                    }
                    else{
                        System.out.println("\nPriority limit reached. Exiting\n");
                        break;
                    }

                }

                //Finishes rule by adding result of rule and required block type for rule to take effect
                while(continueAddingRules){
                    try{
                        System.out.println("\nInput resulting cell type from rule (SOLID, FLOOR, EMPTY)\n");
                        String resultType = getInput(scanTerminal);
                        int resultTypeNum;
                        System.out.println("\nInput requried cell type for rule to apply (ANY, SOLID, FLOOR, EMPTY)\n");
                        String cellType = getInput(scanTerminal);
                        int cellTypeNum;
                        //sets result
                        if(resultType.equalsIgnoreCase("solid")){
                            resultTypeNum = Cell.SOLID;
                        }
                        else if(resultType.equalsIgnoreCase("floor")){
                            resultTypeNum = Cell.FLOOR;
                        }
                        else if(resultType.equalsIgnoreCase("empty")){
                            resultTypeNum = Cell.EMPTY;
                        }
                        else{
                            throw new InputMismatchException("result type not possible");
                        }
                        //sets required type
                        if(cellType.equalsIgnoreCase("solid")){
                            cellTypeNum = Cell.SOLID;
                        }
                        else if(cellType.equalsIgnoreCase("floor")){
                            cellTypeNum = Cell.FLOOR;
                        }
                        else if(cellType.equalsIgnoreCase("empty")){
                            cellTypeNum = Cell.EMPTY;
                        }
                        else if(cellType.equalsIgnoreCase("any")){
                            cellTypeNum = Cell.NO_TYPE;
                        }
                        else{
                            throw new InputMismatchException("required type type not possible");
                        }
                        //creates rule
                            rules.add(new Rule(andSet, orSet, resultTypeNum, cellTypeNum));
                            System.out.println("\n[Rule Created]\n");
                            System.out.println("Make another rule? (y/n)\n");
                            if(!getInput(scanTerminal).equals("y")){
                                continueAddingRules = false;
                            }
                           
                            break;
                        
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage() + " Please Try Again");
                        
                    }
                }
                
            }
            catch(Exception e){
                
                System.out.println(e.getMessage() + ". Please try again");
                
                while(scan.hasNext()){
                    scan.nextLine();
                }
            }
        }
        scan.close();
        scanTerminal.close();
        return rules;
    }

    /**
     * Gets input and stylizes line
     * @param scan, a Scanner object for terminal
     * @return String, terminal input
     */
    private static String getInput(Scanner scan){
        System.out.print("> ");
        return scan.nextLine().trim();

    }
}