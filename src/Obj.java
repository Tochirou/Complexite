/**
 * Created by Tom Veniat on 02/01/15.
 */
public class Obj {
    private static int objCount=0;
    private int width;
    private int height;
    private int id;

    public Obj(int width, int height){
        this.width=width;
        this.height=height;
        this.id=++objCount;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getArea(){
        return this.width*this.height;
    }

    public int getId(){
        return this.id;
    }


    public String toString(){
        return "( "+width+","+height+" )";
    }

}
