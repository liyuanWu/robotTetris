package robotTetris.viewer;

import robotTetris.basic.Point3D;
import robotTetris.logic.Cube;
import robotTetris.logic.World;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leowoo on 2014/11/11.
 */
public class Viewer2D extends Applet{
    private static final int CUBESIZE=20;
    private static final int FRAMERATE = 60;
    private World world = World.getInstance();

    public Viewer2D(){

       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Viewer2D.this.repaint();
            }
        },0,1000/FRAMERATE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //if out of bound, ignore
                if(e.getX()>CUBESIZE*world.WIDTH || e.getY()> CUBESIZE*world.HEIGHT){
                    return;
                }
               //calculate click location
                int x = e.getX() / CUBESIZE;
                int y = world.HEIGHT - e.getY() / CUBESIZE -1;

                //generate Cube
               world.generateNewCube(new Point3D(x,y,0));
            }
        });
    }

    public void update(Graphics g){
        Image offScreenImage=this.createImage(800,600);
        Graphics graphicOffScreen=offScreenImage.getGraphics();
        paint(graphicOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    public void paint(Graphics g){
        //draw lines
       for(int i=0;i<=world.HEIGHT;i++){
           g.drawLine(0,CUBESIZE*i,world.WIDTH*CUBESIZE,CUBESIZE*i);
       }
       for(int j=0;j<=world.WIDTH;j++){
           g.drawLine(CUBESIZE*j,0,CUBESIZE*j,CUBESIZE*world.HEIGHT);
       }
        //draw falling cubes
       for(Cube cube:world.getFallingCubes()){
           this.paintCube(g,cube,Color.GREEN);
       }
        //draw pile
        for(ArrayList<Cube> cubeList:world.getPile()){
            for(Cube cube : cubeList){
                this.paintCube(g,cube,Color.BLUE);
            }
        }
    }
    private void paintCube(Graphics g, Cube cube,Color color){
        g.setColor(color);
        g.fillRect(CUBESIZE * cube.getLocation().x, CUBESIZE * (world.HEIGHT - cube.getLocation().y-1),CUBESIZE,CUBESIZE);
        g.setColor(Color.BLACK);
    }
}
