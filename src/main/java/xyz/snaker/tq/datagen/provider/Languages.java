package xyz.snaker.tq.datagen.provider;

import java.util.Locale;

import xyz.snaker.snakerlib.utility.tools.CollectionStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.tools.LanguageProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class Languages extends LanguageProvider implements LanguageProviderTools<Languages>
{
    public Languages(PackOutput output)
    {
        super(output, Tourniqueted.MODID, Locale.US.toString().toLowerCase());
    }

    @Override
    public void addTranslations()
    {
        CollectionStuff.mapDeferredRegistries(Blocks.REGISTRAR, Block[]::new).forEach(this::block);
        CollectionStuff.mapDeferredRegistries(Items.REGISTRAR, Item[]::new).forEach(this::item);
        CollectionStuff.mapDeferredRegistries(Entities.REGISTRAR, EntityType<?>[]::new).forEach(this::entity);
        CollectionStuff.mapDeferredRegistries(Sounds.REGISTRAR, SoundEvent[]::new).forEach(this::sound);
        CollectionStuff.mapDeferredRegistries(Tabs.REGISTRAR, CreativeModeTab[]::new).forEach(this::tab);
    }

    @Override
    public Languages getInstance()
    {
        return this;
    }
}
