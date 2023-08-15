package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.data.SnakerConstants;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.ComatoseNyliumBlock;
import xyz.snaker.tq.level.block.ShaderBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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
    static final DeferredRegister<Block> REGISTRAR = DeferredRegister.create(ForgeRegistries.BLOCKS, Tourniqueted.MODID);

    public static final RegistryObject<Block> SWIRL = register("swirl_block", () -> new ShaderBlock<>(BlockEntities.SWIRL));
    public static final RegistryObject<Block> SNOWFLAKE = register("snowflake_block", () -> new ShaderBlock<>(BlockEntities.SNOWFLAKE));
    public static final RegistryObject<Block> WATERCOLOUR = register("watercolour_block", () -> new ShaderBlock<>(BlockEntities.WATERCOLOUR));
    public static final RegistryObject<Block> MULTICOLOUR = register("multicolour_block", () -> new ShaderBlock<>(BlockEntities.MULTICOLOUR));
    public static final RegistryObject<Block> FLARE = register("flare_block", () -> new ShaderBlock<>(BlockEntities.FLARE));
    public static final RegistryObject<Block> STARRY = register("starry_block", () -> new ShaderBlock<>(BlockEntities.STARRY));
    public static final RegistryObject<Block> GEOMETRIC = register("geometric_block", () -> new ShaderBlock<>(BlockEntities.GEOMETRIC));

    public static final RegistryObject<Block> CATNIP = registerBlockWithItem("catnip", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), SnakerConstants.BlockProperties.FLOWER));
    public static final RegistryObject<Block> SPLITLEAF = registerBlockWithItem("splitleaf", () -> new FlowerBlock(Effects.SYNCOPE::get, Maths.secondsToTicks(5), SnakerConstants.BlockProperties.FLOWER));
    public static final RegistryObject<Block> SNAKEROOT = registerBlockWithItem("snakeroot", () -> new TallGrassBlock(SnakerConstants.BlockProperties.GRASS));
    public static final RegistryObject<Block> TALL_SNAKEROOT = registerBlockWithItem("tall_snakeroot", () -> new TallGrassBlock(SnakerConstants.BlockProperties.GRASS));
    public static final RegistryObject<Block> POTTED_CATNIP = register("potted_catnip", () -> new FlowerPotBlock(() -> UnsafeStuff.cast(FLOWER_POT), CATNIP, SnakerConstants.BlockProperties.NORMAL));
    public static final RegistryObject<Block> POTTED_SPLITLEAF = register("potted_splitleaf", () -> new FlowerPotBlock(() -> UnsafeStuff.cast(FLOWER_POT), SPLITLEAF, SnakerConstants.BlockProperties.NORMAL));

    public static final RegistryObject<Block> COMA_STONE = registerBlockWithItem("coma_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(0.5F).sound(SoundType.NETHER_ORE)));
    public static final RegistryObject<Block> DELUSIVE_NYLIUM = registerBlockWithItem("delusive_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> ILLUSIVE_NYLIUM = registerBlockWithItem("illusive_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> IMMATERIAL_NYLIUM = registerBlockWithItem("immaterial_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> SPECTRAL_NYLIUM = registerBlockWithItem("spectral_nylium", ComatoseNyliumBlock::new);
    public static final RegistryObject<Block> SURREAL_NYLIUM = registerBlockWithItem("surreal_nylium", ComatoseNyliumBlock::new);

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
