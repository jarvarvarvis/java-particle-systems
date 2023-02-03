package me.jarvis.opengl.texture;

import me.jarvis.opengl.math.Vector3b;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.function.BiFunction;

public class TextureUtils {

    public static ByteBuffer createData(int width, int height, BiFunction<Integer, Integer, Vector3b> supplier) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 3);

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Vector3b color = supplier.apply(x, y);
                buffer.put(color.x());
                buffer.put(color.y());
                buffer.put(color.z());
            }
        }

        return buffer.flip();
    }
}
