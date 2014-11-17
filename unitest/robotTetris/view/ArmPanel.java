package robotTetris.view;

import robotTetris.basic.Point3D;
import robotTetris.controller.Controller;

import javax.swing.*;
import java.awt.*;

/*
 * Created by robin on 2014/11/11.
 */

public class ArmPanel extends JPanel {

	private static ArmPanel self;
	
	private final int WIDTH = 500, HEIGHT = 500;
	
	public static ArmPanel getInstance() {
		
		if (self == null)
			self = new ArmPanel();
		return self;
	}
	
	private ArmPanel() {
		
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		
		setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(10));
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.setColor(Color.RED);
		Point3D mid = Controller.getArmMidLocation();
		g2d.drawLine(0, HEIGHT, mid.x, HEIGHT - mid.y);

		g2d.setColor(Color.WHITE);
		Point3D head = Controller.getArmHeadLocation();
		g2d.drawLine(mid.x, HEIGHT - mid.y, head.x, HEIGHT - head.y);
	}
}