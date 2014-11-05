package robotTetris.logic;

import robotTetris.basic.Point3D;

/**
 * Created by leowoo on 2014/11/5.
 */
public class Arm {

    private final int baseArmLength = 1;
    private final int HeadArmLength = 1;
    private final int armWidth = 1;


    public static final int basicRotateAngle = 1;

    public static Arm getInstance(){
        return null;
    };

    public Point3D getHeadPosition(){
        return null;
    };

    public boolean isCollision(Cube cube){
        return false;
    }

    public boolean rotateBaseArm(){
        return false;
    }

    public boolean rotateHeadArm(){
        return false;
    }


    public int getBaseArmLength() {
        return baseArmLength;
    }

    public int getHeadArmLength() {
        return HeadArmLength;
    }

    public int getArmWidth() {
        return armWidth;
    }
}
