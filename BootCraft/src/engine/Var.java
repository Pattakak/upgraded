package engine;

import java.util.ArrayList;

import engine.world.Block;
import engine.world.Chunk;
import engine.world.ChunkColumn;
import engine.world.Player;
import graphics.Camera;
import graphics.Shader;
import graphics.textures.TextureProcess;

/*
 * @date 2018-04-01 
 * @description Provides a bunch of public variables that will be used throughout the program
 */

public class Var {
	
	public static int height = 1024;
	public static int width = 1024;
	public static int deltaTime; //Time it takes for one loop
	public static boolean showCursor;
	
	public static int blockTypes = 3; //Number of blocks added
	public static Player player = new Player(-3, 12, 0, 0, 0);
	public static Camera camera;
	public static ArrayList<ChunkColumn> loadedChunkColumns = new ArrayList<ChunkColumn>();
	
	//TEMP
	public static Shader block;
	public static Block stone;
	//public static ChunkColumn column;
}
