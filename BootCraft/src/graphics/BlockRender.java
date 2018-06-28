package graphics;

import engine.Var;
import engine.world.Block;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/*
 * @date 2018-04-01
 * @description This class provides shaders and textures to render a block, it will also do Matrix Math to render the blocks for us
 * 
 * Also, this class is completely a thing of the past
 * We no longer use this class because model matrices make the game run slow
 * This class has been abandoned on 2018-04-15
 * 
 * RIP 2018-2018
 */

public class BlockRender {
	
	//TEMP for testing purposes ONLY!!!!!
	static float rotation = 0f;
	//End temp

	//This method generates a model matrix for a block so it can be transformed into world space
	public static Matrix4f generateModel(Block b) {
		Matrix4f model = new Matrix4f();
		
		//This offset vector is the offset of the block, it is crucial in transforming the block into world space
		int[] relativePosition = b.getPos();
		int[] chunkLoc = b.getChunkLoc(); //TODO the code fails to get a chunk location
		int[] position = new int[3];
		try{
			for (int i = 0; i < 3; i++) {
				position[i] = 16 * chunkLoc[i] + relativePosition[i];
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			position = relativePosition;
		}
		Vector3f offset = new Vector3f((float) (position[0] + .5f),(float) (position[1] + .5),(float) (position[2] + .5));
		model.translate(offset);
		//model.identity();
		return model;
	}
	
	//BEGIN TEMP
	//Anything that depends on rotation is only temporary and it is added because the textures must be tested
	
	public static void rotate() {
		rotation += .01f;
	}
	
}
