package me.jarvis.particle;

import me.jarvis.opengl.math.Vector3f;

import java.io.IOException;

public abstract class DynamicalParticleSystem extends ParticleSystem {

    public DynamicalParticleSystem(int particlesAmount) throws IOException {
        super(particlesAmount);
    }

    public abstract Vector3f computeNextChange(Vector3f current);

    public void update(float deltaTime) {
        for (Particle particle : this.particles) {
            float x = particle.getX(), y = particle.getY(), z = particle.getZ();
            Vector3f next = this.computeNextChange(new Vector3f(x, y, z));

            float velocityX = next.x() * deltaTime;
            float velocityY = next.y() * deltaTime;
            float velocityZ = next.z() * deltaTime;
            particle.setX(x + velocityX);
            particle.setY(y + velocityY);
            particle.setZ(z + velocityZ);
        }
    }
}
