package me.jarvis.opengl.math;

import me.jarvis.util.Mathf;

public record Vector3f(float x, float y, float z) {

    public static final Vector3f ZERO = new Vector3f(0, 0, 0);
    public static final Vector3f UP = new Vector3f(0, 1, 0);

    public Vector3f addX(float x) {
        return new Vector3f(this.x + x, this.y, this.z);
    }

    public Vector3f addY(float y) {
        return new Vector3f(this.x, this.y + y, this.z);
    }

    public Vector3f addZ(float z) {
        return new Vector3f(this.x, this.y, this.z + z);
    }

    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3f sub(Vector3f other) {
        return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3f scale(float scalar) {
        return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public float squaredLength() {
        return this.dot(this);
    }

    public float length() {
        return Mathf.sqrt(this.squaredLength());
    }

    public Vector3f normalized() {
        float length = this.length();
        return this.scale(1.f / length);
    }

    public Vector3f cross(Vector3f other) {
        return new Vector3f(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }
}
