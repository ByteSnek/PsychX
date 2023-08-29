#version 150

uniform float Time;
uniform float Intensity;
uniform vec3 Value;
uniform vec3 Colour;

in vec2 textureProjection;
out vec4 fragColor;

float sRandom(vec2 sRandomPosition) {

    return fract(sin(dot(sRandomPosition.xy, vec2(0.8, 17.233))));

}

float sNoise(vec2 sNoisePosition) {

    vec2 sIndex = floor(sNoisePosition);
    vec2 sFract = fract(sNoisePosition);

    vec2 sUV = sFract * sFract * (3.0 - 2.0 * sFract);

    float sA = sRandom(sIndex + vec2(0.0, 0.0));
    float sB = sRandom(sIndex + vec2(1.0, 0.0));
    float sC = sRandom(sIndex + vec2(0.0, 1.0));
    float sD = sRandom(sIndex + vec2(1.0, 1.0));

    return mix(sA, sB, sUV.x) + (sC - sA) * sUV.y * (1.0 - sUV.x) + (sD - sB) * sUV.x * sUV.y;
}

float sFog(vec2 sFogPosition) {

    float sVertex = 0.5;
    float sAmount = 0.5;

    vec2 sShift = vec2(1000.0);

    mat2 sRotation = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));

    for (int i = 0; i < 10; i++) {

        sVertex += sAmount * sNoise(sFogPosition);

        sFogPosition = sRotation * sFogPosition * 1.5 + sShift;

        sAmount *= 0.5;

    }

    return sVertex;
}

void main() {

    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = (gl_FragCoord.xy * 4.0 - sResolution.xy) / min(sResolution.x, sResolution.y);

    float sNewTime = 3.0 * Time / 1.0;

    vec2 sQuaternion = vec2(0.0);

    sQuaternion.x = sFog(sProjection + 0.00 * sNewTime);
    sQuaternion.y = sFog(sProjection + vec2(3.0));

    vec2 sNewRandom = vec2(0.0);

    sNewRandom.x = sFog(sProjection + 1.0 * sQuaternion + vec2(1.7, 9.2) + 0.15 * sNewTime);
    sNewRandom.y = sFog(sProjection + 1.0 * sQuaternion + vec2(8.3, 2.8) + 0.126 * sNewTime);

    float sFogColour = sFog(sProjection + sNewRandom);

    vec3 sColour = mix(vec3(0, 0, 0), vec3(0, 0, 0), 1.0);

    sColour = mix(sColour, Value, clamp(length(sQuaternion), 0.0, Intensity));
    sColour = mix(sColour, Colour, clamp(length(sNewRandom.x), 0.0, Intensity));

    sColour = (sFogColour + 0.2) * sColour;

    fragColor = vec4(sColour, 1);
}