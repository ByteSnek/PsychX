package snaker.tq;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.math.PoseStackBuilder;
import snaker.snakerlib.utility.LevelStuff;
import snaker.snakerlib.utility.MiscStuff;
import snaker.tq.client.fx.SyncopeFX;
import snaker.tq.client.model.entity.*;
import snaker.tq.client.model.item.CosmoSpineModel;
import snaker.tq.client.render.block.ShaderBlockRenderer;
import snaker.tq.client.render.entity.*;
import snaker.tq.config.TqConfig;
import snaker.tq.level.effect.Syncope;
import snaker.tq.level.entity.boss.AntiCosmo;
import snaker.tq.level.entity.boss.Utterfly;
import snaker.tq.level.entity.creature.Flutterfly;
import snaker.tq.level.entity.creature.Frolicker;
import snaker.tq.level.entity.mob.*;
import snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Subscriptions
{
    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client
    {
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(SnipeModel.LAYER_LOCATION, SnipeModel::createBodyLayer);
            event.registerLayerDefinition(CosmoModel.LAYER_LOCATION, CosmoModel::createBodyLayer);
            event.registerLayerDefinition(FlareModel.LAYER_LOCATION, FlareModel::createBodyLayer);
            event.registerLayerDefinition(FlutterflyModel.LAYER_LOCATION, FlutterflyModel::createBodyLayer);
            event.registerLayerDefinition(FrolickerModel.LAYER_LOCATION, FrolickerModel::createBodyLayer);
            event.registerLayerDefinition(UtterflyModel.LAYER_LOCATION, UtterflyModel::createBodyLayer);
            event.registerLayerDefinition(CosmicCreeperModel.LAYER_LOCATION, CosmicCreeperModel::createBodyLayer);
            event.registerLayerDefinition(AntiCosmoModel.LAYER_LOCATION, AntiCosmoModel::createBodyLayer);
            event.registerLayerDefinition(CosmoSpineModel.LAYER_LOCATION, CosmoSpineModel::createBodyLayer);
            event.registerLayerDefinition(EerieCretinModel.LAYER_LOCATION, EerieCretinModel::createBodyLayer);
            event.registerLayerDefinition(LeetModel.LAYER_LOCATION, LeetModel::createBodyLayer);
            event.registerLayerDefinition(TestModel.LAYER_LOCATION, TestModel::createBodyLayer);
        }

        @SubscribeEvent
        @SuppressWarnings({"RedundantSuppression", "unchecked"})
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            registerBer(event, Rego.BE_SWIRL, ShaderBlockRenderer.Swirl::new);
            registerBer(event, Rego.BE_SNOWFLAKE, ShaderBlockRenderer.Snowflake::new);
            registerBer(event, Rego.BE_WATERCOLOUR, ShaderBlockRenderer.WaterColour::new);
            registerBer(event, Rego.BE_MULTICOLOUR, ShaderBlockRenderer.MultiColour::new);
            registerBer(event, Rego.BE_FLARE, ShaderBlockRenderer.Flare::new);
            registerBer(event, Rego.BE_STARRY, ShaderBlockRenderer.Starry::new);
        }

        private static <T extends BlockEntity> void registerBer(EntityRenderersEvent.RegisterRenderers event, RegistryObject<BlockEntityType<T>> type, BlockEntityRendererProvider<T> renderer)
        {
            event.registerBlockEntityRenderer(type.get(), renderer);
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common
    {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event)
        {
            MiscStuff.addFlowerPotPlant(Rego.BLOCK_CATNIP, Rego.BLOCK_POTTED_CATNIP);
        }

        @SubscribeEvent
        public static void addEntityAttributes(EntityAttributeCreationEvent event)
        {
            bindAttributes(event, Rego.ENTITY_COSMO, Cosmo.attributes());
            bindAttributes(event, Rego.ENTITY_SNIPE, Snipe.attributes());
            bindAttributes(event, Rego.ENTITY_FLARE, Flare.attributes());
            bindAttributes(event, Rego.ENTITY_COSMIC_CREEPER, CosmicCreeper.attributes());
            bindAttributes(event, Rego.ENTITY_FROLICKER, Frolicker.attributes());
            bindAttributes(event, Rego.ENTITY_FLUTTERFLY, Flutterfly.attributes());
            bindAttributes(event, Rego.ENTITY_UTTERFLY, Utterfly.attributes());
            bindAttributes(event, Rego.ENTITY_ANTI_COSMO, AntiCosmo.attributes());
            bindAttributes(event, Rego.ENTITY_EERIE_CRETIN, EerieCretin.attributes());
            bindAttributes(event, Rego.ENTITY_LEET, Leet.attributes());
            bindAttributes(event, Rego.ENTITY_TEST, Test.attributes());
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event)
        {
            MinecraftForge.EVENT_BUS.register(new SyncopeFX());
            registerEntityRenderer(Rego.ENTITY_COSMO, CosmoRenderer::new);
            registerEntityRenderer(Rego.ENTITY_SNIPE, SnipeRenderer::new);
            registerEntityRenderer(Rego.ENTITY_FLARE, FlareRenderer::new);
            registerEntityRenderer(Rego.ENTITY_COSMIC_CREEPER, CosmicCreeperRenderer::new);
            registerEntityRenderer(Rego.ENTITY_FROLICKER, FrolickerRenderer::new);
            registerEntityRenderer(Rego.ENTITY_FLUTTERFLY, FlutterflyRenderer::new);
            registerEntityRenderer(Rego.ENTITY_UTTERFLY, UtterflyRenderer::new);
            registerEntityRenderer(Rego.ENTITY_ANTI_COSMO, AntiCosmoRenderer::new);
            registerEntityRenderer(Rego.ENTITY_HOMMING_ARROW, HommingArrowRenderer::new);
            registerEntityRenderer(Rego.ENTITY_EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
            registerEntityRenderer(Rego.ENTITY_COSMIC_RAY, CosmicRayRenderer::new);
            registerEntityRenderer(Rego.ENTITY_EERIE_CRETIN, EerieCretinRenderer::new);
            registerEntityRenderer(Rego.ENTITY_LEET, LeetRenderer::new);
            registerEntityRenderer(Rego.ENTITY_TEST, TestRenderer::new);
        }

        @SubscribeEvent
        public static void registerSpawns(SpawnPlacementRegisterEvent event)
        {
            registerSpawn(event, Rego.ENTITY_COSMO, Cosmo::spawnRules);
            registerSpawn(event, Rego.ENTITY_FLARE, Flare::spawnRules);
            registerSpawn(event, Rego.ENTITY_COSMIC_CREEPER, CosmicCreeper::spawnRules);
            registerSpawn(event, Rego.ENTITY_FROLICKER, Frolicker::spawnRules);
            registerSpawn(event, Rego.ENTITY_EERIE_CRETIN, EerieCretin::spawnRules);
            registerSpawn(event, Rego.ENTITY_LEET, Leet::spawnRules);
            registerSpawn(event, Rego.ENTITY_SNIPE, Snipe::spawnRules);
            registerSpawn(event, Rego.ENTITY_FLUTTERFLY, Flutterfly::spawnRules);
            registerSpawn(event, Rego.ENTITY_TEST, Test::spawnRules);
        }

        private static <T extends Entity> void registerSpawn(SpawnPlacementRegisterEvent event, RegistryObject<EntityType<T>> type, SpawnPlacements.SpawnPredicate<T> predicate)
        {
            event.register(type.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, predicate, SpawnPlacementRegisterEvent.Operation.AND);
        }

        private static <T extends Entity> void registerEntityRenderer(RegistryObject<EntityType<T>> type, EntityRendererProvider<T> renderer)
        {
            EntityRenderers.register(type.get(), renderer);
        }

        private static <T extends LivingEntity> void bindAttributes(EntityAttributeCreationEvent event, RegistryObject<EntityType<T>> entity, AttributeSupplier map)
        {
            event.put(entity.get(), map);
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeClient
    {
        @SubscribeEvent
        @SuppressWarnings("rawtypes")
        public static void postRenderEntity(RenderLivingEvent.Post event)
        {
            try {
                PoseStack stack = event.getPoseStack();
                PoseStackBuilder builder = new PoseStackBuilder(stack);
                RenderSystem.getProjectionMatrix();
                builder.popPose();
            } catch (Exception e) {
                SnakerLib.LOGGER.error(e);
            }
        }

        @SubscribeEvent
        @SuppressWarnings("rawtypes")
        public static void preRenderEntity(RenderLivingEvent.Pre event)
        {
            try {
                PoseStack stack = event.getPoseStack();
                PoseStackBuilder builder = new PoseStackBuilder(stack);
                LivingEntity entity = event.getEntity();
                Syncope syncope = Rego.EFFECT_SYNCOPE.get();
                MobEffectInstance effect = entity.getEffect(syncope);
                builder.pushPose();
                if (effect != null) {
                    int effectDuration = effect.getDuration();
                    boolean effectOver = effectDuration <= 0;
                    if (effectOver) {
                        entity.removeEffect(syncope);
                    }
                }
            } catch (Exception e) {
                SnakerLib.LOGGER.error(e);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommon
    {
        @SubscribeEvent
        public static void playerTickEvent(TickEvent.PlayerTickEvent event)
        {
            try {
                Player player = event.player;
                Level level = player.level();
                if (LevelStuff.isDimension(level, Rego.Keys.COMATOSE) && TqConfig.COMMON.syncopeActiveInComatoseDimension.get()) {
                    float tickCount = player.tickCount;
                    if (SnakerLib.secOffs(tickCount, 1)) {
                        MiscStuff.addEffectDirect(player, Rego.EFFECT_SYNCOPE.get());
                    }
                }
            } catch (Exception e) {
                SnakerLib.LOGGER.error(e);
            }
        }
    }
}