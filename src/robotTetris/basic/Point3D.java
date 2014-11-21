package robotTetris.basic;

/**
 * Created by leowoo on 2014/11/5.
 */
public class Point3D {
    public float x;
    public float y;
    public float z;
    public Point3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
	public String toString(){
    	return "("+x+","+y+","+z+")";
    }
}
