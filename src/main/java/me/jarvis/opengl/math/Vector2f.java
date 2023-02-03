package me.jarvis.opengl.math;

public record Vector2f(float x, float y) {

    public Vector2f add(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    public Vector2f sub(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    public Vector2f scale(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    public float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public float squaredLength() {
        return this.x * this.x + this.y * this.y;
    }

    public Vector2f reflect(Vector2f wallNormal) {
        assert wallNormal.squaredLength() == 1;
        return this.sub(wallNormal.scale(2).scale(wallNormal.dot(this)));
    }
}
