package snaker.snakerlib.level.world;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by SnakerBone on 09/07/2023
 **/
public abstract class SnakerBiome
{
    protected transient float downfall = 0.30000000000000004F;
    protected transient float temp = 0.6F;

    protected abstract Biome make(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers);

    public record DirectHolder<T>(T value) implements Holder<T>
    {
        public boolean isBound()
        {
            return true;
        }

        public boolean is(@NotNull ResourceLocation location)
        {
            return false;
        }

        public boolean is(@NotNull ResourceKey<T> key)
        {
            return false;
        }

        public boolean is(@NotNull TagKey<T> key)
        {
            return false;
        }

        public boolean is(@NotNull Predicate<ResourceKey<T>> predicate)
        {
            return false;
        }

        public @NotNull Either<ResourceKey<T>, T> unwrap()
        {
            return Either.right(value);
        }

        public @NotNull Optional<ResourceKey<T>> unwrapKey()
        {
            return Optional.empty();
        }

        public Holder.@NotNull Kind kind()
        {
            return Holder.Kind.DIRECT;
        }

        public String toString()
        {
            return String.valueOf(value);
        }

        public boolean canSerializeIn(@NotNull HolderOwner<T> owner)
        {
            return true;
        }

        public @NotNull Stream<TagKey<T>> tags()
        {
            return Stream.of();
        }

        public @NotNull T value()
        {
            return value;
        }

    }
}
