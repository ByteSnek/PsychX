#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = 6. * (gl_FragCoord.xy / sResolution.xy);

    for (int i = 1; i < 8; i++)
    {
        float sTime = Time;
        float sNumberIn = float(i);

        sProjection += vec2(0.7 / sNumberIn * sin(sNumberIn * sProjection.y + sTime + 0.3 * sNumberIn) + 0.8, 0.4 / sNumberIn * sin(sNumberIn * sProjection.x + sTime + 0.3 * sNumberIn) + 1.6);
    }

    vec3 sColour = vec3(0.5 * sin(sProjection.x) + 0.5, 0.5 * sin(sProjection.y) + 0.5, sin(sProjection.x + sProjection.y));

    fragColor = vec4(sColour, 1.0);
}