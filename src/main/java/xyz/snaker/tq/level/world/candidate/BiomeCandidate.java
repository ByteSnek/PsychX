package xyz.snaker.tq.level.world.candidate;

import java.util.function.Supplier;

import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;

/**
 * Created by SnakerBone on 9/11/2023
 **/
public enum BiomeCandidate
{
    DELUSION(
            ParticleTypes.ASH,
            0.001F,
            Sounds.SFX,
            6000,
            8,
            2,
            0x033c25,
            0x04311f,
            0x012416,
            -16777216,
            0x0a2f20,
            0x0a2f20,
            Sounds.AMBIENT2,
            false,
            0,
            0.7F
    ),
    ILLUSION(
            ParticleTypes.WHITE_ASH,
            0.001F,
            Sounds.SFX,
            6000,
            8,
            2,
            0x06324f,
            0x03263d,
            0x011b2c,
            -16777216,
            0x012238,
            0x012238,
            Sounds.AMBIENT0,
            false,
            0,
            0.7F
    ),
    IMMATERIAL(
            ParticleTypes.ELECTRIC_SPARK,
            0.001F,
            Sounds.SFX,
            6000,
            8,
            2,
            -16777216,
            -16741991,
            -16750951,
            -16777216,
            -16751002,
            -16777165,
            () -> SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE,
            false,
            0,
            0.7F
    ),
    SPECTRAL(
            ParticleTypes.INSTANT_EFFECT,
            0.001F,
            Sounds.SFX,
            6000,
            8,
            2,
            0x53003d,
            0x1e1234,
            0x332748,
            -16777216,
            0x2e165c,
            0x2e165c,
            Sounds.AMBIENT1,
            false,
            0,
            0.7F
    ),
    SURREAL(
            ParticleTypes.FLAME,
            0.001F,
            Sounds.SFX,
            6000,
            8,
            2,
            0x590808,
            0x380303,
            0x230202,
            -16777216,
            0x5e0909,
            0x5e0909,
            () -> SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE,
            false,
            0,
            0.7F
    );

    private final ParticleOptions particleOptions;
    private final float particleSpawnChance;

    private final SoundEvent moodSound;
    private final int moodSoundTickDelay;
    private final int moodSoundBlockSearchExtent;
    private final double moodSoundPositionOffset;

    private final int fogColour;
    private final int waterColour;
    private final int waterFogColour;
    private final int skyColour;
    private final int foliageColour;
    private final int grassColour;
    private final SoundEvent ambientSound;

    private final boolean hasPrecipitation;
    private final int downfall;
    private final float temperature;

    BiomeCandidate(ParticleOptions particleOptions, float particleSpawnChance, Supplier<SoundEvent> moodSound, int moodSoundTickDelay, int moodSoundBlockSearchExtent, double moodSoundPositionOffset, int fogColour, int waterColour, int waterFogColour, int skyColour, int foliageColour, int grassColour, Supplier<SoundEvent> ambientSound, boolean hasPrecipitation, int downfall, float temperature)
    {
        this.particleOptions = particleOptions;
        this.particleSpawnChance = particleSpawnChance;
        this.moodSound = moodSound.get();
        this.moodSoundTickDelay = moodSoundTickDelay;
        this.moodSoundBlockSearchExtent = moodSoundBlockSearchExtent;
        this.moodSoundPositionOffset = moodSoundPositionOffset;
        this.fogColour = fogColour;
        this.waterColour = waterColour;
        this.waterFogColour = waterFogColour;
        this.skyColour = skyColour;
        this.foliageColour = foliageColour;
        this.grassColour = grassColour;
        this.ambientSound = ambientSound.get();
        this.hasPrecipitation = hasPrecipitation;
        this.downfall = downfall;
        this.temperature = temperature;
    }

    public AmbientParticleSettings getAmbientParticleSettings()
    {
        return new AmbientParticleSettings(particleOptions, particleSpawnChance);
    }

    public AmbientMoodSettings getAmbientMoodSettings()
    {
        return new AmbientMoodSettings(Holder.direct(moodSound), moodSoundTickDelay, moodSoundBlockSearchExtent, moodSoundPositionOffset);
    }

    public MobSpawnSettings.Builder getMobSpawnSettingsBuilder()
    {
        return new MobSpawnSettings.Builder();
    }

    public BiomeSpecialEffects.Builder getBiomeSpecialEffectsBuilder()
    {
        return new BiomeSpecialEffects.Builder()
                .fogColor(fogColour)
                .waterColor(waterColour)
                .waterFogColor(waterFogColour)
                .skyColor(skyColour)
                .foliageColorOverride(foliageColour)
                .grassColorOverride(grassColour)
                .ambientParticle(getAmbientParticleSettings())
                .ambientMoodSound(getAmbientMoodSettings())
                .ambientLoopSound(Holder.direct(ambientSound));
    }

    public BiomeGenerationSettings.Builder getBiomeGenerationSettingsBuilder(BootstapContext<Biome> context)
    {
        return new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
    }

    public Biome create(MobSpawnSettings.Builder spawnSettingsBuilder, BiomeGenerationSettings.Builder generationSettingsBuilder)
    {
        var generationSettings = generationSettingsBuilder.build();
        var spawnSettings = spawnSettingsBuilder.build();
        var specialEffects = getBiomeSpecialEffectsBuilder().build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(hasPrecipitation)
                .downfall(downfall)
                .temperature(temperature)
                .generationSettings(generationSettings)
                .mobSpawnSettings(spawnSettings)
                .specialEffects(specialEffects)
                .build();
    }
}
