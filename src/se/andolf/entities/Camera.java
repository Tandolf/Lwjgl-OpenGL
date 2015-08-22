package se.andolf.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

public class Camera {

	public static float MOVEMENT_SPEED = 0.05f;
	private Vec3 position;
	private Vec3 viewDirection;
	private Vec3 up;
	private Vec3 currentPosition = new Vec3();
	private Vector3f strafeDirection;

	public Camera() {
		position = new Vec3(0.0f, 0.0f, 0.0f);
		viewDirection = new Vec3(0.0f, 0.0f, -1.0f);
		up = new Vec3(0.0f, 1.0f, 0.0f);
		currentPosition = position;
	}

	public void mouseUpdate() {

		Vector3f vD = new Vector3f(viewDirection.getX(), viewDirection.getY(),
				viewDirection.getZ());
		Vector3f u = new Vector3f(up.getX(), up.getY(), up.getZ());
		strafeDirection = Vector3f.cross(vD, u, null);

		Mat4 tempMat = new Mat4(viewDirection, Vec3.VEC3_ZERO, Vec3.VEC3_ZERO,
				Vec3.VEC3_ZERO);
		tempMat = Matrices
				.rotate((float) -Mouse.getDX() * 0.001f, up)
				.multiply(
						Matrices.rotate(
								(float) Mouse.getDY() * 0.001f,
								new Vec3(strafeDirection.getX(),
										strafeDirection.getY(), strafeDirection
												.getZ()))).multiply(tempMat);
		Vec4 vec4 = tempMat.getColumn(0);
		viewDirection = new Vec3(vec4.getX(), vec4.getY(),
				vec4.getZ());
	}

	public Mat4 getWorldModelToViewMatrix() {
		return Matrices.lookAt(position, position.add(viewDirection), up);
	}

	public void moveForward() {
		position = currentPosition.add(viewDirection.multiply(MOVEMENT_SPEED));
		currentPosition = position;

	}

	public void moveBackward() {
		position = currentPosition.add(viewDirection.multiply(-MOVEMENT_SPEED));
		currentPosition = position;
	}

	public void strafeLeft() {
		position = currentPosition.add(new Vec3(strafeDirection.getX(),
				strafeDirection.getY(), strafeDirection.getZ())
				.multiply(-MOVEMENT_SPEED));
		currentPosition = position;
	}

	public void strafeRight() {

		position = currentPosition.add(new Vec3(strafeDirection.getX(),
				strafeDirection.getY(), strafeDirection.getZ())
				.multiply(MOVEMENT_SPEED));
		currentPosition = position;
	}

	public void moveUp() {
		position = currentPosition.add(up.multiply(MOVEMENT_SPEED));
		currentPosition = position;
	}

	public void moveDown() {
		position = currentPosition.add(up.multiply(-MOVEMENT_SPEED));
		currentPosition = position;
	}

}