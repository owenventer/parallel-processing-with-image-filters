//imports
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MeanFilterSerial{
    public static void main(String args[])throws IOException{
      
      //Reading in the image
      BufferedImage img = null;
      File file = null;
      String fileNameIn=args[0];
      String fileNameOut=args[1];
      int frameSizeIn=Integer.parseInt(args[2]);
      //frame for the filters
      int frameSize=frameSizeIn;
      //try/catch in case of errors
      try{
        file= new File("./InputIMGs/"+fileNameIn);
        img = ImageIO.read(file);
      }catch(IOException e){
        System.out.println("ERROR:"+e);
      }
      //storing the height and width of the current image
      int height=img.getHeight();
      int width=img.getWidth();

      //Arrays for storing all values in the grid
      
      int [][] rGrid= new int[width][height];
      int [][] gGrid= new int[width][height];
      int [][] bGrid= new int[width][height];

      //Initial forloop for saving all the RGB values
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          //Pulling in data about the current pixel
          int pixel = img.getRGB(x,y);
          //Creating a colour from the current pixel
          Color color = new Color(pixel, true);

          rGrid[x][y]=color.getRed();
          gGrid[x][y]=color.getGreen();
          bGrid[x][y]=color.getBlue();
        }
      }
      //System.out.println("DONE WITH SAVING PIC VALUES");
      //start timing 
      long startTime = System.currentTimeMillis();

      //Second for loop for updating the pixels
      for (int y = (frameSize-1)/2; y < height-(frameSize-1)/2; y++) {
        for (int x = (frameSize-1)/2; x < width-(frameSize-1)/2; x++) {
          //creating a base new colour
          //Color newColor = new Color(255,255,255,255);
          //creating arrays of the rgb values in the frame
          int totalR=0;
          int totalG=0;
          int totalB=0;
          int countTest=0;
          for (int yFrame = 0; yFrame < frameSize; yFrame++) {
            for (int xFrame = 0; xFrame < frameSize; xFrame++) {
              totalR=totalR+rGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
              totalG=totalG+gGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
              totalB=totalB+bGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
              countTest++;
            }
          }

          
          int avgR=totalR/(frameSize*frameSize);
          int avgG=totalG/(frameSize*frameSize);
          int avgB=totalB/(frameSize*frameSize);

          //System.out.println("red:"+avgR+"  green:"+avgG+"  blue:"+avgB+"  RUNS:"+countTest);
          //Code to change the pixels that are to be written
          //Mean the arrays
          
          //Creating a new colour from the updated RGB values
          Color newColor = new Color(avgR, avgG, avgB);

          //updating the image with the new pixel colour
          img.setRGB(x, y, newColor.getRGB());
        }
      }
      long endTime = System.currentTimeMillis();

      System.out.println("Image blur took (Mean Filter Serial): " + (endTime - startTime) + 
              " milliseconds.");

        
    
      //Save the image, use try/catch for errors
      try{
        file = new File("./OutputIMGs/"+fileNameOut);
        ImageIO.write(img, "jpg", file);
        //System.out.println("Finished.");
      }catch(IOException e){
      System.out.println("ERROR:"+e);
    
}

}//main() ends here
}