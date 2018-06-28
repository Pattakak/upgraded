package engine.world;

import engine.Var;
import engine.generation.Generate;
import engine.generation.Generate.*;

/*
 * This is what will actually be loaded in and rendered
 * It will be a 16 tall column of chunks
 * 
 * @date 2018-04-15
 */

public class ChunkColumn {

	int xLoc, zLoc;
	Chunk[] chunks;
	
	public ChunkColumn(int xLocation, int zLocation) {
		this.xLoc = xLocation;
		this.zLoc = zLocation;
		
		this.createChunks();
		Generate.generateColumn(this);
		this.update();
		
		Var.loadedChunkColumns.add(this);
	}
	
	public void unloadColumn() {
		Var.loadedChunkColumns.remove(this); //Might be wrong, make sure you get this right when you start unloading chunks
	}
	
	private void createChunks() {
		this.chunks = new Chunk[16];
		int[] chunkLoc = new int[3];
		chunkLoc[0] = this.xLoc;
		chunkLoc[2] = this.zLoc;
		
		for (int i = 0; i < 16; i++) {
			chunkLoc[1] = i;
			this.chunks[i] = new Chunk(this.xLoc, i, this.zLoc);
			int f = this.chunks[i].getChunkLocation()[1];
		}
		
	}
	
	public void renderColumn() {
		for (Chunk c : this.chunks) {
			c.renderChunk();
		}
	}
	
	public Chunk[] getChunks() {
		return this.chunks;
	}
	
	public void placeBlock(int xPos, int yPos, int zPos, int type) { //This has an absolute y value
		if (yPos >= 0 && yPos <= 255) {
			int yBlock = yPos % 16;
			int yChunk = (yPos - yBlock) / 16;
			
			int position = xPos * 256 + yBlock * 16 + zPos * 1;
			this.chunks[yChunk].placeBlock(position, type);
		}
		
	}
	
	public void update() {
		for (Chunk c : this.chunks) {
			c.updateChunk();
		}
	}
	
	public int getXLoc() {
		return this.xLoc;
	}
	public int getZLoc() {
		return this.zLoc;
	}
}
