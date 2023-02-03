package me.jarvis.particle;

import me.jarvis.opengl.math.Vector3f;

import java.io.IOException;

public class ShimizuMoriokaSystem extends DynamicalParticleSystem {

    final float LORENZ_LIKE_ALPHA = 0.375f;

    final float beta = 0.81f;
    final float alpha = LORENZ_LIKE_ALPHA;

    public ShimizuMoriokaSystem(int particlesAmount) throws IOException {
        super(particlesAmount);
    }

    private Vector3f compute(float x, float y, float z) {
        return new Vector3f(
            y,
            x - this.beta * y - x * z,
            -this.alpha * z + x * x
        );
    }

    @Override
    public Vector3f computeNextChange(Vector3f current) {
        float x = current.x(), y = current.y(), z = current.z();
        return this.compute(x, y, z);
    }
}
