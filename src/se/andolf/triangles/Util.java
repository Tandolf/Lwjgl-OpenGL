package se.andolf.triangles;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static se.andolf.triangles.Constants.ELEMENTS_OF_VERTEX3;
import static se.andolf.triangles.Constants.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class Util {
	
	public static float degreesToRadians(float degrees) {
		return degrees * (float)(Math.PI / 180d);
	}
	
	public static float coTangent(float angle) {
		return (float)(1f / Math.tan(angle));
	}
	
	public static FloatBuffer createFloatBuffer(Vertex[] verts){
		
		FloatBuffer vertData  = BufferUtils.createFloatBuffer(verts.length * ELEMENTS_OF_VERTEX3 * SIZE_OF_FLOAT);
		
		for(int i = 0; i < verts.length; i++){
			if(i % 2 ==0)
				vertData.put(verts[i].color.x).put(verts[i].color.y).put(verts[i].color.z);
			else
				vertData.put(verts[i].position.x).put(verts[i].position.y).put(verts[i].position.z);
		}
		vertData.flip();
		return vertData;		
	}
	
	public static FloatBuffer createMatrixBuffer(Matrix4f matrix){
		
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		
		return matrixBuffer;		
	}
	
	public static boolean checkProgramStatus(int programID){
		if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE){
			//get info log length
			int logSize = glGetProgrami(programID, GL_INFO_LOG_LENGTH);
			
			System.out.println(logSize);
			//print the error message from the info log
			System.err.println("PROGRAM ERROR " + glGetProgramInfoLog(programID, logSize));			
			return false;
		}
		return true;
	}
	
	public static boolean checkShaderStatus(int shaderID){
		
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE){
			//get info log length
			int logSize = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
			//print the error message from the info log
			System.err.println("SHADER COMPILE " + glGetShaderInfoLog(shaderID, logSize));		
			return false;
		}
		return true;
	}
	
	public static void printMatrix4f(Matrix4f projectionMatrix) {
		
		System.out.print(projectionMatrix.m00);
		System.out.print(" ");
		System.out.print(projectionMatrix.m01);
		System.out.print(" ");
		System.out.print(projectionMatrix.m02);
		System.out.print(" ");
		System.out.println(projectionMatrix.m03);
		
		System.out.print(projectionMatrix.m10);
		System.out.print(" ");
		System.out.print(projectionMatrix.m11);
		System.out.print(" ");
		System.out.print(projectionMatrix.m12);
		System.out.print(" ");
		System.out.println(projectionMatrix.m13);
		
		System.out.print(projectionMatrix.m20);
		System.out.print(" ");
		System.out.print(projectionMatrix.m21);
		System.out.print(" ");
		System.out.print(projectionMatrix.m22);
		System.out.print(" ");
		System.out.println(projectionMatrix.m23);
		
		System.out.print(projectionMatrix.m30);
		System.out.print(" ");
		System.out.print(projectionMatrix.m31);
		System.out.print(" ");
		System.out.print(projectionMatrix.m32);
		System.out.print(" ");
		System.out.println(projectionMatrix.m13);
		System.out.println(" ");
	}
	
	public static Matrix4f createProjectionMatrix(float fov, float aspect, float nearPlane, float farPlane){
		
		// Setup projection matrix
		Matrix4f projectionMatrix = new Matrix4f();
		float fieldOfView = fov;
		float aspectRatio = aspect;
		float near_plane = nearPlane;
		float far_plane = farPlane;

		float y_scale = coTangent(degreesToRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;
		
		projectionMatrix.setIdentity();
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		projectionMatrix.m33 = 0;
		
		return projectionMatrix;
	}
	
	public static Matrix4f convertMat4ToMatrix4f(Mat4 mat4){
		Matrix4f matrix4f = new Matrix4f();
		
		Vec4 vec4 = mat4.getColumn(0);
		matrix4f.m00 = vec4.getX();
		matrix4f.m01 = vec4.getY();
		matrix4f.m02 = vec4.getZ();
		matrix4f.m03 = vec4.getW();
		
		
		vec4 = mat4.getColumn(1);
		matrix4f.m10 = vec4.getX();
		matrix4f.m11 = vec4.getY();
		matrix4f.m12 = vec4.getZ();
		matrix4f.m13 = vec4.getW();
		
		vec4 = mat4.getColumn(2);
		matrix4f.m20 = vec4.getX();
		matrix4f.m21 = vec4.getY();
		matrix4f.m22 = vec4.getZ();
		matrix4f.m23 = vec4.getW();
		
		vec4 = mat4.getColumn(3);
		matrix4f.m30 = vec4.getX();
		matrix4f.m31 = vec4.getY();
		matrix4f.m32 = vec4.getZ();
		matrix4f.m33 = vec4.getW();
		
		return matrix4f;
		
	}
}