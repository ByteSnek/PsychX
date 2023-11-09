package xyz.snaker.tq.rego;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.Annotations;
import xyz.snaker.snakerlib.utility.Streams;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.utility.NoCreativeTab;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Created by SnakerBone on 12/12/2022
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rego
{
    static final Predicate<Item> BLACKLISTED_ITEMS = item -> item instanceof BlockItem || item.equals(Items.ENTITY_TAB_DISPLAY.get()) || item.equals(Items.BLOCK_TAB_DISPLAY.get()) || item.equals(Items.ITEM_TAB_DISPLAY.get());
    static final Predicate<Block> BLACKLISTED_BLOCKS = block -> block instanceof FlowerPotBlock || block instanceof LiquidBlock;
    static final Predicate<Item> WHITELISTED_EGGS = item -> item instanceof ForgeSpawnEggItem;

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTab().equals(Tabs.ITEMS.get())) {
            Streams.mapDeferredRegistries(Items.REGISTER, Item[]::new).forEach(item ->
            {
                if (!BLACKLISTED_ITEMS.test(item)) {
                    if (!WHITELISTED_EGGS.test(item)) {
                        safeAccept(event, item);
                    }
                }
            });
        }

        if (event.getTab().equals(Tabs.BLOCKS.get())) {
            Streams.mapDeferredRegistries(Blocks.REGISTER, Block[]::new).forEach(block ->
            {
                if (!BLACKLISTED_BLOCKS.test(block)) {
                    safeAccept(event, block);
                }
            });
        }

        if (event.getTab().equals(Tabs.MOBS.get())) {
            Streams.mapDeferredRegistries(Items.REGISTER, Item[]::new).forEach(item ->
            {
                if (WHITELISTED_EGGS.test(item)) {
                    safeAccept(event, item);
                }
            });
        }
    }

    static <T extends ItemLike> void safeAccept(BuildCreativeModeTabContentsEvent event, T obj)
    {
        Map<Boolean, T> map = new HashMap<>();
        ItemStack stack = obj.asItem().getDefaultInstance();
        boolean valid = stack.getCount() == 1;
        map.put(valid, obj);
        if (valid) {
            Class<?> itemClass = stack.getItem().getClass();
            if (Annotations.isPresent(itemClass, NoCreativeTab.class)) {
                return;
            }
            if (stack.getItem() instanceof BlockItem item) {
                Class<?> blockClass = item.getBlock().getClass();
                if (Annotations.isPresent(blockClass, NoCreativeTab.class)) {
                    return;
                }
            }
            event.accept(obj);
        } else {
            String itemName = map.get(false).asItem().toString();
            SnakerLib.LOGGER.warnf("ItemStack '[]' is empty or invalid", itemName);
        }
    }

    public static void initialize()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Tabs.REGISTER.register(bus);
        Items.REGISTER.register(bus);
        Blocks.REGISTER.register(bus);
        BlockEntities.REGISTER.register(bus);
        Sounds.REGISTER.register(bus);
        Entities.REGISTER.register(bus);
        TrunkPlacers.REGISTER.register(bus);
        Features.REGISTER.register(bus);
        LootModifiers.REGISTER.register(bus);
        FoliagePlacers.REGISTER.register(bus);
        Fluids.REGISTER.register(bus);
        FluidTypes.REGISTER.register(bus);
        Effects.REGISTER.register(bus);
    }
}