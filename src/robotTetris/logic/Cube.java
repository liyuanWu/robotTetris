package robotTetris.logic;

import robotTetris.basic.Point3D;

/**
 * Created by leowoo on 2014/11/5.
 */
public class Cube {
    private Point3D location;
    private static final int width = 1;
    private static final int basicFallDownDistance=1;

    public void fallDown(){

    }

    private int getWidth(){
        return width;
    }

    public Point3D getLocation() {
        return location;
    }
}
