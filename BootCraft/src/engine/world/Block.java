package engine.world;

/*
 * @date 2018-04-01
 * @description This class will provide a block object and all necessary data for it
 */
 
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import graphics.BlockRender;
import graphics.Shader;
import static org.lwjgl.opengl.GL15.*;

public class Block {

	int[] pos; //This array will have the x, y, and z coordinates of a block, must be integer
	int type; //This is a number that will determine what type of block this is, a list will be generated when the game starts working
	float[] vertices;
	int[] indices;
	int VBO, VAO;
	int[] chunkLocation;
	int faceAmount;
	Matrix4f model;
	
	/*
	 * Types of blocks:
	 * 
	 * 0: air
	 * 1: stone
	 * 2: dirt
	 * 3: grass (Special Class)
	 */
	
	
	public Block(Chunk chunk, int[] pos, int type, Byte renderFaces) {
	
		this.chunkLocation = chunk.getChunkLocation();
		//this.chunkLocation = chunkLoc;
		/*
		for (int i = 0; i < 3; i++) {
			this.chunkLocation[i] = chunkLoc[i];
			this.chunkLocation[i] = chunk.getChunkLocation()[i];
		}*/
		
		//These next two if statements make sure the position and type values are within range
		if (pos.length == 3) {
			this.pos = pos;
		} else {
			throw new IllegalArgumentException("Position coordinates must have three values");
		}
		
		//Since the coordinates are localized to be relative to a chunk, they must range between 0 and 15 for each coordinate
		
		if (this.pos[0] < 0 || this.pos[0] > 15 || this.pos[1] < 0 || this.pos[1] > 15 || this.pos[2] < 0 || this.pos[2] > 15) {
			throw new IllegalArgumentException("Position coordinates must be in bounds");
		}
		
		if (type <= engine.Var.blockTypes && type >= 0) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("Block type must be between 0 and " + engine.Var.blockTypes);
		}
		
		this.makeFaceAmount(renderFaces);
		
		//Set up and bind the VAO
		
			this.VAO = glGenVertexArrays();
			this.VBO = glGenBuffers();
		
		//this.EBO = glGenBuffers();
		
		float[] worldVertices = translateVertices(generateVertices(renderFaces));
		
		glBindVertexArray(this.getVAO());
		glBindBuffer(GL_ARRAY_BUFFER, this.VBO);
		glBufferData(GL_ARRAY_BUFFER, worldVertices, GL_STATIC_DRAW);
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.EBO);
		//glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		
		//Unbind VAO
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0); 
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 32, 12);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 32, 20);
		glEnableVertexAttribArray(2);
		
		
		//index, amount of floats, type, ???, stride (how many bytes is in between the next set of vertices) (6 * 4 = 24), offset)
		//A float has 4 bytes 
		
		//this.model = BlockRender.generateModel(this);
		
		
		
		
	}
	
	float[] translateVertices(float[] modelVertices) {
		int[] relativePosition = this.getPos();
		int[] chunkLoc = this.getChunkLoc(); //TODO the code fails to get a chunk location
		int[] position = new int[3];
		
		
		
		try{
			for (int i = 0; i < 3; i++) {
				position[i] = 16 * chunkLoc[i] + relativePosition[i];
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			position = relativePosition;
		}

		float xOff = (float) (position[0] + .5f);
		float yOff = (float) (position[1] + .5f);
		float zOff = (float) (position[2] + .5f);
		
		for (int i = 0; i < modelVertices.length / 8; i++) { //Divided by five because that is how many values are in each set of coordinates, 3 coords, 2 texcoords
			modelVertices[8 * i + 0] += xOff; 
			modelVertices[8 * i + 1] += yOff;
			modelVertices[8 * i + 2] += zOff;
		}
		return modelVertices;
	}
	
	protected float[] generateVertices(Byte faces) {
		//This is a byte that has a bit that says whether the face should be rendered or not
		//it will be xPos, xNeg, yPos, yNeg, zPos, zNeg
		//0b111111 will render all faces
		
		//TODO implement this tomorrow
		//I'm tired.
		
		
		// First 3 is vertices, second 2 is texture mapping coords
		float yPos[] = { //The top side
			-.5f, .5f, -.5f, 1f, 1f,  0f, 1f, 0f,
 			-.5f, .5f, .5f,  0f, 1f,  0f, 1f, 0f,
			.5f, .5f, -.5f,  1f, 0f,  0f, 1f, 0f,
			
			.5f, .5f, .5f,   0f, 0f,  0f, 1f, 0f,
			.5f, .5f, -.5f,  1f, 0f,  0f, 1f, 0f,
			-.5f, .5f, .5f,  0f, 1f,  0f, 1f, 0f
		};
		
		float yNeg[] = { //The bottom side	
			.5f, -.5f, -.5f,  1f, 0f,  0f, -1f, 0f,
			-.5f, -.5f, .5f,  0f, 1f,  0f, -1f, 0f,
			-.5f, -.5f, -.5f, 1f, 1f,  0f, -1f, 0f,
			
			.5f, -.5f, .5f,   0f, 0f,  0f, -1f, 0f,
			-.5f, -.5f, .5f,  0f, 1f,  0f, -1f, 0f,
			.5f, -.5f, -.5f,  1f, 0f,  0f, -1f, 0f
		};
		
		float zPos[] = {
			.5f, .5f, .5f,    1f, 1f,  0f, 0f, 1f, 
			-.5f, .5f, .5f,   0f, 1f,  0f, 0f, 1f,
			.5f, -.5f, .5f,   1f, 0f,  0f, 0f, 1f,
			
			-.5f, -.5f, .5f,  0f, 0f,  0f, 0f, 1f,
			.5f, -.5f, .5f,   1f, 0f,  0f, 0f, 1f,
			-.5f, .5f, .5f,   0f, 1f,  0f, 0f, 1f
		};
		
		float zNeg[] = {
			-.5f, .5f, -.5f,   1f, 1f,  0f, 0f, -1f,
			.5f, .5f, -.5f,    0f, 1f,  0f, 0f, -1f,
			-.5f, -.5f, -.5f,  1f, 0f,  0f, 0f, -1f,
			
			.5f, -.5f, -.5f,   0f, 0f,  0f, 0f, -1f,
			-.5f, -.5f, -.5f,  1f, 0f,  0f, 0f, -1f,
			.5f, .5f, -.5f,    0f, 1f,  0f, 0f, -1f
		};
		
		float xPos[] = {
			.5f, .5f, -.5f,    1f, 1f,  1f, 0f, 0f,
			.5f, .5f, .5f,     0f, 1f,  1f, 0f, 0f,
			.5f, -.5f, -.5f,   1f, 0f,  1f, 0f, 0f,
			
			.5f, -.5f, .5f,    0f, 0f,  1f, 0f, 0f,
			.5f, -.5f, -.5f,   1f, 0f,  1f, 0f, 0f,
			.5f, .5f, .5f,     0f, 1f,  1f, 0f, 0f
		};
		
		float xNeg[] = {
			-.5f, .5f, .5f,     1f, 1f,  -1f, 0f, 0f,
			-.5f, .5f, -.5f,    0f, 1f,  -1f, 0f, 0f,
			-.5f, -.5f, .5f,    1f, 0f,  -1f, 0f, 0f,
			
			-.5f, -.5f, -.5f,   0f, 0f,  -1f, 0f, 0f,
			-.5f, -.5f, .5f,    1f, 0f,  -1f, 0f, 0f,
			-.5f, .5f, -.5f,    0f, 1f,  -1f, 0f, 0f
		};
		
		//Below is the manual adding of vertices to one array
		//I'm not smart enough to do this better
		
		float vertices[] = new float[this.getFaceAmount() * 48];
		int index = 0;
		
		if (((faces >> 5) & 0b1) == 1) {
			for (float c : xPos) {
				vertices[index] = c;
				index++;
			}
		}
		if (((faces >> 4) & 0b1) == 1) {
			for (float c : xNeg) {
				vertices[index] = c;
				index++;
			}
		}
		if (((faces >> 3) & 0b1) == 1) {
			for (float c : yPos) {
				vertices[index] = c;
				index++;
			}
		}
		if (((faces >> 2) & 0b1) == 1) {
			for (float c : yNeg) {
				vertices[index] = c;
				index++;
			}
		}
		if (((faces >> 1) & 0b1) == 1) {
			for (float c : zPos) {
				vertices[index] = c;
				index++;
			}
		}
		if (((faces >> 0) & 0b1) == 1) {
			for (float c : zNeg) {
				vertices[index] = c;
				index++;
			}
		}
	
		return vertices;
	}
	
	public void makeFaceAmount(Byte faces) { //This uses the faces byte and returns how many faces there will be
		
		int faceAmount = 0;
		for (int i = 0; i < 6; i++) {
			if ((faces >> i & 0b1) == 1) {
				faceAmount++;
			}
		}
		this.faceAmount = faceAmount;
		
	}
	
	public int getFaceAmount() {
		return this.faceAmount;
	}
	
	public int getVAO() {
		return this.VAO;
	}
	
	public int[] getPos() {
		return this.pos;
	}
	
	public int[] getChunkLoc() {
		return this.chunkLocation;
	}
	public int getType() {
		return this.type;
	}
	
}
