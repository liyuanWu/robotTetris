package logic;

import  basic.Point3D;

/**
 * Created by leowoo on 2014/11/5.
 * Modified by robin on 2014/11/11.
 */

public class Camera {

	private static Camera self;
	
    private final double BASIC_ROTATE_ANGLE = 2*Math.PI/360 * 2;
    private final int RADIUS = (int) (Cube.WIDTH * World.WIDTH * 0.6);
    private final static int CAMERAHEIGHT = 50;
    
    private Point3D[] map;
    private int size;
    private int index;
    
    public static Camera getInstance(){
    	
    	if (self == null)
    		self = new Camera();
        return self;
    }
    
    private Camera() {
    	
    	double fullRound = 2*Math.PI;
    	size = 0;
    	while ((fullRound % BASIC_ROTATE_ANGLE) <= 0.001) {
    		fullRound += 2*Math.PI;
    	}
    	size = (int) (fullRound / BASIC_ROTATE_ANGLE);
    	map = new Point3D[size];
    	
    	double angle = 0.0;
    	for (int i=0; i<size; i++) {
    		
    		int x = (int) (RADIUS * Math.cos(angle));
    		int y = (int) (RADIUS * Math.sin(angle));
    		Point3D tmp = new Point3D(x, y, Camera.CAMERAHEIGHT);
    		map[i] = tmp;
    		angle += BASIC_ROTATE_ANGLE;
    	}
    	
    	index = 0;
    }

    public void rotateLeft(){

    	index--;
    	if (index == -1)
    		index = size-1;
    }

    public void rotateRight(){

    	index++;
    	if (index == size)
    		index = 0;
    }

    public Point3D getLocation(){
    	
    	return map[index];
    }
}
