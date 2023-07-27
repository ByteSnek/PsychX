package snaker.snakerlib.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Created by SnakerBone on 27/06/2023
 **/
public abstract class SnakerBlockEntity extends BlockEntity
{
    public SnakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithFullMetadata);
    }

    @Override
    public void setChanged()
    {
        level.setBlock(worldPosition, getBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        super.setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL_IMMEDIATE);
    }

    public void markDirtyAndDispatch()
    {
        super.setChanged();
        dispatchToNearbyPlayers(this);
    }

    public static void dispatchToNearbyPlayers(BlockEntity blockEntity)
    {
        Level level = blockEntity.getLevel();
        if (level != null) {
            Packet<ClientGamePacketListener> packet = blockEntity.getUpdatePacket();
            if (packet != null) {
                List<? extends Player> players = level.players();
                BlockPos pos = blockEntity.getBlockPos();
                for (Player player : players) {
                    if (player instanceof ServerPlayer sPlayer) {
                        if (true) { // @formatter:off
                                    // 2do: make this thing actually do something with
                                    // my shader blocks in tq when placed by world gen.
                                    // if you have a solution to this please message me RIGHT NOW.
                                    // i have been trying to fix this issue for months but to no prevail
                                    // no matter how hard i try i just cannot make
                                    // them update. i dont know if its because im retarded
                                    // or if its something on minecrafts end (maybe?) i have no clue
                                    // @formatter:on
                            sPlayer.connection.send(packet);
                        }
                    }
                }
            }
        }
    }


    private static boolean isPlayerNearby(double x1, double z1, double x2, double z2)
    {
        return Math.hypot(x1 - x2, z1 - z2) < 64;
    }
}
