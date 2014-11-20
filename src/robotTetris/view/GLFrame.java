package view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class GLFrame extends JFrame {

	private static GLFrame self;
	
	private static final int CANVAS_WIDTH = 640;
	private static final int CANVAS_HEIGHT = 480;
	private static final int FPS = 60;
	
	public static GLFrame getInstance() {
		
		if (self == null)
			self = new GLFrame();
		return self;
	}
	
	private GLFrame() {
		
		// Create the OpenGL rendering canvas
	    GLCanvas canvas = new GLCanvas();  // heavy-weight GLCanvas
	    canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	    GLListenner gl = new GLListenner();
	    canvas.addGLEventListener(gl);

	    // For Handling KeyEvents
	    KeyboardListener key = new KeyboardListener();
	    canvas.addKeyListener(key);
	    canvas.setFocusable(true);
	    canvas.requestFocus();

	    // Create a animator that drives canvas' display() at the specified FPS. 
	    final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
	      
	    // Create the top-level container frame
	    getContentPane().add(canvas);
	    addWindowListener(new WindowAdapter() {
	         @Override 
	         public void windowClosing(WindowEvent e) {
	        	 // Use a dedicate thread to run the stop() to ensure that the
	        	 // animator stops before program exits.
	        	 new Thread() {
	        		 @Override 
	        		 public void run() {
	        			 animator.stop(); // stop the animator loop
	        			 System.exit(0);
	        		 }
	        	 }.start();
	         }
	    });
	    pack();
	    animator.start();
	}
}