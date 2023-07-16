#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

mat3 sRotateX(float sNumIn)
{
    float sCosine = cos(sNumIn);
    float sSine = sin(sNumIn);

    return mat3(1, 0, 0, 0, sCosine, -sSine, 0, sSine, sCosine);
}

mat3 sRotateY(float sNumIn)
{
    float sCosine = cos(sNumIn);
    float sSine = sin(sNumIn);

    return mat3(sCosine, 0, -sSine, 0, 1, 0, sSine, 0, sCosine);
}

float sRandom(vec2 sVecIn)
{
    return fract(sin(dot(sVecIn.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float sNoise(vec2 sVecIn)
{
    vec2 sFloor = floor(sVecIn);
    vec2 sFract = fract(sVecIn);

    float sRand1 = sRandom(sFloor + vec2(0.0, 0.0));
    float sRand2 = sRandom(sFloor + vec2(1.0, 0.0));
    float sRand3 = sRandom(sFloor + vec2(0.0, 1.0));
    float sRand4 = sRandom(sFloor + vec2(1.0, 1.0));

    vec2 sUV = sFract * sFract * (3.0 - 2.0 * sFract);

    return mix(sRand1, sRand2, sUV.x) + (sRand3 - sRand1) * sUV.y * (1.0 - sUV.x) + (sRand4 - sRand2) * sUV.x * sUV.y;
}

float sFractMotion(vec2 sVecIn)
{
    float sPosition = 0.0;
    float sAmount = 0.5;

    vec2 sShift = vec2(100.0);

    mat2 sRotate = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));

    for (int i = 0; i < 16; i++)
    {
        sPosition += sAmount * sNoise(sVecIn);

        sVecIn = sRotate * sVecIn * 2.0 + sShift;

        sAmount *= 0.5;
    }

    return sPosition;
}

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sProjection = (gl_FragCoord.xy * 2.0 - sResolution.xy) / min(sResolution.x, sResolution.y);

    float sTime = 5.0 * Time / 5.0;

    vec2 sQuaternion = vec2(1.0);

    sQuaternion.x = sFractMotion(sProjection + 0.00 * sTime);
    sQuaternion.y = sFractMotion(sProjection + vec2(1.0));

    vec2 sRotation = vec2(0.0);

    sRotation.x = sFractMotion(sProjection + 7.0 * sQuaternion + vec2(1.7, 9.2) + 0.15 * sTime);
    sRotation.y = sFractMotion(sProjection + 2.0 * sQuaternion + vec2(8.3, 2.8) + 0.126 * sTime);

    vec2 sUV = gl_FragCoord.xy / sResolution.xy - 0.5;

    sUV.x *= (sResolution.x / sResolution.y);

    vec3 sColour = vec3(0.0);

    sColour = vec3(sFractMotion(sUV * (1.0 + 0.2 * sin(Time * 0.666))));

    sColour = mix(sColour, vec3(0.5 + 0.5 * sin(Time + sUV.yxx + vec3(0, 2, 4))), 0.9);

    sColour = mix(sColour, vec3(1.0), clamp(length(sRotation.x), 0.0, 1.0));

    fragColor = vec4(sColour, 1.0);
}