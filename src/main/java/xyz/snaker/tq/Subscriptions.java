package xyz.snaker.tq;

import xyz.snaker.snakerlib.math.PoseStackBuilder;
import xyz.snaker.snakerlib.utility.tools.BlockStuff;
import xyz.snaker.snakerlib.utility.tools.EntityStuff;
import xyz.snaker.snakerlib.utility.tools.TimeStuff;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.client.fx.SyncopeFX;
import xyz.snaker.tq.client.model.entity.*;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;
import xyz.snaker.tq.client.render.block.ShaderBlockRenderer;
import xyz.snaker.tq.client.render.entity.*;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.level.effect.Syncope;
import xyz.snaker.tq.level.entity.boss.AntiCosmo;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.rego.*;

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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

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
            event.registerBlockEntityRenderer(BlockEntities.SWIRL.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.SWIRL));
            event.registerBlockEntityRenderer(BlockEntities.SNOWFLAKE.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.SNOWFLAKE));
            event.registerBlockEntityRenderer(BlockEntities.WATERCOLOUR.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.WATERCOLOUR));
            event.registerBlockEntityRenderer(BlockEntities.MULTICOLOUR.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.MULTICOLOUR));
            event.registerBlockEntityRenderer(BlockEntities.FLARE.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.FIRE));
            event.registerBlockEntityRenderer(BlockEntities.STARRY.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.BLACK_STARS));
            event.registerBlockEntityRenderer(BlockEntities.GEOMETRIC.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.CLIP));
        }

        @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class Common
        {
            @SubscribeEvent
            public static void commonSetup(FMLCommonSetupEvent event)
            {
                BlockStuff.addPotPlant(Blocks.PINKTAILS.get(), Blocks.POTTED_CATNIP);
                BlockStuff.addPotPlant(Blocks.SPLITLEAF.get(), Blocks.POTTED_SPLITLEAF);
            }

            @SubscribeEvent
            public static void addEntityAttributes(EntityAttributeCreationEvent event)
            {
                bindAttributes(event, Entities.COSMO, Cosmo.attributes());
                bindAttributes(event, Entities.SNIPE, Snipe.attributes());
                bindAttributes(event, Entities.FLARE, Flare.attributes());
                bindAttributes(event, Entities.COSMIC_CREEPER, CosmicCreeper.attributes());
                bindAttributes(event, Entities.FROLICKER, Frolicker.attributes());
                bindAttributes(event, Entities.FLUTTERFLY, Flutterfly.attributes());
                bindAttributes(event, Entities.UTTERFLY, Utterfly.attributes());
                bindAttributes(event, Entities.ANTI_COSMO, AntiCosmo.attributes());
                bindAttributes(event, Entities.EERIE_CRETIN, EerieCretin.attributes());
                bindAttributes(event, Entities.LEET, Leet.attributes());
                bindAttributes(event, Entities.TEST, Test.attributes());
            }

            @SubscribeEvent
            public static void clientSetup(FMLClientSetupEvent event)
            {
                MinecraftForge.EVENT_BUS.register(new SyncopeFX());
                registerEntityRenderer(Entities.COSMO, CosmoRenderer::new);
                registerEntityRenderer(Entities.SNIPE, SnipeRenderer::new);
                registerEntityRenderer(Entities.FLARE, FlareRenderer::new);
                registerEntityRenderer(Entities.COSMIC_CREEPER, CosmicCreeperRenderer::new);
                registerEntityRenderer(Entities.FROLICKER, FrolickerRenderer::new);
                registerEntityRenderer(Entities.FLUTTERFLY, FlutterflyRenderer::new);
                registerEntityRenderer(Entities.UTTERFLY, UtterflyRenderer::new);
                registerEntityRenderer(Entities.ANTI_COSMO, AntiCosmoRenderer::new);
                registerEntityRenderer(Entities.HOMMING_ARROW, HommingArrowRenderer::new);
                registerEntityRenderer(Entities.EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
                registerEntityRenderer(Entities.COSMIC_RAY, CosmicRayRenderer::new);
                registerEntityRenderer(Entities.EERIE_CRETIN, EerieCretinRenderer::new);
                registerEntityRenderer(Entities.LEET, LeetRenderer::new);
                registerEntityRenderer(Entities.TEST, TestRenderer::new);
            }

            @SubscribeEvent
            public static void registerSpawns(SpawnPlacementRegisterEvent event)
            {
                registerSpawn(event, Entities.COSMO, Cosmo::spawnRules);
                registerSpawn(event, Entities.FLARE, Flare::spawnRules);
                registerSpawn(event, Entities.COSMIC_CREEPER, CosmicCreeper::spawnRules);
                registerSpawn(event, Entities.FROLICKER, Frolicker::spawnRules);
                registerSpawn(event, Entities.EERIE_CRETIN, EerieCretin::spawnRules);
                registerSpawn(event, Entities.LEET, Leet::spawnRules);
                registerSpawn(event, Entities.SNIPE, Snipe::spawnRules);
                registerSpawn(event, Entities.FLUTTERFLY, Flutterfly::spawnRules);
                registerSpawn(event, Entities.TEST, Test::spawnRules);
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
                PoseStack stack = event.getPoseStack();
                PoseStackBuilder builder = new PoseStackBuilder(stack);
                RenderSystem.getProjectionMatrix();
                builder.popPose();
            }

            @SubscribeEvent
            @SuppressWarnings("rawtypes")
            public static void preRenderEntity(RenderLivingEvent.Pre event)
            {
                PoseStack stack = event.getPoseStack();
                PoseStackBuilder builder = new PoseStackBuilder(stack);
                LivingEntity entity = event.getEntity();
                Syncope syncope = Effects.SYNCOPE.get();
                MobEffectInstance effect = entity.getEffect(syncope);
                builder.pushPose();
                if (effect != null) {
                    int effectDuration = effect.getDuration();
                    boolean effectOver = effectDuration <= 0;
                    if (effectOver) {
                        entity.removeEffect(syncope);
                    }
                }
            }
        }

        @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
        public static class ForgeCommon
        {
            @SubscribeEvent
            public static void playerTickEvent(TickEvent.PlayerTickEvent event)
            {
                Player player = event.player;
                Level level = player.level();
                if (WorldStuff.isDimension(level, Keys.COMATOSE) && TqConfig.COMMON.syncopeActiveInComatoseDimension.get()) {
                    float tickCount = player.tickCount;
                    if (TimeStuff.secOffs(tickCount, 1)) {
                        EntityStuff.addEffectDirect(player, Effects.SYNCOPE.get());
                    }
                }
            }
        }
    }
}