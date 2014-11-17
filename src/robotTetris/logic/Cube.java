package robotTetris.logic;

import robotTetris.basic.Point3D;

/**
 * Created by leowoo on 2014/11/5.
 */
public class Cube {
    private Point3D location;
    public static final int width = 50;
    private static final int basicFallDownDistance=1;

    public Cube(Point3D location){
        this.location = location;
    }

    public void fallDown(){
        --this.location.y;
    }

    public int getWidth(){
        return width;
    }

    public Point3D getLocation() {
        return location;
    }
}
