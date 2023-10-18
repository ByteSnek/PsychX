package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;
import xyz.snaker.tq.utility.level.WorldGenStuff;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Illusion
{
    public static Biome create(BootstapContext<Biome> context)
    {
        AmbientParticleSettings particles = new AmbientParticleSettings(ParticleTypes.WHITE_ASH, WorldGenStuff.PARTICLE_SPAWN_CHANCE);
        AmbientMoodSettings mood = new AmbientMoodSettings(WorldGenStuff.RANDOM_SOUND_FX, 6000, 8, 2);
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x06324f)
                .waterColor(0x03263d)
                .waterFogColor(0x011b2c)
                .skyColor(-16777216)
                .foliageColorOverride(0x012238)
                .grassColorOverride(0x012238)
                .ambientParticle(particles)
                .ambientMoodSound(mood)
                .ambientLoopSound(Holder.direct(Sounds.XXED.get()));

        WorldGenStuff.addIllusiveTree(gen);
        WorldGenStuff.addDefaultPlants(gen);
        WorldGenStuff.addSnowflakeRubble(gen);
        WorldGenStuff.addStarryRubble(gen);
        WorldGenStuff.addDefaultCarvers(gen);
        WorldGenStuff.addDefaultEntitySpawns(spawns);

        WorldGenStuff.addMonsterSpawn(spawns, Entities.COSMIC_CREEPER, 12, 1, 1);
        WorldGenStuff.addMonsterSpawn(spawns, Entities.COSMO, 8, 1, 3);
        WorldGenStuff.addCreatureSpawn(spawns, Entities.FROLICKER, 6, 1, 2);

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
