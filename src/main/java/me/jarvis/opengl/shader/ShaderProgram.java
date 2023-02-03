package me.jarvis.opengl.shader;

import me.jarvis.opengl.base.Bindable;
import me.jarvis.opengl.base.GLObject;
import me.jarvis.opengl.math.Matrix4f;
import me.jarvis.opengl.math.Vector2f;
import org.lwjgl.opengl.GL33;

import javax.annotation.Nonnull;

public class ShaderProgram extends GLObject implements Bindable {

    public ShaderProgram() {
        super(GL33.glCreateProgram());
    }

    public ShaderProgram attachShader(@Nonnull Shader shader) {
        GL33.glAttachShader(this.getHandle(), shader.getHandle());
        return this;
    }

    public ShaderProgram link() {
        GL33.glLinkProgram(this.getHandle());
        this.checkStatus();
        return this;
    }

    public void checkStatus() {
        int status = GL33.glGetProgrami(this.getHandle(), GL33.GL_LINK_STATUS);
        if (status != GL33.GL_TRUE)
            throw new RuntimeException(GL33.glGetProgramInfoLog(this.getHandle()));
    }


    @Override
    public void bind() {
        GL33.glUseProgram(this.getHandle());
    }

    @Override
    public void unbind() {
        GL33.glUseProgram(0);
    }


    public int getAttributeLocation(CharSequence name) {
        return GL33.glGetAttribLocation(this.getHandle(), name);
    }

    public int getUniformLocation(CharSequence name) {
        return GL33.glGetUniformLocation(this.getHandle(), name);
    }


    public void setUniform(String name, int value) {
        GL33.glUniform1i(this.getUniformLocation(name), value);
    }

    public void setUniform(String name, Vector2f value) {
        GL33.glUniform2f(this.getUniformLocation(name), value.x(), value.y());
    }

    public void setUniform(String name, Matrix4f value) {
        GL33.glUniformMatrix4fv(this.getUniformLocation(name), false, value.toBuffer());
    }

    @Override
    public void dispose() {
        this.unbind();
        GL33.glDeleteProgram(this.getHandle());
    }
}
