package graphics;

import static org.lwjgl.opengl.GLCapabilities.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GLXCapabilities.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.openvr.Texture;
import org.lwjgl.system.MemoryStack;

import engine.Load;
import engine.Var;
import engine.world.Block;
import engine.world.Player;
import input.Input;

/*
 * @date 2018-04-01
 * @description provides an initializing code, and a loop for the game to run
 */

public class Frame implements Runnable {

	public static long window;
	
	@Override
	public void run() {
		init();
	}
	
	public static void init() {
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to inialize GLFW :(");
		}
		
		//All the settings
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);
		
		window = glfwCreateWindow(Var.width, Var.height, "Minecraft Testing", NULL, NULL);
		input.Callback.createCallbacks();
	    try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
	    }
	    
	    glfwMakeContextCurrent(window);
	    glfwSwapInterval(1);
	    glfwShowWindow(window);
	    
	    
	    GL.createCapabilities();
		
	    //Important little settings
	    //glClearColor(0.4f, 0.4f, 0.8f, 1.0f);
	    glClearColor(0.04f, 0.04f, 0.08f, 1.0f);
	    
	    glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	    //End of settings
	    
	    //TEMP Make sure you organize this into ANOTHER METHOD this is only for testing purposes!!!!!!!!!!!!
	    Game.initialize();
	   // new Thread(new Load()).start();
	    
		loop();
	}
	
	//The most important method in the program, it keeps it running
	public static void loop() {
		
		long time0 = System.currentTimeMillis();
		long time;
		
		while (!glfwWindowShouldClose(window)) {
			
			//Loop Code
			time = System.currentTimeMillis();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			
			//Process input
			Input.processInput(window);
			
			//Places the cursor position in the center
			if (!Var.showCursor) {
				glfwSetCursorPos(window, Var.width / 2 , Var.height / 2);
			}
			
			Game.loop();

			
			//It is important that this code is placed after the rendering
			glfwSwapBuffers(window);
			glfwPollEvents();
			Var.deltaTime = (int) (System.currentTimeMillis() - time);
			if (Var.deltaTime > 16) {
				System.out.println("Lagged at " + (System.currentTimeMillis() - time0) + " for " + Var.deltaTime);
			}
			//System.out.println((1 / (double) Var.deltaTime) * 1000 + " FPS");
			
			//This block is used to make sure the game runs always at 60 fps
			/*
			try {
				Thread.sleep(17 - (System.currentTimeMillis() - time));
			}  catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println("Lagged at " + (System.currentTimeMillis() - time0));
			}*/
		}
		//Terminating code below
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		System.out.println("Terminated");
		System.exit(0);
		
	}
	
	
}
