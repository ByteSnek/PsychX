package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import bytesnek.hiss.math.Maths;
import bytesnek.snakerlib.level.block.FlowerBlock;
import bytesnek.snakerlib.level.block.SaplingBlock;
import bytesnek.snakerlib.level.block.TallPlantBlock;
import bytesnek.snakerlib.level.block.grower.SimpleTreeGrower;
import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.snakerlib.utility.block.BlockProperties;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.block.ComasoteBlock;
import bytesnek.tq.level.block.OverlayBlock;
import bytesnek.tq.level.block.ShaderBlock;
import bytesnek.tq.level.world.candidate.FeatureCandidate;

import static net.minecraft.world.level.block.Blocks.LAVA;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Blocks
{
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Tourniqueted.MODID);

    public static final RegistryObject<Block> SWIRL = registerShaderBlock("swirl", BlockEntities.SWIRL);
    public static final RegistryObject<Block> SNOWFLAKE = registerShaderBlock("snowflake", BlockEntities.SNOWFLAKE);
    public static final RegistryObject<Block> WATERCOLOUR = registerShaderBlock("watercolour", BlockEntities.WATERCOLOUR);
    public static final RegistryObject<Block> MULTICOLOUR = registerShaderBlock("multicolour", BlockEntities.MULTICOLOUR);
    public static final RegistryObject<Block> FLARE = registerShaderBlock("flare", BlockEntities.FLAMES);
    public static final RegistryObject<Block> STARRY = registerShaderBlock("starry", BlockEntities.STARRY);
    public static final RegistryObject<Block> GEOMETRIC = registerShaderBlock("geometric", BlockEntities.GEOMETRIC);
    public static final RegistryObject<Block> BURNING = registerShaderBlock("burning", BlockEntities.BURNING);
    public static final RegistryObject<Block> FOGGY = registerShaderBlock("foggy", BlockEntities.FOGGY);
    public static final RegistryObject<Block> STATIC = registerShaderBlock("static", BlockEntities.STATIC);

    public static final RegistryObject<Block> PINKTAILS = registerBlockWithItem("pink_tails", () -> new FlowerBlock(() -> MobEffects.HARM, Maths.secondsToTicks(5), BlockProperties.PLANT.apply(MapColor.COLOR_PINK), Tags.GROUNDRICH, true));
    public static final RegistryObject<Block> SPLITLEAF = registerBlockWithItem("split_leaf", () -> new FlowerBlock(() -> MobEffects.HARM, Maths.secondsToTicks(5), BlockProperties.PLANT.apply(MapColor.COLOR_CYAN), Tags.GROUNDRICH, true));
    public static final RegistryObject<Block> SNAKEROOT = registerBlockWithItem("snake_root", () -> new TallPlantBlock(BlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Tags.GROUNDRICH, true));
    public static final RegistryObject<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snake_root", () -> new TallPlantBlock(BlockProperties.GRASS.apply(MapColor.COLOR_CYAN), Tags.GROUNDRICH, true));

    public static final RegistryObject<Block> COMASTONE = registerBlockWithItem("coma_stone", () -> new Block(BlockProperties.NORMAL.apply(MapColor.COLOR_BLACK)));

    public static final RegistryObject<Block> ILLUSIVE_SAPLING = registerBlockWithItem("illusive_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureCandidate.ILLUSIVE_TREE.getConfiguredFeatureKey()), BlockProperties.GRASS.apply(MapColor.COLOR_PURPLE), Tags.GROUNDRICH, true));
    public static final RegistryObject<Block> ILLUSIVE_LOG = registerBlockWithItem("illusive_log", () -> new RotatedPillarBlock(BlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> ILLUSIVE_PLANKS = registerBlockWithItem("illusive_planks", () -> new Block(BlockProperties.WOOD.apply(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> ILLUSIVE_LEAVES = registerBlockWithItem("illusive_leaves", () -> new LeavesBlock(BlockProperties.LEAVES.apply(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> DELUSIVE_SAPLING = registerBlockWithItem("delusive_sapling", () -> new SaplingBlock(new SimpleTreeGrower(FeatureCandidate.DELUSIVE_TREE.getConfiguredFeatureKey()), BlockProperties.GRASS.apply(MapColor.COLOR_MAGENTA), Tags.GROUNDRICH, true));
    public static final RegistryObject<Block> DELUSIVE_LOG = registerBlockWithItem("delusive_log", () -> new RotatedPillarBlock(BlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> DELUSIVE_PLANKS = registerBlockWithItem("delusive_planks", () -> new Block(BlockProperties.WOOD.apply(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> DELUSIVE_LEAVES = registerBlockWithItem("delusive_leaves", () -> new LeavesBlock(BlockProperties.LEAVES.apply(MapColor.COLOR_MAGENTA)));

    public static final RegistryObject<Block> DARK_OVERLAY = registerOverlay("dark");
    public static final RegistryObject<Block> WHITE_OVERLAY = registerOverlay("white");
    public static final RegistryObject<Block> LIGHT_OVERLAY = registerOverlay("light");
    public static final RegistryObject<Block> BRIGHT_OVERLAY = registerOverlay("bright");
    public static final RegistryObject<Block> CREME_OVERLAY = registerOverlay("creme");
    public static final RegistryObject<Block> SHIMMER_OVERLAY = registerOverlay("shimmer");
    public static final RegistryObject<Block> SKIN_OVERLAY = registerOverlay("skin");
    public static final RegistryObject<Block> TURQUOISE_OVERLAY = registerOverlay("turquoise");

    public static final RegistryObject<LiquidBlock> COMASOTE = REGISTER.register("comasote", () -> new ComasoteBlock(Fluids.COMASOTE, BlockBehaviour.Properties.copy(LAVA).mapColor(MapColor.COLOR_MAGENTA).noLootTable()));

    static RegistryObject<Block> register(String name, Supplier<Block> block)
    {
        return REGISTER.register(name, block);
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
        RegistryObject<T> obj = REGISTER.register(name, block);
        registerBlockItem(name, obj);
        return obj;
    }

    static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        Items.REGISTER.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static class Tags
    {
        public static final TagKey<Block> GROUNDRICH = TagKey.create(Registries.BLOCK, new ResourceReference("groundrich"));
    }
}
