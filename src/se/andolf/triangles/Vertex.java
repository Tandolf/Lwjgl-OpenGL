package se.andolf.triangles;

import org.lwjgl.util.vector.Vector3f;

public final class Vertex {
	
	public Vector3f position;
	public Vector3f color;
	
	public Vertex(float x, float y, float z){
		this.position = new Vector3f(x, y, z);
		this.color = new Vector3f(x, y, z);
	}
}
