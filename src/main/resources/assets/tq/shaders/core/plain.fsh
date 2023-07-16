#version 150

uniform vec4 Colour;

in vec2 textureProjection;
out vec4 fragColor;

void main()
{
    fragColor = vec4(Colour);
}