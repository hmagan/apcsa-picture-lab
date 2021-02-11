import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    int count = 0;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
        count++;
      }
    }
    System.out.println("mirrorTemple iterated " + count + " times.");
  }
  
  public void mirrorArms()
  {
      Pixel[][] pixels = this.getPixels2D();
      int mirrorPoint = 192;
      //left arm
      for(int i = 158; i < mirrorPoint; i++){
          for(int j = 104; j < 170; j++){
              pixels[mirrorPoint + (mirrorPoint - i)][j].setColor(pixels[i][j].getColor());
          }
      }
      mirrorPoint = 197;
      //right arm
      for(int i = 171; i < mirrorPoint; i++){
          for(int j = 239; j < 295; j++){
              //subtract 10 to move the arm up 10 pixels
              //prevents arm from looking like it's hanging off
              pixels[mirrorPoint + (mirrorPoint - i) - 10][j].setColor(pixels[i][j].getColor());
          }
      }
  }
  
  public void mirrorGull()
  {
      Pixel[][] pixels = this.getPixels2D();
      int mirrorPoint = 345;
      for(int i = 234; i < 322; i++){
          for(int j = 238; j < mirrorPoint; j++){
              pixels[i][mirrorPoint + (mirrorPoint - j)].setColor(pixels[i][j].getColor());
          }
      }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(int i = 0; i < pixels.length; i++){
        for(int j = 0; j < pixels[i].length; j++){
            pixels[i][j].setRed(0);
            pixels[i][j].setGreen(0);
        }
    }
  }
  
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(int i = 0; i < pixels.length; i++){
        for(int j = 0; j < pixels[i].length; j++){
            Color pixColor = pixels[i][j].getColor();
            pixels[i][j].setRed(255 - pixColor.getRed());
            pixels[i][j].setGreen(255 - pixColor.getGreen());
            pixels[i][j].setBlue(255 - pixColor.getBlue());
        }
    }
  }
  
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(int i = 0; i < pixels.length; i++){
        for(int j = 0; j < pixels[i].length; j++){
            int average = (int) pixels[i][j].getAverage();
            pixels[i][j].setRed(average);
            pixels[i][j].setGreen(average);
            pixels[i][j].setBlue(average);
        }
    }
  }
  
  public void mirrorVerticalRightToLeft()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(int i = 0; i < pixels.length; i++){
          for(int j = 0; j < pixels[i].length/2; j++){
              pixels[i][j].setColor(pixels[i][pixels[i].length-1-j].getColor());
          }
      }
  }
  
  public void mirrorHorizontal()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(int i = 0; i < pixels.length/2; i++){
          for(int j = 0; j < pixels[i].length; j++){
              pixels[pixels.length-1-i][j].setColor(pixels[i][j].getColor());
          }
      }
  }
  
  public void mirrorHorizontalBotToTop()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(int i = 0; i < pixels.length/2; i++){
          for(int j = 0; j < pixels[i].length; j++){
              pixels[i][j].setColor(pixels[pixels.length-1-i][j].getColor());
          }
      }
  }
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
