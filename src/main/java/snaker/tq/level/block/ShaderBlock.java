package snaker.tq.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.level.block.SnakerEntityBlock;
import snaker.snakerlib.level.block.entity.SnakerBlockEntity;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlock<T extends SnakerBlockEntity> extends SnakerEntityBlock
{
    private final RegistryObject<BlockEntityType<T>> blockEntity;

    public ShaderBlock(RegistryObject<BlockEntityType<T>> blockEntity)
    {
        super(MapColor.NONE, properties -> properties.strength(5).noOcclusion());
        this.blockEntity = blockEntity;
    }

    @Nullable
    @Override
    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state)
    {
        return blockEntity.get().create(pos, state);
    }

    @Override
    @SuppressWarnings({"deprecation"})
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack)
    {
        if (!level.isClientSide && placer != null) {
            SnakerLib.LOGGER.info(String.format("A shader block has been placed by %s at [ %s ]", placer.getDisplayName().getString(), pos.toShortString()));
        }
    }
}