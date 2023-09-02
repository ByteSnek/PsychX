#version 150

uniform float Time;

out vec4 fragColour;

float random(vec2 pPosition)
{
    return fract(sin(dot(pPosition.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float noise(vec2 pPosition)
{
    vec2 sFloor = floor(pPosition);
    vec2 sFract = fract(pPosition);

    float sRand1 = random(sFloor + vec2(0.0, 0.0));
    float sRand2 = random(sFloor + vec2(1.0, 0.0));
    float sRand3 = random(sFloor + vec2(0.0, 1.0));
    float sRand4 = random(sFloor + vec2(1.0, 1.0));

    vec2 sUV = sFract * sFract * (3.0 - 2.0 * sFract);

    return mix(sRand1, sRand2, sUV.x) + (sRand3 - sRand1) * sUV.y * (1.0 - sUV.x) + (sRand4 - sRand2) * sUV.x * sUV.y;
}

float fractalMotion(vec2 pPosition)
{
    float sPosition = 0.0;
    float sAmount = 0.5;

    vec2 sShift = vec2(100.0);

    mat2 sRotate = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));

    for (int i = 0; i < 16; i++)
    {
        sPosition += sAmount * noise(pPosition);
        pPosition = sRotate * pPosition * 2.0 + sShift;
        sAmount *= 0.5;
    }

    return sPosition;
}

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = (gl_FragCoord.xy * 2.0 - sResolution.xy) / min(sResolution.x, sResolution.y);

    float sTime = 5.0 * Time / 5.0;

    vec2 sQuat = vec2(1.0);

    sQuat.x = fractalMotion(sUV + 0.00 * sTime);
    sQuat.y = fractalMotion(sUV + vec2(1.0));

    vec2 sRotation = vec2(0.0);

    sRotation.x = fractalMotion(sUV + 7.0 * sQuat + vec2(1.7, 9.2) + 0.15 * sTime);
    sRotation.y = fractalMotion(sUV + 2.0 * sQuat + vec2(8.3, 2.8) + 0.126 * sTime);

    vec2 sUV0 = gl_FragCoord.xy / sResolution.xy - 0.5;

    sUV0.x *= (sResolution.x / sResolution.y);

    vec3 sColour = vec3(0.0);

    sColour = vec3(fractalMotion(sUV0 * (1.0 + 0.2 * sin(Time * 0.666))));
    sColour = mix(sColour, vec3(0.5 + 0.5 * sin(Time + sUV0.yxx + vec3(0, 2, 4))), 0.9);
    sColour = mix(sColour, vec3(1.0), clamp(length(sRotation.x), 0.0, 1.0));

    fragColour = vec4(sColour, 1.0);
}