package input;

/*
 * @date 2018-04-06
 * @description This class will process the input of keys that are pressed
 */

import org.lwjgl.glfw.GLFW;

import engine.Var;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	
	//This method should only process input which is a held down key, and not a single press
	public static void processInput(long window) {
		//Move player side to side
		if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
			Var.player.movePlayer(0);
		}
		if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
			Var.player.movePlayer(1);
		}
		if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
			Var.player.movePlayer(2);
		}
		if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
			Var.player.movePlayer(3);
		}
		
		//Move player up and down
		if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
			Var.player.movePlayerVertical(-1);
		}
		if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
			Var.player.movePlayerVertical(1);
		}
	}

}
