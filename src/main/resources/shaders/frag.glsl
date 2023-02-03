#version 330

in vec4 passedVertexColor;
out vec4 fragColor;

void main() {
    fragColor = passedVertexColor;
}
