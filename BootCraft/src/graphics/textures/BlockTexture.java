package graphics.textures;

import engine.Var;

/*
 *  This class will set up an array to store the textures for each block, with 1 being stone
 *  The default texture (texture unknown) will be placed in the 0 spot of the array
 *  
 *  @date 2018-04-10
 */

public class BlockTexture {

	public static TextureProcess blockTextures;
	public static TextureProcess nullTexture;
	
	public static void initializeBlockTextures() {
		//RIP all of the code below
		//We are reforming the texture process so there is only one texture file
		blockTextures = new TextureProcess("./res/texture/texture.png");
		nullTexture = new TextureProcess("./res/texture/default.png");
		
		
		/*
		blockTextures = new TextureProcess[Var.blockTypes + 1];
		
		//This line creates a default purple and black texture
		blockTextures[0] = new TextureProcess("./res/texture/default.png");
		
		//This loop puts the right textures in
		for (int i = 1; i < blockTextures.length; i++) {
			
			//If the image doesen't exist, the block id will point to the default texture
			try {
				blockTextures[i] = new TextureProcess("./res/texture/block" + (i) + ".png");
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println("Replacing block " + i + " with a missing texture");
				blockTextures[i] = new TextureProcess("./res/texture/default.png");
			}
			
		}*/
	}
	
	//This method is officially useless jsyk
	/*
	public static int getTextureID(int blockID) {
		try {
			return blockTextures[blockID].getTexture();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Block ID is invalid");
			return blockTextures[0].getTexture();
		}
	}*/
	
}
