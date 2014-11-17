package robotTetris.view;

import robotTetris.basic.Point3D;
import robotTetris.controller.Controller;

import javax.swing.*;
import java.awt.*;

/*
 * Created by robin on 2014/11/11.
 */

public class CameraPanel extends JPanel {

	private static CameraPanel self;
	
	private final int WIDTH = 500, HEIGHT = 500;
	
	public static CameraPanel getInstance() {
		
		if (self == null)
			self = new CameraPanel();
		return self;
	}
	
	private CameraPanel() {
		
		setSize(500, 500);
		setLayout(null);
		
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		int midX = WIDTH/2;
		int midY = HEIGHT/2;
		int radius = 10;
		Point3D camera = Controller.getCameraLocation();
		g.fillOval(camera.x + midX - radius, camera.y + midY + radius, radius, radius);
	}
}