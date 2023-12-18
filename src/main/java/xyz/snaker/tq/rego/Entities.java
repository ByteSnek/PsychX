package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.crystal.ComaCrystal;
import xyz.snaker.tq.level.entity.crystal.ComaCrystalLightningBolt;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.level.entity.projectile.CosmicRay;
import xyz.snaker.tq.level.entity.projectile.ExplosiveHommingArrow;
import xyz.snaker.tq.level.entity.projectile.HommingArrow;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Entities
{
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Tourniqueted.MODID);

    public static final Supplier<EntityType<Cosmo>> COSMO = registerMob("cosmo", Cosmo::new, 1F, 2F);
    public static final Supplier<EntityType<Snipe>> SNIPE = registerMob("snipe", Snipe::new, 1F, 1.5F);
    public static final Supplier<EntityType<Flare>> FLARE = registerMob("flare", Flare::new, 1F, 2F);
    public static final Supplier<EntityType<CosmicCreeper>> COSMIC_CREEPER = registerMob("cosmic_creeper", CosmicCreeper::new, 1F, 2F);
    public static final Supplier<EntityType<CosmicCreeperite>> COSMIC_CREEPERITE = registerMob("cosmic_creeperite", CosmicCreeperite::new, 1F, 2F);
    public static final Supplier<EntityType<Utterfly>> UTTERFLY = registerMob("utterfly", Utterfly::new, 6F, 3F);
    public static final Supplier<EntityType<Frolicker>> FROLICKER = registerCreature("frolicker", Frolicker::new, 0.5F, 0.5F);
    public static final Supplier<EntityType<Flutterfly>> FLUTTERFLY = registerCreature("flutterfly", Flutterfly::new, 0.5F, 0.5F);
    public static final Supplier<EntityType<CrankyFlutterfly>> CRANKY_FLUTTERFLY = registerMob("cranky_flutterfly", CrankyFlutterfly::new, 0.5F, 0.5F);
    public static final Supplier<EntityType<HommingArrow>> HOMMING_ARROW = registerMisc("homming_arrow", HommingArrow::new, 0.1F, 0.1F);
    public static final Supplier<EntityType<ExplosiveHommingArrow>> EXPLOSIVE_HOMMING_ARROW = registerMisc("explosive_homming_arrow", ExplosiveHommingArrow::new, 0.1F, 0.1F);
    public static final Supplier<EntityType<CosmicRay>> COSMIC_RAY = registerMisc("cosmic_ray", CosmicRay::new, 0.1F, 0.1F);
    public static final Supplier<EntityType<ComaCrystal>> COMA_CRYSTAL = registerMisc("coma_crystal", ComaCrystal::new, 1, 1);
    public static final Supplier<EntityType<ComaCrystalLightningBolt>> COMA_CRYSTAL_LIGHTNING_BOLT = registerMisc("coma_crystal_lightning_bolt", ComaCrystalLightningBolt::new, 1, 1);

    static <T extends Animal> Supplier<EntityType<T>> registerCreature(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return REGISTER.register(name, () -> EntityType.Builder.of(entity, MobCategory.AMBIENT)
                .sized(width, height)
                .build(name)
        );
    }

    static <T extends Mob> Supplier<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return REGISTER.register(name, () -> EntityType.Builder.of(entity, MobCategory.MONSTER)
                .sized(width, height)
                .build(name)
        );
    }

    static <T extends Entity> Supplier<EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> entity, float width, float height)
    {
        return REGISTER.register(name, () -> EntityType.Builder.of(entity, MobCategory.MISC)
                .sized(width, height)
                .build(name)
        );
    }
}
