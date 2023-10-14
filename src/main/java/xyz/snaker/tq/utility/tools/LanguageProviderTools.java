package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.ResourceStuff;
import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.tq.Tourniqueted;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface LanguageProviderTools<T extends LanguageProvider>
{
    T getInstance();

    default void tab(CreativeModeTab tab)
    {
        String name = tab.getDisplayName().getString();
        getInstance().add(name, StringStuff.i18nt(name.substring(name.indexOf('.') + 1)));
    }

    default <I extends EntityType<?>> void entity(I entity)
    {
        String name = ResourceStuff.getPath(entity);
        if (name.equals("cosmo")) {
            getInstance().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
            getInstance().add("entity." + Tourniqueted.MODID + "." + "alpha_" + name, StringStuff.i18nt("alpha_" + name));
            return;
        }
        getInstance().add("entity." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Item> void item(I item)
    {
        String name = ResourceStuff.getPath(item);
        if (name.equals("atlas")) {
            name = Tourniqueted.NAME.toLowerCase() + "_" + name;
        }
        getInstance().add("item." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends Block> void block(I block)
    {
        String name = ResourceStuff.getPath(block);
        getInstance().add("block." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default void sound(SoundEvent sound)
    {
        String name = ResourceStuff.getPath(sound);
        getInstance().add("sounds." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }

    default <I extends MobEffect> void effect(I effect)
    {
        String name = ResourceStuff.getPath(effect);
        getInstance().add("effect." + Tourniqueted.MODID + "." + name, StringStuff.i18nt(name));
    }
}
