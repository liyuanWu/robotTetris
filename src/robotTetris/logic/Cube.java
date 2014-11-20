package logic;

import basic.Point3D;

/**
 * Created by leowoo on 2014/11/5.
 */
public class Cube {
	
    private Point3D location;
    public static final int WIDTH = 2;
    
    private final float[][] COLOR_LIST = {{ 1.0f, 0.0f, 0.0f }, { 1.0f, 0.5f, 0.0f },
    		{ 1.0f, 1.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, { 0.0f, 1.0f, 1.0f } };
    
    private final float[] DYNAMITE_COLOR = { 1.0f, 1.0f, 1.0f };
    private int count = 3;
    
    public float color[];

    public Cube(Point3D location){
    	
        this.location = location;
        int index = (int) (Math.random()*5);
        color = COLOR_LIST[index];
    }

    public void fallDown(){
        --this.location.y;
    }

    public Point3D getLocation() {
        return location;
    }
    
    public void setIntoDynamite() {
    	
    	color = DYNAMITE_COLOR;
    }
    
    public boolean countdown() {
    	
    	count--;
    	if (count == 0)
    		return true;
    	return false;
    }
}
