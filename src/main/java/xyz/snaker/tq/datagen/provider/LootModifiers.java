package xyz.snaker.tq.datagen.provider;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.tools.GlobalLootModifierProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import static net.minecraft.world.level.block.Blocks.LARGE_FERN;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class LootModifiers extends GlobalLootModifierProvider implements GlobalLootModifierProviderTools<LootModifiers>
{
    public LootModifiers(PackOutput output)
    {
        super(output, Tourniqueted.MODID);
    }

    @Override
    public void start()
    {
        addTwineModifier(LARGE_FERN, Items.SATURATED_TWINE, 0.1);
    }

    @Override
    public LootModifiers getInstance()
    {
        return this;
    }
}
