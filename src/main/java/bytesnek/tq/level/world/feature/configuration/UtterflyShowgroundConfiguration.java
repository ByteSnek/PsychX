package bytesnek.tq.level.world.feature.configuration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import bytesnek.tq.level.world.feature.UtterflyShowgroundFeature;

/**
 * Created by SnakerBone on 15/11/2023
 **/
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class UtterflyShowgroundConfiguration implements FeatureConfiguration
{
    public static final Codec<UtterflyShowgroundConfiguration> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("invulnerable")
                            .orElse(false)
                            .forGetter(configuration -> configuration.invulnerable),
                    UtterflyShowgroundFeature.Tower.CODEC.listOf()
                            .fieldOf("towers")
                            .forGetter(configuration -> configuration.towers),
                    BlockPos.CODEC.optionalFieldOf("target")
                            .forGetter(configuration -> Optional.ofNullable(configuration.target))
            ).apply(instance, UtterflyShowgroundConfiguration::new)
    );

    private final boolean invulnerable;
    private final List<UtterflyShowgroundFeature.Tower> towers;
    private final BlockPos target;

    public UtterflyShowgroundConfiguration(boolean invulnerable, List<UtterflyShowgroundFeature.Tower> towers, @Nullable BlockPos target)
    {
        this.invulnerable = invulnerable;
        this.towers = towers;
        this.target = target;
    }

    private UtterflyShowgroundConfiguration(boolean invulnerable, List<UtterflyShowgroundFeature.Tower> towers, Optional<BlockPos> target)
    {
        this.invulnerable = invulnerable;
        this.towers = towers;
        this.target = target.orElse(null);
    }

    public boolean isInvulnerable()
    {
        return invulnerable;
    }

    public List<UtterflyShowgroundFeature.Tower> getTowers()
    {
        return towers;
    }

    public BlockPos getTarget()
    {
        return target;
    }
}
