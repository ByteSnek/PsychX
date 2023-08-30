package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;
import xyz.snaker.tq.utility.DefaultFeatures;
import xyz.snaker.tq.utility.WorldGenStuff;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Immaterial
{
    public static Biome create(BootstapContext<Biome> context)
    {
        AmbientParticleSettings particles = new AmbientParticleSettings(ParticleTypes.ELECTRIC_SPARK, WorldGenStuff.PARTICLE_SPAWN_CHANCE);
        AmbientMoodSettings mood = new AmbientMoodSettings(WorldGenStuff.RANDOM_SOUND_FX, 0, 1, 0);
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .fogColor(-16777216)
                .waterColor(-16741991)
                .waterFogColor(-16750951)
                .skyColor(-16777216)
                .foliageColorOverride(-16751002)
                .grassColorOverride(-16777165)
                .ambientParticle(particles)
                .ambientMoodSound(mood)
                .ambientLoopSound(Holder.direct(Sounds.REGENERATOR.get()));

        BiomeDefaultFeatures.addDefaultCarversAndLakes(gen);

        DefaultFeatures.addDefaultPlants(gen);
        DefaultFeatures.addGeometricRubble(gen);
        DefaultFeatures.addMultiColourRubble(gen);

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Entities.COSMO.get(), 1, 1, 1));

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
