package xyz.snaker.tq.rego;

import java.util.Map;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.concurrent.AsyncHashMap;
import xyz.snaker.snakerlib.utility.tools.AnnotationStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.entity.EntityDropHandler;
import xyz.snaker.tq.utility.IgnoreCreativeTab;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 12/12/2022
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Rego
{
    static final Predicate<RegistryObject<Item>> BLACKLISTED_ITEMS = item -> item.get() instanceof BlockItem || item.equals(Items.MOB_TAB_ICON) || item.equals(Items.BLOCK_TAB_ICON) || item.equals(Items.ITEM_TAB_ICON);
    static final Predicate<RegistryObject<Block>> BLACKLISTED_BLOCKS = block -> block.get() instanceof FlowerPotBlock;
    static final Predicate<RegistryObject<Item>> WHITELISTED_EGGS = item -> item.get() instanceof ForgeSpawnEggItem;

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTab().equals(Tabs.ITEMS.get())) {
            for (RegistryObject<Item> item : Items.REGISTRAR.getEntries()) {
                if (!BLACKLISTED_ITEMS.test(item)) {
                    if (!WHITELISTED_EGGS.test(item)) {
                        safeAccept(event, item);
                    }
                }
            }
        }

        if (event.getTab().equals(Tabs.BLOCKS.get())) {
            for (RegistryObject<Block> block : Blocks.REGISTRAR.getEntries()) {
                if (!BLACKLISTED_BLOCKS.test(block)) {
                    safeAccept(event, block);
                }
            }
        }

        if (event.getTab().equals(Tabs.MOBS.get())) {
            for (RegistryObject<Item> item : Items.REGISTRAR.getEntries()) {
                if (WHITELISTED_EGGS.test(item)) {
                    safeAccept(event, item);
                }
            }
        }
    }

    static <T extends ItemLike> void safeAccept(BuildCreativeModeTabContentsEvent event, RegistryObject<T> obj)
    {
        Map<Boolean, ResourceLocation> map = new AsyncHashMap<>();
        if (AnnotationStuff.isNotPresent(obj, IgnoreCreativeTab.class)) {
            ItemStack stack = obj.get().asItem().getDefaultInstance();
            boolean valid = stack.getCount() == 1;
            map.put(valid, obj.getId());
            if (valid) {
                event.accept(obj);
            } else {
                String itemName = map.get(false).toString();
                SnakerLib.LOGGER.warnf("ItemStack '%s' is empty or invalid", itemName);
            }
        }
    }

    public static void initialize()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityDropHandler.initialize();
        Tabs.REGISTRAR.register(bus);
        Items.REGISTRAR.register(bus);
        Blocks.REGISTRAR.register(bus);
        BlockEntities.REGISTRAR.register(bus);
        Sounds.REGISTRAR.register(bus);
        Entities.REGISTRAR.register(bus);
        Effects.REGISTRAR.register(bus);
        TrunkPlacers.REGISTRAR.register(bus);
        Features.REGISTRAR.register(bus);
    }
}