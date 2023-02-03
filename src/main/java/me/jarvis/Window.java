package me.jarvis;

import me.jarvis.opengl.base.Disposable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

public class Window implements Disposable {

    private int width, height;
    private final long windowHandle;

    private GLFWFramebufferSizeCallback resizeCallback;
    private Callback debugCallback;

    private void handleResize(long windowHandle, int width, int height) {
        this.width = width;
        this.height = height;
        GL33.glViewport(0, 0, width, height);
    }

    public Window(int width, int height) {
        this.width = width;
        this.height = height;

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        this.windowHandle = GLFW.glfwCreateWindow(width, height, "Particles", 0, 0);
        if (this.windowHandle == 0)
            throw new RuntimeException("Unable to create GLFW window");

        this.resizeCallback = GLFWFramebufferSizeCallback.create(this::handleResize).set(this.windowHandle);
        this.debugCallback = null;
    }

    public void prepareForDisplay() {
        GLFW.glfwMakeContextCurrent(this.windowHandle);
        GLFW.glfwSwapInterval(1);
        GL.createCapabilities();

        this.debugCallback = GLUtil.setupDebugMessageCallback();
    }

    public void show() {
        GLFW.glfwShowWindow(this.windowHandle);
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.windowHandle);
    }

    public boolean isOpen() {
        return !GLFW.glfwWindowShouldClose(this.windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public float aspectRatio() {
        return (float)this.width / (float)this.height;
    }

    @Override
    public void dispose() {
        if (this.debugCallback != null)
            this.debugCallback.free();
        this.resizeCallback.free();
        GLFW.glfwDestroyWindow(this.windowHandle);
    }
}
