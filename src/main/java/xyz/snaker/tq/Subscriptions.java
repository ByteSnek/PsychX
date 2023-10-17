package xyz.snaker.tq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.command.DiscardAllEntitiesCommand;
import xyz.snaker.snakerlib.command.HurtAllEntitiesCommand;
import xyz.snaker.snakerlib.command.KillAllEntitiesCommand;
import xyz.snaker.snakerlib.command.PlaygroundModeCommand;
import xyz.snaker.snakerlib.concurrent.event.management.*;
import xyz.snaker.snakerlib.internal.glfw.KeyPair;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.KeyboardStuff;
import xyz.snaker.tq.client.model.entity.*;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;
import xyz.snaker.tq.client.render.block.ShaderBlockRenderer;
import xyz.snaker.tq.client.render.entity.*;
import xyz.snaker.tq.commands.ConfigCommand;
import xyz.snaker.tq.commands.ForceRemoveCommand;
import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.level.entity.boss.Utterfly;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.level.item.Tourniquet;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.rego.Levels;
import xyz.snaker.tq.utility.ComatoseStuff;
import xyz.snaker.tq.utility.Once;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.mojang.blaze3d.platform.Window;
import com.mojang.brigadier.CommandDispatcher;

import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.renderer.RenderType.SOLID;
import static xyz.snaker.tq.client.render.type.ItemLikeRenderType.*;
import static xyz.snaker.tq.rego.BlockEntities.*;
import static xyz.snaker.tq.rego.Entities.*;
import static xyz.snaker.tq.rego.Fluids.COMASOTE;
import static xyz.snaker.tq.rego.Fluids.FLOWING_COMASOTE;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Subscriptions
{
    static final Map<byte[], Boolean> postChainActivity = new ConcurrentHashMap<>();

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
            manager.register(CosmicCreeperiteModel.LAYER_LOCATION, CosmicCreeperiteModel::createBodyLayer);
            manager.register(CosmoSpineModel.LAYER_LOCATION, CosmoSpineModel::createBodyLayer);

            manager.close();
        }

        @SubscribeEvent
        @SuppressWarnings({"RedundantSuppression", "unchecked"})
        public static void onRendererRego(EntityRenderersEvent.RegisterRenderers event)
        {
            EntityRendererRegoManager manager = new EntityRendererRegoManager(event);

            manager.registerBlockEntity(SWIRL, new ShaderBlockRenderer<>(SWIRLY));
            manager.registerBlockEntity(SNOWFLAKE, new ShaderBlockRenderer<>(WINTER));
            manager.registerBlockEntity(WATERCOLOUR, new ShaderBlockRenderer<>(WCOLOUR));
            manager.registerBlockEntity(MULTICOLOUR, new ShaderBlockRenderer<>(MCOLOUR));
            manager.registerBlockEntity(FLAMES, new ShaderBlockRenderer<>(FIRE));
            manager.registerBlockEntity(STARRY, new ShaderBlockRenderer<>(BLACK_STARS));
            manager.registerBlockEntity(GEOMETRIC, new ShaderBlockRenderer<>(CLIP));
            manager.registerBlockEntity(BURNING, new ShaderBlockRenderer<>(BURN));
            manager.registerBlockEntity(FOGGY, new ShaderBlockRenderer<>(BLUR_FOG));
            manager.registerBlockEntity(STATIC, new ShaderBlockRenderer<>(STRANDS));

            manager.registerEntity(COSMO, CosmoRenderer::new);
            manager.registerEntity(SNIPE, SnipeRenderer::new);
            manager.registerEntity(FLARE, FlareRenderer::new);
            manager.registerEntity(COSMIC_CREEPER, CosmicCreeperRenderer::new);
            manager.registerEntity(COSMIC_CREEPERITE, CosmicCreeperiteRenderer::new);
            manager.registerEntity(FROLICKER, FrolickerRenderer::new);
            manager.registerEntity(FLUTTERFLY, FlutterflyRenderer::new);
            manager.registerEntity(UTTERFLY, UtterflyRenderer::new);
            manager.registerEntity(HOMMING_ARROW, HommingArrowRenderer::new);
            manager.registerEntity(EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
            manager.registerEntity(COSMIC_RAY, CosmicRayRenderer::new);

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
            manager.put(SNIPE, Snipe.attributes());
            manager.put(FLARE, Flare.attributes());
            manager.put(COSMIC_CREEPER, CosmicCreeper.attributes());
            manager.put(COSMIC_CREEPERITE, CosmicCreeperite.attributes());
            manager.put(FROLICKER, Frolicker.attributes());
            manager.put(FLUTTERFLY, Flutterfly.attributes());
            manager.put(UTTERFLY, Utterfly.attributes());

            manager.close();
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ClientSetupManager manager = new ClientSetupManager(event);

            manager.setFluidRenderLayer(COMASOTE, SOLID);
            manager.setFluidRenderLayer(FLOWING_COMASOTE, SOLID);

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
            manager.register(SNIPE, Snipe::spawnRules);
            manager.register(FLUTTERFLY, Flutterfly::spawnRules);

            manager.close();
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommon
    {
        private static long twineTickCount;
        private static byte comaStage;

        @SubscribeEvent
        public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
        {
            Player player = event.player;
            Level level = player.level();
            CompoundTag data = player.getPersistentData();
            RandomSource random = level.getRandom();
            Inventory inventory = player.getInventory();
            Once once = new Once();

            ItemStack saturatedTwine = Items.SATURATED_TWINE.get().getDefaultInstance();
            ItemStack weatheredTwine = Items.WEATHERED_TWINE.get().getDefaultInstance();

            if (inventory.contains(saturatedTwine)) {
                twineTickCount++;

                if (twineTickCount >= 720000) {
                    int index = inventory.findSlotMatchingItem(saturatedTwine);
                    ItemStack stack = new ItemStack(weatheredTwine.getItem(), inventory.getItem(index).getCount());
                    inventory.setItem(index, stack);
                    twineTickCount = 0;
                }
            }

            if (level.dimension() == Levels.COMATOSE) {
                if (!data.contains("ComaStage")) {
                    data.putByte("ComaStage", comaStage);
                }

                if (random.nextInt(ComatoseStuff.getComaStageOccurence()) == 0) {
                    comaStage++;
                }

                if (comaStage >= 10) {
                    ComatoseStuff.wakeUpPlayer(player);
                    comaStage = 0;
                }
            }

            if (KeyPair.SHIFT.sequentialDown() && KeyboardStuff.isKeyDown(GLFW.GLFW_KEY_KP_ENTER)) {
                if (TqConfig.COMMON.healthRepairKeybindingsActive.get()) {
                    if (once.once()) {
                        float health = player.getHealth();
                        float maxHealth = player.getMaxHealth();

                        if (health != health) {
                            player.setHealth(maxHealth);
                            player.displayClientMessage(Component.translatable("message.tq.health_repair_success"), true);
                        }

                        once.reset();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event)
        {
            Player player = event.getEntity();
            CompoundTag data = player.getPersistentData();

            if (twineTickCount != 0) {
                data.putLong("TwineTickCount", twineTickCount);
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
        {
            Player player = event.getEntity();
            CompoundTag data = player.getPersistentData();

            if (data.contains("TwineTickCount")) {
                twineTickCount = data.getLong("TwineTickCount");
            }

            if (data.contains("ComaStage")) {
                comaStage = data.getByte("ComaStage");
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
            ForceRemoveCommand.register(dispatcher);
            ConfigCommand.register(dispatcher);
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeClient
    {
        @SubscribeEvent
        public static void onGuiOverlayRender(RenderGuiOverlayEvent.Post event)
        {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            GuiGraphics graphics = event.getGuiGraphics();
            Window window = event.getWindow();

            if (player != null) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                CompoundTag data = player.getPersistentData();

                if (stack.getItem() instanceof Tourniquet tourniquet) {
                    int useDuration = tourniquet.getUseDuration(stack);
                    int remaningUseDuration = tourniquet.getRemainingUseDuration();

                    if (remaningUseDuration != 0) {
                        int width = window.getWidth();
                        int height = window.getHeight();
                        int alpha = useDuration - remaningUseDuration;

                        if (!player.isUsingItem()) {
                            alpha = 0;
                            graphics.flush();
                        }

                        graphics.fill(RenderType.guiOverlay(), 0, 0, width, height, FastColor.ARGB32.color(alpha, 0, 0, 0));
                    }
                }

                if (ComatoseStuff.shouldRenderComaStage(player)) {
                    byte stage = data.getByte("ComaStage");
                    String text = "Coma Stage: " + stage + "/10";
                    graphics.drawString(minecraft.fontFilterFishy, text, 5, 5, 0xFFFFFF);
                }
            }
        }

        @SubscribeEvent
        public static void onViewportRender(ViewportEvent event)
        {
            GameRenderer renderer = event.getRenderer();
            Minecraft minecraft = renderer.getMinecraft();
            Level level = minecraft.level;

            if (level != null) {
                ResourceKey<Level> dimension = level.dimension();

                if (!TqConfig.COMMON.visionConvolveActive.get()) {
                    if (renderer.postEffect != null || renderer.effectActive) {
                        shutdownEffect(minecraft, "vision_convolve");
                    }
                    return;
                }

                if (dimension == Levels.COMATOSE) {
                    if (renderer.postEffect == null || !renderer.effectActive) {
                        loadEffect(minecraft, "vision_convolve");
                    }
                } else {
                    if (renderer.postEffect != null || renderer.effectActive) {
                        shutdownEffect(minecraft, "vision_convolve");
                    }
                }
            }
        }

        private static void shutdownEffect(Minecraft minecraft, String name)
        {
            byte[] nibbles = name.getBytes();

            minecraft.tell(() ->
            {
                minecraft.gameRenderer.shutdownEffect();
                postChainActivity.remove(nibbles);
            });
        }

        private static void loadEffect(Minecraft minecraft, String name)
        {
            byte[] nibbles = name.getBytes();
            ResourceLocation effect = new ResourcePath("shaders/post/" + name + ".json");

            minecraft.tell(() ->
            {
                minecraft.gameRenderer.loadEffect(effect);
                postChainActivity.put(nibbles, true);
            });
        }
    }

    static <MSG> Void notifyInfo(MSG message)
    {
        SnakerLib.LOGGER.info(message);
        return null;
    }

    static <MSG> Void notifyWarn(MSG message)
    {
        SnakerLib.LOGGER.warn(message);
        return null;
    }

    static <MSG> Void notifyError(MSG message)
    {
        SnakerLib.LOGGER.error(message);
        return null;
    }
}