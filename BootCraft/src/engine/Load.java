package engine;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import engine.world.ChunkColumn;
import graphics.Frame;

/*
 * This class will work on a separate thread to load and deload chunks
 * 
 * @date 2018-04-15
 */

public class Load implements Runnable {

	@Override
	public void run() {

		 //We let opengl set up the window before we generate
		/*try {
			//new Thread().sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		ArrayList<ChunkColumn> copy;
		
		int xChunk = (int) (Var.player.getXPos() / 16f);
		int zChunk = (int) (Var.player.getZPos() / 16f);

		while (true) {
			xChunk = (int) (Var.player.getXPos() / 16f);
			zChunk = (int) (Var.player.getZPos() / 16f);

			boolean shouldGenerate = true;

			try {
				copy = Var.loadedChunkColumns;
				for (ChunkColumn cc : copy) {
					if (cc.getXLoc() == xChunk && cc.getZLoc() == zChunk) {
						shouldGenerate = false;
					}
				}
				
				if (shouldGenerate) {
					Var.loadedChunkColumns.add(new ChunkColumn(xChunk, zChunk));
					System.out.println("Generating????");
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
				System.exit(0);
			}

		}

	}

	
	
}
