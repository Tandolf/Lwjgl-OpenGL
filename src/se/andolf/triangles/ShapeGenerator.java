package se.andolf.triangles;

import static se.andolf.triangles.Constants.RED_TRIANGLE_Z;
import static se.andolf.triangles.Constants.SIZE_OF_SHORT;
import static se.andolf.triangles.Util.createFloatBuffer;

import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

public class ShapeGenerator {
	
	public ShapeData makeTriangle(){
		
		ShapeData shape = new ShapeData();
		
		Vertex[] verts = { 
					new Vertex(0.0f, +1.0f, RED_TRIANGLE_Z), 
					new Vertex(+1.0f, +0.0f, +0.0f),
					
					new Vertex(-1.0f, -1.0f, RED_TRIANGLE_Z),				
					new Vertex(+0.0f, +1.0f, +0.0f),
					
					new Vertex(+1.0f, -1.0f, RED_TRIANGLE_Z),
					new Vertex(+0.0f, +0.0f, +1.0f),
		};
		
		shape.setVerts(verts);
		
		shape.setVertData(createFloatBuffer(verts));
		
		short[] indicies = { 0,1,2 };
		
		ShortBuffer indiciesData = BufferUtils.createShortBuffer(indicies.length * SIZE_OF_SHORT);
		indiciesData.put(indicies);		
		indiciesData.flip();
		
		shape.setIndiciesData(indiciesData);
		
		shape.setNumVerticies(verts.length / 2);
		shape.setNumIndicies(indicies.length);
		
		return shape;
	}
	
	public ShapeData makeCube(){
		
		ShapeData shape = new ShapeData();
		
		Vertex[] verts = { 
				
				//top face
				new Vertex(-1.0f, +1.0f, +1.0f), //0
				new Vertex(+0.0f, +1.0f, +0.0f),				
				new Vertex(+1.0f, +1.0f, +1.0f), //1			
				new Vertex(+0.0f, +1.0f, +0.0f),				
				new Vertex(+1.0f, +1.0f, -1.0f), //2
				new Vertex(+0.0f, +1.0f, +0.0f),				
				new Vertex(-1.0f, +1.0f, -1.0f), //3
				new Vertex(+0.0f, +1.0f, +0.0f), 
				
				//back face
				new Vertex(-1.0f, +1.0f, -1.0f), //4
				new Vertex(+0.0f, +0.0f, +1.0f),
				new Vertex(+1.0f, +1.0f, -1.0f), //5			
				new Vertex(+0.0f, +0.0f, +1.0f),
				new Vertex(+1.0f, -1.0f, -1.0f), //6
				new Vertex(+0.0f, +0.0f, +1.0f),
				new Vertex(-1.0f, -1.0f, -1.0f), //7
				new Vertex(+0.0f, +0.0f, +1.0f),
				
				//right face
				new Vertex(+1.0f, +1.0f, -1.0f), //8
				new Vertex(+1.0f, +0.0f, +0.0f),
				new Vertex(+1.0f, +1.0f, +1.0f), //9			
				new Vertex(+1.0f, +0.0f, +0.0f),
				new Vertex(+1.0f, -1.0f, +1.0f), //10
				new Vertex(+1.0f, +0.0f, +0.0f),
				new Vertex(+1.0f, -1.0f, -1.0f), //11
				new Vertex(+1.0f, +0.0f, +0.0f),
				
				//left face
				new Vertex(-1.0f, +1.0f, +1.0f), //12
				new Vertex(+1.0f, +1.0f, +0.0f),
				new Vertex(-1.0f, +1.0f, -1.0f), //13			
				new Vertex(+1.0f, +1.0f, +0.0f),
				new Vertex(-1.0f, -1.0f, -1.0f), //14
				new Vertex(+1.0f, +1.0f, +0.0f),
				new Vertex(-1.0f, -1.0f, +1.0f), //15
				new Vertex(+1.0f, +1.0f, +0.0f),
				
				//bottom face
				new Vertex(+1.0f, +1.0f, +1.0f), //16
				new Vertex(+0.0f, +1.0f, +1.0f),
				new Vertex(-1.0f, +1.0f, +1.0f), //17			
				new Vertex(+0.0f, +1.0f, +1.0f),
				new Vertex(-1.0f, -1.0f, +1.0f), //18
				new Vertex(+0.0f, +1.0f, +1.0f),
				new Vertex(+1.0f, -1.0f, +1.0f), //19
				new Vertex(+0.0f, +1.0f, +1.0f),
				
				//front face
				new Vertex(+1.0f, -1.0f, -1.0f), //20
				new Vertex(+1.0f, +1.0f, +1.0f),
				new Vertex(-1.0f, -1.0f, -1.0f), //21			
				new Vertex(+1.0f, +1.0f, +1.0f),
				new Vertex(-1.0f, -1.0f, +1.0f), //22
				new Vertex(+1.0f, +1.0f, +1.0f),
				new Vertex(+1.0f, -1.0f, +1.0f), //23
				new Vertex(+1.0f, +1.0f, +1.0f)
		};
	
		shape.setVerts(verts);
		
		shape.setVertData(createFloatBuffer(verts));
		
		short[] indicies = { 
				
				0,1,2, 0,2,3,
				4,5,6, 4,6,7,
				8,9,10, 8,10,11,
				12,13,14, 12,14,15,
				16,17,18, 16,18,19,
				20,22,21, 20,23,22
		};
		
		ShortBuffer indiciesData = BufferUtils.createShortBuffer(indicies.length * SIZE_OF_SHORT);
		indiciesData.put(indicies);		
		indiciesData.flip();
		
		shape.setIndiciesData(indiciesData);
		
		shape.setNumVerticies(verts.length / 2);
		shape.setNumIndicies(indicies.length);
		
		return shape;
		
	}
}