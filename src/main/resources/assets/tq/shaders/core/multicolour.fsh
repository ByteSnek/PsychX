#version 150

uniform float Time;

out vec4 fragColour;

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = 6. * (gl_FragCoord.xy / sResolution.xy);
    vec3 sColour = vec3(0);

    for (int i = 1; i < 8; i++)
    {
        float sIndex = float(i);
        sUV += vec2(0.7 / sIndex * sin(sIndex * sUV.y + Time + 0.3 * sIndex) + 0.8, 0.4 / sIndex * sin(sIndex * sUV.x + Time + 0.3 * sIndex) + 1.6);
    }

    sColour = vec3(0.5 * sin(sUV.x) + 0.5, 0.5 * sin(sUV.y) + 0.5, sin(sUV.x + sUV.y));

    fragColour = vec4(sColour, 1.0);
}