package graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import engine.Var;
import engine.generation.Generate;
import engine.world.Block;
import engine.world.Chunk;
import engine.world.ChunkColumn;
import graphics.textures.BlockTexture;
import graphics.textures.TextureProcess;

/*
 * This class's init method will initialize all things needed for the game side, such as chunks and coordinates
 * This class's method loop() will be called during the game loop
 * Only methods that work with rendering and the game engine should be here
 * No methods that are required to run OpenGL are here
 * This is all personalized code, no copying
 * 
 * @date 2018-04-10
 */

public class Game {

	public static void initialize() {
		
		Var.showCursor = false;
		
	    Var.block = new Shader("Block.vs", "Block.fs");
	    

	    BlockTexture.initializeBlockTextures();
	    Var.camera = new Camera();    
	    
	    //Var.column = new ChunkColumn(0, 0);
		//Generate.Generate();
	    
	    
	    //This loop will fill the world with chunks!
	    //TODO Make this happen in the generation class
	    
	    int s = 1;
	    
	    for (int i = 0; i < 1; i++) {
	    	for (int j = 0; j < 1; j++) {
	    		Var.loadedChunkColumns.add(new ChunkColumn(i, j));
	    	}
	    }
	    
	    
	    Var.block.use();
		Var.block.bindUniformInt("ourTexture", 1);
		
		//Sets the light to the player's position
		Var.block.bindUniformVec3f("lightPos", Var.player.getCameraPos());
	}
	
	public static void loop() {
		//Set active textures
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, BlockTexture.blockTextures.getTexture());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, BlockTexture.nullTexture.getTexture());
		
		
		//Update camera
		Var.camera.updateCamera();
		
		//Update Light position (Temp)
		//Var.block.bindUniformVec3f("lightPos", Var.player.getCameraPos());
		
		//Custom rendering below 
		Var.block.use();
		Var.camera.bindMatrixToShader(Var.block);
		Var.block.bindUniformInt("ourTexture", 0); //0 is the block textures
		//BlockRender.model(Var.stone);
		
		
		//Why tho
		

		
		
		//glBindVertexArray(Var.stone.getVAO()); //We can use 1 VAO for all blocks, we just need to do transformations
		//glDrawArrays(GL_TRIANGLES, 0, 36);
		glBindVertexArray(0);
		//Var.column.renderColumn();
		
		//TODO Do multithreading some day, today is not the day though
		try{
			Object[] copy = Var.loadedChunkColumns.toArray();
			for (Object o : copy) {
				ChunkColumn cc = (ChunkColumn) o;
				cc.renderColumn();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
			//System.exit(0);
		}

		
		//System.out.println(Var.deltaTime);
	}

	
}
