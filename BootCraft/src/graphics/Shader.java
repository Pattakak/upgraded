package graphics;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GLCapabilities.*;
import org.apache.commons.io.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Shader {

	int program;
	
	@SuppressWarnings("deprecation")
	public Shader(String vertexName, String fragmentName) {
		String vertexCode = "", fragmentCode = "";
		
		try {
			vertexCode = FileUtils.readFileToString(new File("./shaders/" + vertexName));
			fragmentCode = FileUtils.readFileToString(new File("./shaders/" + fragmentName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int vertex, fragment;
		vertex = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex, vertexCode);
		glCompileShader(vertex);
		
		if (glGetShaderi(vertex, GL_COMPILE_STATUS) != GL_TRUE) {
			System.out.println(glGetShaderInfoLog(vertex));
		    throw new RuntimeException(glGetShaderInfoLog(vertex));
		}
		
		fragment = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment, fragmentCode);
		glCompileShader(fragment);
		
		if (glGetShaderi(fragment, GL_COMPILE_STATUS) != GL_TRUE) {
			System.out.println(glGetShaderInfoLog(fragment));
		    throw new RuntimeException(glGetShaderInfoLog(fragment));
		}
		
		program = glCreateProgram();
		glAttachShader(program, vertex); glAttachShader(program, fragment);
		glLinkProgram(program);
		
		int status = glGetProgrami(program, GL_LINK_STATUS);
		if (status != GL_TRUE) {
			System.out.println(glGetProgramInfoLog(program));
		    throw new RuntimeException(glGetProgramInfoLog(program));
		}
		
		glDeleteShader(vertex); glDeleteShader(fragment);	
		
	}
	
	public void use() {
		glUseProgram(this.program);
	}
	
	//This method takes a matrix and its name and binds it to its corresponding uniform location
	public void bindUniformMatrix4fv(String matrixName, Matrix4f mat4f){
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		int matrixLocation = glGetUniformLocation(this.program, matrixName);
		mat4f.get(fb);
		glUniformMatrix4fv(matrixLocation, false, fb);
	}
	
	//This method binds uniform ints
	//This method can be use to bind a texture to the shader, just pass the id as the integer
	public void bindUniformInt(String intName, int integer) {
		int uniTex = glGetUniformLocation(this.program, intName);
		glUniform1i(uniTex, integer);
		
	}
	public void bindUniformFloat(String floatName, float floating) {
		int uniTex = glGetUniformLocation(this.program, floatName);
		glUniform1f(uniTex, floating);
		
	}
	
	public void bindUniformVec3f(String vectorName, Vector3f vector3f){
		FloatBuffer fb = BufferUtils.createFloatBuffer(3);
		int vectorLocation = glGetUniformLocation(this.program, vectorName);
		vector3f.get(fb);
		glUniform3fv(vectorLocation, fb);
		
	}
}

