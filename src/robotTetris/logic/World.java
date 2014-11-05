package robotTetris.logic;

import robotTetris.basic.Point3D;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by leowoo on 2014/11/5.
 */
public class World {


    //TODO
    public static final int HEIGHT = 0;
    public static final int WIDTH = 0;


    private Vector<ArrayList<Cube>> pile = new Vector<ArrayList<Cube>>(WIDTH);

    private ArrayList<Cube> fallingCubes = new ArrayList<Cube>(3);

    public static World getInstance(){
        return null;
    }

    public ArrayList<Cube> getFallingCubes() {
        return fallingCubes;
    }

    public int[] getTops(){
        return null;
    }

    public void generateNewCube(Point3D point){

    }

    public Vector<ArrayList<Cube>> getPile() {
        return pile;
    }
}
