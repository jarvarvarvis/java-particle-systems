#version 330

layout (location = 0) in vec3 vertexPosition;
layout (location = 1) in vec4 vertexColor;

uniform mat4 mvp;

out vec4 passedVertexColor;

void main() {
    gl_Position = mvp * vec4(vertexPosition / 10, 1.0);
    passedVertexColor = vertexColor;
}
