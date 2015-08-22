#version 410

in vec3 theColor;
out vec4 daColor;

void main(){

	daColor = vec4(theColor, 1.0);
}