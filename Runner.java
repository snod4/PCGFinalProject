import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Runner {



    
    public static void main(String args[]){

        System.out.println("Hello World");
        BufferedImage img = new BufferedImage(500,500, BufferedImage.TYPE_INT_RGB);
        Utility.fillImage(img, Color.GREEN);
        ArrayList<Type>[] andSet = new ArrayList[5];
        ArrayList<Type>[] orSet = new ArrayList[5];
        for(int i = 0; i < 5; i++){
            andSet[i] = new ArrayList<Type>();
            orSet[i] = new ArrayList<Type>();
        }
        ArrayList<Rule> rules = new ArrayList<>();
        TypeCount rule1 = new TypeCount(Cell.SOLID, 4, ">=");
        TypeCount rule2 = new TypeCount(Cell.EMPTY, 8, ">=");
        andSet[0].add(rule1);
      //  andSet[1].add(rule2);
        Rule rule = new Rule(andSet, orSet, Cell.SOLID);

        ArrayList<Type>[] andSet2 = new ArrayList[5];
        ArrayList<Type>[] orSet2 = new ArrayList[5];
        for(int i = 0; i < 5; i++){
            andSet2[i] = new ArrayList<Type>();
        }
        andSet2[0].add(rule2);
        
        Rule rules2 = new Rule(andSet2, orSet2, Cell.EMPTY);
        rules.add(rule);
        rules.add(rules2);
        Automata firstTest = new Automata(img.getWidth(), img.getHeight(), 20, rules);
        firstTest.initialize(2, 0, 8);
        firstTest.evaluateAutomata(5);
       // firstTest.applyEvaluationToImage();
       // Utility.SaveImage(img, ".\\Images\\Testing123.png");
    

    }
}