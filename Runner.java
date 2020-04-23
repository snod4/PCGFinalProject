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
        TypeCount rule1 = new TypeCount(Cell.SOLID, 5, ">=");
        andSet[0].add(rule1);
        Rule rule = new Rule(andSet, orSet, Cell.SOLID);
        rules.add(rule);
        Automata firstTest = new Automata(img.getWidth(), img.getHeight(), 20, rules);
        firstTest.initialize(1, 1, 8);
        firstTest.evaluateAutomata(4);
       // firstTest.applyEvaluationToImage();
       // Utility.SaveImage(img, ".\\Images\\Testing123.png");
    

    }
}