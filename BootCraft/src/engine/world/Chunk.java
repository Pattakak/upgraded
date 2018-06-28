package engine.world;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import engine.Var;
import engine.world.blocks.Dirt;
import engine.world.blocks.Grass;
import engine.world.blocks.Stone;
import graphics.textures.BlockTexture;

/*
 * This class provides chunk data for one chunk
 * A chunk is a block of 16 * 16 * 16 blocks
 * 
 */

public class Chunk {

	int[] chunkLoc;
	int[] blockIDs; //There are 16 ^ 3 blocks in each chunk
	Block[] blocks;
	ArrayList<Block> renderedBlocks; //This is an array of all block VAOS that should be rendered at the time, it will update whenever a block is broken or placed
				  //The coordinates will be 0xXYZ, where x, y, and z range from 0x0 to 0xf
	
	public Chunk(int[] location) {
		this.blockIDs = new int[0x1000]; //16 ^ 3 blocks in the chunk
		this.chunkLoc = location;
	}
	public Chunk(int xLoc, int yLoc, int zLoc) {
		this.blockIDs = new int[0x1000];
		this.chunkLoc = new int[3];
		this.chunkLoc[0] = xLoc;
		this.chunkLoc[1] = yLoc;
		this.chunkLoc[2] = zLoc;
	}
	
	//Possibly the most important method in the game, it takes an entire chunk and renders it
	public void renderChunk() {
		
		Var.block.use(); //This is a VAO for a generic block, once the game becomes more complex, more VAOS might be used
		//Var.block.bindUniformInt("ourTexture", 0);
		
		for (Block b : this.renderedBlocks) {	
			if(b != null) {
				if (b.faceAmount != 0) {
					Var.block.use();
					//Var.block.bindUniformInt("ourTexture", b.getType()); //TODO Organize this code so texture values can change, or not idk. This is a problem for later tbh
					//Var.block.bindUniformMatrix4fv("model", b.model);
					glBindVertexArray(b.getVAO());
					glDrawArrays(GL_TRIANGLES, 0, b.getFaceAmount() * 6); //12 triangle in full cube, times 3 vertices
					glBindVertexArray(0);
					//System.out.println("Rendering Block??? " + b);
				}
			}
		} 
	}
	
	//This method was created to solve the problem of crashes
	//Before this method was created, chunks would go through the entire process of giving block faces every single frame
	//This is super wasteful
	//So this method makes new VAOs only when a chunk is updated, like when it is made, loaded in, or a block has changed in it
	public void updateChunk() {
		this.blocks = new Block[0x1000];
		this.renderedBlocks = new ArrayList<Block>();
		
		for (short i = 0; i < 0x1000; i++) {
			if (this.blockIDs[i] == 0) { //If the block id is 0, there will be a null block which is air
				this.blocks[i] = null;
			} else {
				
				//Take short 0x24a, which will be 00000010 00100111
				//b >> 8 makes it 00000000 00000010
				//0xf is          00000000 00001111
				//With the & operator, it will give us 3, 2 which is our desired x Value
				
				int xPos = ((i >> 8) & 0xf);
				int yPos = ((i >> 4) & 0xf);
				int zPos = ((i >> 0) & 0xf);
				
				int[] blockPos = {xPos, yPos, zPos};
				
				Byte faces = getFaces(i);
				if (faces == 0) { //We nullify a block if we can't see it
					this.blocks[i] = null;
				} else {
					if (this.blockIDs[i] == 1) {
						this.blocks[i] = new Stone(this, blockPos,blockIDs[i], faces);
					} else if (this.blockIDs[i] == 2) {
						this.blocks[i] = new Dirt(this, blockPos, blockIDs[i], faces);
					} else if (this.blockIDs[i] == 3) {
						this.blocks[i] = new Grass(this, blockPos,blockIDs[i], faces);
					} else {
						this.blocks[i] = new Block(this, blockPos, blockIDs[i], faces);
					}
					
					this.renderedBlocks.add(this.blocks[i]);
				}
				
			}
		} //End of for loop
	}
	
	byte getFaces(int blockLoc) { //Takes a block with an id (0xfff) for example, and it gives back which faces the game should render
		//TODO add support for multichunk hiding of faces
		//Right now, edge blocks will be rendered
		
		Byte faces = 0b0;
		int xPos = ((blockLoc >> 8) & 0xf);
		int yPos = ((blockLoc >> 4) & 0xf);
		int zPos = ((blockLoc >> 0) & 0xf);
		
		if (xPos == 0) {faces = (byte) (faces | 0b010000);}
		if (xPos == 15) {faces = (byte) (faces | 0b100000);}
		if (yPos == 0) {faces = (byte) (faces | 0b000100);}
		if (yPos == 15) {faces = (byte) (faces | 0b001000);}
		if (zPos == 0) {faces = (byte) (faces | 0b000001);}
		if (zPos == 15) {faces = (byte) (faces | 0b000010);}
		
		//This block of coding is probably the stupidest, laziest code I've ever done, but it works
		try { if (this.blockIDs[blockLoc + 256] == 0) {faces = (byte) (faces | 0b100000);}} catch (ArrayIndexOutOfBoundsException e) {}
		try { if (this.blockIDs[blockLoc - 256] == 0) {faces = (byte) (faces | 0b010000);}} catch (ArrayIndexOutOfBoundsException e) {}
		try { if (this.blockIDs[blockLoc + 16] == 0) {faces = (byte) (faces | 0b001000);}} catch (ArrayIndexOutOfBoundsException e) {}
		try { if (this.blockIDs[blockLoc - 16] == 0) {faces = (byte) (faces | 0b000100);}} catch (ArrayIndexOutOfBoundsException e) {}
		try { if (this.blockIDs[blockLoc + 1] == 0) {faces = (byte) (faces | 0b000010);}} catch (ArrayIndexOutOfBoundsException e) {}
		try { if (this.blockIDs[blockLoc - 1] == 0) {faces = (byte) (faces | 0b000001);}} catch (ArrayIndexOutOfBoundsException e) {}
		
		return faces;
		
		
	}
	
	//Fills in an entire chunk with one block
	public void fill(int blockType) {
		Arrays.fill(blockIDs, blockType);
	}
	
	public void placeBlock(int position, int id) {
		this.blockIDs[position] = id;
	}
	
	public void fillBelow(int position, int id) {
		int yPos = ((position >> 4) & 0xf);
		for (int i = yPos; i >= 0; i--) {
			this.placeBlock(position - yPos * 16 + i * 16, id);
		}
	}
	
	public int[] getChunkLocation() {
		return this.chunkLoc;
	}
	
	public int[] getBlockIDs() {
		return this.blockIDs;
	}
	
	
}
