#version 330 core

layout (location = 0) in vec2 position;
uniform mat4 position_matrix;

void main() {
    gl_Position = position_matrix * vec4(position, 0.0, 1.0);
}