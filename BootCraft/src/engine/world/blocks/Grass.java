package engine.world.blocks;

import engine.world.Block;
import engine.world.Chunk;

public class Grass extends Block {

	public Grass(Chunk chunk, int[] pos, int type, Byte renderFaces) {
		super(chunk, pos, type, renderFaces);
		// TODO Auto-generated constructor stub
	}
	
	protected float[] generateVertices(Byte faces) {
		
		//Bottom left of the top texture is (48 / 256, 241 / 256)
		
		float originCornerTopx = 48f / 256f; //The x coordinate of the top texture on the bottom left corner of the image
		float originCornerTopy = 240f / 256f; //Change this to 16 if there is a problem
		float originCornerSidex = 32f / 256f;
		float originCornerSidey = 240f / 256f;
		float originCornerBottomx = 16f / 256f;
		float originCornerBottomy = 240f / 256f;
		
		
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
				xPos[8 * i + 3] = xPos[8 * i + 3] * offset + originCornerSidex;
				xPos[8 * i + 4] = xPos[8 * i + 4] * offset + originCornerSidey;
				xNeg[8 * i + 3] = xNeg[8 * i + 3] * offset + originCornerSidex;
				xNeg[8 * i + 4] = xNeg[8 * i + 4] * offset + originCornerSidey;
			
				zPos[8 * i + 3] = zPos[8 * i + 3] * offset + originCornerSidex;
				zPos[8 * i + 4] = zPos[8 * i + 4] * offset + originCornerSidey;
				zNeg[8 * i + 3] = zNeg[8 * i + 3] * offset + originCornerSidex;
				zNeg[8 * i + 4] = zNeg[8 * i + 4] * offset + originCornerSidey;
				
				yPos[8 * i + 3] = yPos[8 * i + 3] * offset + originCornerTopx;
				yPos[8 * i + 4] = yPos[8 * i + 4] * offset + originCornerTopy;
				yNeg[8 * i + 3] = yNeg[8 * i + 3] * offset + originCornerBottomx;
				yNeg[8 * i + 4] = yNeg[8 * i + 4] * offset + originCornerBottomy;
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
