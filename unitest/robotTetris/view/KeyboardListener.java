package robotTetris.view;

import robotTetris.controller.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
 * Created by robin on 2014/11/11.
 */

public class KeyboardListener extends KeyAdapter {

	private final int CAMERA_LEFT = KeyEvent.VK_LEFT;
	private final int CAMERA_RIGHT = KeyEvent.VK_RIGHT;
	
	private final int BASE_ARM_LEFT = KeyEvent.VK_A;
	private final int BASE_ARM_RIGHT = KeyEvent.VK_D;
	private final int HEAD_ARM_LEFT = KeyEvent.VK_J;
	private final int HEAD_ARM_RIGHT = KeyEvent.VK_L;
	
	@Override
	public void keyPressed(KeyEvent event) {
		
		int value = event.getKeyCode();
		
		switch (value) {
		case CAMERA_LEFT : {
			Controller.rotateLeftCamera();
			break;
		}
		case CAMERA_RIGHT : {
			Controller.rotateRightCamera();
			break;
		}
		case BASE_ARM_LEFT : {
			Controller.rotateLeftBaseArm();
			break;
		}
		case BASE_ARM_RIGHT : {
			Controller.rotateRightBaseArm();
			break;
		}
		case HEAD_ARM_LEFT : {
			Controller.rotateLeftHeadArm();
			break;
		}
		case HEAD_ARM_RIGHT : {
			Controller.rotateRightHeadArm();
			break;
		}
		}
		
		Frame.getInstance().repaint();
	}
}