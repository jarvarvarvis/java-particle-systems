package me.jarvis.particle;

import me.jarvis.opengl.base.Disposable;
import me.jarvis.opengl.math.Vector3f;
import me.jarvis.opengl.objects.VAO;
import me.jarvis.opengl.objects.VBO;
import me.jarvis.opengl.shader.ShaderFactory;
import me.jarvis.opengl.shader.ShaderProgram;
import me.jarvis.util.Mathf;
import me.jarvis.util.TrigonometryUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem implements Disposable {

    private final int particlesAmount;

    private final VAO vao;
    private final VBO positionBuffer;
    private final VBO colorBuffer;
    private final ShaderProgram shader;

    protected final List<Particle> particles;

    public ParticleSystem(int particlesAmount) throws IOException {
        this.particlesAmount = particlesAmount;

        this.positionBuffer = new VBO(0);
        this.colorBuffer = new VBO(1);
        this.vao = new VAO();
        this.vao.addVBO(this.positionBuffer);
        this.vao.addVBO(this.colorBuffer);

        this.particles = new ArrayList<>();
        for (int i = 0; i < particlesAmount; ++i) {
            Particle particle = new Particle(
                0, 0, 0,
                1f, 1f, 1f
            );
            this.particles.add(particle);
        }
        this.randomizeParticlePositionsInUnitSphere();

        this.shader = ShaderFactory.loadProgramFromResources("shaders/vert.glsl", "shaders/frag.glsl");;
        this.initBuffers();
    }

    public void randomizeParticlePositionsInUnitSphere() {
        for (Particle particle : this.particles) {
            Vector3f point = TrigonometryUtils.randomInUnitSphere();
            particle.setPosition(point.x(), point.y(), point.z());
        }
    }

    private FloatBuffer getPositionData() {
        FloatBuffer positionData = BufferUtils.createFloatBuffer(this.particlesAmount * 3 * 2);

        for (int i = 0; i < this.particlesAmount; ++i) {
            Particle particle = this.particles.get(i);
            positionData.put(particle.getPreviousX());
            positionData.put(particle.getPreviousY());
            positionData.put(particle.getPreviousZ());
            positionData.put(particle.getX());
            positionData.put(particle.getY());
            positionData.put(particle.getZ());
        }

        return positionData.flip();
    }

    private FloatBuffer getColorData() {
        FloatBuffer colorData = BufferUtils.createFloatBuffer(this.particlesAmount * 4 * 2);

        for (int i = 0; i < this.particlesAmount; ++i) {
            Particle particle = this.particles.get(i);
            colorData.put(particle.getR());
            colorData.put(particle.getG());
            colorData.put(particle.getB());
            colorData.put(0.1f);

            colorData.put(particle.getR());
            colorData.put(particle.getG());
            colorData.put(particle.getB());
            colorData.put(1);
        }

        return colorData.flip();
    }

    private void initBuffers() {
        FloatBuffer positionData = this.getPositionData();
        this.positionBuffer.bind();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, positionData, GL33.GL_STATIC_DRAW);

        FloatBuffer colorData = this.getColorData();
        this.colorBuffer.bind();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, colorData, GL33.GL_STATIC_DRAW);
    }

    public void updateBuffers() {
        this.vao.bind();

        FloatBuffer positionData = this.getPositionData();
        this.positionBuffer.bind();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, positionData, GL33.GL_STATIC_DRAW);
        GL33.glEnableVertexAttribArray(0);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);

        FloatBuffer colorData = this.getColorData();
        this.colorBuffer.bind();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, colorData, GL33.GL_STATIC_DRAW);
        GL33.glEnableVertexAttribArray(1);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
    }

    public void draw() {
        this.updateBuffers();

        this.shader.bind();
        GL33.glDrawArrays(GL33.GL_LINES, 0, this.particlesAmount);
        GL33.glDisableVertexAttribArray(0);
        GL33.glDisableVertexAttribArray(1);
    }

    public ShaderProgram getShader() {
        return this.shader;
    }

    @Override
    public void dispose() {
        this.positionBuffer.dispose();
        this.colorBuffer.dispose();
        this.vao.dispose();
        this.shader.dispose();
    }
}
