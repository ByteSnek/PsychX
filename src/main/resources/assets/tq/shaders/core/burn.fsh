#version 150

uniform float Time;
uniform vec3 Colour;
uniform float Alpha;

out vec4 fragColour;

float random(vec2 sVec2In)
{
    return fract(sin(dot(sVec2In.xy, vec2(13, 78))) * 43760.0);
}

float noise(vec2 pPosition)
{
    vec2 sFloor = floor(pPosition);
    vec2 sFract = fract(pPosition);

    float sRed = random(sFloor);
    float sGreen = random(sFloor + vec2(1.0, 0.0));
    float sBlue = random(sFloor + vec2(0.0, 1.0));
    float sAlpha = random(sFloor + vec2(1.0, 1.0));

    vec2 sUV = sFract * sFract * (3.0 - 2.0 * sFract);

    return mix(sRed, sGreen, sUV.x) + (sBlue - sRed) * sUV.y * (1.0 - sUV.x) + (sAlpha - sGreen) * sUV.x * sUV.y;
}

float fbm(vec2 pPosition)
{
    float sResult = 0.0;
    float sFrequency = 1.0;
    float sAmplitude = 0.5;

    for (int i = 0; i < 12; i++)
    {
        sResult += sAmplitude * (noise((pPosition - vec2(1.0)) * sFrequency));
        sFrequency *= 2.0;
        sAmplitude *= 0.6;
    }

    return sResult;
}

float pattern(vec2 pPosition)
{
    vec2 sRawX = vec2(sin(Time * 0.005), sin(Time * 0.01)) * 6.0;
    vec2 sRawMulX = vec2(3.0);

    float sPositionX = fbm(pPosition * sRawMulX + sRawX);

    vec2 sRawY = vec2(sin(Time * 0.01), sin(Time * 0.01)) * 1.0;
    vec2 sRawMulY = vec2(0.5);

    float sPositionY = fbm((pPosition + sPositionX) * sRawMulY + sRawY);

    vec2 sRawZ = vec2(-0.6, -0.5) + vec2(sin(-Time * 0.001), sin(Time * 0.01)) * 2.0;
    vec2 sRawMulZ = vec2(2.0);

    return fbm((pPosition + sPositionY) * sRawMulZ + sRawZ);
}

vec3 palette(float pColour)
{
    vec3 sRed = vec3(0.5, 0.5, 0.5);
    vec3 sGreen = vec3(0.45, 0.25, 0.15);
    vec3 sBlue = vec3(1.0, 1.0, 1.0);
    vec3 sAlpha = vec3(0.0, 0.1, 0.2);

    return sRed + sGreen * cos(6.0 * (sBlue * pColour + sAlpha));
}

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = gl_FragCoord.xy / sResolution.xy;

    sUV.x *= sResolution.x / sResolution.y;

    float sPattern = pow(pattern(sUV), 2.0);
    vec3 sColour = 0.5 + cos(6.0 * (sPattern + fbm(sUV + Time * 0.1) + Time * 0.1) + Colour);

    fragColour = vec4(sColour, Alpha);
}