package se.andolf.triangles;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Main {

	public static void main(String[] args) {
		
		initWindow();
		Camera camera = new Camera();
		OpenGL openGL = new OpenGL();
		
		openGL.initGL();
		gameLoop(openGL, camera);

	}

	private static void gameLoop(OpenGL openGL, Camera camera) {
		
		while (!Display.isCloseRequested()) {
			
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			openGL.render(camera);
			Display.update();
			Display.sync(60);
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				camera.moveForward();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				camera.strafeLeft();			
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				camera.moveBackward();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				camera.strafeRight();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				camera.moveUp();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				camera.moveDown();
			}
		}		
	}

	private static void initWindow() {
		
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setResizable(true);
			Display.setTitle("Open GL test");
			Display.setVSyncEnabled(true);
			Display.create(pixelFormat, contextAtrributes);
			Mouse.setGrabbed(true);
			
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		System.out.println("GLSL version: " + glGetString(GL_SHADING_LANGUAGE_VERSION));
	}
}