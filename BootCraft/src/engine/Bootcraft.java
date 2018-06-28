package engine;

import graphics.Frame;

/*
 * @date 2018-04-01
 */

public class Bootcraft {

	public static void main(String[] args) {
		//Runnable chunkLoading = new Load();
		//Runnable graphicsThread = new Frame();
		
		//new Thread(graphicsThread).start();
		//new Thread(chunkLoading).start();
		
		graphics.Frame.init();
	}
	
}
