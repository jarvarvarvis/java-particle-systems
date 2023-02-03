package me.jarvis.util;

import me.jarvis.opengl.math.Vector3f;

public class Mathf {

    public static final float PI = (float) Math.PI;

    public static float toRadians(float value) {
        return value * PI / 180.f;
    }

    public static float random() {
        return (float) Math.random();
    }

    public static float randomInRange(float min, float max) {
        assert min <= max;
        return Mathf.lerp(min, max, Mathf.random());
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    public static float lerp(float start, float end, float value) {
        return start * value + end * (1 - value);
    }

    public static Vector3f lerpVector(Vector3f start, Vector3f end, float value) {
        return new Vector3f(
            lerp(start.x(), end.x(), value),
            lerp(start.y(), end.y(), value),
            lerp(start.z(), end.z(), value)
        );
    }

    public static Vector3f lerpVector(Vector3f start, Vector3f end, Vector3f value) {
        return new Vector3f(
            lerp(start.x(), end.x(), value.x()),
            lerp(start.y(), end.y(), value.y()),
            lerp(start.z(), end.z(), value.z())
        );
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }

    public static float acos(float value) {
        return (float) Math.acos(value);
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static float cbrt(float value) {
        return (float) Math.cbrt(value);
    }

    public static float exp(float value) {
        return (float) Math.exp(value);
    }
}
