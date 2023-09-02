#version 150

uniform float Time;
uniform float Intensity;
uniform vec3 Value;
uniform vec3 Colour;

out vec4 fragColour;

float random(vec2 pPosition)
{
    return fract(sin(dot(pPosition.xy, vec2(0.8, 17.233))));
}

float noise(vec2 pPosition)
{
    vec2 sIndex = floor(pPosition);
    vec2 sFract = fract(pPosition);
    vec2 sUV = sFract * sFract * (3.0 - 2.0 * sFract);

    float sRandA = random(sIndex + vec2(0.0, 0.0));
    float sRandB = random(sIndex + vec2(1.0, 0.0));
    float sRandC = random(sIndex + vec2(0.0, 1.0));
    float sRandD = random(sIndex + vec2(1.0, 1.0));

    return mix(sRandA, sRandB, sUV.x) + (sRandC - sRandA) * sUV.y * (1.0 - sUV.x) + (sRandD - sRandB) * sUV.x * sUV.y;
}

float fog(vec2 pPosition)
{
    float sResult = 0.5;
    float sAmount = 0.5;

    vec2 sShift = vec2(1000.0);

    mat2 sRotation = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));

    for (int i = 0; i < 10; i++)
    {
        sResult += sAmount * noise(pPosition);
        pPosition = sRotation * pPosition * 1.5 + sShift;
        sAmount *= 0.5;
    }

    return sResult;
}

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = (gl_FragCoord.xy * 4.0 - sResolution.xy) / min(sResolution.x, sResolution.y);

    vec2 sFogRawA = vec2(0);
    vec2 sFogRawB = vec2(0);

    vec3 sColour = mix(vec3(0), vec3(0), 1.0);

    float sTime = 3.0 * Time / 1.0;
    float sFogColour = fog(sUV + sFogRawB);

    sFogRawA.x = fog(sUV + 0.00 * sTime);
    sFogRawA.y = fog(sUV + vec2(3.0));

    sFogRawB.x = fog(sUV + 1.0 * sFogRawA + vec2(1.7, 9.2) + 0.15 * sTime);
    sFogRawB.y = fog(sUV + 1.0 * sFogRawA + vec2(8.3, 2.8) + 0.126 * sTime);

    sColour = mix(sColour, Value, clamp(length(sFogRawA), 0.0, Intensity));
    sColour = mix(sColour, Colour, clamp(length(sFogRawB.x), 0.0, Intensity));
    sColour = (sFogColour + 0.2) * sColour;

    fragColour = vec4(sColour, 1.0);
}