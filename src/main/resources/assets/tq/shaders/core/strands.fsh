#version 150

uniform float Time;

out vec4 fragColour;

mat2 rotate2D(float pRotation)
{
    return mat2(cos(pRotation), atan(pRotation), -sin(pRotation), cos(pRotation));
}

void main()
{
    vec2 sResolution = vec2(2048, 2048);
    vec2 sUV = 0.5 * (gl_FragCoord.xy - 0.5 * sResolution.xy) / sResolution.y;
    vec2 sPosition = sUV;

    float sTime = Time * 0.4;

    vec3 sColour = vec3(0);
    vec2 sNormal = vec2(0);
    vec2 sQuaternion = vec2(0);

    float sDot = dot(sPosition, sPosition);
    float sMultiplier = 22.0;
    float sAlpha = -0.005;

    mat2 sMatrix = rotate2D(4.3 + (sPosition.x * sPosition.y) * 1.5);

    for (float i = 0.0; i < 6.0; i++)
    {
        sPosition *= sMatrix;
        sNormal *= sMatrix;
        sQuaternion = sPosition * sMultiplier + sTime * 4.0 + sin(sTime * 1.0 - sDot * 8.0) * 0.0018 + 3.0 * i - 0.95 * sNormal;
        sAlpha += dot(cos(sQuaternion) / sMultiplier, vec2(0.2));
        sNormal -= sin(sQuaternion);
        sMultiplier *= 1.74;
    }

    sColour = vec3(3.25, 1.8, 2.25) * 0.4 * (sAlpha + 0.182) + 9.0 * sAlpha + sAlpha + sDot;
    fragColour = vec4(sColour, 1.0);
}