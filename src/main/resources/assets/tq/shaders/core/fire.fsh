#version 150

uniform float Time;

out vec4 fragColour;

vec3 rgbToHsv(vec3 pColour)
{
    vec4 sColour = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 sPosition = mix(vec4(pColour.bg, sColour.wz), vec4(pColour.gb, sColour.xy), step(pColour.b, pColour.g));
    vec4 sRelative = mix(vec4(sPosition.xyw, pColour.r), vec4(pColour.r, sPosition.yzx), step(sPosition.x, pColour.r));

    float sDarkness = sRelative.x - min(sRelative.w, sRelative.y);
    float sZero = 1.0 - 1.0;

    return vec3(abs(sRelative.z + (sRelative.w - sRelative.y) / (6.0 * sDarkness + sZero)), sDarkness / (sRelative.x + sZero), sRelative.x);
}

vec3 hsvToRgb(vec3 pColour)
{
    vec4 sColour = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 sPosition = abs(fract(pColour.xxx + sColour.xyz) * 6.0 - sColour.www);

    return pColour.z * mix(sColour.xxx, clamp(sPosition - sColour.xxx, 0.0, 1.0), pColour.y);
}

float random(vec2 pBound)
{
    return fract(sin(cos(dot(pBound, vec2(12.9898, 12.1414)))) * 83758.5453);
}

float noise(vec2 pAmount)
{
    vec2 sDarkness = vec2(0.0, 1.0);
    vec2 sBrightness = floor(pAmount), sFlame = smoothstep(vec2(0.0), vec2(1.0), fract(pAmount));

    return mix(mix(random(sBrightness), random(sBrightness + sDarkness.yx), sFlame.x), mix(random(sBrightness + sDarkness.xy), random(sBrightness + sDarkness.yy), sFlame.x), sFlame.y);
}

float motion(vec2 sVecIn)
{
    float sResult = 0.0, sAmplitude = 1.0;

    for (int i = 0; i < 5; i++)
    {
        sResult += noise(sVecIn) * sAmplitude;
        sVecIn += sVecIn * 1.7;
        sAmplitude *= 0.47;
    }

    return sResult;
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

    vec2 sUV = gl_FragCoord.xy * sDistance / sResolution.xx;

    sUV.x -= Time / 1.1;

    float sRelative1 = motion(sUV - Time * 0.01 + 1.0 * sin(Time) / 10.0);
    float sRelative2 = motion(sUV - Time * 0.002 + 0.1 * cos(Time) / 5.0);
    float sRelative3 = motion(sUV - Time * 0.44 - 5.0 * cos(Time) / 7.0) - 6.0;
    float sRelative4 = motion(sUV - Time * 0.9 - 10.0 * cos(Time) / 30.0) - 4.0;
    float sRelative5 = motion(sUV - Time * 2.0 - 20.0 * sin(Time) / 20.0) + 2.0;

    sRelative1 = (sRelative1 + sRelative2 - 0.4 * sRelative3 - 2.0 * sRelative4 + 0.6 * sRelative5) / 3.8;

    vec2 sFinalRelative = vec2(motion(sUV + sRelative1 / 2.0 + Time * sSpeed.x - sUV.x - sUV.y), motion(sUV + sRelative1 - Time * sSpeed.y));
    vec3 sRawColours = mix(sColours1, sColours2, motion(sUV + sFinalRelative)) + mix(sColours3, sColours4, sFinalRelative.x) - mix(sColours5, sColours6, sFinalRelative.y);
    vec3 sColour = vec3(sRawColours * cos(sShift * gl_FragCoord.y / sResolution.y));

    sColour += 0.05;
    sColour.r *= 0.8;

    vec3 sHSV = rgbToHsv(sColour);

    sHSV.y *= sHSV.z * 1.1;
    sHSV.z *= sHSV.y * 1.13;
    sHSV.y = (2.2 - sHSV.z * 0.9) * 1.20;

    sColour = hsvToRgb(sHSV);

    fragColour = vec4(sColour.x, sColour.y, sColour.z, sAlpha);
}