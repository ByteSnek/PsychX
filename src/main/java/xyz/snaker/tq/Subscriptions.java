package xyz.snaker.tq;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.brigader.DiscardAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.HurtAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.KillAllEntitiesCommand;
import xyz.snaker.snakerlib.brigader.PlaygroundModeCommand;
import xyz.snaker.snakerlib.concurrent.event.management.*;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.fx.SyncopeFX;
import xyz.snaker.tq.client.model.entity.*;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;
import xyz.snaker.tq.client.render.block.ShaderBlockRenderer;
import xyz.snaker.tq.client.render.entity.*;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.rego.BlockEntities;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Fluids;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.server.command.ConfigCommand;

import com.mojang.brigadier.CommandDispatcher;

import static xyz.snaker.tq.rego.Entities.*;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Subscriptions
{
    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client
    {
        @SubscribeEvent
        public static void onLayerDefinitionRego(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            EntityLayerDefRegoManager manager = new EntityLayerDefRegoManager(event);

            manager.register(SnipeModel.LAYER_LOCATION, SnipeModel::createBodyLayer);
            manager.register(CosmoModel.LAYER_LOCATION, CosmoModel::createBodyLayer);
            manager.register(FlareModel.LAYER_LOCATION, FlareModel::createBodyLayer);
            manager.register(FlutterflyModel.LAYER_LOCATION, FlutterflyModel::createBodyLayer);
            manager.register(FrolickerModel.LAYER_LOCATION, FrolickerModel::createBodyLayer);
            manager.register(UtterflyModel.LAYER_LOCATION, UtterflyModel::createBodyLayer);
            manager.register(CosmicCreeperModel.LAYER_LOCATION, CosmicCreeperModel::createBodyLayer);
            manager.register(CosmoSpineModel.LAYER_LOCATION, CosmoSpineModel::createBodyLayer);
            manager.register(LeetModel.LAYER_LOCATION, LeetModel::createBodyLayer);

            manager.close();
        }

        @SubscribeEvent
        @SuppressWarnings({"RedundantSuppression", "unchecked"})
        public static void onRendererRego(EntityRenderersEvent.RegisterRenderers event)
        {
            EntityRendererRegoManager manager = new EntityRendererRegoManager(event);

            manager.registerBlockEntity(BlockEntities.SWIRL, new ShaderBlockRenderer<>(ItemLikeRenderType.SWIRL));
            manager.registerBlockEntity(BlockEntities.SNOWFLAKE, new ShaderBlockRenderer<>(ItemLikeRenderType.SNOWFLAKE));
            manager.registerBlockEntity(BlockEntities.WATERCOLOUR, new ShaderBlockRenderer<>(ItemLikeRenderType.WATERCOLOUR));
            manager.registerBlockEntity(BlockEntities.MULTICOLOUR, new ShaderBlockRenderer<>(ItemLikeRenderType.MULTICOLOUR));
            manager.registerBlockEntity(BlockEntities.FLARE, new ShaderBlockRenderer<>(ItemLikeRenderType.FIRE));
            manager.registerBlockEntity(BlockEntities.STARRY, new ShaderBlockRenderer<>(ItemLikeRenderType.BLACK_STARS));
            manager.registerBlockEntity(BlockEntities.GEOMETRIC, new ShaderBlockRenderer<>(ItemLikeRenderType.CLIP));
            manager.registerBlockEntity(BlockEntities.BURNING, new ShaderBlockRenderer<>(ItemLikeRenderType.BURN));
            manager.registerBlockEntity(BlockEntities.FOGGY, new ShaderBlockRenderer<>(ItemLikeRenderType.BLUR_FOG));
            manager.registerBlockEntity(BlockEntities.STATIC, new ShaderBlockRenderer<>(ItemLikeRenderType.STRANDS));

            manager.registerEntity(COSMO, CosmoRenderer::new);
            manager.registerEntity(Entities.SNIPE, SnipeRenderer::new);
            manager.registerEntity(FLARE, FlareRenderer::new);
            manager.registerEntity(COSMIC_CREEPER, CosmicCreeperRenderer::new);
            manager.registerEntity(Entities.FROLICKER, FrolickerRenderer::new);
            manager.registerEntity(Entities.FLUTTERFLY, FlutterflyRenderer::new);
            manager.registerEntity(Entities.UTTERFLY, UtterflyRenderer::new);
            manager.registerEntity(Entities.HOMMING_ARROW, HommingArrowRenderer::new);
            manager.registerEntity(Entities.EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
            manager.registerEntity(Entities.COSMIC_RAY, CosmicRayRenderer::new);
            manager.registerEntity(Entities.LEET, LeetRenderer::new);

            manager.close();
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common
    {
        @SubscribeEvent
        public static void onEntityAttributeCreation(EntityAttributeCreationEvent event)
        {
            EntityAttrCreationManager manager = new EntityAttrCreationManager(event);

            manager.put(COSMO, Cosmo.attributes());
            manager.put(Entities.SNIPE, Snipe.attributes());
            manager.put(FLARE, Flare.attributes());
            manager.put(COSMIC_CREEPER, CosmicCreeper.attributes());
            manager.put(Entities.FROLICKER, Frolicker.attributes());
            manager.put(Entities.FLUTTERFLY, Flutterfly.attributes());
            manager.put(Entities.UTTERFLY, Utterfly.attributes());
            manager.put(Entities.LEET, Leet.attributes());

            manager.close();
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ClientSetupManager manager = new ClientSetupManager(event);

            manager.setFluidRenderLayer(Fluids.COMASOTE, RenderType.SOLID);
            manager.setFluidRenderLayer(Fluids.FLOWING_COMASOTE, RenderType.SOLID);
            manager.registerToForge(SyncopeFX.INSTANCE);

            manager.close();
        }

        @SubscribeEvent
        public static void onSpawnPlacementRego(SpawnPlacementRegisterEvent event)
        {
            SpawnPlacementRegoManager manager = new SpawnPlacementRegoManager(event);

            manager.register(COSMO, Cosmo::spawnRules);
            manager.register(FLARE, Flare::spawnRules);
            manager.register(COSMIC_CREEPER, CosmicCreeper::spawnRules);
            manager.register(FROLICKER, Frolicker::spawnRules);
            manager.register(LEET, Leet::spawnRules);
            manager.register(SNIPE, Snipe::spawnRules);
            manager.register(FLUTTERFLY, Flutterfly::spawnRules);

            manager.close();
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommon
    {
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
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeClient
    {
        @SubscribeEvent
        public static void onViewportRender(ViewportEvent event)
        {
            try {
                GameRenderer renderer = event.getRenderer();
                Minecraft minecraft = renderer.getMinecraft();
                Level level = minecraft.level;
                if (level != null) {
                    ResourceKey<Level> dimension = level.dimension();
                    if (dimension == Levels.COMATOSE) {
                        if (renderer.postEffect == null) {
                            loadEffect(minecraft, "vision_convolve");
                        }
                    } else {
                        if (renderer.postEffect != null) {
                            shutdownEffect(minecraft);
                        }
                    }
                }
            } catch (Exception e) {
                SnakerLib.LOGGER.error(e);
            }
        }

        private static void shutdownEffect(Minecraft minecraft)
        {
            minecraft.tell(minecraft.gameRenderer::shutdownEffect);
        }

        private static void loadEffect(Minecraft minecraft, String name)
        {
            ResourceLocation effect = new ResourcePath("shaders/post/" + name + ".json");
            minecraft.tell(() -> minecraft.gameRenderer.loadEffect(effect));
        }
    }
}