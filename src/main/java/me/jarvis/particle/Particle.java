package me.jarvis.particle;

public class Particle {
    
    private float x, y, z;
    private float previousX, previousY, previousZ;
    private float previousPreviousX, previousPreviousY, previousPreviousZ;
    private float r, g, b;

    public Particle(float x, float y, float z,
                    float r, float g, float b) {
        this.x = x; this.y = y; this.z = z;
        this.previousX = x; this.previousY = y; this.previousZ = z;
        this.previousPreviousX = x; this.previousPreviousY = y; this.previousPreviousZ = z;
        this.r = r; this.g = g; this.b = b;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.previousPreviousX = this.previousX;
        this.previousX = this.x;
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.previousPreviousY = this.previousY;
        this.previousY = this.y;
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.previousPreviousZ = this.previousZ;
        this.previousZ = this.z;
        this.z = z;
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getPreviousX() {
        return this.previousPreviousX;
    }

    public float getPreviousY() {
        return this.previousPreviousY;
    }

    public float getPreviousZ() {
        return this.previousPreviousZ;
    }


    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
}
