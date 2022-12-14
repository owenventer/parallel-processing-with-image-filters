//imports
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MeanFilterParallel extends RecursiveAction {
  protected static int threshold = 500;
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
  public MeanFilterParallel(int arrS, int sY, int sX, int eY, int eX) {
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
        //System.out.println("PROCESSING");
        //DO IT
        for (int y = startY; y < endY; y++) {
          for (int x = startX; x < endX; x++) {
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
            finalImg.setRGB(x, y, newColor.getRGB());
          }
        }

      }else{
        //split the work, invoke smaller tasks and wait for result
        //splitting work into two 
        int split=(int) Math.floor(arrSize/2);
        MeanFilterParallel left=new MeanFilterParallel(split,startY, startX, endY, (endX-split)+((frameSize-1)/2));
        int endYvar=endY-(endY/2);
        int endXvar=endX;
        //System.out.println("LEFT:    split:"+split+" startY:"+startY+" startX:"+startX+" endY:"+endYvar+" endX:"+endXvar);
        MeanFilterParallel right= new MeanFilterParallel(arrSize-split, startY, (startX+split)-((frameSize-1)/2), endY, endX);
        int splitvar=arrSize-split;
        int RStartYvar=endY-(endY/2);
        int RstartXvar= startX;

        //System.out.println("RIGHT:    split:"+splitvar+" startY:"+RStartYvar+" startX:"+RstartXvar+" endY:"+endY+" endX:"+endX);
        left.fork();
        right.compute();
        left.join();

        

      }
    }
    public static void main(String args[])throws IOException{
      //Normal Main method
      //Reading in the image
      File file = null;
      String fileNameIn=args[0];
      String fileNameOut=args[1];
      int frameSizeIn=Integer.parseInt(args[2]);
      
      //frame size we are working with
      frameSize=frameSizeIn;
      //try/catch in case of errors
      try{
        file= new File("./InputIMGs/"+fileNameIn);
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
      //System.out.println("DONE WITH SAVING PIC VALUES");

      //start it up , divide and conquer
      //System.out.println(rGrid.length);
      MeanFilterParallel mfp= new MeanFilterParallel(rGrid.length, (frameSize-1)/2, (frameSize-1)/2, height-(frameSize-1)/2,width-(frameSize-1)/2);
      ForkJoinPool pool=new ForkJoinPool(); 

      //start timing 
      long startTime = System.currentTimeMillis();
      pool.invoke(mfp);
      long endTime = System.currentTimeMillis();

      System.out.println("Image blur took (Mean Filter Parallel): " + (endTime - startTime) + 
              " milliseconds.");




      //AFTER JOIN SAVE IT
      try{
        file = new File("./OutputIMGs/"+fileNameOut);
        ImageIO.write(finalImg, "jpg", file);
        //System.out.println("Finished.");
      }catch(IOException e){
      System.out.println("ERROR:"+e);
      }
    }//main() ends here

}
