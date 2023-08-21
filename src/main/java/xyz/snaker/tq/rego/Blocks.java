package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.data.DefaultBlockProperties;
import xyz.snaker.snakerlib.level.block.FlowerBlock;
import xyz.snaker.snakerlib.level.block.SaplingBlock;
import xyz.snaker.snakerlib.level.block.TallPlantBlock;
import xyz.snaker.snakerlib.level.world.SimpleTreeGrower;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.DelusiveBlock;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.world.feature.Features;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.Blocks.FLOWER_POT;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Blocks
{
    public static final DeferredRegister<Block> REGISTRAR = DeferredRegister.create(ForgeRegistries.BLOCKS, Tourniqueted.MODID);

    public static final RegistryObject<Block> SWIRL = register("swirl_block", () -> new ShaderBlock<>(BlockEntities.SWIRL));
    public static final RegistryObject<Block> SNOWFLAKE = register("snowflake_block", () -> new ShaderBlock<>(BlockEntities.SNOWFLAKE));
    public static final RegistryObject<Block> WATERCOLOUR = register("watercolour_block", () -> new ShaderBlock<>(BlockEntities.WATERCOLOUR));
    public static final RegistryObject<Block> MULTICOLOUR = register("multicolour_block", () -> new ShaderBlock<>(BlockEntities.MULTICOLOUR));
    public static final RegistryObject<Block> FLARE = register("flare_block", () -> new ShaderBlock<>(BlockEntities.FLARE));
    public static final RegistryObject<Block> STARRY = register("starry_block", () -> new ShaderBlock<>(BlockEntities.STARRY));
    public static final RegistryObject<Block> GEOMETRIC = register("geometric_block", () -> new ShaderBlock<>(BlockEntities.GEOMETRIC));

    public static final RegistryObject<Block> COMA_STONE = registerBlockWithItem("coma_stone", () -> new Block(DefaultBlockProperties.CUTOUT.apply(MapColor.NONE)));
    public static final RegistryObject<Block> DELUSIVE_NYLIUM = registerBlockWithItem("delusive_nylium", () -> new DelusiveBlock(DefaultBlockProperties.NORMAL.apply(MapColor.NONE)));
    public static final RegistryObject<Block> ILLUSIVE_NYLIUM = registerBlockWithItem("illusive_nylium", () -> new DelusiveBlock(DefaultBlockProperties.NORMAL.apply(MapColor.NONE)));
    public static final RegistryObject<Block> IMMATERIAL_NYLIUM = registerBlockWithItem("immaterial_nylium", () -> new DelusiveBlock(DefaultBlockProperties.NORMAL.apply(MapColor.NONE)));
    public static final RegistryObject<Block> SPECTRAL_NYLIUM = registerBlockWithItem("spectral_nylium", () -> new DelusiveBlock(DefaultBlockProperties.NORMAL.apply(MapColor.NONE)));
    public static final RegistryObject<Block> SURREAL_NYLIUM = registerBlockWithItem("surreal_nylium", () -> new DelusiveBlock(DefaultBlockProperties.NORMAL.apply(MapColor.NONE)));

    public static final RegistryObject<Block> PINKTAILS = registerBlockWithItem("pinktails", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_PINK), Keys.COMATOSE_NYLIUM, true));
    public static final RegistryObject<Block> SPLITLEAF = registerBlockWithItem("splitleaf", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_CYAN), Keys.COMATOSE_NYLIUM, true));
    public static final RegistryObject<Block> SNAKEROOT = registerBlockWithItem("snakeroot", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.COMATOSE_NYLIUM, true));
    public static final RegistryObject<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snakeroot", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.COMATOSE_NYLIUM, true));
    public static final RegistryObject<Block> POTTED_CATNIP = register("potted_catnip", () -> new FlowerPotBlock(() -> UnsafeStuff.cast(FLOWER_POT), PINKTAILS, DefaultBlockProperties.NORMAL.apply(MapColor.PLANT).noLootTable()));
    public static final RegistryObject<Block> POTTED_SPLITLEAF = register("potted_splitleaf", () -> new FlowerPotBlock(() -> UnsafeStuff.cast(FLOWER_POT), SPLITLEAF, DefaultBlockProperties.NORMAL.apply(MapColor.PLANT).noLootTable()));

    public static final RegistryObject<Block> GEOMETRIC_SAPLING = registerBlockWithItem("geometric_sapling", () -> new SaplingBlock(new SimpleTreeGrower(Features.ConfigKey.GEOMETRIC.key()), DefaultBlockProperties.GRASS.apply(MapColor.COLOR_PURPLE), Keys.COMATOSE_NYLIUM, true));
    public static final RegistryObject<Block> GEOMETRIC_LOG = registerBlockWithItem("geometric_log", () -> new RotatedPillarBlock(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> GEOMETRIC_PLANKS = registerBlockWithItem("geometric_planks", () -> new Block(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));

    static RegistryObject<Block> register(String name, Supplier<Block> block)
    {
        return REGISTRAR.register(name, block);
    }

    static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block)
    {
        RegistryObject<T> obj = REGISTRAR.register(name, block);
        registerBlockItem(name, obj);
        return obj;
    }

    static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        Items.REGISTRAR.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
