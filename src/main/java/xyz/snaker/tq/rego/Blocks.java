package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.hiss.sneaky.Sneaky;
import xyz.snaker.snakerlib.level.block.FlowerBlock;
import xyz.snaker.snakerlib.level.block.SaplingBlock;
import xyz.snaker.snakerlib.level.block.TallPlantBlock;
import xyz.snaker.snakerlib.level.block.grower.SimpleTreeGrower;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.snakerlib.utility.block.BlockProperties;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ComasoteBlock;
import xyz.snaker.tq.level.block.OverlayBlock;
import xyz.snaker.tq.level.block.ShaderBlock;
import xyz.snaker.tq.level.levelgen.candidate.FeatureCandidate;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.level.block.Blocks.LAVA;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Blocks
{
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, Tourniqueted.MODID);

    public static final Supplier<Block> SWIRL = registerShaderBlock("swirl", BlockEntities.SWIRL);
    public static final Supplier<Block> SNOWFLAKE = registerShaderBlock("snowflake", BlockEntities.SNOWFLAKE);
    public static final Supplier<Block> WATERCOLOUR = registerShaderBlock("watercolour", BlockEntities.WATERCOLOUR);
    public static final Supplier<Block> MULTICOLOUR = registerShaderBlock("multicolour", BlockEntities.MULTICOLOUR);
    public static final Supplier<Block> FLARE = registerShaderBlock("flare", BlockEntities.FLAMES);
    public static final Supplier<Block> STARRY = registerShaderBlock("starry", BlockEntities.STARRY);
    public static final Supplier<Block> GEOMETRIC = registerShaderBlock("geometric", BlockEntities.GEOMETRIC);
    public static final Supplier<Block> BURNING = registerShaderBlock("burning", BlockEntities.BURNING);
    public static final Supplier<Block> FOGGY = registerShaderBlock("foggy", BlockEntities.FOGGY);
    public static final Supplier<Block> STATIC = registerShaderBlock("static", BlockEntities.STATIC);

    public static final Supplier<Block> PINKTAILS = registerBlockWithItem("pink_tails", () -> new FlowerBlock(BlockProperties.PLANT.apply(MapColor.COLOR_PINK), Tags.COMATOSE_BLOCKS, true));
    public static final Supplier<Block> SPLITLEAF = registerBlockWithItem("split_leaf", () -> new FlowerBlock(BlockProperties.PLANT.apply(MapColor.COLOR_CYAN), Tags.COMATOSE_BLOCKS, true));
    public static final Supplier<Block> SNAKEROOT = registerBlockWithItem("snake_root", () -> new TallPlantBlock(BlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Tags.COMATOSE_BLOCKS, true));
    public static final Supplier<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snake_root", () -> new TallPlantBlock(BlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Tags.COMATOSE_BLOCKS, true));

    public static final Supplier<Block> COMA_SOIL = registerBlockWithItem("coma_soil", () -> new Block(BlockProperties.NORMAL.apply(MapColor.COLOR_GRAY).sound(SoundType.SOUL_SOIL)));
    public static final Supplier<Block> COMA_STONE = registerBlockWithItem("coma_stone", () -> new Block(BlockProperties.NORMAL.apply(MapColor.COLOR_GRAY).sound(SoundType.NETHER_ORE)));
    //public static final Supplier<Block> COMA_FIRE = register("coma_fire", ComaFireBlock::new);

    public static final Supplier<Block> ILLUSIVE_SAPLING = registerBlockWithItem("illusive_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureCandidate.ILLUSIVE_TREE.getConfiguredFeatureKey()), BlockProperties.GRASS.apply(MapColor.COLOR_PURPLE), Tags.COMATOSE_BLOCKS, true));
    public static final Supplier<Block> ILLUSIVE_LOG = registerBlockWithItem("illusive_log", () -> new RotatedPillarBlock(BlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final Supplier<Block> ILLUSIVE_PLANKS = registerBlockWithItem("illusive_planks", () -> new Block(BlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final Supplier<Block> ILLUSIVE_LEAVES = registerBlockWithItem("illusive_leaves", () -> new LeavesBlock(BlockProperties.LEAVES.apply(MapColor.COLOR_PURPLE)));

    public static final Supplier<Block> DELUSIVE_SAPLING = registerBlockWithItem("delusive_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureCandidate.DELUSIVE_TREE.getConfiguredFeatureKey()), BlockProperties.GRASS.apply(MapColor.COLOR_MAGENTA), Tags.COMATOSE_BLOCKS, true));
    public static final Supplier<Block> DELUSIVE_LOG = registerBlockWithItem("delusive_log", () -> new RotatedPillarBlock(BlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));
    public static final Supplier<Block> DELUSIVE_PLANKS = registerBlockWithItem("delusive_planks", () -> new Block(BlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));
    public static final Supplier<Block> DELUSIVE_LEAVES = registerBlockWithItem("delusive_leaves", () -> new LeavesBlock(BlockProperties.LEAVES.apply(MapColor.COLOR_MAGENTA)));

    public static final Supplier<Block> DARK_OVERLAY = registerOverlay("dark");
    public static final Supplier<Block> WHITE_OVERLAY = registerOverlay("white");
    public static final Supplier<Block> LIGHT_OVERLAY = registerOverlay("light");
    public static final Supplier<Block> BRIGHT_OVERLAY = registerOverlay("bright");
    public static final Supplier<Block> CREME_OVERLAY = registerOverlay("creme");
    public static final Supplier<Block> SHIMMER_OVERLAY = registerOverlay("shimmer");
    public static final Supplier<Block> SKIN_OVERLAY = registerOverlay("skin");
    public static final Supplier<Block> TURQUOISE_OVERLAY = registerOverlay("turquoise");

    public static final Supplier<LiquidBlock> COMASOTE = Sneaky.cast(REGISTER.register("comasote", () -> new ComasoteBlock(Fluids.COMASOTE, BlockBehaviour.Properties.copy(LAVA).mapColor(MapColor.COLOR_MAGENTA).noLootTable())));

    static Supplier<Block> register(String name, Supplier<Block> block)
    {
        return Sneaky.cast(REGISTER.register(name, block));
    }

    static <T extends BlockEntity, B extends Supplier<BlockEntityType<T>>> Supplier<Block> registerShaderBlock(String name, B blockEntity)
    {
        return register(name + "_block", () -> new ShaderBlock<>(blockEntity));
    }

    static Supplier<Block> registerOverlay(String name)
    {
        return registerBlockWithItem(name + "_overlay", OverlayBlock::new);
    }

    static <T extends Block> Supplier<T> registerBlockWithItem(String name, Supplier<T> block)
    {
        Supplier<T> obj = Sneaky.cast(register(name, Sneaky.cast(block)));
        registerBlockItem(name, obj);
        return obj;
    }

    static <T extends Block> void registerBlockItem(String name, Supplier<T> block)
    {
        Items.REGISTER.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static class Tags
    {
        public static final TagKey<Block> COMATOSE_BLOCKS = TagKey.create(Registries.BLOCK, new ResourceReference("groundrich"));
    }
}
