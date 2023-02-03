package me.jarvis;

import me.jarvis.opengl.base.Disposable;
import me.jarvis.opengl.math.Matrix4f;
import me.jarvis.particle.*;
import me.jarvis.util.Camera;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL33;

import javax.annotation.Nullable;
import java.io.IOException;

public class Engine implements Disposable {

    private static final Logger log = LogManager.getLogger(Engine.class);

    private final Window window;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWErrorCallback errorCallback;

    private boolean shouldUpdate = true;
    @Nullable
    private DynamicalParticleSystem emitter;

    private final Camera camera;

    public Engine(int width, int height) {
        this.errorCallback = GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");

        this.window = new Window(width, height);
        this.camera = new Camera();

        this.emitter = null;
    }

    private void handleCursorPos(long windowHandle, double x, double y) {
        this.camera.handleCursorPos(x, y);
    }

    private void handleScroll(long windowHandle, double xOff, double yOff) {
        this.camera.handleScroll(xOff, yOff);
    }

    private void handleMouseButton(long window, int button, int action, int mods) {
        this.camera.handleMouseButton(button, action, mods);
    }

    private void handleKey(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS) {
            this.shouldUpdate = !this.shouldUpdate;
        }
        if (key == GLFW.GLFW_KEY_R && action == GLFW.GLFW_PRESS && this.emitter != null) {
            this.emitter.randomizeParticlePositionsInUnitSphere();
        }
    }

    private Matrix4f calculateMVP() {
        Matrix4f projection = Matrix4f.perspective((float) Math.toRadians(90.0f), this.window.aspectRatio(), 0.1f, 10000.0f);
        Matrix4f view = this.camera.calculateViewMatrix();
        return projection.multiply(view);
    }

    public Engine run() throws IOException {
        this.window.prepareForDisplay();
        this.window.show();

        this.cursorPosCallback = GLFWCursorPosCallback.create(this::handleCursorPos).set(this.window.getWindowHandle());
        this.scrollCallback = GLFWScrollCallback.create(this::handleScroll).set(this.window.getWindowHandle());
        this.mouseButtonCallback = GLFWMouseButtonCallback.create(this::handleMouseButton).set(this.window.getWindowHandle());
        this.keyCallback = GLFWKeyCallback.create(this::handleKey).set(this.window.getWindowHandle());

        this.emitter = new ShimizuMoriokaSystem(200000);

        GL33.glEnable(GL33.GL_BLEND);
        GL33.glBlendFunc(GL33.GL_SRC_ALPHA, GL33.GL_ONE_MINUS_SRC_ALPHA);
        GL33.glEnable(GL33.GL_DEPTH_TEST);
        while (this.window.isOpen()) {
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT | GL33.GL_DEPTH_BUFFER_BIT);
            GL33.glClearColor(0, 0, 0, 1);

            if (this.shouldUpdate) {
                emitter.update(0.015f);
            }
            emitter.getShader().bind();
            Matrix4f mvp = this.calculateMVP();
            emitter.getShader().setUniform("mvp", mvp);
            emitter.draw();

            this.window.swapBuffers();
            GLFW.glfwPollEvents();
        }

        return this;
    }

    @Override
    public void dispose() {
        if (this.emitter != null)
            this.emitter.dispose();
        this.cursorPosCallback.free();
        this.scrollCallback.free();
        this.mouseButtonCallback.free();
        this.keyCallback.free();
        this.errorCallback.free();
        this.window.dispose();
    }
}
