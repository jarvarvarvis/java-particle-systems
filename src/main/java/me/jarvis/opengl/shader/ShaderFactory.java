package me.jarvis.opengl.shader;

import me.jarvis.resources.ResourceLoader;
import org.lwjgl.opengl.GL33;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShaderFactory {

    public static Shader createShader(int type, CharSequence sourceCode) {
        Shader shader = new Shader(type);
        shader.setSourceCode(sourceCode);
        shader.compile();

        return shader;
    }

    public static Shader createVertexShader(CharSequence sourceCode) {
        return createShader(GL33.GL_VERTEX_SHADER, sourceCode);
    }

    public static Shader createFragmentShader(CharSequence sourceCode) {
        return createShader(GL33.GL_FRAGMENT_SHADER, sourceCode);
    }

    public static Shader loadShader(int type, String path) throws IOException {
        Path filePath = Path.of(path);
        String source = Files.readString(filePath);
        return createShader(type, source);
    }

    public static ShaderProgram loadProgramFromResources(String vertexPath, String fragmentPath) throws IOException {
        String vertexCode, fragCode;
        try (ResourceLoader loader = new ResourceLoader(vertexPath)) {
            vertexCode = loader.readToString();
        }
        try (ResourceLoader loader = new ResourceLoader(fragmentPath)) {
            fragCode = loader.readToString();
        }

        Shader vertex = ShaderFactory.createVertexShader(vertexCode);
        Shader fragment = ShaderFactory.createFragmentShader(fragCode);

        ShaderProgram program = new ShaderProgram()
            .attachShader(vertex)
            .attachShader(fragment)
            .link();

        vertex.dispose();
        fragment.dispose();

        return program;
    }
}
