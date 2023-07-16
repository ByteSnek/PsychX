#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

void main() {

    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = (2.0 * gl_FragCoord.xy - sResolution) / max(sResolution.x, sResolution.y);

    for (int i = 1; i < 40; i++)
    {
        vec2 sPosition = sProjection;

        sPosition.x += 0.6 / float(i) * sin(float(i) * sProjection.y + Time / 25.0 + 0.3 * float(i)) + 25.0 + 5.0;
        sPosition.y += 0.6 / float(i) * sin(float(i) * sProjection.x + Time / 25.0 + 0.3 * float(i + 10)) - 25.0 + 5.0;

        sProjection = sPosition;

    }

    vec3 sColour = vec3(0.7 * sin(3.0 * sProjection.x) + 0.7, 0.7 * sin(3.0 * sProjection.y) + 0.7, sin(sProjection.x + sProjection.y));

    fragColor = vec4(sColour, 1.0);
}