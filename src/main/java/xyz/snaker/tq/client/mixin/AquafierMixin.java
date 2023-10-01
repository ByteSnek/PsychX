package xyz.snaker.tq.client.mixin;

import javax.annotation.Nullable;
import java.util.Optional;

import xyz.snaker.tq.rego.Levels;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.spongepowered.asm.mixin.*;

import static xyz.snaker.tq.rego.Blocks.COMASOTE;

/**
 * Created by SnakerBone on 30/09/2023
 **/
@Mixin(Aquifer.NoiseBasedAquifer.class)
public abstract class AquafierMixin
{
    @Unique
    public BlockState defaultFluidState = Blocks.LAVA.defaultBlockState();

    @Shadow
    protected boolean shouldScheduleFluidUpdate;

    @Shadow
    @Final
    private Aquifer.FluidPicker globalFluidPicker;

    @Shadow
    protected abstract int getIndex(int pGridX, int pGridY, int pGridZ);

    @Shadow
    @Final
    protected long[] aquiferLocationCache;

    @Shadow
    @Final
    private PositionalRandomFactory positionalRandomFactory;

    @Shadow
    protected static double similarity(int pFirstFluid, int pSecondFluid)
    {
        return 1 - Math.abs(pSecondFluid - pFirstFluid) / 25D;
    }

    @Shadow
    protected abstract Aquifer.FluidStatus getAquiferStatus(long pPackedPos);

    @Shadow
    @Final
    private static double FLOWING_UPDATE_SIMULARITY;

    @Shadow
    protected abstract double calculatePressure(DensityFunction.FunctionContext pContext, MutableDouble pSubstance, Aquifer.FluidStatus pFirstFluid, Aquifer.FluidStatus pSecondFluid);

    @Overwrite
    public @Nullable BlockState computeSubstance(DensityFunction.FunctionContext context, double pSubstance)
    {
        int sBlockX = context.blockX();
        int sBlockY = context.blockY();
        int sBlockZ = context.blockZ();

        Optional<Level> level = Optional.empty();

        if (FMLEnvironment.dist.isClient()) {
            level = Optional.ofNullable(Minecraft.getInstance().level);
        }

        if (level.isPresent()) {
            if (level.get().dimension() == Levels.COMATOSE) {
                defaultFluidState = COMASOTE.get().defaultBlockState();
            }
        }

        if (pSubstance > 0) {
            shouldScheduleFluidUpdate = false;
            return null;
        } else {
            Aquifer.FluidStatus sFluidStatusPos = globalFluidPicker.computeFluid(sBlockX, sBlockY, sBlockZ);
            if (sFluidStatusPos.at(sBlockY).is(Blocks.LAVA)) {
                shouldScheduleFluidUpdate = false;
                return defaultFluidState;
            } else {
                int sPosX = Math.floorDiv(sBlockX - 5, 16);
                int sPosY = Math.floorDiv(sBlockY + 1, 12);
                int sPosZ = Math.floorDiv(sBlockZ - 5, 16);
                int sPosMaxX = Integer.MAX_VALUE;
                int sPosMaxY = Integer.MAX_VALUE;
                int sPosMaxZ = Integer.MAX_VALUE;
                long sSeedX = 0;
                long sSeedY = 0;
                long sSeedZ = 0;

                for (int sX = 0; sX <= 1; ++sX) {
                    for (int sY = -1; sY <= 1; ++sY) {
                        for (int sZ = 0; sZ <= 1; ++sZ) {
                            int sRandPosX = sPosX + sX;
                            int sRandPosY = sPosY + sY;
                            int sRandPosZ = sPosZ + sZ;
                            int sCacheIndex = getIndex(sRandPosX, sRandPosY, sRandPosZ);
                            long sSeededCache = aquiferLocationCache[sCacheIndex];
                            long sPackedPos;

                            if (sSeededCache != Long.MAX_VALUE) {
                                sPackedPos = sSeededCache;
                            } else {
                                RandomSource sRandom = positionalRandomFactory.at(sRandPosX, sRandPosY, sRandPosZ);
                                sPackedPos = BlockPos.asLong(sRandPosX * 16 + sRandom.nextInt(10), sRandPosY * 12 + sRandom.nextInt(9), sRandPosZ * 16 + sRandom.nextInt(10));
                                aquiferLocationCache[sCacheIndex] = sPackedPos;
                            }

                            int sCachedX = BlockPos.getX(sPackedPos) - sBlockX;
                            int sCachedY = BlockPos.getY(sPackedPos) - sBlockY;
                            int sCachedZ = BlockPos.getZ(sPackedPos) - sBlockZ;
                            int sCachedPos = sCachedX * sCachedX + sCachedY * sCachedY + sCachedZ * sCachedZ;

                            if (sPosMaxX >= sCachedPos) {
                                sSeedZ = sSeedY;
                                sSeedY = sSeedX;
                                sSeedX = sPackedPos;
                                sPosMaxZ = sPosMaxY;
                                sPosMaxY = sPosMaxX;
                                sPosMaxX = sCachedPos;
                            } else if (sPosMaxY >= sCachedPos) {
                                sSeedZ = sSeedY;
                                sSeedY = sPackedPos;
                                sPosMaxZ = sPosMaxY;
                                sPosMaxY = sCachedPos;
                            } else if (sPosMaxZ >= sCachedPos) {
                                sSeedZ = sPackedPos;
                                sPosMaxZ = sCachedPos;
                            }
                        }
                    }
                }

                Aquifer.FluidStatus sFluidStatusX = getAquiferStatus(sSeedX);
                double sSimXY = similarity(sPosMaxX, sPosMaxY);
                BlockState sResultState = sFluidStatusX.at(sBlockY);

                if (sSimXY <= 0) {
                    shouldScheduleFluidUpdate = sSimXY >= FLOWING_UPDATE_SIMULARITY;
                    return sResultState;
                } else if (sResultState.is(Blocks.WATER) && globalFluidPicker.computeFluid(sBlockX, sBlockY - 1, sBlockZ).at(sBlockY - 1).is(Blocks.LAVA)) {
                    shouldScheduleFluidUpdate = true;
                    return sResultState;
                } else {
                    MutableDouble sNaN = new MutableDouble(Double.NaN);
                    Aquifer.FluidStatus sFluidStatusY = getAquiferStatus(sSeedY);
                    double sPressureXY = sSimXY * calculatePressure(context, sNaN, sFluidStatusX, sFluidStatusY);

                    if (pSubstance + sPressureXY > 0) {
                        shouldScheduleFluidUpdate = false;
                        return null;
                    } else {
                        Aquifer.FluidStatus sFluidStatusZ = getAquiferStatus(sSeedZ);
                        double sSimXZ = similarity(sPosMaxX, sPosMaxZ);

                        if (sSimXZ > 0) {
                            double sPressureXZ = sSimXY * sSimXZ * calculatePressure(context, sNaN, sFluidStatusX, sFluidStatusZ);

                            if (pSubstance + sPressureXZ > 0) {
                                shouldScheduleFluidUpdate = false;
                                return null;
                            }
                        }

                        double sSimYZ = similarity(sPosMaxY, sPosMaxZ);

                        if (sSimYZ > 0) {
                            double sPressureYZ = sSimXY * sSimYZ * calculatePressure(context, sNaN, sFluidStatusY, sFluidStatusZ);

                            if (pSubstance + sPressureYZ > 0) {
                                shouldScheduleFluidUpdate = false;
                                return null;
                            }
                        }

                        shouldScheduleFluidUpdate = true;

                        return sResultState;
                    }
                }
            }
        }
    }
}
