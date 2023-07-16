#version 150

in vec3 Position;
in vec2 UV;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;

out vec2 textureProjection;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    textureProjection = UV;
}