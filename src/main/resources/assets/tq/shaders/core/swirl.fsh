#version 150

uniform float Time;

out vec4 fragColour;

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = (2.0 * gl_FragCoord.xy - sResolution) / max(sResolution.x, sResolution.y);
    vec2 sPosition = sUV;
    vec3 sColour = vec3(0);

    for (int i = 1; i < 40; i++)
    {
        sPosition.x += 0.6 / float(i) * sin(float(i) * sUV.y + Time / 25.0 + 0.3 * float(i)) + 25.0 + 5.0;
        sPosition.y += 0.6 / float(i) * sin(float(i) * sUV.x + Time / 25.0 + 0.3 * float(i + 10)) - 25.0 + 5.0;
        sUV = sPosition;
    }

    sColour = vec3(0.7 * sin(3.0 * sUV.x) + 0.7, 0.7 * sin(3.0 * sUV.y) + 0.7, sin(sUV.x + sUV.y));
    fragColour = vec4(sColour, 1.0);
}