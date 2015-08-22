package se.andolf.triangles;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class ShapeData {
	
	private Vertex[] verts;
	private FloatBuffer vertData;
	private ShortBuffer indiciesData;
	private int numVerticies;
	private int numIndicies;
	
	public Vertex[] getVerts() {
		return verts;
	}
	
	public void setVerts(Vertex[] verts) {
		this.verts = verts;
	}

	public FloatBuffer getVertData() {
		return vertData;
	}

	public void setVertData(FloatBuffer vertData) {
		this.vertData = vertData;
	}

	public ShortBuffer getIndiciesData() {
		return indiciesData;
	}

	public void setIndiciesData(ShortBuffer indiciesData) {
		this.indiciesData = indiciesData;
	}

	public int getNumVerticies() {
		return numVerticies;
	}

	public void setNumVerticies(int numVerticies) {
		this.numVerticies = numVerticies;
	}

	public int getNumIndicies() {
		return numIndicies;
	}

	public void setNumIndicies(int numIndicies) {
		this.numIndicies = numIndicies;
	}	
}