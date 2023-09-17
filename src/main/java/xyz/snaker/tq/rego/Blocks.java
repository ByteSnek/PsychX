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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static final RegistryObject<Block> SWIRL = registerShaderBlock("swirl", BlockEntities.SWIRL);
    public static final RegistryObject<Block> SNOWFLAKE = registerShaderBlock("snowflake", BlockEntities.SNOWFLAKE);
    public static final RegistryObject<Block> WATERCOLOUR = registerShaderBlock("watercolour", BlockEntities.WATERCOLOUR);
    public static final RegistryObject<Block> MULTICOLOUR = registerShaderBlock("multicolour", BlockEntities.MULTICOLOUR);
    public static final RegistryObject<Block> FLARE = registerShaderBlock("flare", BlockEntities.FLARE);
    public static final RegistryObject<Block> STARRY = registerShaderBlock("starry", BlockEntities.STARRY);
    public static final RegistryObject<Block> GEOMETRIC = registerShaderBlock("geometric", BlockEntities.GEOMETRIC);
    public static final RegistryObject<Block> BURNING = registerShaderBlock("burning", BlockEntities.BURNING);
    public static final RegistryObject<Block> FOGGY = registerShaderBlock("foggy", BlockEntities.FOGGY);
    public static final RegistryObject<Block> STATIC = registerShaderBlock("static", BlockEntities.STATIC);

    public static final RegistryObject<Block> PINKTAILS = registerBlockWithItem("pink_tails", () -> new FlowerBlock(Effects.VISION_CONVOLVE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_PINK), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> SPLITLEAF = registerBlockWithItem("split_leaf", () -> new FlowerBlock(Effects.VISION_CONVOLVE::get, Maths.secondsToTicks(5), DefaultBlockProperties.PLANT.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> SNAKEROOT = registerBlockWithItem("snake_root", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snake_root", () -> new TallPlantBlock(DefaultBlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Keys.GROUNDRICH, true));

    public static final RegistryObject<Block> COMASTONE = registerBlockWithItem("coma_stone", () -> new Block(DefaultBlockProperties.NORMAL.apply(MapColor.COLOR_BLACK)));

    public static final RegistryObject<Block> GEOMETRIC_SAPLING = registerBlockWithItem("geometric_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureKey.GEOMETRIC_TREE.getConfigKey()), DefaultBlockProperties.GRASS.apply(MapColor.COLOR_PURPLE), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> GEOMETRIC_LOG = registerBlockWithItem("geometric_log", () -> new RotatedPillarBlock(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> GEOMETRIC_PLANKS = registerBlockWithItem("geometric_planks", () -> new Block(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> FOGGY_SAPLING = registerBlockWithItem("foggy_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureKey.FOGGY_TREE.getConfigKey()), DefaultBlockProperties.GRASS.apply(MapColor.COLOR_MAGENTA), Keys.GROUNDRICH, true));
    public static final RegistryObject<Block> FOGGY_LOG = registerBlockWithItem("foggy_log", () -> new RotatedPillarBlock(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> FOGGY_PLANKS = registerBlockWithItem("foggy_planks", () -> new Block(DefaultBlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));

    public static final RegistryObject<Block> DARK_OVERLAY = registerOverlay("dark");
    public static final RegistryObject<Block> WHITE_OVERLAY = registerOverlay("white");
    public static final RegistryObject<Block> LIGHT_OVERLAY = registerOverlay("light");
    public static final RegistryObject<Block> BRIGHT_OVERLAY = registerOverlay("bright");
    public static final RegistryObject<Block> CREME_OVERLAY = registerOverlay("creme");
    public static final RegistryObject<Block> SHIMMER_OVERLAY = registerOverlay("shimmer");
    public static final RegistryObject<Block> SKIN_OVERLAY = registerOverlay("skin");
    public static final RegistryObject<Block> TURQUOISE_OVERLAY = registerOverlay("turquoise");

    static RegistryObject<Block> register(String name, Supplier<Block> block)
    {
        return REGISTRAR.register(name, block);
    }

    static <T extends BlockEntity, B extends RegistryObject<BlockEntityType<T>>> RegistryObject<Block> registerShaderBlock(String name, B blockEntity)
    {
        return register(name + "_block", () -> new ShaderBlock<>(blockEntity));
    }

    static RegistryObject<Block> registerOverlay(String name)
    {
        return registerBlockWithItem(name + "_overlay", OverlayBlock::new);
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
