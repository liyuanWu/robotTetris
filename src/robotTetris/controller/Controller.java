package robotTetris.controller;

import robotTetris.basic.Point3D;
import robotTetris.logic.Arm;
import robotTetris.logic.Camera;
import robotTetris.logic.Cube;
import robotTetris.logic.World;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by leowoo on 2014/11/5.
 * Modified by robin on 2014/11/11.
 */

public class Controller {

    public static ArrayList<Cube> getFallingCubes() {
        return World.getInstance().getFallingCubes();
    }

    public static Vector<ArrayList<Cube>> getPile() {
        return World.getInstance().getPile();
    }

    public static void generateNewCube(){
        World.getInstance().generateNewCube(Arm.getInstance().getHeadPosition());
    }

    public static int getBaseArmLength() {
        return Arm.getInstance().getBaseArmLength();
    }

    public static int getHeadArmLength() {
    	
        return Arm.getInstance().getHeadArmLength();
    }

    public static int getArmWidth() {
    	
        return Arm.getInstance().getArmWidth();
    }

    public static boolean rotateLeftBaseArm(){
    	
        return Arm.getInstance().rotateLeftBaseArm();
    }

    public static boolean rotateRightBaseArm(){
    	
        return Arm.getInstance().rotateRightBaseArm();
    }

    public static boolean rotateLeftHeadArm(){
    	
        return Arm.getInstance().rotateLeftHeadArm();
    }

    public static boolean rotateRightHeadArm(){
    	
        return Arm.getInstance().rotateRightHeadArm();
    }

    public static void rotateLeftCamera(){

    	Camera.getInstance().rotateLeft();
    }

    public static void rotateRightCamera(){

    	Camera.getInstance().rotateRight();
    }

    public static Point3D getCameraLocation(){
    	
        return Camera.getInstance().getLocation();
    }

    public static Point3D getArmMidLocation(){
    	
        return Arm.getInstance().getMidPosition();
    }

    public static Point3D getArmHeadLocation(){
    	
        return Arm.getInstance().getHeadPosition();
    }
}
