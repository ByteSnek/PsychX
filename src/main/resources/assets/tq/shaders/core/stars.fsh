#version 150

uniform float Time;
uniform vec3 Colour;
uniform bool Background;
uniform bool Invert;

out vec4 fragColour;

float makeStars(vec2 pPosition, float pAmount)
{
    float sWeight = smoothstep(20.0, 0.0, -pPosition.y * (pAmount / 10.0));

    if (sWeight < 0.1)
    {
        return 0.0;
    }

    pPosition += (Time / 0.3) / pAmount;
    pPosition.y += Time * 0.00000001 / pAmount;
    pPosition.x += sin(pPosition.y + Time * 0.5) / pAmount;
    pPosition *= pAmount;

    vec2 sSpeed = floor(pPosition);
    vec2 sFrag = fract(pPosition);
    vec2 sProjection = 0.5 + 0.35 * sin(12.0 * fract(sin((sSpeed + 0.0 + pAmount) * mat2(7, 3, 6, 5)) * 5.0)) - sFrag;

    float sDelta = length(sProjection);;
    float sAmount = 1.0;

    sAmount = min(sDelta, sAmount);
    sAmount = smoothstep(0.0, sAmount, sin(sFrag.x + sFrag.y) * 0.02);

    return sAmount * sWeight;
}

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = (gl_FragCoord.xy * 2.0 - sResolution.xy) / min(sResolution.x, sResolution.y);

    float sAlpha = Background ? 0.5 : 1.0;
    float sMultiplier = smoothstep(1.0, 0.3, clamp(sUV.y * 0.0 + 1.0, 0.0, sAlpha));

    sMultiplier += makeStars(sUV, 15.0) * 0.8;
    sMultiplier += makeStars(sUV, 10.0);
    sMultiplier += makeStars(sUV, 8.0);
    sMultiplier += makeStars(sUV, 6.0);
    sMultiplier += makeStars(sUV, 5.0);

    vec3 sColour = Colour * sMultiplier;

    if (Invert)
    {
        sColour.rgb = 1.0 - sColour.rgb;
    }

    fragColour = vec4(sColour, 1);
}