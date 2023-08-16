package xyz.snaker.tq.level.block;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.data.DefaultBlockProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlock<T extends BlockEntity> extends BaseEntityBlock
{
    private final RegistryObject<BlockEntityType<T>> blockEntity;

    public ShaderBlock(RegistryObject<BlockEntityType<T>> blockEntity)
    {
        super(DefaultBlockProperties.PERSPECTIVE);
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
            String placerName = placer.getDisplayName().getString();
            String blockPosition = pos.toShortString();
            SnakerLib.LOGGER.infof("A shader block has been placed by %s at [ %s ]", placerName, blockPosition);
        }
    }
}