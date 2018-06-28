package graphics.textures;

/*
 * @date 2018-04-04
 * @description Provides the data for block textures
 * 
 * 
 * For reference, visit 
 * https://github.com/SilverTiger/lwjgl3-tutorial/wiki/Textures
 * https://learnopengl.com/Getting-started/Textures
 *  
 */

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openvr.Texture;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBFramebufferObject.*;

public class TextureProcess {

	int texture;
	
	//Only supports PNG (I think)
	public TextureProcess(String pictureLocation) throws RuntimeException {
		
		this.texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.texture);
		
		//These are necessary parameters you need to set before textures will work
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,GL_REPEAT); //s is x, t is y; This will wrap the texture around and repeat it
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		
		MemoryStack stack = MemoryStack.stackPush();
		IntBuffer w = stack.mallocInt(1);
		IntBuffer h = stack.mallocInt(1);
		IntBuffer comp = stack.mallocInt(1);
		
		//Flips the y axis because if this is false then the image would be upside down
		stbi_set_flip_vertically_on_load(true);
		
		//IMPORTANT that the last component is 4, or else the entire thing will be broken
		
		ByteBuffer image = stbi_load(pictureLocation, w, h, comp, 4);
		if (image == null) {
			throw new RuntimeException("Failed to load image" + System.lineSeparator() + stbi_failure_reason());
		}
		
		int width = w.get();
		int height = h.get();
		
		//glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels)
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		glGenerateMipmap(GL_TEXTURE_2D);
		
		//Frees the memory since we're done
		stbi_image_free(image);
		
		
		//TODO Get your things together and learn EVERYTHING about textures and buffers
		
	}
	
	public int getTexture() {
		return this.texture;
	}
	
}
