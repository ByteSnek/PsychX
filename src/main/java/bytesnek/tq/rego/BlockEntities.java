package bytesnek.tq.rego;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.block.entity.shader.*;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class BlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Tourniqueted.MODID);

    public static final Supplier<BlockEntityType<SwirlBlockEntity>> SWIRL = register("swirl", () -> BlockEntityType.Builder.of(SwirlBlockEntity::new, Blocks.SWIRL.get()).build(null));
    public static final Supplier<BlockEntityType<SnowflakeBlockEntity>> SNOWFLAKE = register("snowflake", () -> BlockEntityType.Builder.of(SnowflakeBlockEntity::new, Blocks.SNOWFLAKE.get()).build(null));
    public static final Supplier<BlockEntityType<WatercolourBlockEntity>> WATERCOLOUR = register("watercolour", () -> BlockEntityType.Builder.of(WatercolourBlockEntity::new, Blocks.WATERCOLOUR.get()).build(null));
    public static final Supplier<BlockEntityType<MulticolourBlockEntity>> MULTICOLOUR = register("multicolour", () -> BlockEntityType.Builder.of(MulticolourBlockEntity::new, Blocks.MULTICOLOUR.get()).build(null));
    public static final Supplier<BlockEntityType<FlareBlockEntity>> FLAMES = register("flare", () -> BlockEntityType.Builder.of(FlareBlockEntity::new, Blocks.FLARE.get()).build(null));
    public static final Supplier<BlockEntityType<StarryBlockEntity>> STARRY = register("starry", () -> BlockEntityType.Builder.of(StarryBlockEntity::new, Blocks.STARRY.get()).build(null));
    public static final Supplier<BlockEntityType<GeometricBlockEntity>> GEOMETRIC = register("geometric", () -> BlockEntityType.Builder.of(GeometricBlockEntity::new, Blocks.GEOMETRIC.get()).build(null));
    public static final Supplier<BlockEntityType<BurningBlockEntity>> BURNING = register("burning", () -> BlockEntityType.Builder.of(BurningBlockEntity::new, Blocks.BURNING.get()).build(null));
    public static final Supplier<BlockEntityType<FoggyBlockEntity>> FOGGY = register("foggy", () -> BlockEntityType.Builder.of(FoggyBlockEntity::new, Blocks.FOGGY.get()).build(null));
    public static final Supplier<BlockEntityType<StaticBlockEntity>> STATIC = register("static", () -> BlockEntityType.Builder.of(StaticBlockEntity::new, Blocks.STATIC.get()).build(null));

    static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type)
    {
        return REGISTER.register(name + "_block_entity", type);
    }
}
