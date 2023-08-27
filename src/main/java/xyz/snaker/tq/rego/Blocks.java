package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.data.DefaultBlockProperties;
import xyz.snaker.snakerlib.level.block.FlowerBlock;
import xyz.snaker.snakerlib.level.block.SaplingBlock;
import xyz.snaker.snakerlib.level.block.TallPlantBlock;
import xyz.snaker.snakerlib.level.world.SimpleTreeGrower;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.OverlayBlock;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.world.feature.FeatureKey;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static final RegistryObject<Block> PINKTAILS = registerBlockWithItem("pink_tails", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_PINK), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> SPLITLEAF = registerBlockWithItem("split_leaf", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> SNAKEROOT = registerBlockWithItem("snake_root", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snake_root", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));

    public static final RegistryObject<Block> COMASTONE = registerBlockWithItem("coma_stone", () -> new Block(DefaultBlockProperties.NORMAL.apply(MapColor.COLOR_BLACK)));

    public static final RegistryObject<Block> GEOMETRIC_SAPLING = registerBlockWithItem("geometric_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureKey.GEOMETRIC.getConfigKey()), DefaultBlockProperties.GRASS.apply(MapColor.COLOR_PURPLE), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> GEOMETRIC_LOG = registerBlockWithItem("geometric_log", () -> new RotatedPillarBlock(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> GEOMETRIC_PLANKS = registerBlockWithItem("geometric_planks", () -> new Block(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> COMASTONE_OVERLAY = registerBlockWithItem("coma_stone_overlay", OverlayBlock::new);

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
