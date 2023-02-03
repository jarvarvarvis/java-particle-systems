package me.jarvis.util;

import me.jarvis.opengl.math.Vector3f;

public class TrigonometryUtils {

    private static float wrapSmallerDegrees(float value) {
        while (value < 0)
            value += 360;
        return value;
    }

    private static float wrapLargerDegrees(float value) {
        while (value > 360)
            value -= 360;
        return value;
    }

    public static float wrapDegrees(float value) {
        if (value > 360) return wrapLargerDegrees(value);
        if (value < 0) return wrapSmallerDegrees(value);
        return value;
    }

    public static Vector3f randomInUnitSphere() {
        float theta = Mathf.random() * 2.0f * Mathf.PI;
        float phi = Mathf.acos(2.0f * Mathf.random() - 1.0f);
        float r = Mathf.cbrt(Mathf.random());

        float sinTheta = Mathf.sin(theta);
        float cosTheta = Mathf.cos(theta);

        float sinPhi = Mathf.sin(phi);
        float cosPhi = Mathf.cos(phi);

        return new Vector3f(
            r * sinPhi * cosTheta,
            r * sinPhi * sinTheta,
            r * cosPhi);
    }

    public static Vector3f degreeEulerAnglesToVector(float yawDegrees, float pitchDegrees) {
        float yaw = (float)Math.toRadians(yawDegrees);
        float pitch = (float)Math.toRadians(pitchDegrees);

        float x = (float)(Math.cos(yaw) * Math.cos(pitch));
        float y = (float)(Math.sin(pitch));
        float z = (float)(Math.sin(yaw) * Math.cos(pitch));

        return new Vector3f(x, y, z);
    }
}
