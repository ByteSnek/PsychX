package xyz.snaker.tq;

import java.util.Arrays;

import xyz.snaker.snakerlib.brigader.DiscardAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.HurtAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.KillAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.PlaygroundModeCommand;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.fx.SyncopeFX;
import xyz.snaker.tq.client.model.entity.*;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;
import xyz.snaker.tq.client.render.block.ShaderBlockRenderer;
import xyz.snaker.tq.client.render.entity.*;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.rego.BlockEntities;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Fluids;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.commands.CommandSourceStack;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.command.ConfigCommand;

import com.mojang.brigadier.CommandDispatcher;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Subscriptions
{
    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ItemBlockRenderTypes.setRenderLayer(Fluids.COMASOTE.get(), RenderType.SOLID);
            ItemBlockRenderTypes.setRenderLayer(Fluids.FLOWING_COMASOTE.get(), RenderType.SOLID);
        }

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
            event.registerLayerDefinition(CosmoSpineModel.LAYER_LOCATION, CosmoSpineModel::createBodyLayer);
            event.registerLayerDefinition(LeetModel.LAYER_LOCATION, LeetModel::createBodyLayer);
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
            event.registerBlockEntityRenderer(BlockEntities.BURNING.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.BURN));
            event.registerBlockEntityRenderer(BlockEntities.FOGGY.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.BLUR_FOG));
            event.registerBlockEntityRenderer(BlockEntities.STATIC.get(), new ShaderBlockRenderer<>(ItemLikeRenderType.STRANDS));
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common
    {
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
            bindAttributes(event, Entities.LEET, Leet.attributes());
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event)
        {
            registerEffects(SyncopeFX.INSTANCE);
            registerEntityRenderer(Entities.COSMO, CosmoRenderer::new);
            registerEntityRenderer(Entities.SNIPE, SnipeRenderer::new);
            registerEntityRenderer(Entities.FLARE, FlareRenderer::new);
            registerEntityRenderer(Entities.COSMIC_CREEPER, CosmicCreeperRenderer::new);
            registerEntityRenderer(Entities.FROLICKER, FrolickerRenderer::new);
            registerEntityRenderer(Entities.FLUTTERFLY, FlutterflyRenderer::new);
            registerEntityRenderer(Entities.UTTERFLY, UtterflyRenderer::new);
            registerEntityRenderer(Entities.HOMMING_ARROW, HommingArrowRenderer::new);
            registerEntityRenderer(Entities.EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
            registerEntityRenderer(Entities.COSMIC_RAY, CosmicRayRenderer::new);
            registerEntityRenderer(Entities.LEET, LeetRenderer::new);
        }

        @SubscribeEvent
        public static void registerSpawns(SpawnPlacementRegisterEvent event)
        {
            registerSpawn(event, Entities.COSMO, Cosmo::spawnRules);
            registerSpawn(event, Entities.FLARE, Flare::spawnRules);
            registerSpawn(event, Entities.COSMIC_CREEPER, CosmicCreeper::spawnRules);
            registerSpawn(event, Entities.FROLICKER, Frolicker::spawnRules);
            registerSpawn(event, Entities.LEET, Leet::spawnRules);
            registerSpawn(event, Entities.SNIPE, Snipe::spawnRules);
            registerSpawn(event, Entities.FLUTTERFLY, Flutterfly::spawnRules);
        }

        private static void registerEffects(Object... targets)
        {
            Arrays.stream(targets).forEach(MinecraftForge.EVENT_BUS::register);
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

        @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
        public static class ForgeCommon
        {
            private static boolean postChainActive;

            @SubscribeEvent
            public static void onPlayerTick(TickEvent.PlayerTickEvent event)
            {
                Player player = event.player;
                Level level = player.level();
                if (event.phase == TickEvent.Phase.END) {
                    if (TqConfig.COMMON.visionConvolveActive.get()) {
                        if (level.dimension() == Levels.COMATOSE) {
                            if (level.isClientSide && !Minecraft.getInstance().gameRenderer.effectActive) {
                                loadPostChain(player, "vision_convolve");
                            }
                        } else if (level.dimension() != Levels.COMATOSE && postChainActive) {
                            shutDownPostChain(player);
                        }
                    }
                }
            }

            @SubscribeEvent
            public static void onCommandRego(RegisterCommandsEvent event)
            {
                CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
                PlaygroundModeCommand.register(dispatcher);
                HurtAllEntitiesCommand.register(dispatcher);
                KillAllEntitiesCommand.register(dispatcher);
                DiscardAllEntitiesCommand.register(dispatcher);
                ConfigCommand.register(dispatcher);
            }

            @SubscribeEvent
            public static void onLevelUnload(LevelEvent.Unload event)
            {
                postChainActive = false;
            }

            private static void loadPostChain(Player player, String name)
            {
                if (player.level().isClientSide) {
                    ResourcePath path = new ResourcePath("shaders/post/" + name + ".json");
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(path));
                    postChainActive = true;
                } else {
                    player.getPersistentData().putBoolean("PostChainActive", postChainActive);
                }
            }

            private static void shutDownPostChain(Player player)
            {
                if (player.level().isClientSide) {
                    Minecraft.getInstance().tell(Minecraft.getInstance().gameRenderer::shutdownEffect);
                    postChainActive = false;
                } else {
                    player.getPersistentData().putBoolean("PostChainActive", postChainActive);
                }
            }
        }
    }
}