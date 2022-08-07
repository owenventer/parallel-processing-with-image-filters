//imports
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class MedianFilterSerial {

    public static void main(String args[])throws IOException{
      
        //Reading in the image
        BufferedImage img = null;
        File file = null;
        //try/catch in case of errors
        try{
          file= new File("Sample.jpg");
          img = ImageIO.read(file);
        }catch(IOException e){
          System.out.println("ERROR:"+e);
        }
        //storing the height and width of the current image
        int height=img.getHeight();
        int width=img.getWidth();
        for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
  
              //Pulling in data about the current pixel
              int pixel = img.getRGB(x,y);
  
              //Creating a colour from the current pixel
              Color color = new Color(pixel, true);
  
              //Getting RGB from current pixel 
              int r = color.getRed();
              int g = color.getGreen();
              int b = color.getBlue();
  
              //Code to change the pixels that are to be written
              g = 150;
              b = 150;
  
              //Creating a new colour from the updated RGB values
              color = new Color(r, g, b);
  
              //updating the image with the new pixel colour
              img.setRGB(x, y, color.getRGB());
  
           }
        }
        //Save the image, use try/catch for errors
        try{
          file = new File("/Users/owen/OneDrive - University of Cape Town/My UCT/2nd Year/CSC2002S/Week 2/Assignment 1/Output.jpg");
          ImageIO.write(img, "jpg", file);
          System.out.println("Finished.");
        }catch(IOException e){
        System.out.println("ERROR:"+e);
      
  }
  
  }//main() ends here
}
