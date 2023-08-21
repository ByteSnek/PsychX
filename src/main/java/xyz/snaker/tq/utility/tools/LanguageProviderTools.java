package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface LanguageProviderTools<T extends LanguageProvider>
{
    T getInstance();

    default void tab(RegistryObject<CreativeModeTab> tab)
    {
        String name = tab.getId().getPath();
        getInstance().add("itemGroup." + name, StringStuff.i18nt(name));
    }

    default <I extends EntityType<?>> void entity(RegistryObject<I> entity)
    {
        String name = entity.getId().getPath();
        if (name.equals("cosmo")) {
            getInstance().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
            getInstance().add("entity." + Tourniqueted.MODID + "." + "alpha_" + name, StringStuff.i18nt("alpha_" + name));
            return;
        }
        getInstance().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Item> void item(RegistryObject<I> item)
    {
        String name = item.getId().getPath();
        getInstance().add("item." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Block> void block(RegistryObject<I> block)
    {
        String name = block.getId().getPath();
        getInstance().add("block." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default void sound(RegistryObject<SoundEvent> sound)
    {
        String name = sound.getId().getPath();
        getInstance().add("sounds." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends MobEffect> void effect(RegistryObject<I> effect)
    {
        String name = effect.getId().getPath();
        getInstance().add("effect." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }
}
