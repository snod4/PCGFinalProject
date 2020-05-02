import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class Automata {




    private Cell previousCells[][];
    private Cell newCells[][];
    private int imgWidth, imgHeight;
    private int cellSize;

    // Constructors



    public Automata(int imgWidth, int imgHeight, int cellSize) {

        if (imgWidth % cellSize != 0 || imgHeight % cellSize != 0) {
            throw new Error("Cell Size does not divide evenly into image dimensions");
        }

        previousCells = new Cell[imgHeight/cellSize][imgWidth/cellSize];
        newCells = new Cell[imgHeight/cellSize][imgWidth/cellSize];
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.cellSize = cellSize;

    }

    public Automata(int imgWidth, int imgHeight, int cellSize, ArrayList<Rule> rules) {
        Cell.setRules(rules);

        if (imgWidth % cellSize != 0 || imgHeight % cellSize != 0) {
            throw new Error("Cell Size does not divide evenly into image dimensions");
        }

        previousCells = new Cell[imgHeight/cellSize][imgWidth/cellSize];
        newCells = new Cell[imgHeight/cellSize][imgWidth/cellSize];
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.cellSize = cellSize;
        
    }

    public void initialize(int solidChance, int floorChance, int emptyChance){
        Random rand = new Random(3);
        for(int r = 0; r < previousCells.length; r++){
            for(int c = 0; c < previousCells[r].length; c++){
                

                int probabiltiy = rand.nextInt(solidChance + floorChance + emptyChance + 1);
                System.out.println(probabiltiy);
                //draws solid border around image
                if(r == 0 || c == 0 || r == previousCells.length-1 || c == previousCells[r].length - 1){
                    previousCells[r][c] = new Cell(Cell.SOLID);
                    newCells[r][c] = new Cell(Cell.SOLID);
                }
                //initalizes each cell to a wall, floor, or empty space 
                else if(solidChance + floorChance + emptyChance > 10){
                 throw new Error("Sum of chance of solid, floor, and empty is greater than 10");
                }
               else if(probabiltiy <= solidChance){
                    previousCells[r][c] = new Cell(Cell.SOLID);
                    newCells[r][c] = new Cell(Cell.SOLID);
                }
                 else if(probabiltiy > solidChance && probabiltiy <= solidChance + floorChance){
                    previousCells[r][c] = new Cell(Cell.FLOOR);
                    newCells[r][c] = new Cell(Cell.FLOOR);
                 }
                else if(probabiltiy > solidChance + floorChance && probabiltiy <= solidChance + floorChance + emptyChance){
                    previousCells[r][c] = new Cell(Cell.EMPTY);
                    newCells[r][c] = new Cell(Cell.EMPTY);
                }
                else{
                    System.out.println("Error " + probabiltiy);
                }
            }
            
        }
        applyEvaluationToImage("initialization", previousCells);
        System.out.println("Initialization Complete");
    }

    public void addRule(Rule rule){
        Cell.addRule(rule);
    }

    public void evaluateAutomata(int iterations){

    
        for(int i = 0; i < iterations; i++){
            for(int r = 0; r < previousCells.length; r++){
               for(int c = 0; c < previousCells[r].length; c++){
                   previousCells[r][c].evaluateSurroundingCells(previousCells, newCells, r, c);
                }
            }
        }

        System.out.println("Evaluation Complete");

        applyEvaluationToImage("testingImg", newCells);
    }

    public void applyEvaluationToImage(String name, Cell[][] cells){
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Utility.fillImage(img, Color.GREEN);
        int imgRow = 0, imgCol = 0;
        int r = 0, c = 0;
        for( r = 0; r < cells.length; r++){
            for( c = 0; c < cells[r].length; c++){
                 imgRow = cellSize * (cells.length - r - 1); //accounts for Utility class flipping of row variable
                 imgCol = cellSize * c;
                if(cells[r][c].isModified()){
                    System.out.println("This cell at (" + r + ", " + c + ") was modified to " + cells[r][c].getType());
                }
                if(cells[r][c].isType(Cell.SOLID)){
                    System.out.println("This cell at (" + r + ", " + c + ") was colored black");
                    Utility.DrawFilledRect(img, imgCol, imgRow, cellSize, cellSize, Color.BLACK);
                }
                else if(cells[r][c].isType(Cell.FLOOR)){
                    Utility.DrawFilledRect(img, imgCol, imgRow, cellSize, cellSize, Color.RED);
                }
                else if(cells[r][c].isType(Cell.EMPTY)){
                    Utility.DrawFilledRect(img, imgCol, imgRow, cellSize, cellSize, Color.WHITE);
                }

                
            }
        }
        // if(name.equals("testingImg")){
        //     System.out.println("Placing test square at previousCells (" + r + ", " + c + ") at image Location  (" + imgRow + "," + imgCol + ")");
        // Utility.DrawFilledRect(img, imgCol, imgRow, cellSize, cellSize, Color.GREEN);
        // }
        Random rand = new Random(System.currentTimeMillis());
        Utility.SaveImage(img, ".\\Images\\"+Integer.toString(rand.nextInt()) + ".png");

      Utility.SaveImage(img, ".\\Images\\" + name + ".png");
      System.out.println("Finished");
    }

    



//Methods


}