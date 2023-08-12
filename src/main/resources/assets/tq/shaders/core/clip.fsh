#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

void main() {
    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = (gl_FragCoord.xy + gl_FragCoord.xy - sResolution) / sResolution.xy;

    float sFxy =  floor(sProjection.x / 0.3 + 0.8 * Time) + floor(sProjection.y / 0.3 + 0.8 * Time);
    float sMd = mod(sFxy, 4.0) > 1.0 ? sProjection.x : sProjection.y;
    float sFc = fract(sMd / 0.3);

    vec3 sColour = vec3(step(sFc, 0.5));

    fragColor = vec4(sColour, 1.0);
}