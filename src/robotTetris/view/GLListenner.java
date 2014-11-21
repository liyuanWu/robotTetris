package robotTetris.view;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import robotTetris.basic.Point3D;
import robotTetris.controller.Controller;
import robotTetris.logic.Cube;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Vector;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.GL_COMPILE;
import static javax.media.opengl.GL2.GL_QUADS;
import static javax.media.opengl.GL2ES1.GL_LIGHT_MODEL_AMBIENT;
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

    private Texture floorTexture; // texture over the shape
    private String floorTextureFileName = "images/floor.jpg";
    private String floorTextureFileType = ".jpg";

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

    private void drawCube(Point3D p1, Point3D p2, Point3D p3, Point3D p4,Point3D p5, Point3D p6, Point3D p7, Point3D p8, GL2 gl){
       Point3D[] pointArray = new Point3D[]{p1,p2,p3,p4,p5,p6,p7,p8};
       final int index_list[][] = new int[][]{
               {0, 1, 2, 3},
               {4, 5, 6, 7},
               {0, 1, 5, 4},
               {0, 3, 7, 4},
               {1, 2, 6, 5},
               {2, 3, 7, 6}
        };

       for(int i=0;i<6;i++){
           gl.glBegin(GL_QUADS);
           gl.glVertex3d(pointArray[index_list[i][0]].x,pointArray[index_list[i][0]].y,pointArray[index_list[i][0]].z);
           gl.glVertex3d(pointArray[index_list[i][1]].x,pointArray[index_list[i][1]].y,pointArray[index_list[i][1]].z);
           gl.glVertex3d(pointArray[index_list[i][2]].x,pointArray[index_list[i][2]].y,pointArray[index_list[i][2]].z);
           gl.glVertex3d(pointArray[index_list[i][3]].x,pointArray[index_list[i][3]].y,pointArray[index_list[i][3]].z);
           gl.glEnd();
       }

    }
    private void drawArm(Point3D armHead, Point3D armBase, float armWidth, GL2 gl){
        float armLength = (float) Math.sqrt(Math.pow(armHead.x - armBase.x, 2) + Math.pow(armHead.y - armBase.y, 2));
        float varX = armWidth * (armHead.y-armBase.y) / armLength;
        float varY = armWidth * (armHead.x-armBase.x) / armLength;
        Point3D p1 = new Point3D(armHead.x - varX,armHead.y + varY,armWidth);
        Point3D p2 = new Point3D(armHead.x + varX,armHead.y - varY,armWidth);
        Point3D p3 = new Point3D(armBase.x + varX,armBase.y - varY,armWidth);
        Point3D p4 = new Point3D(armBase.x - varX,armBase.y + varY,armWidth);
        Point3D p5 = new Point3D(armHead.x - varX,armHead.y + varY,-armWidth);
        Point3D p6 = new Point3D(armHead.x + varX,armHead.y - varY,-armWidth);
        Point3D p7 = new Point3D(armBase.x + varX,armBase.y - varY,-armWidth);
        Point3D p8 = new Point3D(armBase.x - varX,armBase.y + varY,-armWidth);
        this.drawCube(p1,p2,p3,p4,p5,p6,p7,p8,gl);
    }
	@Override
	public void display(GLAutoDrawable drawable) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
	      gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	      gl.glLoadIdentity();  // reset the model-view matrix

          float cameraX = Controller.getCameraLocation().x,cameraY = Controller.getCameraLocation().y;
//          double cameraLength = Math.sqrt(cameraX * cameraX + cameraY * cameraY);
//          double directionX = cameraX - cameraY/cameraLength;
//          double directionY = cameraY + cameraX/cameraLength;
          glu.gluLookAt(cameraX,cameraY,Controller.getCameraLocation().z,0,0,0,0,1,0);


        gl.glBegin(GL_QUADS);
        gl.glColor3f(1.0f,0.0f,0.0f);
        gl.glVertex3f(-50, -1, 50);
        gl.glColor3f(0.0f,1.0f,0.0f);
        gl.glVertex3f( 50, -1, 50);
        gl.glColor3f(0.0f,0.0f,1.0f);
        gl.glVertex3f( 50, -1, -50);
        gl.glColor3f(1.0f,1.0f,1.0f);
        gl.glVertex3f(-50, -1, -50);
        gl.glEnd();
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


          gl.glColor3f(1f,1f,0f);
	      ArrayList<Cube> dynamitingCubes = Controller.getDynamite();
          for(Cube cube: dynamitingCubes){
              this.drawCube(cube,gl);
          }

        gl.glColor3f(0f,1f,0f);
        drawArm( Controller.getArmMidLocation(),new Point3D(0,0,0),(float)Controller.getArmWidth(),gl);
        gl.glColor3f(0f,1f,1f);
        drawArm( Controller.getArmHeadLocation(), Controller.getArmMidLocation(),(float)Controller.getArmWidth(),gl);
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
             floorTexture = TextureIO.newTexture(
                      this.getClass().getResource(floorTextureFileName), false, floorTextureFileType);
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

        // Diffuse light comes from a particular location. Diffuse's value in RGBA
        float[] lightDiffuseValue = {1.0f, 1.0f, 1.0f, 1.0f};
        // Diffuse light location xyz (in front of the screen).
        float lightDiffusePosition[] = {0.0f, 0.0f, 2.0f, 1.0f};

        float Light_Model_Ambient[] = { 0.6f , 0.6f , 0.6f , 1.0f }; // ȱʡֵ

        gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(Light_Model_Ambient));
        gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuseValue, 0);
        gl.glLightfv(GL_LIGHT1, GL_POSITION, lightDiffusePosition, 0);
        gl.glEnable(GL_LIGHT1);    // Enable Light-1
        gl.glEnable(GL_LIGHTING); // But disable lighting
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
