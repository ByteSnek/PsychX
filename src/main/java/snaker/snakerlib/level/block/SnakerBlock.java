package snaker.snakerlib.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

/**
 * Created by SnakerBone on 27/06/2023
 **/
public class SnakerBlock extends Block
{
    public SnakerBlock(MapColor colour, Function<Properties, Properties> properties)
    {
        super(properties.apply(Properties.of().mapColor(colour)));
    }

    public SnakerBlock(MapColor colour, SoundType sound, float hardness, float resistance)
    {
        super(Properties.of().sound(sound).strength(hardness, resistance).mapColor(colour));
    }

    public SnakerBlock(MapColor colour, SoundType sound, float hardness, float resistance, boolean dummy)
    {
        super(Properties.of().sound(sound).strength(hardness, resistance).mapColor(colour).requiresCorrectToolForDrops());
    }
}
