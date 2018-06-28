package engine.generation;

import engine.Var;
import engine.world.Chunk;
import engine.world.ChunkColumn;

/*
 * This class will generate the world!
 * 
 * @date 2018-04-14
 */

public class Generate {

	//Sidenote, I probably should'nt be getting off track anyway
	//My game runs like crap and I don't even feel like fixing it
	//At this point, I just want to see how far I can go before this code becomes too messy
	
	public static void Generate(long seed) {
		
		System.out.println("Generating World...");
		//generateColumn(Var.column);
		
		int s = 16;
		long time0 = System.currentTimeMillis();
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				int height = 4;
				//generateGround(Var.column, i, height, j);
				
			}
		}
		System.out.println("Done in " + (System.currentTimeMillis() - time0) + " ms");
		
		
	}
	
	public static void Generate() {
		Generate((long) Math.random() * Long.MAX_VALUE);
	}
	
	public static void generateColumn(ChunkColumn cc) {
		System.out.println("Generating....");
		
		int xOff = cc.getXLoc() * 16;
		int zOff = cc.getZLoc() * 16;
		//generateGround(cc, 0, 1, 0);
		
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int height = 1;
				generateGround(cc, i, height, j);

			}
		}
		// System.out.println(c.getChunkLocation()[1]);

	}
	
	public static void generateChunk(Chunk c) {
		c.fill(0);
		/*
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int position = i * 256 + 16 * (int) (Math.random() * 16) + j;
				c.fillBelow(position, 2);
			}
		} */
		
		for (int i = 0; i < 0x1000; i++) {
			double r = Math.random();
			if (r > .9) {
				c.placeBlock(i, (int) (Math.random() * 4));
			}
		}
		c.updateChunk();
	}
	
	private static void generateGround(ChunkColumn cc, int xPos, int yPos, int zPos) { //In a chunk column, it places 1 grass block, 3 dirt underneath, and the rest stone
		cc.placeBlock(xPos, yPos, zPos, 3);
		for (int i = 0; i < 4; i++) {
			cc.placeBlock(xPos, yPos - 1 - i, zPos, 2);
		}
		for (int i = yPos - 4; i >=0; i--) {
			cc.placeBlock(xPos, i, zPos, 1);
		}
		cc.update();
		
	}
	
}
