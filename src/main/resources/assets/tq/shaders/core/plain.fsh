#version 150

uniform vec4 Colour;

out vec4 fragColour;

void main()
{
    fragColour = vec4(Colour);
}