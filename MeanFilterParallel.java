//imports
import java.io.File;
import java.io.IOException;
import java.util.concurrent.RecursiveAction;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MeanFilterParallel extends RecursiveAction {
  protected static int threshold = 10000;
  private int arrSize;
  private int startY;
  private int StartX;

  //constructor
  public MeanFilterParallel(int arrS, int sY, int sX) {
      arrSize=arrS;
      startY=sY;
      StartX=sX;
  }


  protected void computeDirectly() {

  }
    @Override
    protected void compute() {
      if(arrSize<threshold){
        //DO IT

      }else{
        //split the work, invoke smaller tasks and wait for result

      }
    }
    public static void main(String args[])throws IOException{
      

    }//main() ends here

}
