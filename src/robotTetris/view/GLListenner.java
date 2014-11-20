package view;

import basic.Point3D;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import controller.Controller;
import logic.Cube;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.GL_COMPILE;
import static javax.media.opengl.GL2.GL_QUADS;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

public class GLListenner implements GLEventListener {

	   private GLU glu;  // for the GL Utility
	   // Display lists for box and top
	   private int boxDList;
	   private int topDList;

	   private Texture texture; // texture over the shape
	   private String textureFileName = "images/crate.png";
	   private String textureFileType = ".png";

	   // Texture image flips vertically. Shall use TextureCoords class to retrieve the
	   // top, bottom, left and right coordinates.
	   private float textureTop, textureBottom, textureLeft, textureRight;

    private void drawCube(Cube cube, GL2 gl){
        float cubeWidth = Cube.WIDTH;
        Point3D cubeLocation = cube.getLocation();
        gl.glTranslatef(cubeLocation.x * cubeWidth, cubeLocation.y * cubeWidth,0);
        gl.glCallList(boxDList);
        gl.glTranslatef(-cubeLocation.x * cubeWidth,-cubeLocation.y * cubeWidth,0);
    }
	@Override
	public void display(GLAutoDrawable drawable) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
	      gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	      gl.glLoadIdentity();  // reset the model-view matrix

          glu.gluLookAt(Controller.getCameraLocation().x,Controller.getCameraLocation().y,Controller.getCameraLocation().z,0,0,0,0,1,0);

	      // Bind the texture to the current OpenGL graphics context.
	      texture.bind(gl);


	      Vector<ArrayList<Cube>> pile = Controller.getPile();
          for(ArrayList<Cube> cubeList:pile){
              for(Cube cube:cubeList){
                  this.drawCube(cube,gl);
              }
          }

	      ArrayList<Cube> list = Controller.getFallingCubes();
          for(Cube cube:list){
            this.drawCube(cube,gl);
          }

	      ArrayList<Cube> dynamitingCubes = Controller.getDynamite();
          for(Cube cube: dynamitingCubes){
              this.drawCube(cube,gl);
          }

	      Point3D mid = Controller.getArmMidLocation();
	      Point3D head = Controller.getArmHeadLocation();

	      gl.glBegin(GL_QUADS);
	      gl.glColor3fv(new float[]{1.0f, 0.0f, 0.0f}, 0);
	      gl.glVertex2fv(new float[]{-1, 0}, 0);
	      gl.glVertex2fv(new float[]{mid.x-1, mid.y}, 0);
	      gl.glVertex2fv(new float[]{mid.x, mid.y}, 0);
	      gl.glVertex2fv(new float[]{0, 0}, 0);
	      gl.glVertex2fv(new float[]{-1, 0}, 0);
	      gl.glEnd();

	      gl.glBegin(GL_QUADS);
	      gl.glColor3fv(new float[]{0.0f, 1.0f, 1.0f}, 0);
	      gl.glVertex2fv(new float[]{mid.x-1, mid.y}, 0);
	      gl.glVertex2fv(new float[]{head.x-1, head.y}, 0);
	      gl.glVertex2fv(new float[]{head.x, head.y}, 0);
	      gl.glVertex2fv(new float[]{mid.x, mid.y}, 0);
	      gl.glVertex2fv(new float[]{mid.x-1, mid.y}, 0);
	      gl.glEnd();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		
		  GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
	      glu = new GLU();                         // get GL Utilities
          glu.gluLookAt(Controller.getCameraLocation().x,Controller.getCameraLocation().y,Controller.getCameraLocation().z,0,0,0,0,1,0);
          System.out.print(Controller.getCameraLocation());
	      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
	      gl.glClearDepth(1.0f);      // set clear depth value to farthest
	      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
	      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
	      gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
	      gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting

	      // Enable LIGHT0, which is pre-defined on most video cards.
	      gl.glEnable(GL_LIGHT0);
	      // gl.glEnable(GL_LIGHTING);

	      // Add colors to texture maps, so that glColor3f(r,g,b) takes effect.
	      gl.glEnable(GL_COLOR_MATERIAL);

	      // We want the best perspective correction to be done
	      gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

	      // Load the texture image
	      try {
	         // Create a OpenGL Texture object from (URL, mipmap, file suffix)
	         // Use URL so that can read from JAR and disk file.
	         texture = TextureIO.newTexture(
	               this.getClass().getResource(textureFileName), false, textureFileType);
	      } catch (GLException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	    	 System.out.println(textureFileName);
	         e.printStackTrace();
	      }

	      // Use linear filter for texture if image is larger than the original texture
	      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	      // Use linear filter for texture if image is smaller than the original texture
	      gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

	      // Texture image flips vertically. Shall use TextureCoords class to retrieve
	      // the top, bottom, left and right coordinates, instead of using 0.0f and 1.0f.
	      TextureCoords textureCoords = texture.getImageTexCoords();
	      textureTop = textureCoords.top();
	      textureBottom = textureCoords.bottom();
	      textureLeft = textureCoords.left();
	      textureRight = textureCoords.right();

	      // Enable the texture
	      texture.enable(gl);
	      // gl.glEnable(GL_TEXTURE_2D);

	      // Run the build lists after initializing the texture
	      buildDisplayLists(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

	      if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;

	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);

	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

	      // Enable the model-view transform
	      gl.glMatrixMode(GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset
	}
	

	   
	   private void buildDisplayLists(GL2 gl) {
	      // Build two lists, and returns handle for the first list
	      int base = gl.glGenLists(2);
	      
	      // Create a new list for box (with open-top), pre-compile for efficiency
	      boxDList = base;
	      gl.glNewList(boxDList, GL_COMPILE);

	      gl.glBegin(GL_QUADS);
	      // Front Face
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(-1.0f, -1.0f, 1.0f); // bottom-left of the texture and quad
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(1.0f, -1.0f, 1.0f);  // bottom-right of the texture and quad
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, 1.0f);   // top-right of the texture and quad
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, 1.0f);  // top-left of the texture and quad
	      // Back Face
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(1.0f, -1.0f, -1.0f);
	      // Top Face
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      // Bottom Face
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(1.0f, -1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(-1.0f, -1.0f, 1.0f);
	      // Right face
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(1.0f, -1.0f, 1.0f);
	      // Left Face
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(-1.0f, -1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, -1.0f);

	      gl.glEnd();
	      gl.glEndList();

	      // Ready to make the second display list
	      topDList = boxDList + 1;
	      gl.glNewList(topDList, GL_COMPILE);
	      gl.glBegin(GL_QUADS);
	      // Top Face
	      gl.glTexCoord2f(textureLeft, textureTop);
	      gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(textureLeft, textureBottom);
	      gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureBottom);
	      gl.glVertex3f(1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(textureRight, textureTop);
	      gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glEnd();
	      gl.glEndList();
	   }
}
