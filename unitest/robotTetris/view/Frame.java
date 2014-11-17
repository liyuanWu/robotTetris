package robotTetris.view;

import javax.swing.JFrame;

/*
 * Created by robin on 2014/11/11.
 */

public class Frame extends JFrame {

	private static Frame self;
	
	private ArmPanel armPanel;
	private CameraPanel cameraPanel;
	
	private KeyboardListener keyboardListener;
	
	public static Frame getInstance() {
		
		if (self == null)
			self = new Frame();
		return self;
	}
	
	private Frame() {
		
		setSize(1000, 550);
		setLayout(null);
		
		keyboardListener = new KeyboardListener();
		addKeyListener(keyboardListener);
		
		armPanel = ArmPanel.getInstance();
		armPanel.setLocation(0, 0);
		add(armPanel);
		
		cameraPanel = CameraPanel.getInstance();
		cameraPanel.setLocation(500, 0);
		add(cameraPanel);
	}
}