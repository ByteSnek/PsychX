package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;
import xyz.snaker.tq.utility.WorldGenStuff;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Spectral
{
    public static Biome create(BootstapContext<Biome> context)
    {
        AmbientParticleSettings particles = new AmbientParticleSettings(ParticleTypes.INSTANT_EFFECT, WorldGenStuff.PARTICLE_SPAWN_CHANCE);
        AmbientMoodSettings mood = new AmbientMoodSettings(WorldGenStuff.RANDOM_SOUND_FX, 6000, 8, 2);
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x53003d)
                .waterColor(0x1e1234)
                .waterFogColor(0x332748)
                .skyColor(-16777216)
                .foliageColorOverride(0x2e165c)
                .grassColorOverride(0x2e165c)
                .ambientParticle(particles)
                .ambientMoodSound(mood)
                .ambientLoopSound(Holder.direct(Sounds.RELATIVE_RESOLUTE.get()));

        WorldGenStuff.addDefaultPlants(gen);
        WorldGenStuff.addSwirlRubble(gen);
        WorldGenStuff.addWaterColourRubble(gen);
        WorldGenStuff.addDefaultCarvers(gen);
        WorldGenStuff.addDefaultEntitySpawns(spawns);

        WorldGenStuff.addMonsterSpawn(spawns, Entities.COSMO, 10, 1, 3);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0)
                .temperature(0.7F)
                .generationSettings(gen.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(effects.build())
                .build();
    }
}
