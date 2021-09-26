#version 330 core

out vec4 frag;
uniform vec3 col;

void main() {
    frag.w = 1.0;
    frag.rgb = col;
}