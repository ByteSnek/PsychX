#version 150

uniform float Time;
uniform vec3 Colour;
uniform float Alpha;

in vec2 textureProjection;
out vec4 fragColour;

float noise(vec3 pColour, float pAmount)
{
    const vec3 sMultiplier = vec3(1e0, 1e2, 1e3);

    pColour *= pAmount;

    vec3 sColoursA = floor(mod(pColour, pAmount)) * sMultiplier;
    vec3 sColoursB = floor(mod(pColour + vec3(1.0), pAmount)) * sMultiplier;
    vec3 sFract = fract(pColour);

    sFract = sFract * sFract * (3.0 - 2.0 * sFract);

    vec4 sColourMix = vec4(sColoursA.x + sColoursA.y + sColoursA.z, sColoursB.x + sColoursA.y + sColoursA.z, sColoursA.x + sColoursB.y + sColoursA.z, sColoursB.x + sColoursB.y + sColoursA.z);
    vec4 sColourFrag = fract(sin(sColourMix * 1e-1) * 1e3);

    float sMixedX = mix(mix(sColourFrag.x, sColourFrag.y, sFract.x), mix(sColourFrag.z, sColourFrag.w, sFract.x), sFract.y);

    sColourFrag = fract(sin((sColourMix + sColoursB.z - sColoursA.z) * 1e-1) * 1e3);

    float sMixedY = mix(mix(sColourFrag.x, sColourFrag.y, sFract.x), mix(sColourFrag.z, sColourFrag.w, sFract.x), sFract.y);

    return mix(sMixedX, sMixedY, sFract.z) * 2.0 - 1.0;
}

void main()
{
    vec3 sCoord = vec3(textureProjection, 0.0);

    float sColour = 0.0;
    float sRad = (distance(sCoord.xy, vec2(0.5, 0.35)) * 2.5);
    float sLevel = clamp((sCoord.x - 1.0) * 1024.0, 0.0, 1.0);
    float sDensity = 8.0;
    float sNoiseAmount = 0.0;

    for (int i = 1; i <= 5; i++)
    {
        float power = pow(2.0, float(i));

        sNoiseAmount += (2.0 / power) * max(noise((sCoord * 1.75) + vec3(Time * 0.0, Time * 0.0, Time * -0.01), power * sDensity), -1.0);
    }

    sNoiseAmount *= 0.5;

    vec3 sFragColour = Colour;

    float sRed = -1.0 + mod(Time, 6.0) + sNoiseAmount;
    float sGreen = -1.0 + mod(Time + 2.0, 6.0) + sNoiseAmount;
    float sBlue = -1.0 + mod(Time + 4.0, 6.0) + sNoiseAmount;

    sColour = min(min(abs(sRad - sRed), abs(sRad - sGreen)), abs(sRad - sBlue));
    sColour += sLevel;

    fragColour = vec4(sFragColour, max(0.1, 1.0 - pow(sColour, 0.3)) * (1.0 - sLevel) * Alpha);
}