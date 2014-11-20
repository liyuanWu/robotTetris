package controller;

import basic.Point3D;
import logic.Arm;
import logic.Camera;
import logic.Cube;
import logic.World;

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

    	Point3D phyicPosition = Arm.getInstance().getHeadPosition();
    
    	Point3D indexPosition = new Point3D((int)phyicPosition.x/Cube.WIDTH,
    			(int)phyicPosition.y/Cube.WIDTH,phyicPosition.z/Cube.WIDTH);
    	
    	if (indexPosition.x < 0)
    		indexPosition.x = 0;
    	if (indexPosition.x >= World.WIDTH)
    		indexPosition.x = World.WIDTH-1;
    	if (indexPosition.y >= World.HEIGHT)
    		indexPosition.y = World.HEIGHT-1;
    	if (indexPosition.y <= 0)
    		return;

    	World.getInstance().generateNewCube(indexPosition);
    }



    public static int getBaseArmLength() {
        return Arm.getInstance().getBaseArmLength();
    }

    public static int getHeadArmLength() {
    	
        return Arm.getInstance().getHeadArmLength();
    }

    public static double getArmWidth() {
    	
        return Arm.getInstance().getArmWidth();
    }

    public static void rotateLeftBaseArm(){
    	
    	ArrayList<Integer> res = Arm.getInstance().rotateLeftBaseArm();
    	
    	if (!res.isEmpty())
    		World.getInstance().setDynamite(res);
    }

    public static void rotateRightBaseArm(){
    	
    	ArrayList<Integer> res = Arm.getInstance().rotateRightBaseArm();
    	
    	if (!res.isEmpty())
    		World.getInstance().setDynamite(res);
    }

    public static void rotateLeftHeadArm(){
    	
    	ArrayList<Integer> res = Arm.getInstance().rotateLeftHeadArm();
    	
    	if (!res.isEmpty())
    		World.getInstance().setDynamite(res);
    }

    public static void rotateRightHeadArm(){
    	
    	ArrayList<Integer> res = Arm.getInstance().rotateRightHeadArm();
    	
    	if (!res.isEmpty())
    		World.getInstance().setDynamite(res);
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

	public static ArrayList<Cube> getDynamite() {
		return World.getInstance().getDynamite();
	}
}
