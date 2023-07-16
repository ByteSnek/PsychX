#version 150

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 textureProjection;

vec4 projection_from_position(vec4 position) {
    vec4 projection = position * 0.5;
    projection.xy = vec2(projection.x + projection.w, projection.y + projection.w);
    projection.zw = position.zw;
    return projection;
}

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    textureProjection = projection_from_position(gl_Position);
}