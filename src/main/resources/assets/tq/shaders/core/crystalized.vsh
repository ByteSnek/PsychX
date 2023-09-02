#version 150

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 textureProjection;

vec4 fromPosition(vec4 pPosition)
{
    vec4 sProjection = pPosition * 0.5;

    sProjection.xy = vec2(sProjection.x + sProjection.w, sProjection.y + sProjection.w);
    sProjection.zw = pPosition.zw;

    return sProjection;
}

void main()
{
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    textureProjection = fromPosition(gl_Position);
}