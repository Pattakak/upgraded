package graphics;

import org.joml.Matrix4f;

/*
 * @date 2018-04-06
 * @description This class is used in conjunction of the player class to set the camera angles and position
 * 
 * For reference, visit
 * https://learnopengl.com/Getting-started/Camera
 * https://joml-ci.github.io/JOML/apidocs/org/joml/Vector3f.html
 */

import org.joml.Vector3f;
import engine.Var;


public class Camera {

	Vector3f cameraPos;
	Vector3f cameraDir; //Camera direction calculated from pitch and yaw values
	Vector3f cameraRight; //This is the right vector of the cameras position, it is in the direction of the cross product of j hat and the camera direction
	Vector3f cameraUp; 
	
	Matrix4f view = new Matrix4f();
	Matrix4f projection = new Matrix4f();
	double FOV = 90; //In degrees
	float width = Var.width; float height = Var.height; //TODO make it resizable
	
	//This is a camera constructor that is dependent on the player's position
	public Camera() {
		projection.identity();
		//FOV, aspect ratio, near z, far z
		projection.perspective((float) Math.toRadians(FOV), (float) (width / height), .1f, 100f);
		
		//Uncomment this command for unlimited fun!
		//projection.ortho(-10f, 10f, -10f, 10f, .0001f, 100f);
		this.updateCamera();
	}
	
	//This will be a constructor for a static camera that does not move, only for the FUTURE!!!
	public Camera(float xPos, float yPos, float zPos, float pitch, float yaw, float roll) {
		//TODO THIS!
	}
	
	void updateCamera() {
		cameraPos = Var.player.getCameraPos();
		
		float pitch = Var.player.getPitch();
		float yaw = Var.player.getYaw();
		
		
		//zDir must be negative for the same reasons as described in the player class
		float xDir = (float) (Math.cos(yaw) * Math.cos(pitch));
		float yDir = (float) (Math.sin(pitch));
		float zDir = (float) -(Math.sin(yaw) * Math.cos(pitch));
		cameraDir = new Vector3f(xDir, yDir, zDir);
		
		cameraRight = new Vector3f(0f, 1f, 0f);
		cameraRight.cross(cameraDir).normalize();
		cameraUp = cameraRight;
		cameraUp.cross(cameraDir).mul(-1);
		//cameraUp.mul(-1);
		
		//Creation of the view matrix
		
		//Camera position, point in space to look at, up vector
		//Edit this if there are any problems
		view.identity();
		view.lookAt(cameraPos, cameraDir.add(cameraPos), cameraUp);	
	}
	
	public void updatePerspective() {
		projection.identity();
		//FOV, aspect ratio, near z, far z
		projection.perspective((float) Math.toRadians(FOV), (float) (width / height), .1f, 100f);
	}
	
	//This method binds the view matrix to a shader
	public void bindMatrixToShader(Shader shader) {
		shader.bindUniformMatrix4fv("view", view);
		shader.bindUniformMatrix4fv("projection", projection);
	}
	
}
