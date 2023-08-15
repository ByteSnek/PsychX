package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.block.entity.shader.*;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class BlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> REGISTRAR = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Tourniqueted.MODID);

    public static final RegistryObject<BlockEntityType<SwirlBlockEntity>> SWIRL = register("swirl_be", () -> BlockEntityType.Builder.of(SwirlBlockEntity::new, Blocks.SWIRL.get()).build(null));
    public static final RegistryObject<BlockEntityType<SnowflakeBlockEntity>> SNOWFLAKE = register("snowflake_be", () -> BlockEntityType.Builder.of(SnowflakeBlockEntity::new, Blocks.SNOWFLAKE.get()).build(null));
    public static final RegistryObject<BlockEntityType<WatercolourBlockEntity>> WATERCOLOUR = register("watercolour_be", () -> BlockEntityType.Builder.of(WatercolourBlockEntity::new, Blocks.WATERCOLOUR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MulticolourBlockEntity>> MULTICOLOUR = register("multicolour_be", () -> BlockEntityType.Builder.of(MulticolourBlockEntity::new, Blocks.MULTICOLOUR.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlareBlockEntity>> FLARE = register("flare_be", () -> BlockEntityType.Builder.of(FlareBlockEntity::new, Blocks.FLARE.get()).build(null));
    public static final RegistryObject<BlockEntityType<StarryBlockEntity>> STARRY = register("starry_be", () -> BlockEntityType.Builder.of(StarryBlockEntity::new, Blocks.STARRY.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeometricBlockEntity>> GEOMETRIC = register("geometric_be", () -> BlockEntityType.Builder.of(GeometricBlockEntity::new, Blocks.GEOMETRIC.get()).build(null));

    static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type)
    {
        return REGISTRAR.register(name, type);
    }
}
