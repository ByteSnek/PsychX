#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

vec3 sRGBtoHSV(vec3 sVecIn)
{
    vec4 sConversion = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 sPosition = mix(vec4(sVecIn.bg, sConversion.wz), vec4(sVecIn.gb, sConversion.xy), step(sVecIn.b, sVecIn.g));
    vec4 sRelative = mix(vec4(sPosition.xyw, sVecIn.r), vec4(sVecIn.r, sPosition.yzx), step(sPosition.x, sVecIn.r));

    float sDarkness = sRelative.x - min(sRelative.w, sRelative.y);
    float sZero = 1.0 - 1.0;

    return vec3(abs(sRelative.z + (sRelative.w - sRelative.y) / (6.0 * sDarkness + sZero)), sDarkness / (sRelative.x + sZero), sRelative.x);
}

vec3 sHSVtoRGB(vec3 sVecIn)
{
    vec4 sConversion = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 sPosition = abs(fract(sVecIn.xxx + sConversion.xyz) * 6.0 - sConversion.www);

    return sVecIn.z * mix(sConversion.xxx, clamp(sPosition - sConversion.xxx, 0.0, 1.0), sVecIn.y);
}

float sRandom(vec2 sVecIn)
{
    return fract(sin(cos(dot(sVecIn, vec2(12.9898, 12.1414)))) * 83758.5453);
}

float sNoise(vec2 sVecIn)
{
    vec2 sDarkness = vec2(0.0, 1.0);
    vec2 sBrightness = floor(sVecIn), sFlame = smoothstep(vec2(0.0), vec2(1.0), fract(sVecIn));

    return mix(mix(sRandom(sBrightness), sRandom(sBrightness + sDarkness.yx), sFlame.x), mix(sRandom(sBrightness + sDarkness.xy), sRandom(sBrightness + sDarkness.yy), sFlame.x), sFlame.y);
}

float sFractMotion(vec2 sVecIn)
{
    float sTotal = 0.0, sAmplitude = 1.0;

    for (int i = 0; i < 5; i++)
    {
        sTotal += sNoise(sVecIn) * sAmplitude;

        sVecIn += sVecIn * 1.7;

        sAmplitude *= 0.47;
    }

    return sTotal;
}

void main()
{
    vec2 sResolution = vec2(256, 256);

    vec3 sColours1 = vec3(0.5, 0.0, 0.1);
    vec3 sColours2 = vec3(0.9, 0.1, 0.0);
    vec3 sColours3 = vec3(0.2, 0.1, 0.7);
    vec3 sColours4 = vec3(1.0, 0.9, 0.1);
    vec3 sColours5 = vec3(0.1);
    vec3 sColours6 = vec3(0.9);

    vec2 sSpeed = vec2(1.2, 0.1);

    float sShift = 1.327 + sin(Time * 2.0) / 2.4;
    float sAlpha = 1.0;

    float sDistance = 3.5 - sin(Time * 0.4) / 1.89;

    vec2 sProjection = gl_FragCoord.xy * sDistance / sResolution.xx;

    sProjection.x -= Time / 1.1;

    float sRelative1 = sFractMotion(sProjection - Time * 0.01 + 1.0 * sin(Time) / 10.0);
    float sRelative2 = sFractMotion(sProjection - Time * 0.002 + 0.1 * cos(Time) / 5.0);
    float sRelative3 = sFractMotion(sProjection - Time * 0.44 - 5.0 * cos(Time) / 7.0) - 6.0;
    float sRelative4 = sFractMotion(sProjection - Time * 0.9 - 10.0 * cos(Time) / 30.0) - 4.0;
    float sRelative5 = sFractMotion(sProjection - Time * 2.0 - 20.0 * sin(Time) / 20.0) + 2.0;

    sRelative1 = (sRelative1 + sRelative2 - 0.4 * sRelative3 - 2.0 * sRelative4 + 0.6 * sRelative5) / 3.8;

    vec2 sFinalRelative = vec2(sFractMotion(sProjection + sRelative1 / 2.0 + Time * sSpeed.x - sProjection.x - sProjection.y), sFractMotion(sProjection + sRelative1 - Time * sSpeed.y));
    vec3 sRawColours = mix(sColours1, sColours2, sFractMotion(sProjection + sFinalRelative)) + mix(sColours3, sColours4, sFinalRelative.x) - mix(sColours5, sColours6, sFinalRelative.y);
    vec3 sColour = vec3(sRawColours * cos(sShift * gl_FragCoord.y / sResolution.y));

    sColour += 0.05;
    sColour.r *= 0.8;

    vec3 sHSV = sRGBtoHSV(sColour);

    sHSV.y *= sHSV.z * 1.1;
    sHSV.z *= sHSV.y * 1.13;
    sHSV.y = (2.2 - sHSV.z * 0.9) * 1.20;

    sColour = sHSVtoRGB(sHSV);

    fragColor = vec4(sColour.x, sColour.y, sColour.z, sAlpha);
}