import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Box;
import model.Obj;

/**
 * Created by Tom Veniat on 31/12/14.
 */
public class BinPacking {

    private int boxesWidth;
    private int boxesHeight;
    private List<Obj> objects;          //The objects to put in the boxes in the original order.
    private List<Obj> sortedObjects;    //The List we will sort and work with.
    private List<Box> packing;          //The List of boxes that contains the objects.


    public BinPacking(int width, int height, List<Obj> objects){
        this.boxesWidth =width;
        this.boxesHeight =height;
        this.objects=objects;
        this.sortedObjects=this.objects;

        this.packing=new ArrayList<Box>();
        this.packing.add(new Box(width,height));
    }

    public BinPacking(String file){
        this(Integer.parseInt(parseFile(file)[0]),Integer.parseInt(parseFile(file)[1]),parseObjectsList(parseFile(file)[2]));
    }

    private static ArrayList<Obj> parseObjectsList(String list){
        ArrayList<Obj> objects=new ArrayList<Obj>();
        String[] objectList=list.split(",");
        for (String str : objectList){
            objects.add(parseObj(str));
        }
        return objects;
    }

    private static Obj parseObj(String str) {
        String[] size = str.split("x");
        return new Obj(Integer.parseInt(size[0]),Integer.parseInt(size[1]));
    }

    private static String[] parseFile(String file) {
        String[] data = new String[3];
        InputStream ips = null;
        try {
            ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            String line = br.readLine();
            String[] size = line.split("x");
            data[0]=size[0];
            data[1]=size[0];
            data[2]=br.readLine();

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * This function try to put the object in the order they appears in the sortedObjects List.
     * It begins trying to put the object At the top left of a box. If an object doesn't fit in a box this function try to put it in the next box.
     * If there isn't enough space for an object in any of the boxes, it creates a new box and put the object inside.
     *
     * @return The number of boxes needed to have a spot for each object.
     */
    public int firstFit(){
        for (Obj o : sortedObjects){
            int i=0;
            while ((i<packing.size())&&(!packing.get(i).fit(o))){
                i++;
            }
            if (i>=packing.size()){
                packing.add(new Box(this.boxesWidth,this.boxesHeight));
                packing.get(packing.size()-1).fit(o);
            }
        }
        return packing.size();
    }

    public void setBoxesHeight(int boxesHeight) {
        this.boxesHeight = boxesHeight;
    }

    public void setBoxesWidth(int boxesWidth) {
        this.boxesWidth = boxesWidth;
    }

    public String toString(){
        StringBuilder str =new StringBuilder("Fitting of objects ");
        for(Obj o : sortedObjects)
            str.append(o);
        str.append(" in ( "+ boxesWidth +","+ boxesHeight +" ) boxes\n\t");
        str.append("\n\n");

        for (Box pack : packing){
            str.append(pack);
        }

        return str.toString();
    }


    /**
     * Method that sort the objects by width, the biggest width will come first.
     * If two objects have the same width, the object with the biggest height (biggest area too) will come first.
     */
    public void widthFirstSort(){
        areaSort();
        Collections.sort(sortedObjects, new Comparator<Obj>() {
            @Override
            public int compare(Obj o1, Obj o2) {
                return ((Integer)o2.getWidth()).compareTo((Integer)o1.getWidth());
            }
        });
    }

    /**
     * Method that sort the objects by height, the biggest height will come first.
     * If two objects have the same height, the object with the biggest width (biggest area too) will come first.
     */
    public void heightFirstSort(){
        areaSort();
        Collections.sort(sortedObjects, new Comparator<Obj>() {
            @Override
            public int compare(Obj o1, Obj o2) {
                return ((Integer)o2.getHeight()).compareTo((Integer)o1.getHeight());
            }
        });
    }

    /**
     * Method that sort the objects by area, the biggest area will come first.
     */
    public void areaSort(){
        Collections.sort(sortedObjects, new Comparator<Obj>() {
            @Override
            public int compare(Obj o1, Obj o2) {
                return ((Integer)o2.getArea()).compareTo((Integer)o1.getArea());
            }
        });
    }

    /**
     * This method call the FirstFit method after each possible sorting method.
     * It also keep the best method and apply it before printing the result at the end.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void resolve() throws InvocationTargetException, IllegalAccessException {

        Method bestSort;
        int minNumber;
        ArrayList<Method> sortPossibilities=getSortMethods();
        int bestPossibleSolution=bestPossibleSolution();

        bestSort=null;
        int number = this.firstFit();
        System.out.println("For the noSort algorithm it needs "+number+" box(es).");

        minNumber=number;
        reinit();
        for (Method m : sortPossibilities) {
            m.invoke(this);
            number = this.firstFit();
            System.out.println("For the " + m.getName() + " algorithm it needs " + number + " box(es).");
            if (number < minNumber) {
                bestSort = m;
                minNumber = number;
            }
            reinit();
        }

        if (bestSort!=null){
            System.out.println("\n\nThe best solution is the "+bestSort.getName()+" solution, with "+number+" box(es).");
            bestSort.invoke(this);
        }else{
            System.out.println("\n\nThe best solution is the original order, with "+number+" box(es).");
            sortedObjects=objects;
        }
        this.firstFit();
        System.out.println(this);
    }

    /**
     * Method used after each try, it re-initializes the list of boxes.
     */
    private void reinit() {
        this.packing=new ArrayList<Box>();
        this.packing.add(new Box(boxesWidth, boxesHeight));
    }
    
    public List<Box> getBox() {
    	return packing;
    }

    /**
     * Used to get all the sorting method that can be used to sort the objects we want to pack.
     *
     * @return an ArrayList containing all the Methods objects.
     */
    private ArrayList<Method>getSortMethods(){
        ArrayList<Method> sortMethods=new ArrayList<Method>();
        try {
            sortMethods.add(BinPacking.class.getMethod("widthFirstSort", null));
            sortMethods.add(BinPacking.class.getMethod("heightFirstSort", null));
            sortMethods.add(BinPacking.class.getMethod("areaSort", null));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return sortMethods;
    }

    /**
     *
     * @return the best possible number of boxes (in number of places, not every time realizable).
     */
    private int bestPossibleSolution(){
        int bestPossibleSolution=0;
        for (Obj o : objects){
            bestPossibleSolution+=o.getArea();
        }
        bestPossibleSolution/=(boxesHeight*boxesWidth);
        return ++bestPossibleSolution;
    }

}

