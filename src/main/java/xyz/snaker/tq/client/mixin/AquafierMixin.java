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
    protected abstract int getIndex(int gridX, int gridY, int gridZ);

    @Shadow
    @Final
    protected long[] aquiferLocationCache;

    @Shadow
    @Final
    private PositionalRandomFactory positionalRandomFactory;

    @Shadow
    protected static double similarity(int firstFluid, int secondFluid)
    {
        return 1 - Math.abs(secondFluid - firstFluid) / 25D;
    }

    @Shadow
    protected abstract Aquifer.FluidStatus getAquiferStatus(long packedPos);

    @Shadow
    @Final
    private static double FLOWING_UPDATE_SIMULARITY;

    @Shadow
    protected abstract double calculatePressure(DensityFunction.FunctionContext context, MutableDouble substance, Aquifer.FluidStatus firstFluid, Aquifer.FluidStatus secondFluid);

    @Overwrite
    public @Nullable BlockState computeSubstance(DensityFunction.FunctionContext pContext, double pSubstance)
    {
        int i = pContext.blockX();
        int j = pContext.blockY();
        int k = pContext.blockZ();

        Optional<Level> level = Optional.empty();

        if (FMLEnvironment.dist.isClient()) {
            level = Optional.ofNullable(Minecraft.getInstance().level);
        }

        if (level.isPresent()) {
            if (level.get().dimension() == Levels.COMATOSE) {
                defaultFluidState = COMASOTE.get().defaultBlockState();
            }
        }

        if (pSubstance > 0.0D) {
            this.shouldScheduleFluidUpdate = false;
            return null;
        } else {
            Aquifer.FluidStatus aquifer$fluidstatus = this.globalFluidPicker.computeFluid(i, j, k);
            if (aquifer$fluidstatus.at(j).is(Blocks.LAVA)) {
                this.shouldScheduleFluidUpdate = false;
                return defaultFluidState;
            } else {
                int l = Math.floorDiv(i - 5, 16);
                int i1 = Math.floorDiv(j + 1, 12);
                int j1 = Math.floorDiv(k - 5, 16);
                int k1 = Integer.MAX_VALUE;
                int l1 = Integer.MAX_VALUE;
                int i2 = Integer.MAX_VALUE;
                long j2 = 0L;
                long k2 = 0L;
                long l2 = 0L;

                for (int i3 = 0; i3 <= 1; ++i3) {
                    for (int j3 = -1; j3 <= 1; ++j3) {
                        for (int k3 = 0; k3 <= 1; ++k3) {
                            int l3 = l + i3;
                            int i4 = i1 + j3;
                            int j4 = j1 + k3;
                            int k4 = this.getIndex(l3, i4, j4);
                            long i5 = this.aquiferLocationCache[k4];
                            long l4;
                            if (i5 != Long.MAX_VALUE) {
                                l4 = i5;
                            } else {
                                RandomSource randomsource = this.positionalRandomFactory.at(l3, i4, j4);
                                l4 = BlockPos.asLong(l3 * 16 + randomsource.nextInt(10), i4 * 12 + randomsource.nextInt(9), j4 * 16 + randomsource.nextInt(10));
                                this.aquiferLocationCache[k4] = l4;
                            }

                            int i6 = BlockPos.getX(l4) - i;
                            int j5 = BlockPos.getY(l4) - j;
                            int k5 = BlockPos.getZ(l4) - k;
                            int l5 = i6 * i6 + j5 * j5 + k5 * k5;
                            if (k1 >= l5) {
                                l2 = k2;
                                k2 = j2;
                                j2 = l4;
                                i2 = l1;
                                l1 = k1;
                                k1 = l5;
                            } else if (l1 >= l5) {
                                l2 = k2;
                                k2 = l4;
                                i2 = l1;
                                l1 = l5;
                            } else if (i2 >= l5) {
                                l2 = l4;
                                i2 = l5;
                            }
                        }
                    }
                }

                Aquifer.FluidStatus aquifer$fluidstatus1 = this.getAquiferStatus(j2);
                double d1 = similarity(k1, l1);
                BlockState blockstate = aquifer$fluidstatus1.at(j);

                if (d1 <= 0.0D) {
                    this.shouldScheduleFluidUpdate = d1 >= FLOWING_UPDATE_SIMULARITY;
                    return blockstate;
                } else if (blockstate.is(Blocks.WATER) && this.globalFluidPicker.computeFluid(i, j - 1, k).at(j - 1).is(Blocks.LAVA)) {
                    this.shouldScheduleFluidUpdate = true;
                    return blockstate;
                } else {
                    MutableDouble mutabledouble = new MutableDouble(Double.NaN);
                    Aquifer.FluidStatus aquifer$fluidstatus2 = this.getAquiferStatus(k2);
                    double d2 = d1 * this.calculatePressure(pContext, mutabledouble, aquifer$fluidstatus1, aquifer$fluidstatus2);

                    if (pSubstance + d2 > 0.0D) {
                        this.shouldScheduleFluidUpdate = false;
                        return null;
                    } else {
                        Aquifer.FluidStatus aquifer$fluidstatus3 = this.getAquiferStatus(l2);
                        double d0 = similarity(k1, i2);
                        if (d0 > 0.0D) {
                            double d3 = d1 * d0 * this.calculatePressure(pContext, mutabledouble, aquifer$fluidstatus1, aquifer$fluidstatus3);
                            if (pSubstance + d3 > 0.0D) {
                                this.shouldScheduleFluidUpdate = false;
                                return null;
                            }
                        }

                        double d4 = similarity(l1, i2);
                        if (d4 > 0.0D) {
                            double d5 = d1 * d4 * this.calculatePressure(pContext, mutabledouble, aquifer$fluidstatus2, aquifer$fluidstatus3);
                            if (pSubstance + d5 > 0.0D) {
                                this.shouldScheduleFluidUpdate = false;
                                return null;
                            }
                        }

                        this.shouldScheduleFluidUpdate = true;
                        return blockstate;
                    }
                }
            }
        }
    }
}
