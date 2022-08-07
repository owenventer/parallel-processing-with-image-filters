//imports
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MeanFilterSerial{
    //comment
    public static void main(String args[])throws IOException{
        BufferedImage img = null;
        File f = null;
    
        //read image
        try{
          f = new File("Sample.jpg");
          img = ImageIO.read(f);
        }catch(IOException e){
          System.out.println(e);
        }
    
        // some code goes here...
        int width = img.getWidth();
        int height = img.getHeight();

        //Getting the values
        int getP = img.getRGB(0,0);
        //get alpha
        int getA = (getP>>24) & 0xff;
        int getR = (getP>>16) & 0xff;
        int getG = (getP>>8) & 0xff;
        int getB = getP & 0xff;

        //Setting the new values
       
        int setA = 255;
        int setR = 100;
        int setG = 150;
        int setB = 200;

        //set the pixel value
        int setP = (setA<<24) | (setR<<16) | (setG<<8) | setB;
        img.setRGB(0, 0, setP);

        //write image
        try{
            f = new File("/Output.jpg");
            ImageIO.write(img, "jpg", f);
        }catch(IOException e){
            System.out.println(e);

      }//main() ends here
}
}