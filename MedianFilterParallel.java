//imports
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MedianFilterParallel extends RecursiveAction {
  protected static int threshold = 1000;
  private int arrSize;
  private int startY;
  private int startX;
  private int endY;
  private int endX;
  private static int frameSize;
  static int [][] rGrid;
  static int [][] gGrid;
  static int [][] bGrid;
  static BufferedImage finalImg = null;

  //constructor
  public MedianFilterParallel(int arrS, int sY, int sX, int eY, int eX) {
      arrSize=arrS;
      startY=sY;
      startX=sX;
      endY=eY;
      endX=eX;
 
  }


  protected void computeDirectly() {

  }
    @Override
    protected void compute() {
      if(arrSize<threshold){
        //arrays outside of loop to save time.
        int []arrR=new int[frameSize*frameSize];
        int []arrG=new int[frameSize*frameSize];
        int []arrB=new int[frameSize*frameSize];
        int arrCount=0; 
        //DO IT
        for (int y = startY; y < endY; y++) {
          for (int x = startX; x < endX; x++) {
            arrCount=0;
            //creating a base new colour
            //Color newColor = new Color(255,255,255,255);
            //creating arrays of the rgb values in the frame
            int countTest=0;
            for (int yFrame = 0; yFrame < frameSize; yFrame++) {
              for (int xFrame = 0; xFrame < frameSize; xFrame++) {
                arrR[arrCount]=rGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
                arrG[arrCount]=gGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
                arrB[arrCount]=bGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];

                arrCount++;
              }
            }
  
            
            Arrays.sort(arrR);
            Arrays.sort(arrG);
            Arrays.sort(arrB);
            int medR=0;
            int medG=0;
            int medB=0;
            //working out mean
            if(arrCount%2==0){
                medR=(arrR[arrR.length/2]+arrR[(arrR.length/2)-1])/2;
                medG=(arrG[arrG.length/2]+arrR[(arrG.length/2)-1])/2;
                medB=(arrB[arrB.length/2]+arrR[(arrB.length/2)-1])/2;

            }else{
                medR=arrR[(arrR.length/2)-1];
                medG=arrG[(arrG.length/2)-1];
                medB=arrB[(arrB.length/2)-1];
            }
  
            //System.out.println("red:"+avgR+"  green:"+avgG+"  blue:"+avgB+"  RUNS:"+countTest);
            //Code to change the pixels that are to be written
            //Mean the arrays
            
            //Creating a new colour from the updated RGB values
            Color newColor = new Color(medR, medG, medB);
  
            //updating the image with the new pixel colour
            finalImg.setRGB(x, y, newColor.getRGB());
          }
        }

      }else{
        //split the work, invoke smaller tasks and wait for result
        //splitting work into two 
        int split=arrSize/2;
        MedianFilterParallel left=new MedianFilterParallel(split,startY, startX, endY-(endY/2), endX-(endX/2));
        MedianFilterParallel right= new MedianFilterParallel(arrSize-split, endY-(endY/2)+1, endX+(endX/2)+1, endY, endX);
        left.fork();
        right.compute();

        

      }
    }
    public static void main(String args[])throws IOException{
      //Normal Main method
      //Reading in the image
      File file = null;
      //frame size we are working with
      frameSize=9;
      //try/catch in case of errors
      try{
        file= new File("Sample.jpg");
        finalImg = ImageIO.read(file);
      }catch(IOException e){
        System.out.println("ERROR:"+e);
      }
      

      //storing the height and width of the current image
      int height=finalImg.getHeight();
      int width=finalImg.getWidth();
    

      //Arrays for storing all values in the grid
      
      rGrid= new int[width][height];
      gGrid= new int[width][height];
      bGrid= new int[width][height];

      //Initial forloop for saving all the RGB values
      for (int y = 0; y < height; y++) 
      {
        for (int x = 0; x < width; x++) 
        {
          //Pulling in data about the current pixel
          int pixel = finalImg.getRGB(x,y);
          //Creating a colour from the current pixel
          Color color = new Color(pixel, true);

          rGrid[x][y]=color.getRed();
          gGrid[x][y]=color.getGreen();
          bGrid[x][y]=color.getBlue();
        }
      }
      System.out.println("DONE WITH SAVING PIC VALUES");

      //start it up , divide and conquer
      System.out.println(rGrid.length);
      MedianFilterParallel mfp= new MedianFilterParallel(rGrid.length, (frameSize-1)/2, (frameSize-1)/2, height-(frameSize-1)/2,width-(frameSize-1)/2);
      ForkJoinPool pool=new ForkJoinPool(); 
      long startTime = System.currentTimeMillis();
        pool.invoke(mfp);
        long endTime = System.currentTimeMillis();
 
        System.out.println("Image blur took " + (endTime - startTime) + 
                " milliseconds.");




      //AFTER JOIN SAVE IT
      try{
        file = new File("/Users/owen/OneDrive - University of Cape Town/My UCT/2nd Year/CSC2002S/Week 2/Assignment 1/OutputMedP.jpg");
        ImageIO.write(finalImg, "jpg", file);
        System.out.println("Finished.");
      }catch(IOException e){
      System.out.println("ERROR:"+e);
      }
    }//main() ends here

}
