#version 150

uniform float Time;

in vec2 textureProjection;
out vec4 fragColor;

vec3 sRaytraceX;
vec3 sRaytraceY;
vec3 sRaytraceZ;
vec3 sLight = vec3(0.0, 0.0, 1.0);
vec2 sSeed = vec2(0.0, 0.0);
vec4 sColours1 = vec4(0.0, 157.0, 113.0, 270.0);
vec4 sColours2 = vec4(1.0, 158.0, 114.0, 271.0);
float sColourMax = 1.0;
float sRadius = 0.25;
float sZoom = 0.0;
float sOctaves = 15.0;
float sPower;
float sInitialResolution;

vec4 sRandom(vec4 sVecIn)
{
    return fract(sin(sVecIn) * 1399763.5453123);
}

float sNoise(vec2 sVecIn)
{
    vec2 sFloor = floor(sVecIn);
    vec2 sFract = fract(sVecIn);

    sFract = sFract * sFract * (3.0 - 2.0 * sFract);

    float sFloorXY = sFloor.x + sFloor.y * 157.0;

    vec4 sHSV = sRandom(vec4(sFloorXY) + vec4(sColours1.xy, sColours2.xy));

    vec2 sMixHSV = mix(sHSV.xy, sHSV.zw, sFract.xx);

    return mix(sMixHSV.x, sMixHSV.y, sFract.y);
}

float sFractNoise(vec2 sVecInR, vec2 sVecInG, vec2 sVecInB)
{
    vec4 sXYZ = vec4(sVecInR * sVecInG.x, sVecInR * sVecInG.y);

    vec4 sFloor = floor(sXYZ);
    vec4 sFract = fract(sXYZ);

    sFract = sFract * sFract * (3.0 - 2.0 * sFract);

    vec2 sHSV = sFloor.xz + sFloor.yw * 157.0;

    vec4 sMixHSV = mix(sRandom(sHSV.xxyy + sColours1.xyxy), sRandom(sHSV.xxyy + sColours2.xyxy), sFract.xxzz);

    return dot(mix(sMixHSV.xz, sMixHSV.yw, sFract.yw), sVecInB);
}

float sFragNoise(vec3 sVecInX)
{
    vec3 sFloor = floor(sVecInX);
    vec3 sFract = fract(sVecInX);

    sFract = sFract * sFract * (3.0 - 2.0 * sFract);

    float sHSV = sFloor.x + dot(sFloor.yz, vec2(157.0, 113.0));

    vec4 sMixHSV = mix(sRandom(vec4(sHSV) + sColours1), sRandom(vec4(sHSV) + sColours2), sFract.xxxx);

    return mix(mix(sMixHSV.x, sMixHSV.y, sFract.y), mix(sMixHSV.z, sMixHSV.w, sFract.y), sFract.z);
}

vec2 sVertNoise(vec3 sVecIn)
{
    return vec2(sFragNoise(sVecIn), sFragNoise(sVecIn + 100.0));
}

float sMap(vec2 sVecIn)
{
    float sAmount;

    if (sInitialResolution < 0.0015)
    {
        sAmount = sFractNoise(sVecIn.xy, vec2(20.6, 100.6), vec2(0.9, 0.1));

    } else if (sInitialResolution < 0.005)
    {
        sAmount = sNoise(sVecIn.xy * 20.6);

    } else sAmount = sNoise(sVecIn.xy * 10.3);

    return (sAmount - 0.5);
}

vec3 sObjectDistance(vec3 sVecInPosition, vec3 sVecInRaytrace, float sNumIn, vec2 sVecInSeed)
{
    float sRoot = sNumIn * sNumIn;

    vec3 sDistance = sVecInRaytrace * 10000.0;
    vec3 sNormalRed = vec3(0.0, 0.0, 1.0);

    float sVis = 1.0 / dot(sNormalRed, sVecInRaytrace);
    float sDepth = 0.0125;

    if (sVis < 0.0) sDepth = -sDepth;

    float sDepthVis = 2.0 * sDepth * sVis;

    vec3 sRaytraceVec = sVecInRaytrace * (dot(sNormalRed, sVecInPosition) - sDepth) * sVis - sVecInPosition;
    vec3 sRaytraceDepthTest = sRaytraceVec + sNormalRed * sDepth;

    float sRaytraceResult = dot(sRaytraceDepthTest, sRaytraceDepthTest);

    vec3 sRaytraceResult2 = sRaytraceVec + sVecInRaytrace * sDepthVis;
    vec3 sRaytraceResult3 = sRaytraceResult2 - sNormalRed * sDepth;

    float sRaytraceResultLength = dot(sRaytraceResult3, sRaytraceResult3);

    vec3 sRaytraceNormal = normalize(cross(sVecInRaytrace, sNormalRed));

    float sRaytraceDot = dot(sVecInPosition, sRaytraceNormal);

    vec3 sRaytraceCross = cross(sVecInRaytrace, sRaytraceNormal);

    float sResultDot = dot(sRaytraceCross, sVecInPosition) / dot(sRaytraceCross, sNormalRed);
    float sVisNum = 0.2 / 0.0125;

    if ((sRaytraceResult < sRoot || sRaytraceResultLength < sRoot) || (abs(sRaytraceDot) < sNumIn && sResultDot <= 0.0125 && sResultDot >= -0.0125))
    {
        vec3 sRaytraceResultNew2 = sRaytraceResult2;

        float sRaytraceNewLength = sRaytraceResult;

        if (sRaytraceNewLength >= sRoot)
        {
            vec3 sRaytraceNormalCross = cross(sNormalRed, sRaytraceNormal);

            float sVisRaytraceSqrt = inversesqrt(sRoot - sRaytraceDot * sRaytraceDot) * abs(dot(sVecInRaytrace, sRaytraceNormalCross));

            vec3 sRaytraceVecDot = sVecInRaytrace / sVisRaytraceSqrt;

            sRaytraceVec = -sResultDot * sNormalRed - sRaytraceDot * sRaytraceNormal - sRaytraceVecDot;

            if (sRaytraceResultLength >= sRoot)
            {
                sRaytraceResult2 = -sResultDot * sNormalRed - sRaytraceDot * sRaytraceNormal + sRaytraceVecDot;
            }

            sDepthVis = dot(sRaytraceResult2 - sRaytraceVec, sVecInRaytrace);
        }

        sDepthVis = (abs(sDepthVis) + 0.1) / (15.0);

        sDepthVis = mix(0.0125, sDepthVis, 0.2);

        if (sDepthVis > 0.01) sDepthVis = 0.01;

        float sIrres = 0.35 / sNumIn;

        sNumIn *= sZoom;

        sVecInRaytrace = sVecInRaytrace * sDepthVis * 5.0;

        for (float m = 0.0; m < 15.0; m += 1.0)
        {
            if (m >= sOctaves) break;

            float sRaytraceNewDelta = length(sRaytraceVec.xy);

            vec2 sRaytraceToPos = abs(sRaytraceVec.xy / sRaytraceNewDelta);

            if (sRaytraceToPos.x > 0.5) sRaytraceToPos = abs(sRaytraceToPos * 0.5 + vec2(-sRaytraceToPos.y, sRaytraceToPos.x) * 0.86602540);

            float sRaytraceNormalised = sRaytraceNewDelta + sRaytraceToPos.x * sRaytraceToPos.x;

            sRaytraceNewDelta *= sZoom;

            float sRaytraceCrossDirty = sRaytraceNewDelta - sNumIn - 0.1;

            sRaytraceNewDelta = pow(sRaytraceNewDelta, sPower) + 0.1;
            sRaytraceCrossDirty = max(sRaytraceCrossDirty, mix(sMap(sRaytraceToPos * sRaytraceNewDelta + sVecInSeed), 1.0, abs(sRaytraceVec.z * sVisNum))) + sRaytraceNormalised * sIrres - 0.245;

            if ((sRaytraceCrossDirty < sInitialResolution * 20.0) || abs(sRaytraceVec.z) > 0.0125 + 0.01) break;

            sRaytraceVec += sVecInRaytrace * sRaytraceCrossDirty;
            sVecInRaytrace *= 0.99;
        }

        if (abs(sRaytraceVec.z) < 0.0125 + 0.01) sDistance = sRaytraceVec + sVecInPosition;
    }

    return sDistance;
}

vec4 sFilterFlake(vec4 sVecInColour, vec3 sVecInPosition, vec3 sVecInRaytraceX, vec3 sVecInRaytraceY, vec3 sVecInRaytraceZ)
{
    vec3 sDistanceX = sObjectDistance(sVecInPosition, sVecInRaytraceX, sRadius, sSeed);
    vec3 sDistanceY = sObjectDistance(sVecInPosition, sVecInRaytraceY, sRadius, sSeed);
    vec3 sDistanceZ = sObjectDistance(sVecInPosition, sVecInRaytraceZ, sRadius, sSeed);

    vec3 sDistanceXYZ = vec3(dot(sDistanceX, sDistanceX), dot(sDistanceY, sDistanceY), dot(sDistanceZ, sDistanceZ));

    if (sDistanceXYZ.x < 10000.0 || sDistanceXYZ.y < 10000.0 || sDistanceXYZ.z < 10000.0)
    {
        vec3 sDistanceNormalXYZ = normalize(cross(sDistanceY - sDistanceX, sDistanceZ - sDistanceX));

        if (sDistanceXYZ.x < 10000.0 && sDistanceXYZ.y < 10000.0 && sDistanceXYZ.z < 10000.0)
        {
            sRaytraceX = sDistanceNormalXYZ;
        }

        float sDistancePow = pow(abs(dot(sDistanceNormalXYZ, sLight)), 3.0);

        vec3 sMixToDistance = mix(vec3(0.0, 0.4, 1.0), sVecInColour.xyz * 10.0, abs(dot(sDistanceNormalXYZ, sVecInRaytraceX)));

        sMixToDistance = mix(sMixToDistance, vec3(2.0), sDistancePow);
        sVecInColour.xyz = mix(sVecInColour.xyz, sMixToDistance, sColourMax * sColourMax * (0.5 + abs(dot(sDistanceNormalXYZ, sVecInRaytraceX)) * 0.5));
    }

    return sVecInColour;
}

void main()
{
    vec2 sResolution = vec2(256, 256);

    float sTimeV = Time * 0.2;

    sInitialResolution = 1.0 / sResolution.y;

    vec2 sProjection = (-sResolution.xy + 2.0 * gl_FragCoord.xy) * sInitialResolution;

    vec3 sRotation;

    mat3 sRaytraceMatrix;

    vec3 sNewRaytraceX = normalize(vec3(sProjection, 2.0));
    vec3 sNewRaytraceY;
    vec3 sNewRaytraceZ;
    vec3 sVecNewPosition = vec3(0.0, 0.0, 1.0);

    fragColor = vec4(0.0, 0.0, 0.0, 0.0);

    sRaytraceX = vec3(0.0);
    sRaytraceY = vec3(0.0);
    sRaytraceZ = vec3(0.0);

    vec4 sReferenceColour = vec4(0.0);

    sOctaves = 15.0 - 8.0;

    vec2 sAddRotation = vec2(0.0);
    vec3 sAddPosition = vec3(0.0);

    float sMaxAlphas = 1.0;

    sVecNewPosition.z = 1.0;
    sColourMax = 1.0;
    sRadius = 0.25;

    float sMatrixZoom = (sZoom - 0.1) / 8.0;

    for (int i = 0; i < 20; i++)
    {
        vec2 sProjectionVec2 = sProjection - vec2(0.25) + vec2(0.1 * float(i));

        sNewRaytraceX = vec3(sProjectionVec2, 2.0) - sRaytraceX * 2.0;

        sNewRaytraceY = normalize(sNewRaytraceX + vec3(0.0, sInitialResolution * 2.0, 0.0));
        sNewRaytraceZ = normalize(sNewRaytraceX + vec3(sInitialResolution * 2.0, 0.0, 0.0));
        sNewRaytraceX = normalize(sNewRaytraceX);

        vec2 sVecRaytraceMatrixDelta = sNewRaytraceX.xy * length(sVecNewPosition) / dot(normalize(sVecNewPosition), sNewRaytraceX) + vec2(0.0, sTimeV);

        sSeed = floor((sVecRaytraceMatrixDelta + vec2(0.0, sVecNewPosition.z))) + sVecNewPosition.z;

        vec3 sSeedZ = vec3(sSeed, sVecNewPosition.z);

        sVecRaytraceMatrixDelta = floor(sVecRaytraceMatrixDelta);

        if (sFragNoise(sSeedZ) > 0.2 && i < int(8.0))
        {
            sPower = sFragNoise(sSeedZ * 10.0) * 1.9 + 0.1;

            sRotation.xy = sin((0.5 - sVertNoise(sSeedZ)) * sTimeV * 5.0) * 0.3 + sAddRotation;
            sRotation.z = (0.5 - sFragNoise(sSeedZ + vec3(10.0, 3.0, 1.0))) * sTimeV * 5.0;

            sSeedZ.z += sTimeV * 0.5;
            sAddPosition.xy = sVecRaytraceMatrixDelta + vec2(0.25, 0.25 - sTimeV) + sVertNoise(sSeedZ) * 0.5;

            vec3 sSine = sin(sRotation);
            vec3 sCosine = cos(sRotation);

            sRaytraceMatrix = mat3(vec3(sCosine.x, 0.0, sSine.x), vec3(0.0, 1.0, 0.0), vec3(-sSine.x, 0.0, sCosine.x));
            sRaytraceMatrix = mat3(vec3(1.0, 0.0, 0.0), vec3(0.0, sCosine.y, sSine.y), vec3(0.0, -sSine.y, sCosine.y)) * sRaytraceMatrix;
            sRaytraceMatrix = mat3(vec3(sCosine.z, sSine.z, 0.0), vec3(-sSine.z, sCosine.z, 0.0), vec3(0.0, 0.0, 1.0)) * sRaytraceMatrix;

            sLight = normalize(vec3(1.0, 0.0, 1.0)) * sRaytraceMatrix;
            vec4 sFinalColours = sFilterFlake(fragColor, (sVecNewPosition + sAddPosition) * sRaytraceMatrix, sNewRaytraceX * sRaytraceMatrix, sNewRaytraceY * sRaytraceMatrix, sNewRaytraceZ * sRaytraceMatrix);

            fragColor = mix(sFinalColours, fragColor, min(1.0, fragColor.w));
        }

        sSeedZ = vec3(sVecRaytraceMatrixDelta, sVecNewPosition.z) + vec3(0.5, 1000.0, 300.0);

        if (sFragNoise(sSeedZ * 10.0) > 0.4)
        {
            float sRandSeedNoise = 0.3 + sFragNoise(sSeedZ * 100.0);

            sAddPosition.xy = sVecRaytraceMatrixDelta + vec2(0.2, 0.2 - sTimeV) + sVertNoise(sSeedZ * 100.0) * 0.6;

            float sNewDeltaNumLength = length(sNewRaytraceX * dot(sNewRaytraceX, sVecNewPosition + sAddPosition) - sVecNewPosition - sAddPosition);

            sNewDeltaNumLength = max(0.0, (1.0 - sNewDeltaNumLength * 10.0 * sRandSeedNoise));
            fragColor.xyzw += vec4(1.0, 1.2, 3.0, 1.0) * pow(sNewDeltaNumLength, 5.0) * (pow(0.6 + sRandSeedNoise, 2.0) - 0.6) * sMaxAlphas;
        }

        sColourMax -= 1.1 / 8.0;
        sVecNewPosition.z += 1.0;
        sOctaves += 2.0;
        sMaxAlphas -= 1.1 / float(20);
        sZoom -= sMatrixZoom;
    }

    vec3 sColourOut = mix(vec3(0.0), vec3(0.0, 0.0, 0.4), (-0.55 + sProjection.y) * 2.0);

    fragColor.xyz += mix((sColourOut.xyz - fragColor.xyz) * 0.1, vec3(0.2, 0.5, 1.0), clamp((-sProjection.y + 1.0) * 0.5, 0.0, 1.0));

    fragColor = min(vec4(1.0), fragColor);
    fragColor.a = 1.0;
}