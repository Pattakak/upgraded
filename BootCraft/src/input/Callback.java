package input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import static org.lwjgl.opengl.GL11.*;
import engine.Var;
import graphics.Frame;
import engine.generation.Generate.*;

/*
  * This class will set up callbacks that are called when the state of the frame is changed
  * This will be used for keys, and for the mouse
  * 
  * @date 2018-04-08 
  * 
  */
public class Callback {

	public static void createCallbacks() {
		
		glfwSetInputMode(Frame.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		
		glfwSetKeyCallback(Frame.window, (window, key, scancode, action, mods) -> {
			 //Put input here
			 //The escape button closes the window
	    	 if (key == GLFW_KEY_ESCAPE) {
	    		 glfwSetWindowShouldClose(Frame.window, true);
	    	 }
	    	 if (glfwGetKey(Frame.window, GLFW_KEY_F3) == GLFW_PRESS) {
	    		 Var.player.printInfo();
	    	 }
	    	 if (glfwGetKey(Frame.window, GLFW_KEY_F1) == GLFW_PRESS) {
	    		 Var.showCursor = !Var.showCursor;
	    		 if (Var.showCursor) {
	    			glfwSetInputMode(Frame.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	    		 } else {
	    			glfwSetInputMode(Frame.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	    		 }
	    	 }
	    	 if (glfwGetKey(Frame.window, GLFW_KEY_F5) == GLFW_PRESS) {
	    		 engine.generation.Generate.Generate((long) 1283712037);
	    		 //TODO actually implement seeds
	    		 
	    	 }
	    	 if (glfwGetKey(Frame.window, GLFW_KEY_L) == GLFW_PRESS) {
	    		
	    		 Var.block.bindUniformVec3f("lightPos", Var.player.getCameraPos());
	    		 
	    	 }
	    	 
	    });
		
		glfwSetWindowSizeCallback(Frame.window, new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height){
                Var.width = width;
                Var.height = height;
                Var.camera.updatePerspective();
                glViewport(0, 0, Var.width, Var.height);
                //System.out.println("Resizing");
            }
        });
		
		GLFWCursorPosCallback posCallback;
		glfwSetCursorPosCallback(Frame.window, posCallback = GLFWCursorPosCallback.create((window, xPos, yPos) -> {
			if (!Var.showCursor) {
				Var.player.changeDirection(xPos - Var.width / 2, yPos - Var.height / 2);
			}
		 }));
	}
	
}
