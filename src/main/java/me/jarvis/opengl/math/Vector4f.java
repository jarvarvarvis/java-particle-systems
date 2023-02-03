package me.jarvis.opengl.math;

public record Vector4f(float x, float y, float z, float w) {

    public Vector4f add(Vector4f other) {
        return new Vector4f(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    public Vector4f sub(Vector4f other) {
        return new Vector4f(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }
}
