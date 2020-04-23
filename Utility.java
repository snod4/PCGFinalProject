/**
 * @author Leonard Maxim
 * 
 * Procedural Content Generatio @ DIS Copenhagen
 *
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
class Utility
{
	/**
	   * This method is used to draw lines between the yVals[] values starting from (startX, startY) using a Turtle  
	   * the x will increase constantly by  xIncrement 
	   * 
	   * |		*        *              |	   *        * 
	   * |                    *         |     / \      / \   *
	   * |*   *   *    *           -->  |*___*   *____*   \ /
	   * |                  *           |                  *
	   * |_______________________       |_______________________
	   * 
	   * @param turtle Instance of the turtle used for drawing
	   * @param yVals[]  An array of all the y values that have to be plotted
	   * @param xIncrement The amount by which the x value will change for each y value (if the value is small, the points will be very close together)
	   * @param startX Starting x position of the graph 
	   * @param startY Starting y position of the graph
	   * @param color color of the graph
	   */
	public static void DrawGraph(Turtle turtle, int yVals[], int xIncrement, int startX, int startY, Color color)
	{
		  int currentX = startX;
		  
		  for(int i=0; i<yVals.length - 1; i++)
		  {
			  DrawLine(turtle, currentX , startY + yVals[i], currentX + xIncrement, startY + yVals[i+1], color);
			  currentX += xIncrement;
		  }
	}

	/**
	 * fillImage: colors in an image with a provided color
	 * @param img an instance of BufferedImage
	 * @param color an instance of Color to color img
	 */
	public static void fillImage(BufferedImage img, Color color){
		for(int r = 0; r < img.getHeight(); r++){
			for(int c = 0; c < img.getWidth(); c++){
				img.setRGB(r, c, color.getRGB());
			}
		}

	}
	
	/**
	   * This method is used to draw lines between the yVals[] values starting from (startX, startY) on a provided BufferedImage  
	   * the x will increase constantly by  xIncrement 
	   * 
	   * |		*        *              |	   *        * 
	   * |                    *         |     / \      / \   *
	   * |*   *   *    *           -->  |*___*   *____*   \ /
	   * |                  *           |                  *
	   * |_______________________       |_______________________
	   * 
	   * @param img Instance of the BufferedImage
	   * @param yVals[]  An array of all the y values that have to be plotted
	   * @param xIncrement The amount by which the x value will change for each y value (if the value is small, the points will be very close together)
	   * @param startX Starting x position of the graph 
	   * @param startY Starting y position of the graph
	   * @param color color of the graph
	   */
	public static void DrawGraph(BufferedImage img, int yVals[], int xIncrement, int startX, int startY, int thickness ,Color color)
	{
		  int currentX = startX;
		  
		  for(int i=0; i<yVals.length - 1; i++)
		  {
			  DrawLine(img, currentX ,  startY + yVals[i], currentX + xIncrement, startY + yVals[i+1], thickness,color);
			  currentX += xIncrement;
		  }
	}
	
	/**
	   * Draws a line between two points (fromX, fromY) and (toX, toY) using a Turtle 
	   * @param turtle Instance of the turtle used for drawing
	   * @param fromX  X coordinate of the starting point
	   * @param fromY  Y coordinate of the starting point
	   * @param toX  X coordinate of the destination point 
	   * @param toY  Y coordinate of the destination point
	   * @param color  Color of the line - the turtle will reset to black after drawing
	   */
	 public static void DrawLine(Turtle turtle ,double fromX, double fromY, double toX, double toY, Color color)
	  {
		  turtle.penColor(color);
		  turtle.up();
		  turtle.setPosition(fromX, fromY);
		  turtle.down();
		  turtle.setPosition(toX, toY);
		  turtle.penColor(Color.black);
	  }
	 
	 /**
	   * Draws a line between two points (fromX, fromY) and (toX, toY) in a given BufferedImage.
	   *
	   * @param img Image used for drawing
	   * @param fromX  X coordinate of the starting point
	   * @param fromY  Y coordinate of the starting point
	   * @param toX  X coordinate of the destination point 
	   * @param toY  Y coordinate of the destination point
	   * @param thickness thickness of line
	   * @param color  Color of the line
	   */
	 public static void DrawLine(BufferedImage img, double fromX, double fromY, double toX, double toY, int thickness, Color color)
	  {
		 Point2D.Double fromVec = new Point2D.Double(fromX, img.getHeight() - fromY - 1);
		 Point2D.Double toVec = new Point2D.Double(toX, img.getHeight() - toY);
		 
		 Graphics2D g2d = img.createGraphics();
         g2d.setColor(color);
		 g2d.setStroke(new BasicStroke(thickness));
         
         g2d.drawLine((int)fromVec.x, (int)fromVec.y, (int)toVec.x, (int)toVec.y);
	  }
	 
	 /**
	  * Creates a filled rectangle within a BufferedImage.
	  * Starts from bottom left - (0, 0)
	  * 
	  * @param img - image to be modified
	  * @param startX - X starting position
	  * @param startY - Y starting position
	  * @param width - width
	  * @param height - height
	  * @param c - color
	  * **/
	 static void DrawFilledRect(BufferedImage img, int startX, int startY, int width, int height,Color c)
	  {
		 Point2D.Double startVec = new Point2D.Double(startX, img.getHeight() - startY - 1);

		 Graphics2D g2d = img.createGraphics();
         g2d.setColor(c);
         g2d.fillRect((int)startVec.x, (int)startVec.y - height, width, height);

	  }
	 
	 /**
	  * Creates an empty rectangle within a BufferedImage.
	  * Starts from bottom left - (0, 0)
	  * 
	  * @param img - image to be modified
	  * @param startX - X starting position
	  * @param startY - Y starting position
	  * @param width - width
	  * @param height - height
	  * @param thickness - thickness of the rectangle bounds
	  * @param c - color of the rectangle bounds
	  * **/
	 static void DrawEmptyRect(BufferedImage img, int startX, int startY, int width, int height, int thickness,Color c)
	  {
		 Point2D.Double startVec = new Point2D.Double(startX, img.getHeight() - startY - 1);

		 Graphics2D g2d = img.createGraphics();
		 g2d.setStroke(new BasicStroke(thickness));
         g2d.setColor(c);
         g2d.drawRect((int)startVec.x, (int)startVec.y - height, width, height);

	  }
	 /**
	  * Writes image in folder with specified name. 
	  * Images will be saved as PNG
	  *
	  *   @param img - image to be created
	  *   @param url - path to the folder in which the image will be saved including name and file extension

	  * **/
	 static void SaveImage(BufferedImage img, String url)
	 {
		 File f = new File(url);
	     try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * Returns an a buffered image from path
	  * 
	  *   @param url - path to the folder where the image is located
	  *   (don't forget to add the file extension)
	  * **/
	 static BufferedImage LoadImage(String url)
	 {
		 BufferedImage img = null;
			
			try {
			   img = ImageIO.read(new File(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return img;
	 }
	 
	 /**
	  * Maps a number from one range to another
	  * @param value - value that has to be remapped
	  * @param istart - lower bound of first range
	  * @param istop - upper bound of second range
	  * @param ostart - lower bound of first range
	  * @param ostop - upper bound of second range
	  * **/
	 static public final double map(double value, double istart, double istop, double ostart, double ostop) 
	 {
		 return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	 }
	 
}