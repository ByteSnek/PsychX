package xyz.snaker.tq.datagen.provider;

import java.util.Locale;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.*;
import xyz.snaker.tq.utility.tools.LanguageProviderTools;

import net.minecraft.data.PackOutput;
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
        for (var obj : Blocks.REGISTRAR.getEntries()) {
            block(obj.get());
        }
        for (var obj : Items.REGISTRAR.getEntries()) {
            item(obj.get());
        }
        for (var obj : Effects.REGISTRAR.getEntries()) {
            effect(obj.get());
        }
        for (var obj : Entities.REGISTRAR.getEntries()) {
            entity(obj.get());
        }
        for (var obj : Sounds.REGISTRAR.getEntries()) {
            sound(obj.get());
        }
        for (var obj : Tabs.REGISTRAR.getEntries()) {
            tab(obj);
        }

        add("tooltip.tq.possible_issue", "If you're a player and you're seeing this then something with the mod may have gone wrong. Please go report this to github.com/SnakerBone/Tourniqueted/issues");
        add("tooltip.tq.debug_tool_mode.none", "None");
        add("tooltip.tq.debug_tool_mode.biome_tp", "Biome Teleport");
    }

    @Override
    public Languages getInstance()
    {
        return this;
    }
}
