package me.jarvis.particle;

import me.jarvis.opengl.math.Vector3f;

import java.io.IOException;

public class LorenzAttractor extends DynamicalParticleSystem {

    final float sigma = 10f;
    final float rho = 28f;
    final float beta = 8f / 3;

    public LorenzAttractor(int particlesAmount) throws IOException {
        super(particlesAmount);
    }

    private Vector3f compute(float x, float y, float z) {
        return new Vector3f(
            this.sigma * (y - x),
            x * (this.rho - z) - y,
            x * y - this.beta * z
        );
    }

    @Override
    public Vector3f computeNextChange(Vector3f current) {
        float x = current.x(), y = current.y(), z = current.z();
        return this.compute(x, y, z);
    }
}
