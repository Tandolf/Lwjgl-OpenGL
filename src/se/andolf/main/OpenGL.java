package se.andolf.main;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.util.glu.GLU.gluErrorString;
import static se.andolf.util.Constants.SIZE_OF_FLOAT;
import static se.andolf.util.Util.checkProgramStatus;
import static se.andolf.util.Util.checkShaderStatus;
import static se.andolf.util.Util.createMatrixBuffer;
import static se.andolf.util.Util.createProjectionMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import se.andolf.entities.Camera;
import se.andolf.entities.ShapeData;
import se.andolf.generators.ShapeGenerator;
import se.andolf.util.Util;

public class OpenGL {
	
	private int programID;
	private ShapeData shape;

	private void sendDataToOpenGL(){
		
			ShapeGenerator factory = new ShapeGenerator();
			
			shape = factory.makeCube();
			
			//create a vertex array Id
			int vaoId = glGenVertexArrays();
			//bind the vertex array
			glBindVertexArray(vaoId);
			
			//create vertex buffer object
			int vboId = glGenBuffers();
			//bind the buffer object to a buffer array
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			//bind the data to the selected buffer array
			glBufferData(GL_ARRAY_BUFFER, shape.getVertData(), GL_STATIC_DRAW);			
			
			//Describe the data
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * SIZE_OF_FLOAT, 0);
			//go to verts attribute
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * SIZE_OF_FLOAT, 3 * SIZE_OF_FLOAT);

			//unbind buffer
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			int iboId = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, shape.getIndiciesData(), GL_STATIC_DRAW);
			
			int errorCheckValue = glGetError();
			if (errorCheckValue != GL_NO_ERROR) {
				System.out.println("ERROR - Init could not be launched " + gluErrorString(errorCheckValue));
			}			
	}
	
	private void installShaders() {
		
		StringBuilder vertexShaderSource = new StringBuilder();
		StringBuilder fragmentShaderSource = new StringBuilder();
				
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/se/andolf/main/shader.vert"));
			String line = null;
			while((line = reader.readLine()) != null){
				vertexShaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("File failed to read");
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/se/andolf/main/shader.frag"));
			String line = null;
			while((line = reader.readLine()) != null){
				fragmentShaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("File failed to read");
			e.printStackTrace();
		}
		
		//create shaderProgram
		programID = glCreateProgram();
		//create shaders
		int vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		int fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		
		//set shadercode to shader object
		glShaderSource(vertexShaderID, vertexShaderSource);
		glShaderSource(fragmentShaderID, fragmentShaderSource);
		
		//compile the shaders
		glCompileShader(vertexShaderID);
		glCompileShader(fragmentShaderID);
		
		if(!checkShaderStatus(vertexShaderID) || !checkShaderStatus(fragmentShaderID)){
			return;
		}
		
		//attach shaders to program
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		
		//link the program
		glLinkProgram(programID);
		
		//check if linking worked
		if(!checkProgramStatus(programID)){
			return;			
		}
		
		//use the shader program
		glUseProgram(programID);
	}

	public void initGL(){
			
		sendDataToOpenGL();
		glEnable(GL_DEPTH_TEST);
		installShaders();
		
	}
	
	
	public void render(Camera camera){
			
		glClear(GL_DEPTH_BUFFER_BIT);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
			
		//setup matrix
		Matrix4f translationMatrix = new Matrix4f();
		Matrix4f rotationMatrix = new Matrix4f();
		Matrix4f projectionMatrix = createProjectionMatrix(60, (float)(Display.getWidth()/Display.getHeight()),  0.1f, 10.0f);
		Matrix4f ModelToViewMatrix = Util.convertMat4ToMatrix4f(camera.getWorldModelToViewMatrix());
		Matrix4f fullTransformMatrix;
		
		camera.mouseUpdate();
		
		//transform/rotate
		translationMatrix.translate(new Vector3f(0.0f,0.0f,-5.0f));
		rotationMatrix.rotate(0, new Vector3f(1.0f, 0.0f, 0.0f));
		rotationMatrix.rotate(0, new Vector3f(0.0f, 1.0f, 0.0f));

		//multiply matrix
		translationMatrix = Matrix4f.mul(translationMatrix, rotationMatrix, null);		
		ModelToViewMatrix = Matrix4f.mul(ModelToViewMatrix, translationMatrix, null);
		fullTransformMatrix = Matrix4f.mul(projectionMatrix, ModelToViewMatrix, null);
		
		int projectionTransformMatrixUniformLocation = glGetUniformLocation(programID, "fullTransformMatrix");
		glUniformMatrix4(projectionTransformMatrixUniformLocation, false, createMatrixBuffer(fullTransformMatrix));
		
		glDrawElements(GL_TRIANGLES, shape.getNumIndicies(), GL_UNSIGNED_SHORT, 0);
	}
	
	public void cleanUp(){
		
		Display.destroy();
		
	}
}