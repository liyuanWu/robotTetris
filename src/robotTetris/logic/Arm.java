package robotTetris.logic;

import robotTetris.basic.Point3D;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by leowoo on 2014/11/5.
 * Modified by robin on 2014/11/11.
 */

public class Arm {

	private static Arm self;
	
    private final int BASE_ARM_LENGTH = 350;
    private final int HEAD_ARM_LENGTH = 250;
    private final int ARM_WIDTH = 1;
    
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
    	mid = new Point3D(0, BASE_ARM_LENGTH, 0);
    	head = new Point3D(0, BASE_ARM_LENGTH + HEAD_ARM_LENGTH, 0);
    }

    public boolean isCollision(Cube cube){
    	
    	Point3D cubeCorner = cube.getLocation();
    	int x1 = cubeCorner.x;
    	int y1 = cubeCorner.y;
    	int width = cube.getWidth();
    	int x2 = x1 + width;
    	int y2 = y1 + width;
    	
    	boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
    	boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);
        return (isBaseCollision || isHeadCollision);
    }

    public boolean rotateLeftBaseArm(){
    	
    	angleBase += BASIC_ROTATE_ANGLE;
    	int x = (int) (BASE_ARM_LENGTH * Math.cos(angleBase));
    	int y = (int) (BASE_ARM_LENGTH * Math.sin(angleBase));
    	Point3D tmpmid = new Point3D(x, y, 0);

    	angleHead += BASIC_ROTATE_ANGLE;
    	x = (int) (HEAD_ARM_LENGTH * Math.cos(angleHead));
    	y = (int) (HEAD_ARM_LENGTH * Math.sin(angleHead));
    	Point3D tmphead = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	Vector<ArrayList<Cube>> pile = World.getInstance().getPile();
    	int size = pile.size();
    	boolean isCollision = false;
    	for (int i=0; i<size; i++) {
    		
    		ArrayList<Cube> list = pile.get(i);

            int height = list.size();
            if (height != 0){
                Cube top = list.get(list.size()-1);
                Point3D location = top.getLocation();
                int x1 = location.x;
                int y1 = location.y;
                int width = top.getWidth();
                int x2 = x1 + width;
                int y2 = y1 + width;

                boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
                boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);

                if (isBaseCollision || isHeadCollision) {
                    isCollision = true;
                    break;
                }
            }



    	}
    	if (!isCollision){
    			mid = tmpmid;
    			head = tmphead;
    			return true;
    		}
    	
    	angleBase -= BASIC_ROTATE_ANGLE;
    	angleHead -= BASIC_ROTATE_ANGLE;
        return false;
    }

    public boolean rotateRightBaseArm(){
    	
    	angleBase -= BASIC_ROTATE_ANGLE;
    	int x = (int) (BASE_ARM_LENGTH * Math.cos(angleBase));
    	int y = (int) (BASE_ARM_LENGTH * Math.sin(angleBase));
    	Point3D tmpmid = new Point3D(x, y, 0);

    	angleHead -= BASIC_ROTATE_ANGLE;
    	x = (int) (HEAD_ARM_LENGTH * Math.cos(angleHead));
    	y = (int) (HEAD_ARM_LENGTH * Math.sin(angleHead));
    	Point3D tmphead = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	Vector<ArrayList<Cube>> pile = World.getInstance().getPile();
    	int size = pile.size();
    	boolean isCollision = false;
    	for (int i=0; i<size; i++) {
    		
    		ArrayList<Cube> list = pile.get(i);
            int height = list.size();
            if (height != 0) {
                Cube top = list.get(list.size() - 1);

                Point3D location = top.getLocation();
                int x1 = location.x;
                int y1 = location.y;
                int width = top.getWidth();
                int x2 = x1 + width;
                int y2 = y1 + width;

                boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
                boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);

                if (isBaseCollision || isHeadCollision){
                    isCollision = true;
                    break;
                }
            }
    	}
    	if (!isCollision)
            if ((tmphead.x < World.WIDTH * Cube.width) && (tmphead.y > 0)) {
    			mid = tmpmid;
    			head = tmphead;
    			return true;
    		}

    	angleBase += BASIC_ROTATE_ANGLE;
    	angleHead += BASIC_ROTATE_ANGLE;
        return false;
    }

    public boolean rotateLeftHeadArm(){
    	
    	angleHead += BASIC_ROTATE_ANGLE;
    	int x = (int) (HEAD_ARM_LENGTH * Math.cos(angleHead));
    	int y = (int) (HEAD_ARM_LENGTH * Math.sin(angleHead));
    	Point3D tmphead = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	Vector<ArrayList<Cube>> pile = World.getInstance().getPile();
    	int size = pile.size();
    	boolean isCollision = false;
    	for (int i=0; i<size; i++) {
    		
    		ArrayList<Cube> list = pile.get(i);int height = list.size();
            if (height != 0) {
                Cube top = list.get(list.size() - 1);

                Point3D location = top.getLocation();
                int x1 = location.x;
                int y1 = location.y;
                int width = top.getWidth();
                int x2 = x1 + width;
                int y2 = y1 + width;

                boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
                boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);

                if (isBaseCollision || isHeadCollision) {
                    isCollision = true;
                    break;
                }
            }
    	}
    	if (!isCollision){
    			head = tmphead;
    			return true;
    		}

    	angleHead -= BASIC_ROTATE_ANGLE;
        return false;
    }

    public boolean rotateRightHeadArm(){
    	
    	angleHead -= BASIC_ROTATE_ANGLE;
    	int x = (int) (HEAD_ARM_LENGTH * Math.cos(angleHead));
    	int y = (int) (HEAD_ARM_LENGTH * Math.sin(angleHead));
    	Point3D tmphead = new Point3D(x + mid.x, y + mid.y, 0);
    	
    	Vector<ArrayList<Cube>> pile = World.getInstance().getPile();
    	int size = pile.size();
    	boolean isCollision = false;
    	for (int i=0; i<size; i++) {

    		ArrayList<Cube> list = pile.get(i);
            int height = list.size();
            if (height != 0) {
                Cube top = list.get(list.size() - 1);

                Point3D location = top.getLocation();
                int x1 = location.x;
                int y1 = location.y;
                int width = top.getWidth();
                int x2 = x1 + width;
                int y2 = y1 + width;

                boolean isBaseCollision = isCollision(0, 0, mid.x, mid.y, x1, y1, x2, y2);
                boolean isHeadCollision = isCollision(mid.x, mid.y, head.x, head.y, x1, y1, x2, y2);

                if (isBaseCollision || isHeadCollision) {
                    isCollision = true;
                    break;
                }
            }
    	}
    	if (!isCollision)
            if ((tmphead.x < World.WIDTH * Cube.width) && (tmphead.y > 0)) {
                head = tmphead;
                return true;
        }

    	angleHead += BASIC_ROTATE_ANGLE;
        return false;
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

    public int getArmWidth() {
    	
        return ARM_WIDTH;
    }
    
    private boolean isCollision(int lineX1, int lineY1, int lineX2, int lineY2,
    		int areaX1, int areaY1, int areaX2, int areaY2) {
    	
    	boolean[] head = new boolean[4];
    	boolean[] tail = new boolean[4];
    	
    	head[0] = (lineX1 < areaX1);
    	head[1] = (lineX1 > areaX2);
    	head[2] = (lineY1 < areaY1);
    	head[3] = (lineY1 > areaY2);
    	
    	tail[0] = (lineX2 < areaX1);
    	tail[1] = (lineX2 > areaX2);
    	tail[2] = (lineY2 < areaY1);
    	tail[3] = (lineY2 > areaY2);
		return false;
    }
}
