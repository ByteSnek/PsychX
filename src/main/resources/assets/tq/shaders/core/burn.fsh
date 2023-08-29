#version 150

uniform float Time;
uniform vec3 Colour;
uniform float Alpha;

in vec2 textureProjection;
out vec4 fragColor;

float random(vec2 sVec2In) {
    return fract(sin(dot(sVec2In.xy, vec2(13, 78))) * 43760.0);
}

float noise(vec2 sVec2In) {
    vec2 sFloor = floor(sVec2In);
    vec2 sFract = fract(sVec2In);
    float sRandom1 = random(sFloor);
    float sRandom2 = random(sFloor + vec2(1.0, 0.0));
    float sRandom3 = random(sFloor + vec2(0.0, 1.0));
    float sRandom4 = random(sFloor + vec2(1.0, 1.0));
    vec2 sU = sFract * sFract * (3.0 - 2.0 * sFract);
    return mix(sRandom1, sRandom2, sU.x) + (sRandom3 - sRandom1) * sU.y * (1.0 - sU.x) + (sRandom4 - sRandom2) * sU.x * sU.y;
}

float fbm(vec2 sVec2In) {
    float sValue = 0.0;
    float sFrequency = 1.0;
    float sAmplitude = 0.5;
    for (int i = 0; i < 12; i++) {
        sValue += sAmplitude * (noise((sVec2In - vec2(1.0)) * sFrequency));
        sFrequency *= 2.0;
        sAmplitude *= 0.6;
    }
    return sValue;
}

float pattern(vec2 sVec2In) {
    vec2 sOffset = vec2(-0.5);
    vec2 sPos1 = vec2(sin(Time * 0.005), sin(Time * 0.01)) * 6.0;
    vec2 sScale1 = vec2(3.0);
    float s1 = fbm(sVec2In * sScale1 + sPos1);
    vec2 sPos2 = vec2(sin(Time * 0.01), sin(Time * 0.01)) * 1.0;
    vec2 sScale2 = vec2(0.5);
    float s2 = fbm((sVec2In + s1) * sScale2 + sPos2);
    vec2 sPos3 = vec2(-0.6, -0.5) + vec2(sin(-Time * 0.001), sin(Time * 0.01)) * 2.0;
    vec2 sScale3 = vec2(2.);
    float s3 = fbm((sVec2In + s2) * sScale3 + sPos3);
    return s3;
}

vec3 palette(float sFloatIn) {
    vec3 sVec1 = vec3(0.5, 0.5, 0.5);
    vec3 sVec2 = vec3(0.45, 0.25, 0.15);
    vec3 sVec3 = vec3(1.0, 1.0, 1.0);
    vec3 sVec4 = vec3(0.0, 0.1, 0.2);
    return sVec1 + sVec2 * cos(6.0 * (sVec3 * sFloatIn + sVec4));
}

void main() {
    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = gl_FragCoord.xy / sResolution.xy;
    sProjection.x *= sResolution.x / sResolution.y;
    float sValue = pow(pattern(sProjection), 2.0);
    vec3 sColour = 0.5 + cos(6.0 * (sValue + fbm(sProjection + Time * 0.1) + Time * 0.1) + Colour);
    fragColor = vec4(sColour, Alpha);
}