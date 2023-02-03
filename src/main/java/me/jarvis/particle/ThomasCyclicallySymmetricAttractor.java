package me.jarvis.particle;

import me.jarvis.opengl.math.Vector3f;
import me.jarvis.util.Mathf;

import java.io.IOException;

public class ThomasCyclicallySymmetricAttractor extends DynamicalParticleSystem {

    final float b = 0.208186f;

    public ThomasCyclicallySymmetricAttractor(int particlesAmount) throws IOException {
        super(particlesAmount);
    }

    private Vector3f compute(float x, float y, float z) {
        return new Vector3f(
            Mathf.sin(y) - this.b * x,
            Mathf.sin(z) - this.b * y,
            Mathf.sin(x) - this.b * z
        );
    }

    @Override
    public Vector3f computeNextChange(Vector3f current) {
        float x = current.x(), y = current.y(), z = current.z();
        return this.compute(x, y, z);
    }
}
