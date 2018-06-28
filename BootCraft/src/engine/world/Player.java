package engine.world;

/*
 * @date 2018-04-04
 * @description Provides variables for player, such as camera position and angle
 */

import org.joml.Vector3f;

import engine.Var;

public class Player {

	float xPos, yPos, zPos;
	float yaw, pitch;
	float speed = .005f; //This is in units per millisecond
	double sensitivity = .001; //This is radians per pixel of mouse moved
	
	public Player() {
		this.xPos = 0f;
		this.yPos = 0f;
		this.zPos = 0f;
		this.yaw = 0f;
		this.pitch = 0f;
	}
	
	public Player(float xPos, float yPos, float zPos, float yaw, float pitch) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.yaw = yaw;
		this.pitch = pitch;
		
	}
	
	public void movePlayer(int direction) {
		/*
		 * 0 is forward
		 * 1 is left
		 * 2 is down
		 * 3 is right
		 */
		
		
		//This entire method is really shortened down because of beautiful logic about trigonometry
		//However, it does not mean that this will work
		//If there are any problems that come up with moving the player, refer to here
		float angle = (float) (this.yaw + Math.PI / 2  * direction);
		
			//zPos needs to be negative because if you look in the negative y direction, then you will see x go right and z go down
			//This means that z needs to be flipped so it can follow trigonometry standards
		
			this.xPos += Math.cos(angle) * speed * Var.deltaTime;
			this.zPos -= Math.sin(angle) * speed * Var.deltaTime;
	}
	
	public void movePlayerVertical(int direction) {
		/*
		 * 1 is up
		 * -1 is down
		 */
		
		//TODO Fix the spooky magic case of the inverted coordinate system
		this.yPos += (float) ((float) direction * this.speed * Var.deltaTime);
	}
	
	//Uses the mouse input to change the direction of pitch and yaw
	public void changeDirection(double deltaX, double deltaY) {
		//The movement is inverted so we make it minus instead of plus
		//For yaw, we use counterclockwise as positive, so if we go left, we go positive
		//deltaX is positive with right movement
		this.yaw -= deltaX * this.sensitivity;
		this.pitch -= deltaY * this.sensitivity;
		
		if (this.pitch > Math.PI / 2) {
			this.pitch = (float) Math.PI / 2 - .001f; //If the .001f wasnt there, the camera would flip if you looked up
		} else if (this.pitch < -Math.PI / 2) {
			this.pitch = (float) (-Math.PI / 2 + .001f);
		}
	}
	
	public void printInfo() {
		float yawDeg = (float) (this.yaw / Math.PI * 180);
		float pitchDeg = (float) (this.pitch / Math.PI * 180);
		
		System.out.println("");
		System.out.println("(" + this.xPos + ", " + this.yPos + ", " + this.zPos + "), (" + yawDeg + ", " + pitchDeg + ")");
	}
	
	public Vector3f getCameraPos() {
		return new Vector3f(this.xPos, this.yPos, this.zPos);
	}
	
	public float getPitch() {
		return this.pitch;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public float getXPos() {
		return this.xPos;
	}
	
	public float getZPos() {
		return this.zPos;
	}
}
