import java.util.IllegalFormatException;

/**
 * Created by Tom Veniat on 02/01/15.
 */
public class Box {
    private int width;
    private int height;
    private int[][] content;
    private int left;                   //number of empty spaces currently in the Box.
    private double fillingRatio=0.0;    //In percentage.

    public Box(int width,int height){
        this.width=width;
        this.height=height;
        this.content=new int[height][width];
        this.left=width*height;
    }

    public String toString(){
        StringBuilder str=new StringBuilder();
        for(int[] line : content){
            for (int place : line){
                str.append(place);
            }
            str.append("\n");
        }
        str.append("Filling ratio : ");
        str.append(fillingRatio);
        str.append("\n");
        return str.toString();
    }

    public boolean fit(Obj o) throws IllegalFormatException {
        if ((o.getWidth() > this.width) || (o.getHeight() > this.height)) {
            throw new IllegalArgumentException(o + " : This object is too big for the boxes.");
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (content[y][x] == 0) {
                    if ((x + o.getWidth() - 1 < this.width) && (y + o.getHeight() - 1 < this.height) && (content[y][x + o.getWidth() - 1] == 0) && (content[y + o.getHeight() - 1][x] == 0) && (content[y + o.getHeight() - 1][x + o.getWidth() - 1] == 0)) {
                        boolean good = true;
                        int xSize = o.getWidth();
                        int ySize = o.getHeight();
                        for (int curY = 0; curY < ySize; curY++) {
                            for (int curX = 0; curX < xSize; curX++) {
                                if (content[curY + y][curX + x]!=0)
                                    good=false;
                            }
                        }
                        if(good){
                            this.put(o, x, y);
                            return true;}
                    }
                }
            }
        }
        return false;
    }
    public void put(Obj o,int xOrig, int yOrig){
        int xSize=o.getWidth();
        int ySize=o.getHeight();
        for (int y=0;y<ySize;y++){
            for (int x=0;x<xSize;x++){
                content[yOrig+y][xOrig+x]=(o.getId()%9)+1;
            }
       }
       this.updateRatio(o.getArea());
    }

    private void updateRatio(int sizeModification){
        left-=sizeModification;
        fillingRatio=Math.round((1.0-(double)left/(width*height))*100);
    }



}
