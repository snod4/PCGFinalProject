import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Utilizes cellular automota to generate dungeons
 */
public class Automata {

    private Cell previousCells[][]; // cells that evaluated
    private Cell newCells[][]; // cells resulting from the application
    private int imgWidth, imgHeight;
    private int cellSize;

    // Constructors

    // ruleless initialization
    public Automata(int imgWidth, int imgHeight, int cellSize) {
        // ensures cells fit evenly in image
        if (imgWidth % cellSize != 0 || imgHeight % cellSize != 0) {
            throw new Error("Cell Size does not divide evenly into image dimensions");
        }

        previousCells = new Cell[imgHeight / cellSize][imgWidth / cellSize];
        newCells = new Cell[imgHeight / cellSize][imgWidth / cellSize];
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.cellSize = cellSize;

    }

    // initialization with rules
    public Automata(int imgWidth, int imgHeight, int cellSize, ArrayList<Rule> rules) {
        Cell.setRules(rules);
        // ensures cells fit evenly in image
        if (imgWidth % cellSize != 0 || imgHeight % cellSize != 0) {
            throw new Error("Cell Size does not divide evenly into image dimensions");
        }

        previousCells = new Cell[imgHeight / cellSize][imgWidth / cellSize];
        newCells = new Cell[imgHeight / cellSize][imgWidth / cellSize];
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.cellSize = cellSize;

    }

    /**
     * Initializes the cells using random generation
     * 
     * @param solidChance - chance for solid cell to be generated
     * @param floorChance - chance for floor cell to be generated
     * @param emptyChance - chance for empty cell to be generated
     */
    public void initialize(int solidChance, int floorChance, int emptyChance) {
        Random rand = new Random(System.currentTimeMillis());
        // goes through cells
        for (int r = 0; r < previousCells.length; r++) {
            for (int c = 0; c < previousCells[r].length; c++) {

                int probabiltiy = rand.nextInt(solidChance + floorChance + emptyChance + 1);

                // draws solid border around image
                if (r == 0 || c == 0 || r == previousCells.length - 1 || c == previousCells[r].length - 1) {
                    previousCells[r][c] = new Cell(Cell.SOLID);
                    newCells[r][c] = new Cell(Cell.SOLID);
                }
                // initalizes each cell to a wall, floor, or empty space
                else if (solidChance + floorChance + emptyChance > 10) {
                    throw new Error("Sum of chance of solid, floor, and empty is greater than 10");
                } else if (probabiltiy <= solidChance) {
                    previousCells[r][c] = new Cell(Cell.SOLID);
                    newCells[r][c] = new Cell(Cell.SOLID);
                } else if (probabiltiy > solidChance && probabiltiy <= solidChance + floorChance) {
                    previousCells[r][c] = new Cell(Cell.FLOOR);
                    newCells[r][c] = new Cell(Cell.FLOOR);
                } else if (probabiltiy > solidChance + floorChance
                        && probabiltiy <= solidChance + floorChance + emptyChance) {
                    previousCells[r][c] = new Cell(Cell.EMPTY);
                    newCells[r][c] = new Cell(Cell.EMPTY);
                } else {
                    System.out.println("Error " + probabiltiy + " not in range");
                }
            }

        }
        // gives us an image of initialized space
        applyEvaluationToImage("initialization", previousCells);
        System.out.println("Initialization Complete");
    }

    /**
     * Add rule to algorithm
     * 
     * @param rule, a Rule object
     */
    public void addRule(Rule rule) {
        Cell.addRule(rule);
    }

    /**
     * runs the cellular automata algorithm, evaluating the cells
     * 
     * @param iterations - number of evaluations
     */
    public void evaluateAutomata(int iterations) {

        for (int i = 0; i < iterations; i++) {
            for (int r = 0; r < previousCells.length; r++) {
                for (int c = 0; c < previousCells[r].length; c++) {
                    // evaluates indivdual cells
                    previousCells[r][c].evaluateSurroundingCells(previousCells, newCells, r, c);
                    
               }
            }

            for(int r = 0; r < previousCells.length; r++){
                for(int c = 0; c < previousCells[r].length; c++){
                     //evaluates indivdual cells
                     previousCells[r][c] = new Cell(newCells[r][c].getType());
 
                }
             }

            
            
        }

        System.out.println("Evaluation Complete");
        //gets image of the resulting space
        applyEvaluationToImage("Result", newCells);
    }

    /**
     * applies cells to image
     * @param name - name of image
     * @param cells - space of Cell objects
     */
    public void applyEvaluationToImage(String name, Cell[][] cells){
        //generates image
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        //need to change coordinates since Utility's (0,0) is bottom left corner 
        int imgRow = 0, imgCol = 0; 
        //coordinates of cells
        int r = 0, c = 0;
        for(r = 0; r < cells.length; r++){
            for( c = 0; c < cells[r].length; c++){
                 imgRow = cellSize * (cells.length - r - 1); //accounts for Utility class flipping of row variable
                 imgCol = cellSize * c;
 
                 //Fills in picture according to cell type
                if(cells[r][c].isType(Cell.SOLID)){
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
        
        //saves image
        Utility.SaveImage(img, ".\\Images\\"+name + ".png");
        System.out.println("Finished");
    }

    



//Methods


}