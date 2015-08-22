#version 410

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 vertexColor;

uniform mat4 fullTransformMatrix;

out vec3 theColor;

void main(){
	
	vec4 v = vec4(position, 1.0);
	gl_Position = fullTransformMatrix * v;
	theColor = vertexColor;

}