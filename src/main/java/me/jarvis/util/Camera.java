package me.jarvis.util;

import me.jarvis.opengl.math.Matrix4f;
import me.jarvis.opengl.math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private float yaw, pitch;
    private Vector3f offset;
    private float distance;
    private double lastMouseX, lastMouseY;

    private boolean isMoving, isRotating;
    private boolean shouldResetLastMousePos;
    
    public Camera() {
        this.yaw = 0;
        this.pitch = 0;
        this.offset = new Vector3f(0, 0, 0);
        this.distance = 100.0f;

        this.isMoving = false;
        this.isRotating = false;
        this.shouldResetLastMousePos = true;
    }

    private float getSmoothDistanceScalar(float defaultFactor) {
        return defaultFactor * this.distance / 100f;
    }

    public void handleCursorPos(double x, double y) {
        if (this.shouldResetLastMousePos) {
            lastMouseX = x;
            lastMouseY = y;
            this.shouldResetLastMousePos = false;
        }

        if (this.isRotating) {
            this.yaw += (lastMouseX - x) * 0.5;
            this.yaw = TrigonometryUtils.wrapDegrees(this.yaw);
            this.pitch += (lastMouseY - y) * 0.5;
            this.pitch = Mathf.clamp(this.pitch, -89.0f, 89.0f);
        }

        if (this.isMoving) {
            Vector3f forward = TrigonometryUtils.degreeEulerAnglesToVector(this.yaw, this.pitch);
            Vector3f right = forward.cross(Vector3f.UP).normalized();
            Vector3f up = forward.cross(right).normalized();
            float dragSpeed = this.getSmoothDistanceScalar(0.008f);
            Vector3f horizontalOffset = right.scale((float)(lastMouseX - x) * dragSpeed);
            Vector3f verticalOffset = up.scale((float)(lastMouseY - y) * dragSpeed);
            this.offset = this.offset.sub(horizontalOffset).add(verticalOffset);
        }

        lastMouseX = x;
        lastMouseY = y;
    }

    public void handleScroll(double xOff, double yOff) {
        this.distance -= yOff * getSmoothDistanceScalar(10f);
        this.distance = Math.max(0f, this.distance);
    }

    public void handleMouseButton(int button, int action, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            this.isRotating = action == GLFW.GLFW_PRESS;
            this.shouldResetLastMousePos = action == GLFW.GLFW_RELEASE;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            this.isMoving = action == GLFW.GLFW_PRESS;
            this.shouldResetLastMousePos = action == GLFW.GLFW_RELEASE;
        }
    }

    public Matrix4f calculateViewMatrix() {
        Vector3f rawOffset = TrigonometryUtils.degreeEulerAnglesToVector(this.yaw, this.pitch);
        Vector3f currentPosition = rawOffset.scale(this.distance);
        Vector3f offsetPosition = currentPosition.add(this.offset);
        return Matrix4f.lookAt(offsetPosition, this.offset, Vector3f.UP);
    }
}
