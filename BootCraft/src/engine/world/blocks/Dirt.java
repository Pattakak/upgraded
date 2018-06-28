package engine.world.blocks;

import engine.world.Block;
import engine.world.Chunk;

public class Dirt extends Block {

	public Dirt(Chunk chunk, int[] pos, int type, Byte renderFaces) {
		super(chunk, pos, type, renderFaces);
	}
	

	
	protected float[] generateVertices(Byte faces) {
		float originCornerx = 16f / 256f; //The x coordinate of the top texture on the bottom left corner of the image
		float originCornery = 240f / 256f; //Change this to 16 if there is a problem
		
		
		float offset = 16f / 256f;
 		
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
			
			//This for loop goes through the texture coordinates and sets them so they fit the specific face texture for the block
			for (int i = 0; i < 6; i++) {
				xPos[8 * i + 3] = xPos[8 * i + 3] * offset + originCornerx;
				xPos[8 * i + 4] = xPos[8 * i + 4] * offset + originCornery;
				xNeg[8 * i + 3] = xNeg[8 * i + 3] * offset + originCornerx;
				xNeg[8 * i + 4] = xNeg[8 * i + 4] * offset + originCornery;
				
				zPos[8 * i + 3] = zPos[8 * i + 3] * offset + originCornerx;
				zPos[8 * i + 4] = zPos[8 * i + 4] * offset + originCornery;
				zNeg[8 * i + 3] = zNeg[8 * i + 3] * offset + originCornerx;
				zNeg[8 * i + 4] = zNeg[8 * i + 4] * offset + originCornery;
				
				yPos[8 * i + 3] = yPos[8 * i + 3] * offset + originCornerx;
				yPos[8 * i + 4] = yPos[8 * i + 4] * offset + originCornery;
				yNeg[8 * i + 3] = yNeg[8 * i + 3] * offset + originCornerx;
				yNeg[8 * i + 4] = yNeg[8 * i + 4] * offset + originCornery;
			}
			
			
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

}
