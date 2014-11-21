package robotTetris.logic;

import robotTetris.basic.Point3D;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by leowoo on 2014/11/5.
 * Modified by robin on 2014/11/11.
 */

public class Arm {

	private static Arm self;
	
    private final int BASE_ARM_LENGTH = 8;
    private final int HEAD_ARM_LENGTH = 5;
    private final double ARM_WIDTH = 0.5;
    
    private final double BASIC_ROTATE_ANGLE = 2*Math.PI/360;
    
    private Point3D head, mid;
    private double angleBase, angleHead;

    public static Arm getInstance(){
    	
    	if (self == null)
    		self = new Arm();
        return self;
    }
    
    private Arm() {
    	
    	angleBase = angleHead = Math.PI/2;
    	mid = new Point3D(0, BASE_ARM_LENGTH * Cube.WIDTH, 0);
    	head = new Point3D(0, (BASE_ARM_LENGTH + HEAD_ARM_LENGTH) * Cube.WIDTH, 0);
    }

    public boolean isCollision(Cube cube){
    	
    	Point3D cubeCorner = cube.getLocation();
    	float x1 = cubeCorner.x;
    	float y1 = cubeCorner.y;
    	float width = Cube.WIDTH;
    	float x2 = x1 + width;
    	float y2 = y1 + width;
    	
    	boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
    	boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);
        return (isBaseCollision || isHeadCollision);
    }

    public ArrayList<Integer> rotateLeftBaseArm(){
    	
    	angleBase += BASIC_ROTATE_ANGLE;
    	float x = (float) (BASE_ARM_LENGTH * Cube.WIDTH * Math.cos(angleBase));
    	float y = (float) (BASE_ARM_LENGTH * Cube.WIDTH * Math.sin(angleBase));
    	mid = new Point3D(x, y, 0);

    	angleHead += BASIC_ROTATE_ANGLE;
    	x = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.cos(angleHead));
    	y = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.sin(angleHead));
    	head = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	ArrayList<Integer> res = getCollisionFallingCube(mid, head);
    	return res;
    }

    public ArrayList<Integer> rotateRightBaseArm(){
    	
    	angleBase -= BASIC_ROTATE_ANGLE;
    	float x = (float) (BASE_ARM_LENGTH * Cube.WIDTH * Math.cos(angleBase));
    	float y = (float) (BASE_ARM_LENGTH * Cube.WIDTH * Math.sin(angleBase));
    	mid = new Point3D(x, y, 0);

    	angleHead -= BASIC_ROTATE_ANGLE;
    	x = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.cos(angleHead));
    	y = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.sin(angleHead));
    	head = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	ArrayList<Integer> res = getCollisionFallingCube(mid, head);
    	return res;
    }

    public ArrayList<Integer> rotateLeftHeadArm(){
    	
    	angleHead += BASIC_ROTATE_ANGLE;
    	float x = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.cos(angleHead));
    	float y = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.sin(angleHead));
    	head = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	ArrayList<Integer> res = getCollisionFallingCube(mid, head);
    	return res;
    }

    public ArrayList<Integer> rotateRightHeadArm(){
    	
    	angleHead -= BASIC_ROTATE_ANGLE;
    	float x = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.cos(angleHead));
    	float y = (float) (HEAD_ARM_LENGTH * Cube.WIDTH * Math.sin(angleHead));
    	head = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	ArrayList<Integer> res = getCollisionFallingCube(mid, head);
    	return res;
    }

    public Point3D getHeadPosition(){
        return head;
    }

    public Point3D getMidPosition(){
        return mid;
    }
    
    public int getBaseArmLength() {
    	
        return BASE_ARM_LENGTH;
    }

    public int getHeadArmLength() {
    	
        return HEAD_ARM_LENGTH;
    }

    public double getArmWidth() {
    	
        return ARM_WIDTH;
    }
    
    private ArrayList<Integer> getCollisionFallingCube(Point3D tmpMid, Point3D tmpHead) {
    	
    	ArrayList<Integer> res = new ArrayList<Integer>();
    	ArrayList<Cube> list = World.getInstance().getFallingCubes();
    	int size = list.size();
    	for (int i=0; i<size; i++) {
    		
    		Cube cube = list.get(i);

    		Point3D location = cube.getLocation();
        	float x1 = location.x;
        	float y1 = location.y;
        	float width = Cube.WIDTH;
        	float x2 = x1 + width;
        	float y2 = y1 + width;
        	
    		boolean isBaseCollision = isCollision(0, 0, tmpMid.x, tmpMid.y, x1, y1, x2, y2);
        	boolean isHeadCollision = isCollision(tmpMid.x, tmpMid.y, tmpHead.x, tmpHead.y, x1, y1, x2, y2);
        	
        	if (isBaseCollision || isHeadCollision)
        		res.add(i);
    	}
    	return res;
    }
    
    private boolean isCollision(float lineX1, float lineY1, float lineX2, float lineY2,
    		float areaX1, float areaY1, float areaX2, float areaY2) {
    	
    	Rectangle rect = new Rectangle((int)areaX1, (int)areaY1, (int)areaX2, (int)areaY2);
    	return rect.intersectsLine(lineX1, lineY1, lineX2, lineY2);
    }
}