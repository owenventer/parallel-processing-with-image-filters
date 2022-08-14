//imports
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class MedianFilterSerial {

    public static void main(String args[])throws IOException{
      
        //Reading in the image
        BufferedImage img = null;
        File file = null;
        String fileNameIn=args[0];
        String fileNameOut=args[1];
        int frameSizeIn=Integer.parseInt(args[2]);
        //frame size we are working with
        int frameSize=frameSizeIn;
        //try/catch in case of errors
        try{
          file= new File("./InputIMGs/"+fileNameIn);
          img = ImageIO.read(file);
        }catch(IOException e){
          System.out.println("READING IN FILE ERROR:"+e);
        }
        //storing the height and width of the current image
        int height=img.getHeight();
        int width=img.getWidth();
  
        //Arrays for storing all values in the grid
        int [][] aGrid= new int[width][height];
        int [][] rGrid= new int[width][height];
        int [][] gGrid= new int[width][height];
        int [][] bGrid= new int[width][height];
  
        //Initial forloop for saving all the ARGB values
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            //Pulling in data about the current pixel
            int pixel = img.getRGB(x,y);
            //Creating a colour from the current pixel
            Color color = new Color(pixel, true);
  
            aGrid[x][y]=color.getAlpha();
            rGrid[x][y]=color.getRed();
            gGrid[x][y]=color.getGreen();
            bGrid[x][y]=color.getBlue();
          }
        }
        //System.out.println("DONE WITH SAVING PIC VALUES");

        //creating arrays outside of the loop so to save time
        int []arrR=new int[frameSize*frameSize];
        int []arrG=new int[frameSize*frameSize];
        int []arrB=new int[frameSize*frameSize];
        int arrCount=0;

        //start time
        long startTime = System.currentTimeMillis();
        //Second for loop for updating the pixels
        for (int y = (frameSize-1)/2; y < height-(frameSize-1)/2; y++) {
          for (int x = (frameSize-1)/2; x < width-(frameSize-1)/2; x++) {
            //creating a base new colour
            //Color newColor = new Color(255,255,255,255);
            //current arrays will be overwritten ***HOPEFULLY,CHECK THIS FIRST IF ERRORS
            arrCount=0;
            for (int yFrame = 0; yFrame < frameSize; yFrame++) {
              for (int xFrame = 0; xFrame < frameSize; xFrame++) {
                //adding to arrays
                arrR[arrCount]=rGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
                arrG[arrCount]=gGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];
                arrB[arrCount]=bGrid[(x+xFrame)-(frameSize-1)/2][(y+yFrame)-(frameSize-1)/2];

                arrCount++;
              }
            }
            //sorting arrays
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
            
            
  
            //System.out.println("red:"+medR+"  green:"+medG+"  blue:"+medB+"  RUNS:"+arrCount);
            //Code to change the pixels that are to be written
            //Mean the arrays
            
            //Creating a new colour from the updated RGB values
            Color newColor = new Color(medR, medG, medB);
  
            //updating the image with the new pixel colour
            img.setRGB(x, y, newColor.getRGB());
          }
        }

        long endTime = System.currentTimeMillis();
  
        System.out.println("Image blur took (Median Filter Serial): " + (endTime - startTime) + 
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
