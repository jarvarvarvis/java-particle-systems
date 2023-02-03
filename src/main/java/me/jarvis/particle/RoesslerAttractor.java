package me.jarvis.particle;

import me.jarvis.opengl.math.Vector3f;

import java.io.IOException;

public class RoesslerAttractor extends DynamicalParticleSystem {

    float a = 0.1f;
    float b = 0.1f;
    float c = 14f;

    public RoesslerAttractor(int particlesAmount) throws IOException {
        super(particlesAmount);
    }

    private Vector3f compute(float x, float y, float z) {
        return new Vector3f(
            -y - z,
            x + a * y,
            b + z * (x - c)
        );
    }

    @Override
    public Vector3f computeNextChange(Vector3f current) {
        float x = current.x(), y = current.y(), z = current.z();
        return this.compute(x, y, z);
    }
}
