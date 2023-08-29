#version 150

uniform float Time;
uniform vec3 Colour;
uniform float Alpha;

in vec2 textureProjection;
out vec4 fragColor;

float sNoise(vec3 sUV, float sResolution) {
    const vec3 s = vec3(1e0, 1e2, 1e3);
    sUV *= sResolution;
    vec3 sUV0 = floor(mod(sUV, sResolution)) * s;
    vec3 sUV1 = floor(mod(sUV + vec3(1.0), sResolution)) * s;
    vec3 sFract = fract(sUV);
    sFract = sFract * sFract * (3.0 - 2.0 * sFract);
    vec4 sVec = vec4(sUV0.x + sUV0.y + sUV0.z, sUV1.x + sUV0.y + sUV0.z, sUV0.x + sUV1.y + sUV0.z, sUV1.x + sUV1.y + sUV0.z);
    vec4 sVecFract = fract(sin(sVec * 1e-1) * 1e3);
    float sR0 = mix(mix(sVecFract.x, sVecFract.y, sFract.x), mix(sVecFract.z, sVecFract.w, sFract.x), sFract.y);
    sVecFract = fract(sin((sVec + sUV1.z - sUV0.z) * 1e-1) * 1e3);
    float sR1 = mix(mix(sVecFract.x, sVecFract.y, sFract.x), mix(sVecFract.z, sVecFract.w, sFract.x), sFract.y);
    return mix(sR0, sR1, sFract.z) * 2.0 - 1.0;
}

void main() {
    vec3 sCoord = vec3(textureProjection, 0.0);
    float sColour = 0.0;
    float sRad = (distance(sCoord.xy, vec2(0.5, 0.35)) * 2.5);
    float sLevel = clamp((sCoord.x - 1.0) * 1024.0, 0.0, 1.0);
    float sDensity = 8.0;
    float sNoiseAmount = 0.0;
    for (int i = 1; i <= 5; i++) {
        float power = pow(2.0, float(i));
        sNoiseAmount += (2.0 / power) * max(sNoise((sCoord * 1.75) + vec3(Time * 0.0, Time * 0.0, Time * -0.01), power * sDensity), -1.0);
    }
    sNoiseAmount *= 0.5;
    vec3 sFragColour = Colour;
    float sTime1 = -1.0 + mod(Time, 6.0) + sNoiseAmount;
    float sTime2 = -1.0 + mod(Time + 2.0, 6.0) + sNoiseAmount;
    float sTime3 = -1.0 + mod(Time + 4.0, 6.0) + sNoiseAmount;
    sColour = min(min(abs(sRad - sTime1), abs(sRad - sTime2)), abs(sRad - sTime3));
    sColour += sLevel;
    fragColor = vec4(sFragColour, max(0.1, 1.0 - pow(sColour, 0.3)) * (1.0 - sLevel) * Alpha);
}