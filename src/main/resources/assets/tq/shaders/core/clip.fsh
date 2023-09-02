#version 150

uniform float Time;

out vec4 fragColour;

void main()
{
    vec2 sResolution = vec2(256, 256);
    vec2 sUV = (gl_FragCoord.xy + gl_FragCoord.xy - sResolution) / sResolution.xy;

    float sDirectionCalculation =  floor(sUV.x / 0.3 + 0.8 * Time) + floor(sUV.y / 0.3 + 0.8 * Time);
    float sDirection = mod(sDirectionCalculation, 4.0) > 1.0 ? sUV.x : sUV.y;
    float sRawColour = fract(sDirection / 0.3);

    vec3 sColour = vec3(step(sRawColour, 0.5));

    fragColour = vec4(sColour, 1.0);
}