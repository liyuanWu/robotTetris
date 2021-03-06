package robotTetris.logic;


import robotTetris.basic.Point3D;
import robotTetris.controller.Controller;

import java.util.*;

/**
 * Created by leowoo on 2014/11/5.
 */
public class World {


    public static final int HEIGHT = 10;
    public static final int WIDTH = 8;
    //refresh interval in second
    public static final float REFRESHINTERVAL= 1F;


    private Vector<ArrayList<Cube>> pile ;

    private ArrayList<Cube> fallingCubes;
    private ArrayList<Cube> dynamite;

    private static World instance;

    private int durationOfNonFallingCubes = 0;

    public static World getInstance() {
        if (World.instance == null) {
            World.instance = new World();
        }
        return World.instance;
    }

    public World(){
        this.pile = new Vector<ArrayList<Cube>>(WIDTH);
        for(int i=0;i<WIDTH;i++){
        	ArrayList<Cube> list = new ArrayList<Cube>(HEIGHT);
            this.pile.add(list);
        }
        this.fallingCubes = new ArrayList<Cube>(3);
        dynamite = new ArrayList<Cube>(3);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                World.this.refresh();
            }
        },0,(int)(REFRESHINTERVAL * 1000));
    }
    
    public void setDynamite(ArrayList<Integer> list) {
    	
    	for (int i=list.size()-1; i>=0; i--) {
    		int index = list.get(i);
    		Cube cube = fallingCubes.get(index);
    		fallingCubes.remove(index);
    		cube.setIntoDynamite();
    		dynamite.add(cube);
    	}
    }

    public void resetDurationOfNonFallingCubes(){
        this.durationOfNonFallingCubes = 0;
    }

    private void refresh(){
        if(fallingCubes.isEmpty()){
            ++this.durationOfNonFallingCubes;
            if(this.durationOfNonFallingCubes%3==0){
                Controller.generateNewCube();
            }
        }
        for(Cube cube:fallingCubes) {
            cube.fallDown();
        }
        //detect collision
        ArrayList<Cube> collisionCubes = new ArrayList<Cube>();
        for(Cube cube:fallingCubes) {
            //if cube has collided with pile,
            // then add cube on the top of pile
            if(isPointCollideWithPile(cube.getLocation())) {
                collisionCubes.add(cube);
                this.pile.get((int) cube.getLocation().x).add(cube);
            }
        }
        
        for (int i=0; i<dynamite.size(); i++) {
        	
        	Cube cube = dynamite.get(i);
        	boolean disappear = cube.countdown();
        	if (disappear)
        		dynamite.remove(i);
        	i--;
        }
        
        //then delete them
        this.fallingCubes.removeAll(collisionCubes);
        this.eliminateBottomLines();
    }

    private void eliminateBottomLines(){
        int lowestHeight = Integer.MAX_VALUE;
        for(ArrayList<Cube> cubeList : pile){
            lowestHeight = lowestHeight < cubeList.size()? lowestHeight: cubeList.size();
        }
        for(int k=0;k<lowestHeight;k++){
            for(ArrayList<Cube> cubeList : pile){
                cubeList.remove(0);
                for(Cube cube:cubeList){
                    cube.fallDown();
                }
            }

        }

    }

    public ArrayList<Cube> getFallingCubes() {
        return fallingCubes;
    }

    public ArrayList<Cube> getDynamite() {
        return dynamite;
    }

    public int[] getTops() {
        int[] ret = new int[pile.size()];
        for(int i=0;i<pile.size();i++){
           ret[i] = pile.get(i).size();
        }
        return ret;
    }

    private static Comparator<Cube> cubeComparator = new Comparator<Cube>() {
        @Override
        public int compare(Cube o1, Cube o2) {
            float o1Value = o1.getLocation().y + o1.getLocation().x * HEIGHT + o1.getLocation().z * HEIGHT * WIDTH;
            float o2Value = o2.getLocation().y + o2.getLocation().x * HEIGHT + o2.getLocation().z * HEIGHT * WIDTH;
            return (int)(o1Value - o2Value);
        }
    };
    public void generateNewCube(Point3D point) {

        if (point.x >= World.WIDTH || point.x < 0 ||
                point.y >= World.HEIGHT || point.y < 0 ||
                point.z != 0) {
            throw new InvalidPointException(point);
        }

        Cube cube = new Cube(point);

        //if the point is in pile,ignore
        if(isPointCollideWithPile(point)){
            return;
        }
        //if the point collide with falling cubes,ignore
        if(isPointCollideWithFallingCubes(point)){
            return;
        }

        //add the cube to fallingCube list
        this.fallingCubes.add(cube);
        Collections.sort(this.fallingCubes, cubeComparator);

        this.resetDurationOfNonFallingCubes();
    }

    private boolean isPointCollideWithFallingCubes(Point3D point) {
        for(Cube cube:fallingCubes){
            if(cube.getLocation().x == point.x &&
                    cube.getLocation().y == point.y &&
                    cube.getLocation().z == point.z){
                return true;
            }
        }
        return false;
    }
    private boolean isPointCollideWithPile(Point3D point) {
        return pile.get((int) point.x).size() >= point.y;
    }

    public Vector<ArrayList<Cube>> getPile() {
        return pile;
    }


    static class InvalidPointException extends RuntimeException {
        public InvalidPointException(Point3D point3D) {
            super("Invalid point: (" + point3D.x + "," + point3D.y + "," + point3D.z + ")");
        }
    }
}


